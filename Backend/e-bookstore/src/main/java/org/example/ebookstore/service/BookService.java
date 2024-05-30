package org.example.ebookstore.service;

import org.example.ebookstore.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();

    void addBook(Book book);

    Book getBookById(int id);

    void deleteBookByBookId(Integer bookId);

    List<Book> getBooks();

    void recoverBookByBookId(Integer bookId);

    List<Book> searchByTitle(String title);
}
