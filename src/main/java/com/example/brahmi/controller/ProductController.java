package com.example.brahmi.controller;

import com.example.brahmi.dto.CategoryDto;
import com.example.brahmi.dto.ProductDto;
import com.example.brahmi.service.CategoryService;
import com.example.brahmi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/products")
    public String getAllProducts(ModelMap model) {
        List<ProductDto> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/product/{id}")
    public String getProductById(@PathVariable Long id, Model model) {
        ProductDto product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("/addProduct")
    public String addProductPage(Model model) {
        List<CategoryDto> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("product", new ProductDto());
        return "addProduct";
    }

    @PostMapping("/add/product")
    public String addProduct(@Valid @ModelAttribute("product") ProductDto productDto,
                             BindingResult result,
                             ModelMap model) {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "addProduct";
        }

        productService.saveProduct(productDto);
        model.addAttribute("msg", "Product added successfully");
        return "redirect:/product/products";
    }


    @GetMapping("/update/product/{id}")
    public String updateProductPage(@PathVariable Long id, Model model) {
        ProductDto productDto = productService.getProductById(id);
        model.addAttribute("product", productDto);
        List<CategoryDto> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "updateProduct";
    }

    @PutMapping("/update/product/{id}")
    public String updateProduct(@PathVariable Long id,
                                @Valid @ModelAttribute("product") ProductDto productDto,
                                BindingResult result,
                                ModelMap model) {
        if (result.hasErrors()) {
            return "updateProduct";
        }

        productService.updateProduct(id, productDto);
        model.addAttribute("msg", "Product updated successfully");
        return "redirect:/product/products";
    }

    @GetMapping("/delete/product/{id}")
    public String deleteProduct(@PathVariable Long id, ModelMap model) {
        productService.deleteProduct(id);
        model.addAttribute("msg", "Product deleted successfully");
        return "redirect:/product/products";
    }
}
