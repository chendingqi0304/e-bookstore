package org.example.ebookstore.service.impl;

import org.example.ebookstore.dao.UserAuthDao;
import org.example.ebookstore.entity.User;
import org.example.ebookstore.entity.UserAuth;
import org.example.ebookstore.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Scope("session")
public class UserAuthServiceImpl implements UserAuthService {
    @Autowired
    private UserAuthDao userAuthDao;
    private Instant startTime;

    public Boolean login(UserAuth user) {
        if (userAuthDao.existByAccountAndPassword(user.getAccount(), user.getPassword())) {
            startTime=Instant.now();
            return true;
        } else return false;
    }

    public Duration logout(Integer userId) {
        return Duration.between(startTime, Instant.now());
    }
}
