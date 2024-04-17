package com.avin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avin.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long>{
    
}
