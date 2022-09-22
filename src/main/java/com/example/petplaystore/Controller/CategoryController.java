package com.example.petplaystore.Controller;

import com.example.petplaystore.payload.ReqCategory;
import com.example.petplaystore.payload.Response;
import com.example.petplaystore.service.CategoryService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public HttpEntity<?> categoryList() {
        Response response = categoryService.getCategory();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public HttpEntity<?> categorySave(@RequestBody ReqCategory reqCategory) {
        Response response = categoryService.saveCategory(reqCategory);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update/{id}")
    public HttpEntity<?> categorySave(@PathVariable Long id, @RequestBody ReqCategory reqCategory) {
        Response response = categoryService.updateCategory(id, reqCategory);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete")
    public HttpEntity<?> categoryDelete(@RequestParam Long categoryId, @RequestParam(required = false) Boolean SetApp) {
        Response response = categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(response);
    }

}
