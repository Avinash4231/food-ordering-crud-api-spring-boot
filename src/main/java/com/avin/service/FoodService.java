package com.avin.service;

import java.util.List;

import com.avin.model.Category;
import com.avin.model.Food;
import com.avin.model.Restaurant;
import com.avin.request.CreateFoodRequest;

public interface FoodService {
    public Food createFood(CreateFoodRequest req, Category category,Restaurant restaurant);
    void deleteFood(Long foodId) throws Exception;
    public List<Food> getRestaurantsFood(Long restaurantId,
    boolean isVegeterian,boolean isNonVeg,boolean isSeasonal, String foodCategory);

    public List<Food> searchFood(String keyword);
    public Food findFoodById(Long foodId)throws Exception;
    public Food updateAvailabilityStatus(Long foodId)throws Exception;
}
