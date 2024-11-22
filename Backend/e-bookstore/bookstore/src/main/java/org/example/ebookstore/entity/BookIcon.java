package org.example.ebookstore.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("book_data")
public class BookIcon {
    @Id
    private int id;

    private String iconBase64;
    public BookIcon(int id, String iconBase64) {
        this.id = id;
        this.iconBase64 = iconBase64;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getIconBase64() {
        return iconBase64;
    }
    public void setIconBase64(String iconBase64) {
        this.iconBase64 = iconBase64;
    }
}
