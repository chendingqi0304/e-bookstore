package org.example.ebookstore.dao;

import org.example.ebookstore.entity.User;
import org.example.ebookstore.entity.UserAuth;

public interface UserAuthDao {
    UserAuth findByAccountAndPassword(String account, String password);
}
