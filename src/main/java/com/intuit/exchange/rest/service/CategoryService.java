package com.intuit.exchange.rest.service;

import com.intuit.exchange.rest.entity.Category;

public interface CategoryService {

    Category getCategoryById(Long courseId);

    Category addCategory(Category category);

}
