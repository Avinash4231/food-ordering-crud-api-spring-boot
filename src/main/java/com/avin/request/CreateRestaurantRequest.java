package com.avin.request;

import java.util.List;

import com.avin.model.Address;
import com.avin.model.ContactInformation;

import lombok.Data;

@Data
public class CreateRestaurantRequest {
    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private String openingHours;
    private List<String> images; 
}
