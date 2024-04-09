package com.intuit.exchange.rest.service.impl;

import com.intuit.exchange.rest.exception.ResourceNotFoundException;
import com.intuit.exchange.rest.entity.Category;
import com.intuit.exchange.rest.repository.CategoryRepository;
import com.intuit.exchange.rest.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (!category.isPresent()) {
            throw new ResourceNotFoundException("Category", "id", categoryId.toString());
        }
        return category.get();
    }

    @Override
    public Category addCategory(Category category) {
        try {
            return categoryRepository.save(category);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
// TODO
// 1. How to gracefully handle crud exceptions? - Sushma / Akhil