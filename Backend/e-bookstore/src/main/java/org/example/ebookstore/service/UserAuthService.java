package org.example.ebookstore.service;

import org.example.ebookstore.entity.User;
import org.example.ebookstore.entity.UserAuth;
import org.springframework.context.annotation.Scope;

import java.time.Duration;


public interface UserAuthService {
    Boolean login(UserAuth user);
    Duration logout(Integer userId);
}
