package com.example.brahmi.mapper;

import com.example.brahmi.dto.ProductDto;
import com.example.brahmi.entity.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;

@Component
public class ProductMapper {

    public ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setCategory(CategoryMapper.toDto(product.getCategory()));
        productDto.setPrice(BigDecimal.valueOf(product.getPrice().doubleValue()));
        return productDto;
    }

    public Product toEntity(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCategory(CategoryMapper.toEntity(productDto.getCategory()));
        product.setPrice(BigDecimal.valueOf(productDto.getPrice().doubleValue()));
        try {
            MultipartFile image = productDto.getImage();
            if (image != null && !image.isEmpty()) {
                product.setImage(image.getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error converting image to bytes", e);
        }
        return product;
    }

    public static String getImageData(byte[] image) {
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(image);
    }
}
