package com.example.brahmi.service;

import com.example.brahmi.dto.ProductDto;
import com.example.brahmi.entity.Product;
import com.example.brahmi.exception.ResourceNotFoundException;
import com.example.brahmi.mapper.ProductMapper;
import com.example.brahmi.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return productMapper.toDto(product);
    }

    public ProductDto createProduct(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }
}
