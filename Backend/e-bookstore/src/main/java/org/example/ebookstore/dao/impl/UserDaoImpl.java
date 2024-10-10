package org.example.ebookstore.dao.impl;

import org.example.ebookstore.dao.UserDao;
import org.example.ebookstore.entity.User;
import org.example.ebookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User findByuserId(Integer userId){
        return userRepository.findByUserId(userId);
    }

    @Override
    public User findByAccount(String account){
        return userRepository.findByAccount(account);
    }
    @Override
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    @Override
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
    @Override
    public void insert(User user){
        userRepository.save(user);
    }
    @Override
    public void updateAccount(Integer userId, String account){
        User user = userRepository.findByUserId(userId);
        user.setAccount(account);
        userRepository.save(user);
    }
}
