package com.bihan.boilerplate.rest.service;

import com.bihan.boilerplate.rest.model.Category;

public interface CategoryService {

    Category getCategoryById(Long courseId);

    Category addCategory(Category category);

}
