package com.bihan.boilerplate.rest.service;

import com.bihan.boilerplate.rest.entity.Category;

public interface CategoryService {

    Category getCategoryById(Long courseId);

    Category addCategory(Category category);

}
