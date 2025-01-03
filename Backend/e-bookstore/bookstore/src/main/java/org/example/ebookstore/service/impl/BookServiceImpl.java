package org.example.ebookstore.service.impl;

import org.example.ebookstore.dao.BookDao;
import org.example.ebookstore.entity.Book;
import org.example.ebookstore.entity.BookTag;
import org.example.ebookstore.service.BookService;
import org.example.ebookstore.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService{
    @Autowired
    private BookDao bookDao;
    @Autowired
    private ClientService clientService;

    @Override
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookDao.selectAll(pageable);
    }

    @Override
    public Page<Book> getBooks(Pageable pageable){
        return bookDao.selectUndeleted(pageable);
    }

    @Override
    public void addBook(Book book) {
        bookDao.insert(book);
    }

    @Override
    public Book getBookById(int id) {
        return bookDao.getBookById(id);
    }

    @Override
    public void deleteBookByBookId(Integer bookId){
        bookDao.deleteBookByBookId(bookId);
    }

    @Override
    public void recoverBookByBookId(Integer bookId){
        bookDao.recoverBookByBookId(bookId);
    }
    @Override
    public Page<Book> searchByTitle(String title,Pageable pageable){
        return bookDao.searchByTitle(title,pageable);
    }

    @Override
    public Page<Book> searchRetatedTag(String tag,Pageable pageable){
        List<BookTag> bookTags=clientService.getRelatedTags(tag);
        List<String> tags=bookTags.stream().map(BookTag::getName).collect(Collectors.toList());
        return bookDao.searchByTagList(tags,pageable);
    }
}
