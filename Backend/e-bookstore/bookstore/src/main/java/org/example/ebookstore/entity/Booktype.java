package org.example.ebookstore.entity;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
public class Booktype {
    @Id
    @GeneratedValue
    private CriteriaBuilder.In id;
    private String typename;
}
