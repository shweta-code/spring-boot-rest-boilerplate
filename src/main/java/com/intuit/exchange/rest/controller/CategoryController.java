package com.intuit.exchange.rest.controller;

import com.intuit.exchange.rest.entity.Category;
import com.intuit.exchange.rest.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@CrossOrigin(maxAge = 3600)
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("categoryId") Long categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Category> createNewCategory(@RequestBody Category category) {
        return new ResponseEntity<>(categoryService.addCategory(category), HttpStatus.CREATED);
    }
}
