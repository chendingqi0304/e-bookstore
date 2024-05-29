package org.example.ebookstore.repository;

import jakarta.transaction.Transactional;
import org.example.ebookstore.entity.Book;
import org.example.ebookstore.entity.Cart;
import org.example.ebookstore.entity.IdentifyCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Integer> {
    List<Cart> findByUserId(Integer userId);

    Cart findByCartId(Integer cartId);
    @Transactional
    void deleteByCartId(Integer cartId);

    Cart findByBookAndUserId(Book book, Integer userId);

    public interface IdentifyRepository extends JpaRepository <IdentifyCode, Long> {}
}
