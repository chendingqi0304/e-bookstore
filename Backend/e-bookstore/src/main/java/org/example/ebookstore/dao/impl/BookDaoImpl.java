package org.example.ebookstore.dao.impl;

import org.example.ebookstore.dao.BookDao;
import org.example.ebookstore.entity.Book;
import org.example.ebookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {
    @Autowired
    private BookRepository bookRepository;

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
        bookRepository.save(book);
    }

    @Override
    public Book getBookById(int bookId) {
        return bookRepository.findByBookId(bookId);
    }

    @Override
    public void deleteBookByBookId(Integer bookId) {
        bookRepository.removeBook(bookId);
    }

    @Override
    public void recoverBookByBookId(Integer bookId){
        bookRepository.recoverBook(bookId);
    }

    @Override
    public Page<Book> searchByTitle(String title,Pageable pageable){
        return bookRepository.findByTitleContaining(title,pageable);
    }
}
