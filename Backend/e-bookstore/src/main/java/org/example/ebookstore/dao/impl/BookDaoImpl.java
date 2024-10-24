package org.example.ebookstore.dao.impl;

import org.example.ebookstore.dao.BookDao;
import org.example.ebookstore.entity.Book;
import org.example.ebookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import com.alibaba.fastjson2.JSON;

import java.nio.ByteBuffer;
import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Page<Book> selectAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Page<Book> selectUndeleted(Pageable pageable) {
        return bookRepository.findByDeleted(false,pageable);
    }

    @Override
    public void insert(Book book) {
        System.out.println("Add book to DB:");
        bookRepository.saveAndFlush(book);
        int bookId=book.getBookId();
        redisTemplate.opsForValue().set("book"+bookId,JSON.toJSONString(book));
        System.out.println("Add Book :"+bookId+" to Redis");
    }

    @Override
    public Book getBookById(int bookId) {
        System.out.println("Get book by id:"+bookId);
        Book book=null;
        System.out.println("Searching Book: " + bookId + " in Redis");

        String p = (String)redisTemplate.opsForValue().get("book" + bookId);

        if (p == null) {
            System.out.println("Book: " + bookId + " is not in Redis");
            book =bookRepository.findByBookId(bookId);
            redisTemplate.opsForValue().set("book" + bookId, JSON.toJSONString(book));
        } else {
            book = JSON.parseObject(p, Book.class);
            System.out.println("Book: " + bookId + " is in Redis");
        }
        return book;
    }

    @Override
    public void deleteBookByBookId(Integer bookId) {
        System.out.println("Delete book by id:"+bookId);
        bookRepository.removeBook(bookId);
        Book book=null;
        System.out.println("Searching Book: " + bookId + " in Redis");

        String p = (String)redisTemplate.opsForValue().get("book" + bookId);
        if (p == null) {
            System.out.println("Book: " + bookId + " is not in Redis");
            System.out.println("Searching Book: " + bookId + " in DB");
            book =bookRepository.findByBookId(bookId);
        } else {
            book = JSON.parseObject(p, Book.class);
            book.setDeleted(true);
        }
        redisTemplate.opsForValue().set("book" + bookId, JSON.toJSONString(book));
    }

    @Override
    public void recoverBookByBookId(Integer bookId){
        System.out.println("Recover book by id:"+bookId);
        bookRepository.recoverBook(bookId);
        Book book=null;
        System.out.println("Searching Book: " + bookId + " in Redis");

        String p = (String)redisTemplate.opsForValue().get("book" + bookId);
        if (p == null) {
            System.out.println("Book: " + bookId + " is not in Redis");
            System.out.println("Searching Book: " + bookId + " in DB");
            book =bookRepository.findByBookId(bookId);
        } else {
            book = JSON.parseObject(p, Book.class);
            book.setDeleted(false);
        }
        redisTemplate.opsForValue().set("book" + bookId, JSON.toJSONString(book));
    }

    @Override
    public Page<Book> searchByTitle(String title,Pageable pageable){
        return bookRepository.findByTitleContaining(title,pageable);
    }
}
