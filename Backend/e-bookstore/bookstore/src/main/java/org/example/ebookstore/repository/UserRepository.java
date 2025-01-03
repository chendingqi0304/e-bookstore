package org.example.ebookstore.repository;

import org.example.ebookstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUserId(Integer userId);

    User findByEmail(String email);

    User findByUsername(String username);

    User findByAccount(String account);
}
