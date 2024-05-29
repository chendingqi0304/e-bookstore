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
}
