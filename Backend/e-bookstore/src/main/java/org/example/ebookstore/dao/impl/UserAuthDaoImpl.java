package org.example.ebookstore.dao.impl;

import org.example.ebookstore.dao.UserAuthDao;
import org.example.ebookstore.entity.User;
import org.example.ebookstore.entity.UserAuth;
import org.example.ebookstore.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserAuthDaoImpl implements UserAuthDao{
    @Autowired
    private UserAuthRepository userAuthRepository;

    @Override
    public Boolean existByAccountAndPassword(String account, String password){
        return userAuthRepository.existsByAccountAndPassword(account,password);
    }
}
