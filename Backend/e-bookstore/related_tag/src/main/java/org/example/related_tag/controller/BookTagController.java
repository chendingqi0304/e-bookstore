package org.example.related_tag.controller;


import org.example.related_tag.entity.BookTag;
import org.example.related_tag.service.BookTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookTagController {
    @Autowired
    private BookTagService bookTagService;

    @GetMapping("/RelateTag")
    public List<BookTag> RelateTag(@RequestParam("tag") String tag) {
        return bookTagService.getRelateBookTags(tag);
    }
}
