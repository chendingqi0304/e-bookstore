package org.example.ebookstore.repository;

import jakarta.transaction.Transactional;
import org.example.ebookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findByBookId(int bookId);

    List<Book> findByDeleted(boolean b);
    @Modifying
    @Transactional
    @Query("UPDATE Book o SET o.deleted=true WHERE o.bookId=:bookId")
    void removeBook(Integer bookId);

    @Modifying
    @Transactional
    @Query("UPDATE Book o SET o.deleted=false WHERE o.bookId=:bookId")
    void recoverBook(Integer bookId);

    List<Book> findByTitleContaining(String title);
}