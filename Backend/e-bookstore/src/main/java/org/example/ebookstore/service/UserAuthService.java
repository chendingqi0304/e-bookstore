package org.example.ebookstore.service;

import org.example.ebookstore.entity.User;
import org.example.ebookstore.entity.UserAuth;

public interface UserAuthService {
    UserAuth login(UserAuth user);
}
