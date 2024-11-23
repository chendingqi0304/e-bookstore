package org.example.related_tag.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;


@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookTag {
    @Id
    private Long id;
    private String name;


}
