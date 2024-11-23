package org.example.related_tag.service;

import org.example.related_tag.DAO.BookTagDao;
import org.example.related_tag.entity.BookTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookTagService {
    @Autowired
    private BookTagDao bookTagDao;

    public List<BookTag> getRelateBookTags(String tag) {
       return bookTagDao.getRelateBookTags(tag);
    }
}
