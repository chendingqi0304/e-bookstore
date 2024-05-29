package org.example.ebookstore.controller;


import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.ebookstore.entity.Book;
import org.example.ebookstore.entity.Result;
import org.example.ebookstore.service.BookService;
import org.example.ebookstore.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController

public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/booklist")
    public Result getBooklist() {
        HttpSession session = SessionUtils.getSession();
        log.info("sessionID: {}", session.getId());
        Integer userId = (Integer) session.getAttribute("userId");
        log.info("userId:{} get booklist",userId);
        return Result.success(bookService.getAllBooks());
    }

    @PostMapping("/addbook")
    public Result addBook(@RequestParam("bookname") String bookname, @RequestParam("price") Integer price, @RequestParam("author") String author, @RequestParam("introduction") String introduction, @RequestParam("picture") MultipartFile picture)throws IOException {
        log.info("addbook");
        Book book = new Book();
        byte[] fileBytes = null;
        fileBytes =picture.getBytes();
        book.setTitle(bookname);
        book.setPrice(price);
        book.setAuthor(author);
        book.setIntroduction(introduction);
        book.setPicture(fileBytes);
        bookService.addBook(book);
        return Result.success(book);
    }

    @PostMapping("/getBook")
    public Result getBook(@RequestParam("bookId") Integer bookId) {
        log.info("getBook{}",bookId);
        return Result.success(bookService.getBookById(bookId));
    }
}
