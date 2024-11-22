package org.example.ebookstore.repository;

import org.example.ebookstore.entity.BookIcon;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookIconRepository extends MongoRepository<BookIcon, Integer> {
}
