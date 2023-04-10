package com.example.brahmi.controller;

import com.example.brahmi.dto.CategoryDto;
import com.example.brahmi.dto.ProductDto;
import com.example.brahmi.service.CategoryService;
import com.example.brahmi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;


    @GetMapping("/products")
    public String getAllProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product-list";
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable Long id, Model model) {
        ProductDto productDto = productService.getProductById(id);
        model.addAttribute("product", productDto);
        return "product-details";
    }

    @GetMapping("/add")
    public String showAddProductForm(ProductDto productDto, Model model) {
        List<CategoryDto> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "add-product";
    }

    @PostMapping("/add")
    public String addProduct(@Valid ProductDto productDto, BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) {
            return "add-product";
        }
        productService.createProduct(productDto);
        model.addAttribute("products", productService.getAllProducts());
        return "product-list";
    }

    @GetMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id, Model model) {
        productService.deleteProduct(id);
        model.addAttribute("products", productService.getAllProducts());
        return "product-list";
    }
}
