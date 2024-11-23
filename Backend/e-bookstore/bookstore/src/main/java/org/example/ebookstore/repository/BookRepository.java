package org.example.ebookstore.repository;

import jakarta.transaction.Transactional;
import org.example.ebookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Book findByBookId(int bookId);

    Page<Book> findByDeleted(boolean b, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Book o SET o.deleted=true WHERE o.bookId=:bookId")
    void removeBook(Integer bookId);

    @Modifying
    @Transactional
    @Query("UPDATE Book o SET o.deleted=false WHERE o.bookId=:bookId")
    void recoverBook(Integer bookId);

    List<Book> findByTitleContaining(String title);

    Page<Book> findByTitleContaining(String title, Pageable pageable);

    @Query("SELECT DISTINCT b FROM Book b " +
            "JOIN FETCH b.bookTags bt " +//FETCH使用强加载
            "WHERE bt.name IN :tagNames")
    Page<Book> findBooksByTags(List<String> tagNames, Pageable pageable);
}