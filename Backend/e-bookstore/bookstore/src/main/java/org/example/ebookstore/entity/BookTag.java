package org.example.ebookstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "booktag", schema = "e-bookstore-frame")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookTag {
    @Id
    @GeneratedValue
    @Column(name = "booktag_id")
    private Integer id;
    @Column(name = "name")
    private String name;

    @JsonIgnore  // 忽略 Books 属性，防止在序列化时加载
    @ManyToMany(mappedBy = "bookTags")
    @ToString.Exclude
    private Set<Book> books;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookTag bookTag = (BookTag) o;
        return Objects.equals(id, bookTag.id);  // Use `id` or other unique identifier for equality check
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);  // Hash based on `id` or unique identifier
    }
}
