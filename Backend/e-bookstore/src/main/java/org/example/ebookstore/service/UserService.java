package org.example.ebookstore.service;

import org.example.ebookstore.entity.OrderItem;
import org.example.ebookstore.entity.Statistics;
import org.example.ebookstore.entity.User;

import java.util.List;

public interface UserService {
    User findUserByUserId(Integer userId);

    Statistics getStatistics(Integer userId);

    User findByemail(String email);

    User save(User user);

    User findByUsername(String username);

}
