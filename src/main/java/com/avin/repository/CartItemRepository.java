package com.avin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avin.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem,Long>{
    
}
