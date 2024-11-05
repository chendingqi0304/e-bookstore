package org.example.book_author.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "book", schema = "e-bookstore-frame")
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "fieldHandler"})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "bookId")
@Data
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
    @Column(name = "picture")
    private byte[] picture;
    @Column(name = "rest")
    private Integer rest;
    @Column(name = "type")
    private String type;
    @Column(name = "deleted")
    private boolean deleted;
    @Column(name = "isbn")
    private String isbn;

    public boolean getDeleted() {
        return deleted;
    }
}
