package org.example.ebookstore.repository;

import org.example.ebookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findByBookId(int bookId);
}