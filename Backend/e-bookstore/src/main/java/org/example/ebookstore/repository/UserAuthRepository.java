package org.example.ebookstore.repository;

import org.example.ebookstore.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthRepository extends JpaRepository<UserAuth,Integer> {
    UserAuth findByAccountAndPassword(String account, String password);
}
