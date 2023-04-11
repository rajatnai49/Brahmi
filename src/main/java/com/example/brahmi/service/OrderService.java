package com.example.brahmi.service;

import com.example.brahmi.entity.Order;
import com.example.brahmi.entity.Product;
import com.example.brahmi.dto.OrderDto;
import com.example.brahmi.entity.User;
import com.example.brahmi.mapper.OrderMapper;
import com.example.brahmi.mapper.ProductMapper;
import com.example.brahmi.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    public void createOrder(String userEmail, List<Product> products, double totalPrice) {
        User user = userService.findUserByEmail(userEmail);
        if (user == null) {
            throw new IllegalArgumentException("User not found for email: " + userEmail);
        }
        Order order = new Order();
        order.setUser(user);
        order.setProducts(products);
        order.setTotalPrice(BigDecimal.valueOf(totalPrice));
        orderRepository.save(order);
    }


    public void createOrder(Order order) {
        orderRepository.save(order);
    }

    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orderMapper.toDtoList(orders);
    }

    public List<OrderDto> getOrdersByUser(User user) {
        List<Order> orders = orderRepository.findByUser(user);
        return orderMapper.toDtoList(orders);
    }

}

