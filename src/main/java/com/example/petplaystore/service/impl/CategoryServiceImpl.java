package com.example.petplaystore.service.impl;

import com.example.petplaystore.entity.Category;
import com.example.petplaystore.payload.ReqCategory;
import com.example.petplaystore.payload.Response;
import com.example.petplaystore.payload.Status;
import com.example.petplaystore.repository.CategoryRepository;
import com.example.petplaystore.service.CategoryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final JdbcTemplate jdbcTemplate;

    public CategoryServiceImpl(CategoryRepository categoryRepository, JdbcTemplate jdbcTemplate) {
        this.categoryRepository = categoryRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Response saveCategory(ReqCategory reqCategory) {
        Response response = new Response();
        Category category = new Category(reqCategory.getName());
        categoryRepository.save(category);
        response.setStatus(new Status(0, "Progress Completed"));
        return response;
    }


    public Response updateCategory(Long id, ReqCategory reqCategory) {
        Response response = new Response();
        Optional<Category> byId = categoryRepository.findById(id);
        if (byId.isPresent()) {
            Category category = byId.get();
            category.setName(reqCategory.getName());
            categoryRepository.save(category);
            response.setStatus(new Status(0, "Progress Completed"));
        } else {
            response.setStatus(new Status(404, "Category not found"));
        }
        return response;
    }

    public Response deleteCategory(Long id) {
        Response response = new Response();
        Optional<Category> byId = categoryRepository.findById(id);
        if (byId.isPresent()) {
            try {
                categoryRepository.deleteById(id);
                response.setStatus(new Status(0, "Progress Completed"));
            } catch (Exception e) {
                response.setStatus(new Status(1, "This object any early related"));
            }

        } else {
            response.setStatus(new Status(404, "Category not found"));
        }
        return response;
    }

    @Override
    public Response getCategory() {
        Response response = new Response();
        List<Category> all = categoryRepository.findAll();
        response.setData(all);
        response.setStatus(new Status(0, "Progress Completed"));
        return response;
    }


}

