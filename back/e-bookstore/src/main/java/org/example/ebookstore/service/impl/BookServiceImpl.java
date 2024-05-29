package org.example.ebookstore.service.impl;

import org.example.ebookstore.dao.BookDao;
import org.example.ebookstore.entity.Book;
import org.example.ebookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{
    @Autowired
    private BookDao bookDao;

    @Override
    public List<Book> getAllBooks() {
        return bookDao.selectAll();
    }

    @Override
    public void addBook(Book book) {
        bookDao.insert(book);
    }

    @Override
    public Book getBookById(int id) {
        return bookDao.getBookById(id);
    }
}
