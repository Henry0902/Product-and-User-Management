package com.project.repository;

import com.project.table.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository  extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndProductInfoId(Long cartId, Long productId);
    List<CartItem> findByCartId(Long cartId);
//    List<CartItem> findByUserInfoId(Long userId);
}
