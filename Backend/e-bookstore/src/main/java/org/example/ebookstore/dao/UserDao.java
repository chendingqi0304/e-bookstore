package org.example.ebookstore.dao;

import org.example.ebookstore.entity.User;

public interface UserDao {
    User findByuserId(Integer userId);

    User findByEmail(String email);

    void insert(User user);

    void updateAccount(Integer userId, String account);

    User findByUsername(String username);
}
