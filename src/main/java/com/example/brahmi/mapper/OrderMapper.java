package com.example.brahmi.mapper;

import com.example.brahmi.dto.OrderDto;
import com.example.brahmi.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;

    public List<OrderDto> toDtoList(List<Order> orders) {
        return orders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public OrderDto toDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setUser(userMapper.toDto(order.getUser()));
        dto.setProducts(productMapper.toDtoList(order.getProducts()));
        dto.setTotalPrice(order.getTotalPrice());
        return dto;
    }
}
