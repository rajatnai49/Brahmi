package com.example.brahmi.service;

import com.example.brahmi.dto.ProductDto;
import com.example.brahmi.entity.Product;
import com.example.brahmi.exception.ResourceNotFoundException;
import com.example.brahmi.mapper.ProductMapper;
import com.example.brahmi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(productMapper::toDto).collect(Collectors.toList());
    }

    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        return productMapper.toDto(product);
    }

    public ProductDto saveProduct(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        Product updatedProduct = productMapper.toEntity(productDto);
        updatedProduct.setId(existingProduct.getId());
        Product savedProduct = productRepository.save(updatedProduct);
        return productMapper.toDto(savedProduct);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        productRepository.delete(product);
    }
}
