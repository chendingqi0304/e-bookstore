package org.example.ebookstore.dao;

import org.example.ebookstore.entity.User;

public interface UserDao {
    User findByuserId(Integer userId);
}
