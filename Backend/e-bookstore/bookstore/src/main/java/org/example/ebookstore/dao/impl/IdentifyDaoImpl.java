package org.example.ebookstore.dao.impl;

import org.example.ebookstore.dao.IdentifyDao;
import org.example.ebookstore.entity.IdentifyCode;
import org.example.ebookstore.repository.IdentifyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class IdentifyDaoImpl implements IdentifyDao {
    @Autowired
    private IdentifyRepository identifyRepository;
    @Override
    public void insert(String emailAddress, Integer code, LocalDateTime now){
        IdentifyCode identifyCode = new IdentifyCode();
        identifyCode.setEmail(emailAddress);
        identifyCode.setCode(code);
        identifyCode.setSendTime(now);
        identifyRepository.save(identifyCode);
    }
    @Override
    public IdentifyCode findByEmail(String emailAddress){
        return identifyRepository.findCodeById(emailAddress);
    }
}
