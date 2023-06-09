package com.springboot.blog.controller;

import com.springboot.blog.entity.Category;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    //Build add Category REst APi
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
       CategoryDto categoryAdded= categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(categoryAdded, HttpStatus.CREATED);
    }
    @GetMapping("{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") Long categoryId){
        CategoryDto categoryDto= categoryService.getCategory(categoryId);
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
