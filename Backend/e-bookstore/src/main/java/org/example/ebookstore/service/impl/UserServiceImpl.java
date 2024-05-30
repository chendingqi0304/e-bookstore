package org.example.ebookstore.service.impl;

import jakarta.transaction.Transactional;
import org.example.ebookstore.dao.OrderDao;
import org.example.ebookstore.dao.UserDao;
import org.example.ebookstore.entity.OrderItem;
import org.example.ebookstore.entity.Statistics;
import org.example.ebookstore.entity.User;
import org.example.ebookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrderDao orderDao;

    @Override
    public User findUserByUserId(Integer userId) {
        return userDao.findByuserId(userId);
    }

    @Override
    public Statistics getStatistics(Integer userId,Integer time) {
        return orderDao.getStatistics(userId,time);
    }

    @Override
    public User findByemail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User findByUsername(String username){
        return userDao.findByUsername(username);
    }

    @Override
    public User save(User user) {
        userDao.insert(user);
        String account = user.getUserId().toString();
        /* user.setAvatar(userMapper.getDefaultAvatar().getAvatar()); */
        while (account.length() < 5) {
            account = "0" + account;
        }
        user.setAccount(account);
        userDao.updateAccount(user.getUserId(), account);
        return user;
    }
}
