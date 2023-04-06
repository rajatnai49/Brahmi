package com.example.brahmi.service;

import com.example.brahmi.dto.CategoryDto;
import com.example.brahmi.entity.Category;
import com.example.brahmi.exception.ResourceNotFoundException;
import com.example.brahmi.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> new CategoryDto(category.getId(), category.getName(), category.getDescription())).collect(Collectors.toList());
    }

    public CategoryDto getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
        return new CategoryDto(category.getName(), category.getDescription());
    }

    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = new Category(categoryDto.getId(), categoryDto.getName(), categoryDto.getDescription());
        category = categoryRepository.save(category);
        return new CategoryDto(category.getName(), category.getDescription());
    }

    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category = categoryRepository.save(category);
        return new CategoryDto(category.getName(), category.getDescription());
    }

    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
        categoryRepository.delete(category);
    }
}
