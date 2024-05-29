package org.example.ebookstore.dao.impl;

import org.example.ebookstore.dao.BookDao;
import org.example.ebookstore.entity.Book;
import org.example.ebookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class BookDaoImpl implements BookDao {
    @Autowired
    private BookRepository bookRepository;
    @Override
    public List<Book> selectAll() {
        return bookRepository.findAll();
    }
    @Override
    public void insert(Book book){
        bookRepository.save(book);
    }
    @Override
    public Book getBookById(int bookId){
        return bookRepository.findByBookId(bookId);
    }
}
