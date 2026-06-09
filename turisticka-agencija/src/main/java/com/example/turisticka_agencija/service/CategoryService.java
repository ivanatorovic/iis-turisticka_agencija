package com.example.turisticka_agencija.service;

import com.example.turisticka_agencija.dto.CategoryRequest;
import com.example.turisticka_agencija.dto.CategoryResponse;
import com.example.turisticka_agencija.exception.BadRequestException;
import com.example.turisticka_agencija.model.Category;
import com.example.turisticka_agencija.model.Role;
import com.example.turisticka_agencija.model.User;
import com.example.turisticka_agencija.repository.CategoryRepository;
import com.example.turisticka_agencija.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryService(
            CategoryRepository categoryRepository,
            UserRepository userRepository
    ) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public CategoryResponse createCategory(
            CategoryRequest request,
            Principal principal
    ) {
        User user = getAuthenticatedUser(principal);

        if (user.getRole() != Role.MANAGER) {
            throw new BadRequestException("Only MANAGER can create categories");
        }

        if (request.getName() == null || request.getName().isBlank()) {
            throw new BadRequestException("Category name is required");
        }

        String name = request.getName().trim();

        if (categoryRepository.findByNameIgnoreCase(name).isPresent()) {
            throw new BadRequestException("Category already exists");
        }

        Category category = new Category();
        category.setName(name);

        return mapToResponse(categoryRepository.save(category));
    }

    private CategoryResponse mapToResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName()
        );
    }

    private User getAuthenticatedUser(Principal principal) {
        if (principal == null || principal.getName() == null) {
            throw new BadRequestException("User is not authenticated");
        }

        return userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new BadRequestException("Authenticated user not found"));
    }
}