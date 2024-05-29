package org.example.ebookstore.service;

import org.example.ebookstore.entity.User;

public interface UserService {
    User findUserByUserId(Integer userId);
}
