package com.example.brahmi.controller;

import com.example.brahmi.dto.CategoryDto;
import com.example.brahmi.dto.ProductDto;
import com.example.brahmi.entity.Category;
import com.example.brahmi.mapper.CategoryMapper;
import com.example.brahmi.service.CategoryService;
import com.example.brahmi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private CategoryMapper categoryMapper;


    @GetMapping("/products")
    public String getAllProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product-list";
    }

    @GetMapping("/byCategory")
    public String byCategory(@RequestParam CategoryDto categorydto, Model model) {
        Category category = categoryMapper.toEntity(categorydto);
        model.addAttribute("products", productService.getProductsByCategory(category));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "index";
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
        return "redirect:/product/products";
    }

    @GetMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id, Model model) {
        productService.deleteProduct(id);
        model.addAttribute("products", productService.getAllProducts());
        return "redirect:/product/products";
    }
}
