package com.example.brahmi.controller;

import com.example.brahmi.dto.CategoryDto;
import com.example.brahmi.entity.Category;
import com.example.brahmi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category/{id}")
    public String getCategory(@PathVariable long id, ModelMap categoryModel){
        CategoryDto category = categoryService.getCategoryById(id);
        categoryModel.addAttribute("category", category);
        return "category";
    }

    @GetMapping("/categories")
    public String getCategories(ModelMap categoryModel) {
        List<CategoryDto> categories = categoryService.getAllCategories();
        categoryModel.addAttribute("categories", categories);
        return "categories";
    }

    @GetMapping("addCategory")
    public  String addPage() {return "addCategory";}

    @PostMapping("/add/category")
    public String addCandidate(
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "description", required = true) String description,
            ModelMap categoryModel){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(name);
        categoryDto.setDescription(description);
        categoryService.createCategory(categoryDto);
        categoryModel.addAttribute("msg", "Category added");
        return "redirect:/category/categories";
    }

    @GetMapping("/delete/category/{id}")
    public String deleteCategory(@PathVariable long id, ModelMap categoryModel){
        categoryService.deleteCategory(id);
        List<CategoryDto> categoryDtoList = categoryService.getAllCategories();
        categoryModel.addAttribute("categories", categoryDtoList);
        categoryModel.addAttribute("msg", "Candidate Deleted. ");
        return "redirect:/category/categories";
    }

    @PutMapping("/update/category/{id}")
    public String updateCategory(
            @PathVariable Long id,
            @Valid @ModelAttribute("category") CategoryDto categoryDto,
            BindingResult result,
            ModelMap categoryModel) {
        if (result.hasErrors()) {
            return "updateCategory";
        }

        categoryDto.setId(id);
        categoryService.updateCategory(id, categoryDto);
        categoryModel.addAttribute("msg", "Category updated");
        return "redirect:/category/categories";
    }

    @GetMapping("/update/category/{id}")
    public String updatePage(@PathVariable Long id, Model model) {
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        model.addAttribute("category", categoryDto);
        return "updateCategory";
    }

}
