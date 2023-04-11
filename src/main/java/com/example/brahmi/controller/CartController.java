package com.example.brahmi.controller;

import com.example.brahmi.dto.ProductDto;
import com.example.brahmi.global.GlobalData;
import com.example.brahmi.mapper.ProductMapper;
import com.example.brahmi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping(" ")
    public String viewCart(Model model) {
        List<ProductDto> cartItems = productMapper.toDtoList(GlobalData.cart);
        double total = cartItems.stream().mapToDouble(p -> p.getPrice().doubleValue()).sum();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
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
}
