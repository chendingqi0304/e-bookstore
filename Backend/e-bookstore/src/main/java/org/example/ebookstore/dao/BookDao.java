package org.example.ebookstore.dao;

import org.example.ebookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookDao {
    Page<Book> selectAll(Pageable pageable);

    Page<Book> selectUndeleted(Pageable pageable);
    void insert(Book book);

    Book getBookById(int bookId);

    void deleteBookByBookId(Integer bookId);

    void recoverBookByBookId(Integer bookId);

    Page<Book> searchByTitle(String title,Pageable pageable);
}
