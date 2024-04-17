package com.avin.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.lang.Exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avin.model.Address;
import com.avin.model.Cart;
import com.avin.model.CartItem;
import com.avin.model.Order;
import com.avin.model.OrderItem;
import com.avin.model.Restaurant;
import com.avin.model.User;
import com.avin.repository.AddressRepository;
import com.avin.repository.OrderItemRepository;
import com.avin.repository.OrderRepository;
import com.avin.repository.RestaurantRepository;
import com.avin.repository.UserRepository;
import com.avin.request.OrderRequest;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    
    @Autowired 
    private RestaurantService restaurantService;
    @Autowired
    private CartService cartService; 

    @Override
    public Order createOrder(OrderRequest order, User user) throws Exception {
        // TODO Auto-generated method stub
        Address shipAddress=order.getDeliveryAddress();
        Address saveAddress=addressRepository.save(shipAddress);
        if(!user.getAddresses().contains(saveAddress)){
            user.getAddresses().add(saveAddress);
            userRepository.save(user);
        }

        Restaurant restaurant=restaurantService.findRestaurantById(order.getRestaurantId());

        Order createOrder=new Order();
        createOrder.setCustomer(user);
        createOrder.setCreatedAt(new Date(0));
        createOrder.setOrderStatus("PENDING");
        createOrder.setDeliveryAddress(saveAddress);
        createOrder.setRestaurant(restaurant);

        Cart cart=cartService.findCartByUserId(user.getId());   

        List<OrderItem> orderItems=new ArrayList<>();
        for(CartItem cartItem:cart.getItems()){
            OrderItem orderItem=new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            OrderItem savedOrderItem=orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }
        Long totalPrice=cartService.calculateCartTotals(cart);

        createOrder.setItems(orderItems);
        createOrder.setTotalPrice(totalPrice);

        Order savedOrder=orderRepository.save(createOrder);
        restaurant.getOrders().add(savedOrder);
        return createOrder;    
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        // TODO Auto-generated method stub
        Order order=findOrderById(orderId);
        if(orderStatus.equals("OUT_FOR_DELIVERY") 
        || orderStatus.equals("DELIVERED")
        || orderStatus.equals("COMPLETED")
        || orderStatus.equals("PENDING")){
            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }
        throw new Exception("Please Select a valid order status");
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        // TODO Auto-generated method stub
        Order order=findOrderById(orderId);
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> getUsersOrder(Long userId) throws Exception {
        // TODO Auto-generated method stub
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception {
        // TODO Auto-generated method stub
        List<Order> orders=orderRepository.findByRestaurantId(restaurantId);
        if(orderStatus!=null){
            orders=orders.stream().filter(order->
            order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }
        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        // TODO Auto-generated method stub
        Optional<Order> optionalOrder=orderRepository.findById(orderId);
        if(optionalOrder.isEmpty()){
            throw new Exception("order not found");
        }
        return optionalOrder.get();
    }
    
}
