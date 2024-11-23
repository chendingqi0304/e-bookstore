package org.example.related_tag.repository;

import org.example.related_tag.entity.BookTag;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface BookTagRepository extends Neo4jRepository<BookTag, Long> {
    @Query("MATCH (start:Tag {name: $tagName})-[:RELATED_TO*0..2]-(reachable:Tag) RETURN DISTINCT reachable.name AS name, reachable.id AS id")
    List<BookTag> findReachableTags(String tagName);

}
