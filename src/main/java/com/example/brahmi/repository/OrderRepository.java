package com.example.brahmi.repository;

import com.example.brahmi.entity.Order;
import com.example.brahmi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
