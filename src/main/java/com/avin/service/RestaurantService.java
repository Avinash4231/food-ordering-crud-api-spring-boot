package com.avin.service;

import java.util.List;

import com.avin.dto.RestaurantDto;
import com.avin.model.Restaurant;
import com.avin.model.User;
import com.avin.request.CreateRestaurantRequest;

public interface RestaurantService {
    public Restaurant createRestaurant(CreateRestaurantRequest req,User user);
    public Restaurant updateRestaurant(Long restaurantId,CreateRestaurantRequest updatedRestaurant)throws Exception;
    public void deleteRestaurant(Long restaurantId) throws Exception;
    public List<Restaurant> getAllRestaurant();
    public List<Restaurant> searchRestaurant(String keyword);
    public Restaurant findRestaurantById(Long id) throws Exception;
    public Restaurant getRestaurantByUserId(Long UserId)throws Exception;
    public RestaurantDto addToFavourites(Long restaurantId,User user)throws Exception;
    public Restaurant updateRestaurantStatus(Long id)throws Exception;
}
