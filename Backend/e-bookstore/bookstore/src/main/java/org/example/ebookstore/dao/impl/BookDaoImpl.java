package org.example.ebookstore.dao.impl;

import org.example.ebookstore.dao.BookDao;
import org.example.ebookstore.entity.Book;
import org.example.ebookstore.entity.BookIcon;
import org.example.ebookstore.repository.BookIconRepository;
import org.example.ebookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import com.alibaba.fastjson2.JSON;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;

@Repository
public class BookDaoImpl implements BookDao {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookIconRepository bookIconRepository;
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
        bookRepository.saveAndFlush(book);
        book.getBookIcon().setId(book.getBookId());
        bookIconRepository.save(book.getBookIcon());
        int bookId=book.getBookId();
        try {
            redisTemplate.opsForValue().set("book"+bookId,JSON.toJSONString(book));
        }catch (Exception e){}

    }

    @Override
    public Book getBookById(int bookId) {
        Book book=null;

        String p=null;
        try {
            //p = (String) redisTemplate.opsForValue().get("book" + bookId);
        }
        catch (Exception e){}
        if (p == null) {
            book =bookRepository.findByBookId(bookId);
            Optional<BookIcon> icon=bookIconRepository.findById(bookId);
            if (icon.isPresent()) {
                System.out.println("found");
                book.setBookIcon(icon.get());
            }else {
                System.out.println("not found");
                book.setBookIcon(null);
            }
            try {
                redisTemplate.opsForValue().set("book" + bookId, JSON.toJSONString(book));
            }catch (Exception e){}

        } else {
            book = JSON.parseObject(p, Book.class);
        }
        return book;
    }

    @Override
    public void deleteBookByBookId(Integer bookId) {

        bookRepository.removeBook(bookId);
        Book book=null;
        String p=null;
        try {
            p = (String)redisTemplate.opsForValue().get("book" + bookId);
        }catch (Exception e){}

        if (p == null) {
            book =bookRepository.findByBookId(bookId);
            Optional<BookIcon> icon=bookIconRepository.findById(bookId);
            if (icon.isPresent()) {
                book.setBookIcon(icon.get());
            }else {
                book.setBookIcon(null);
            }
        } else {
            book = JSON.parseObject(p, Book.class);
            book.setDeleted(true);
        }
        try {
            redisTemplate.opsForValue().set("book" + bookId, JSON.toJSONString(book));
        }catch (Exception e){}

    }

    @Override
    public void recoverBookByBookId(Integer bookId){
        bookRepository.recoverBook(bookId);
        Book book=null;
        String p=null;
        try {
            p = (String)redisTemplate.opsForValue().get("book" + bookId);
        }catch (Exception e){}

        if (p == null) {
            book =bookRepository.findByBookId(bookId);
            Optional<BookIcon> icon=bookIconRepository.findById(bookId);
            if (icon.isPresent()) {
                book.setBookIcon(icon.get());
            }else {
                book.setBookIcon(null);
            }
        } else {
            book = JSON.parseObject(p, Book.class);
            book.setDeleted(false);
        }
        try {
            redisTemplate.opsForValue().set("book" + bookId, JSON.toJSONString(book));
        }catch (Exception e){}

    }

    @Override
    public Page<Book> searchByTitle(String title,Pageable pageable){
        return bookRepository.findByTitleContaining(title,pageable);
    }
}
