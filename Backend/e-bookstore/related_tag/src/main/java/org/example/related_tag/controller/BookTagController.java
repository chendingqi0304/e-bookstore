package org.example.related_tag.controller;


import org.example.related_tag.entity.Result;
import org.example.related_tag.service.BookTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookTagController {
    @Autowired
    private BookTagService bookTagService;

    @PostMapping("/RelateTag")
    public Result RelateTag(@RequestParam("tag") String tag) {
        return Result.success(bookTagService.getRelateBookTags(tag));
    }
}
