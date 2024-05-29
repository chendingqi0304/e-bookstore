package org.example.ebookstore.repository;

import org.example.ebookstore.entity.IdentifyCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IdentifyRepository extends JpaRepository <IdentifyCode,Integer>{
    @Query("SELECT o from IdentifyCode o where o.email=:emailAddress order by o.sendTime desc limit 1")
    IdentifyCode findCodeById(String emailAddress);
}
