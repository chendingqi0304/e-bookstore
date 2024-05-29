package org.example.ebookstore.service.impl;

import org.example.ebookstore.dao.UserAuthDao;
import org.example.ebookstore.entity.User;
import org.example.ebookstore.entity.UserAuth;
import org.example.ebookstore.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthServiceImpl implements UserAuthService {
    @Autowired
    private UserAuthDao userAuthDao;

    public UserAuth login(UserAuth user){
        return userAuthDao.findByAccountAndPassword(user.getAccount(), user.getPassword());
    }
}
