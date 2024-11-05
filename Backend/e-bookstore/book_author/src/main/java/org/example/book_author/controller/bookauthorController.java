package org.example.book_author.controller;

import org.example.book_author.entity.Result;
import org.example.book_author.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class bookauthorController {
    @Autowired
    BookService bookService;

    @GetMapping("/BookAuthor")
    public Result getBookAuthor(@RequestParam("title") String title) {
        String author = bookService.getBookAuthor(title);
        if (author != null) {
            return Result.success(author);
        }else {
            return Result.error("未找到书籍");
        }
    }
}
