package com.avin.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avin.dto.RestaurantDto;
import com.avin.model.Address;
import com.avin.model.Restaurant;
import com.avin.model.User;
import com.avin.repository.AddressRepository;
import com.avin.repository.RestaurantRepository;
import com.avin.repository.UserRepository;
import com.avin.request.CreateRestaurantRequest;

@Service
public class RestaurantServiceImpl implements RestaurantService{
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        // TODO Auto-generated method stub
        Address address=addressRepository.save(req.getAddress());
        Restaurant restaurant=new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {
        // TODO Auto-generated method stub
        Restaurant restaurant=findRestaurantById(restaurantId);
        if(restaurant.getCuisineType()!=null){
            restaurant.setCuisineType(updatedRestaurant.getCuisineType());
        }
        if(restaurant.getDescription()!=null){
            restaurant.setDescription(updatedRestaurant.getDescription());
        }
        if(restaurant.getName()!=null){
            restaurant.setName(updatedRestaurant.getName());
        }
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        // TODO Auto-generated method stub
        Restaurant restaurant=findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);
        
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        // TODO Auto-generated method stub
        return restaurantRepository.findAll();
        
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> opt=restaurantRepository.findById(id);
        if(opt.isEmpty()){
            throw new Exception("restaurant not found with id"+id);
        }
        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long UserId) throws Exception {
        // TODO Auto-generated method stub
        Restaurant restaurant=restaurantRepository.findByOwnerId(UserId);
        if(restaurant==null){
            throw new Exception("restaurant not found with owner id "+UserId);
        }
        return restaurant;

    }

    @Override
    public RestaurantDto addToFavourites(Long restaurantId, User user) throws Exception {
        // TODO Auto-generated method stub
        Restaurant restaurant=findRestaurantById(restaurantId);

        RestaurantDto dto=new RestaurantDto();
        dto.setDescription(restaurant.getDescription());
        dto.setImages(restaurant.getImages());
        dto.setTitle(restaurant.getName());
        dto.setId(restaurantId);

        boolean isFavourited=false;
        List<RestaurantDto> favourites=user.getFavourites();
        for(RestaurantDto favourite:favourites){
            if(favourite.getId().equals(restaurantId)){
                isFavourited=true;
                break;
            }
        }
        if(isFavourited){
            favourites.removeIf(favourite->favourite.getId().equals(restaurantId));
        }else{
            favourites.add(dto);
        }
        userRepository.save(user);
        return dto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        // TODO Auto-generated method stub
        Restaurant restaurant=findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }
    
}
