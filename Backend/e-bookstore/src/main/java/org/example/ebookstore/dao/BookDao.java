package org.example.ebookstore.dao;

import org.example.ebookstore.entity.Book;

import java.util.List;

public interface BookDao {
    List<Book> selectAll();

    List<Book> selectUndeleted();
    void insert(Book book);

    Book getBookById(int bookId);

    void deleteBookByBookId(Integer bookId);

    void recoverBookByBookId(Integer bookId);

    List<Book> searchByTitle(String title);
}
