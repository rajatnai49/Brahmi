package com.example.brahmi.controller;

import com.example.brahmi.dto.ProductDto;
import com.example.brahmi.entity.Order;
import com.example.brahmi.entity.Product;
import com.example.brahmi.entity.User;
import com.example.brahmi.global.GlobalData;
import com.example.brahmi.mapper.ProductMapper;
import com.example.brahmi.service.OrderService;
import com.example.brahmi.service.ProductService;
import com.example.brahmi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping(" ")
    public String viewCart(Model model, Authentication authentication) {
        List<ProductDto> cartItems = productMapper.toDtoList(GlobalData.cart);
        double total = cartItems.stream().mapToDouble(p -> p.getPrice().doubleValue()).sum();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        if (authentication != null && authentication.isAuthenticated()) {
            String userEmail = authentication.getName();
            model.addAttribute("userEmail", userEmail);
        }
        return "cart";
    }


    @GetMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId) {
        ProductDto productDto = productService.getProductById(productId);
        if (productDto != null) {
            GlobalData.cart.add(productMapper.toEntity(productDto));
        }
        return "redirect:/index";
    }

    @PostMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId) {
        GlobalData.cart.removeIf(product -> product.getId().equals(productId));
        return "redirect:/cart";
    }

    @PostMapping("/place-order")
    public String placeOrder(@RequestParam String userEmail, @RequestParam Double totalPrice) {
        // Create a new Order entity with the required attributes
        User user = userService.findUserByEmail(userEmail);
        List<Product> products = GlobalData.cart;
        Order order = new Order(user, products, totalPrice);

        // Save the order to the database
        orderService.createOrder(order);

        // Clear the cart and redirect to the home page
        GlobalData.cart.clear();
        return "redirect:/index";
    }

}
