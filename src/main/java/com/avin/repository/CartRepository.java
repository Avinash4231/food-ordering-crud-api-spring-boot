package com.avin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avin.model.Cart;

public interface CartRepository extends JpaRepository<Cart,Long>{
    public Cart findByCustomerId(Long userId);
}
