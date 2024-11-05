package org.example.ebookstore.repository;

import jakarta.transaction.Transactional;
import org.example.ebookstore.entity.Book;
import org.example.ebookstore.entity.Cart;
import org.example.ebookstore.entity.IdentifyCode;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Integer> {
    Page<Cart> findByUserIdAndBook_Deleted(Integer userId, boolean deleted,Pageable pageable);

    Cart findByCartId(Integer cartId);
    @Transactional
    void deleteByCartId(Integer cartId);

    Cart findByBookAndUserId(Book book, Integer userId);
}
