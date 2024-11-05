package org.example.book_author.dao;

import org.example.book_author.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookDao {
    String getBookAuthor(String title);
}
