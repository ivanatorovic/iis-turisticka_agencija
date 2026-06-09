package com.example.turisticka_agencija.controller;

import com.example.turisticka_agencija.dto.CategoryRequest;
import com.example.turisticka_agencija.dto.CategoryResponse;
import com.example.turisticka_agencija.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping
    public CategoryResponse createCategory(
            @RequestBody CategoryRequest request,
            Principal principal
    ) {
        return categoryService.createCategory(request, principal);
    }
}