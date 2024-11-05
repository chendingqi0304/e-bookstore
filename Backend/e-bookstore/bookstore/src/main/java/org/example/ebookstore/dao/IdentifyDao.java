package org.example.ebookstore.dao;

import org.example.ebookstore.entity.IdentifyCode;

import java.time.LocalDateTime;

public interface IdentifyDao {
    void insert(String emailAddress, Integer code, LocalDateTime now);

    IdentifyCode findByEmail(String emailAddress);
}
