package org.example.book_author.service.impl;

import org.example.book_author.dao.BookDao;
import org.example.book_author.entity.Book;
import org.example.book_author.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService{
    @Autowired
    private BookDao bookDao;

    @Override
    public String getBookAuthor(String title){
        return bookDao.getBookAuthor(title);
    }
}
