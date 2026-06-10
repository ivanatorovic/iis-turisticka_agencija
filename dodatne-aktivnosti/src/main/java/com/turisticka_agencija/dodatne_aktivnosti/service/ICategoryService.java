package com.turisticka_agencija.dodatne_aktivnosti.service;

import com.turisticka_agencija.dodatne_aktivnosti.model.Category;

import java.util.List;

public interface ICategoryService {

    List<Category> findAllCategories();

    Category findById(Long id);

    Category save(Category category);

    Category update(Long id, Category updatedCategory);

    void delete(Long id);
}