package org.example.ebookstore.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "book", schema = "e-bookstore-frame")
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "fieldHandler"})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "bookId")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Integer bookId;
    @Column(name = "title")
    private String title;
    @Column(name = "price")
    private Integer price;
    @Column(name = "originprice")
    private Integer originprice;
    @Column(name = "author")
    private String author;
    @Column(name = "introduction")
    private String introduction;
    @Column(name = "rest")
    private Integer rest;
    @Column(name = "deleted")
    private boolean deleted;
    @Column(name = "isbn")
    private String isbn;

    @ManyToMany
    @JoinTable(name = "book_booktag",
    joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "booktag_id"))
    @ToString.Exclude
    private Set<BookTag> bookTags;

    public boolean getDeleted() {
        return deleted;
    }

    @Setter
    @Getter
    @Transient
    private BookIcon bookIcon;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(bookId, book.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId);  // Hash based on `id`
    }
}
