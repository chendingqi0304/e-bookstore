package org.example.ebookstore.service.impl;

import org.example.ebookstore.dao.IdentifyDao;
import org.example.ebookstore.entity.IdentifyCode;
import org.example.ebookstore.service.IdentifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class IdentifyServiceImpl implements IdentifyService {
    @Autowired
    private IdentifyDao identifyDao;

    @Override
    public Integer newCode(String emailAddress) {
        LocalDateTime now = LocalDateTime.now();
        Integer code1 = now.hashCode();
        if(code1 < 1000) {
            code1+=114514;
        }
        Integer code2 = emailAddress.hashCode();
        Integer code = Math.abs(code2 % (code1 % 1000000));
        identifyDao.insert(emailAddress, code, now);
        return code;
    }

    @Override
    public Integer getCode(String emailAddress) {
        IdentifyCode answer=identifyDao.findByEmail(emailAddress);
        if(Duration.between(answer.getSendTime(), LocalDateTime.now()).getSeconds()>600){
            return -1;
        }
        else {
            return answer.getCode();
        }
    }
}