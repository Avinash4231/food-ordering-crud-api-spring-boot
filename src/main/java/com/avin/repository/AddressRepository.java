package com.avin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avin.model.Address;

public interface AddressRepository extends JpaRepository<Address,Long>{
    
}
