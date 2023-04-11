package com.example.brahmi.mapper;

import com.example.brahmi.dto.ProductDto;
import com.example.brahmi.entity.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class ProductMapper {

    public ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setCategory(CategoryMapper.toDto(product.getCategory()));
        productDto.setPrice(BigDecimal.valueOf(product.getPrice().doubleValue()));
        productDto.setImageUrl(product.getImageUrl());
        return productDto;
    }

    public Product toEntity(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCategory(CategoryMapper.toEntity(productDto.getCategory()));
        product.setPrice(BigDecimal.valueOf(productDto.getPrice().doubleValue()));
        product.setImageUrl(productDto.getImageUrl());
        return product;
    }

    public List<ProductDto> toDtoList(List<Product> cart) {
        List<ProductDto> cartItems = new ArrayList<>();
        for (Product product : cart) {
            ProductDto productDto = toDto(product);
            cartItems.add(productDto);
        }
        return cartItems;
    }

}
