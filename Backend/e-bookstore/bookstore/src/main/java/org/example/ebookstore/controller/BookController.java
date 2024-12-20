package org.example.ebookstore.controller;


import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.ebookstore.entity.Book;
import org.example.ebookstore.entity.BookIcon;
import org.example.ebookstore.entity.Result;
import org.example.ebookstore.entity.User;
import org.example.ebookstore.service.BookService;
import org.example.ebookstore.service.UserService;
import org.example.ebookstore.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Slf4j
@RestController
@Controller
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;



    @PostMapping("/Allbooklist")
    public Result getAllBooklist(@RequestParam("index") int pageIndex, @RequestParam("size") int pageSize) {
        HttpSession session = SessionUtils.getSession();
        log.info("sessionID: {}", session.getId());
        Integer userId = (Integer) session.getAttribute("userId");
        log.info("userId:{} get booklist", userId);
        Pageable bookpage = PageRequest.of(pageIndex, pageSize);
        return Result.success(bookService.getAllBooks(bookpage));
    }

    @PostMapping("/booklist")
    public Result getBooklist(@RequestParam("index") int pageIndex, @RequestParam("size") int pageSize) {
        HttpSession session = SessionUtils.getSession();
        log.info("sessionID: {}", session.getId());
        Integer userId = (Integer) session.getAttribute("userId");
        log.info("userId:{} get booklist", userId);
        Pageable bookpage = PageRequest.of(pageIndex, pageSize);
        return Result.success(bookService.getBooks(bookpage));
    }

    @PostMapping("/addbook")
    public Result addBook(@RequestParam("bookname") String bookname, @RequestParam("price") Integer price, @RequestParam("originprice") Integer originprice, @RequestParam("author") String author, @RequestParam("introduction") String introduction, @RequestParam("rest") Integer rest, @RequestParam("picture") MultipartFile picture, @RequestParam("isbn") String isbn) throws IOException {
        HttpSession session = SessionUtils.getSession();
        log.info("sessionID: {}", session.getId());
        Integer userId = (Integer) session.getAttribute("userId");
        User user = userService.findUserByUserId(userId);
        if (user == null || user.getType() != 1) {
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
        byte[] imageBytes = picture.getBytes();
        String base64 = Base64.getEncoder().encodeToString(imageBytes);
        BookIcon bookIcon = new BookIcon(0, picture.getContentType(), base64);
        book.setBookIcon(bookIcon);
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
                byte[] imageBytes = picture.getBytes();
                String base64 = Base64.getEncoder().encodeToString(imageBytes);
                BookIcon bookIcon = new BookIcon(bookId, picture.getContentType(), base64);
                newbook.setBookIcon(bookIcon);
            } else {
                newbook.setBookIcon(book.getBookIcon());
            }

            bookService.addBook(newbook);
            return Result.success();
        }
    }

    @PostMapping("/searchByTitle")
    public Result searchByTitle(@RequestParam("title") String title, @RequestParam("index") int pageIndex, @RequestParam("size") int pageSize) {

        Pageable bookpage = PageRequest.of(pageIndex, pageSize);
        return Result.success(bookService.searchByTitle(title, bookpage));
    }

    @QueryMapping
    public Result searchByName(@Argument String title,@Argument Integer page, @Argument Integer size) {
        log.info("enter");
        Pageable bookpage = PageRequest.of(page, size);
        return Result.success(bookService.searchByTitle(title, bookpage));
    }

    @PostMapping("/searchByTag")
    public Result searchByTag(@RequestParam("tag") String tag, @RequestParam("index") int pageIndex, @RequestParam("size") int pageSize) {
        Pageable bookpage= PageRequest.of(pageIndex,pageSize);
        return Result.success(bookService.searchRetatedTag(tag,bookpage));
    }
}
