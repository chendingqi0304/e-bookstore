package org.example.related_tag.DAO;

import org.example.related_tag.entity.BookTag;
import org.example.related_tag.repository.BookTagRepository;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookTagDao {
    @Autowired
    private BookTagRepository bookTagRepository;

    public List<BookTag> getRelateBookTags(String tag) {
        return bookTagRepository.findReachableTags(tag);
    }
}
