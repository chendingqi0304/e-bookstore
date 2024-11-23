package org.example.ebookstore.service;

import org.example.ebookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    Page<Book> getAllBooks(Pageable pageable);

    void addBook(Book book);

    Book getBookById(int id);

    void deleteBookByBookId(Integer bookId);

    Page<Book> getBooks(Pageable pageable);

    void recoverBookByBookId(Integer bookId);

    Page<Book> searchByTitle(String title,Pageable pageable);

    Page<Book> searchRetatedTag(String tag,Pageable pageable);
}
