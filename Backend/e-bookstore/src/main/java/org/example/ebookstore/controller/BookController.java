package org.example.ebookstore.controller;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.ebookstore.entity.Book;
import org.example.ebookstore.entity.Result;
import org.example.ebookstore.entity.User;
import org.example.ebookstore.service.BookService;
import org.example.ebookstore.service.UserService;
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
    @Autowired
    private UserService userService;

    @PostMapping("/Allbooklist")
    public Result getAllBooklist() {
        HttpSession session = SessionUtils.getSession();
        log.info("sessionID: {}", session.getId());
        Integer userId = (Integer) session.getAttribute("userId");
        log.info("userId:{} get booklist", userId);
        return Result.success(bookService.getAllBooks());
    }

    @PostMapping("/booklist")
    public Result getBooklist() {
        HttpSession session = SessionUtils.getSession();
        log.info("sessionID: {}", session.getId());
        Integer userId = (Integer) session.getAttribute("userId");
        log.info("userId:{} get booklist", userId);
        return Result.success(bookService.getBooks());
    }

    @PostMapping("/addbook")
    public Result addBook(@RequestParam("bookname") String bookname, @RequestParam("price") Integer price, @RequestParam("originprice") Integer originprice, @RequestParam("author") String author, @RequestParam("introduction") String introduction, @RequestParam("rest") Integer rest, @RequestParam("picture") MultipartFile picture, @RequestParam("isbn") String isbn) throws IOException {
        HttpSession session = SessionUtils.getSession();
        log.info("sessionID: {}", session.getId());
        Integer userId = (Integer) session.getAttribute("userId");
        User user = userService.findUserByUserId(userId);
        if(user == null||user.getType()!=1) {
            return Result.error("无权限");
        }
        log.info("addbook");
        Book book = new Book();
        byte[] fileBytes = null;
        fileBytes = picture.getBytes();
        book.setTitle(bookname);
        book.setPrice(price);
        book.setOriginprice(originprice);
        book.setAuthor(author);
        book.setIntroduction(introduction);
        book.setPicture(fileBytes);
        book.setType(picture.getContentType());
        book.setRest(rest);
        book.setIsbn(isbn);
        bookService.addBook(book);
        return Result.success(book);
    }

    @PostMapping("/getBook")
    public Result getBook(@RequestParam("bookId") Integer bookId) {
        log.info("getBook{}", bookId);
        return Result.success(bookService.getBookById(bookId));
    }

    @PostMapping("/deleteBook")
    public Result deleteBook(@RequestParam("bookId") Integer bookId) {
        HttpSession session = SessionUtils.getSession();
        log.info("sessionID: {}", session.getId());
        Integer userId = (Integer) session.getAttribute("userId");
        log.info("userId:{} get booklist", userId);
        User user = userService.findUserByUserId(userId);
        if (user == null || user.getType() != 1) {
            return Result.error("非管理员账户或该账号不存在");
        } else {
            Book book = bookService.getBookById(bookId);
            if (book == null) {
                return Result.error("不存在的BookId");
            }
            bookService.deleteBookByBookId(bookId);
            return Result.success();
        }
    }

    @PostMapping("/recoverBook")
    public Result recoverBook(@RequestParam("bookId") Integer bookId) {
        HttpSession session = SessionUtils.getSession();
        log.info("sessionID: {}", session.getId());
        Integer userId = (Integer) session.getAttribute("userId");
        log.info("userId:{} get booklist", userId);
        User user = userService.findUserByUserId(userId);
        if (user == null || user.getType() != 1) {
            return Result.error("非管理员账户或该账号不存在");
        } else {
            Book book = bookService.getBookById(bookId);
            if (book == null) {
                return Result.error("不存在的BookId");
            }
            bookService.recoverBookByBookId(bookId);
            return Result.success();
        }
    }

    @PostMapping("/editBook")
    public Result editBook(@RequestParam("bookId") Integer bookId, @RequestParam("bookname") String bookname, @RequestParam("price") Integer price, @RequestParam("originprice") Integer originprice, @RequestParam("author") String author, @RequestParam("introduction") String introduction, @RequestParam("rest") Integer rest, @RequestParam(value = "picture", required = false) MultipartFile picture, @RequestParam("isbn") String isbn) throws IOException {
        HttpSession session = SessionUtils.getSession();
        log.info("sessionID: {}", session.getId());
        Integer userId = (Integer) session.getAttribute("userId");
        log.info("userId:{} get booklist", userId);
        User user = userService.findUserByUserId(userId);
        if (user == null || user.getType() != 1) {
            return Result.error("非管理员账户或该账号不存在");
        } else {
            Book book = bookService.getBookById(bookId);
            if (book == null) {
                return Result.error("不存在的BookId");
            }
            Book newbook = new Book();
            newbook.setBookId(bookId);
            newbook.setTitle(bookname);
            newbook.setPrice(price);
            newbook.setOriginprice(originprice);
            newbook.setAuthor(author);
            newbook.setIntroduction(introduction);
            newbook.setRest(rest);
            newbook.setIsbn(isbn);
            if (picture != null) {
                byte[] fileBytes = null;
                fileBytes = picture.getBytes();
                newbook.setPicture(fileBytes);
                newbook.setType(picture.getContentType());
            } else {
                newbook.setPicture(book.getPicture());
                newbook.setType(book.getType());
            }

            bookService.addBook(newbook);
            return Result.success();
        }
    }
    @PostMapping("/searchByTitle")
    public Result searchByTitle(@RequestParam("title") String title) {
        return Result.success(bookService.searchByTitle(title));
    }
}
