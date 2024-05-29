package org.example.ebookstore.service.impl;

import jakarta.transaction.Transactional;
import org.example.ebookstore.dao.UserDao;
import org.example.ebookstore.entity.User;
import org.example.ebookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User findUserByUserId(Integer userId){
        return userDao.findByuserId(userId);
    }
}
