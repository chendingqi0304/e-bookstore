package org.example.ebookstore.dao.impl;

import org.example.ebookstore.dao.BookDao;
import org.example.ebookstore.entity.Book;
import org.example.ebookstore.entity.BookIcon;
import org.example.ebookstore.repository.BookIconRepository;
import org.example.ebookstore.repository.BookRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookDaoImpl implements BookDao {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookIconRepository bookIconRepository;


    @Override
    public Page<Book> selectAll(Pageable pageable) {
        Page<Book> booklist = bookRepository.findAll(pageable);

        for (Book book : booklist.getContent()) {
            Optional<BookIcon> icon = bookIconRepository.findById(book.getBookId());
            if (icon.isPresent()) {
                book.setBookIcon(icon.get());
            } else {
                book.setBookIcon(null);
            }
        }
        return booklist;
    }

    @Override
    public Page<Book> selectUndeleted(Pageable pageable) {
        Page<Book> booklist= bookRepository.findByDeleted(false, pageable);
        for (Book book : booklist.getContent()) {
            Optional<BookIcon> icon = bookIconRepository.findById(book.getBookId());
            if (icon.isPresent()) {
                book.setBookIcon(icon.get());
            } else {
                book.setBookIcon(null);
            }
        }
        return booklist;
    }

    @Override
    public void insert(Book book) {
        bookRepository.saveAndFlush(book);
        if (book.getBookIcon() != null) {
            book.getBookIcon().setId(book.getBookId());
            bookIconRepository.save(book.getBookIcon());
        }
    }

    @Override
    public Book getBookById(int bookId) {
        Book book = bookRepository.findByBookId(bookId);
        Optional<BookIcon> icon = bookIconRepository.findById(bookId);
        if (icon.isPresent()) {
            System.out.println("found");
            book.setBookIcon(icon.get());
        } else {
            System.out.println("not found");
            book.setBookIcon(null);
        }
        return book;
    }

    @Override
    public void deleteBookByBookId(Integer bookId) {
        bookRepository.removeBook(bookId);
    }

    @Override
    public void recoverBookByBookId(Integer bookId) {
        bookRepository.recoverBook(bookId);
    }

    @Override
    public Page<Book> searchByTitle(String title, Pageable pageable) {
        Page<Book> booklist= bookRepository.findByTitleContaining(title, pageable);
        for (Book book : booklist.getContent()) {
            Optional<BookIcon> icon = bookIconRepository.findById(book.getBookId());
            if (icon.isPresent()) {
                book.setBookIcon(icon.get());
            } else {
                book.setBookIcon(null);
            }
        }
        return booklist;
    }

    @Override
    public Page<Book>searchByTagList(List<String> tag, Pageable pageable){
        Page<Book> booklist= bookRepository.findBooksByTags(tag,pageable);
        for (Book book : booklist.getContent()) {
            System.out.println(book.getBookTags());

            Optional<BookIcon> icon = bookIconRepository.findById(book.getBookId());
            if (icon.isPresent()) {
                book.setBookIcon(icon.get());
            } else {
                book.setBookIcon(null);
            }
        }
        return booklist;
    }
}
