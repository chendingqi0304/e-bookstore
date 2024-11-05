package org.example.book_author.repository;

import jakarta.transaction.Transactional;
import org.example.book_author.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("SELECT o.author FROM Book o where o.title =:title")
    String getBookAuthor(String title);
}