package com.avin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avin.model.User;

public interface UserRepository extends JpaRepository<User,Long>{
    public User findByEmail(String username);
}
