package com.example.brahmi.controller;

import com.example.brahmi.dto.OrderDto;
import com.example.brahmi.dto.UserDto;
import com.example.brahmi.entity.User;
import com.example.brahmi.service.OrderService;
import com.example.brahmi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Collections;
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/orders")
    public String viewAllOrders(Model model) {
        List<OrderDto> orders = orderService.getAllOrders();
        Collections.reverse(orders);
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/my-orders")
    public String viewUserOrders(Model model, Principal principal) {
        String userEmail = principal.getName();
        User user = userService.findUserByEmail(userEmail);
        if (user == null) {
            throw new IllegalArgumentException("User not found for email: " + userEmail);
        }
        List<OrderDto> orders = orderService.getOrdersByUser(user);
        Collections.reverse(orders);
        model.addAttribute("orders", orders);
        return "my-orders";
    }

}

