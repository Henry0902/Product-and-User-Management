package com.project.repository;

import com.project.table.Cart;
import com.project.table.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserInfoId(Long userId);

}
