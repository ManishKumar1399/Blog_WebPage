package com.springboot.blog.service;

import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.repository.CategoryRepository;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);
}
