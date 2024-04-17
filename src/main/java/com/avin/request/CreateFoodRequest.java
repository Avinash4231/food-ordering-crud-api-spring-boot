package com.avin.request;

import java.util.List;

import com.avin.model.Category;
import com.avin.model.IngredientsItem;

import lombok.Data;

@Data
public class CreateFoodRequest {
    private String name;
    private String description;
    private Long price;
    private Category category;
    private List<String> images; 
    private Long restaurantId;
    private boolean vegeterian;
    private boolean seasional;
    private List<IngredientsItem> ingredients;
}
