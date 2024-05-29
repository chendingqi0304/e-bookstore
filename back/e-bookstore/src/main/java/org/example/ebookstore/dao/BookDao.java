package org.example.ebookstore.dao;

import org.example.ebookstore.entity.Book;

import java.util.List;

public interface BookDao {
    List<Book> selectAll();
    void insert(Book book);
    Book getBookById(int bookId);
}
