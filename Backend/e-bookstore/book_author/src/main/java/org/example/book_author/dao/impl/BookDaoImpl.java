package org.example.book_author.dao.impl;


import org.example.book_author.dao.BookDao;

import org.example.book_author.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookDaoImpl implements BookDao {
    @Autowired
    private BookRepository bookRepository;


    @Override
    public String getBookAuthor(String title){
        return bookRepository.getBookAuthor(title);
    }
}
