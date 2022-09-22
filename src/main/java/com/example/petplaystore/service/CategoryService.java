package com.example.petplaystore.service;


import com.example.petplaystore.payload.ReqCategory;
import com.example.petplaystore.payload.Response;

public interface CategoryService {
    Response saveCategory(ReqCategory reqCategory);

    Response updateCategory(Long id, ReqCategory reqCategory);

    Response deleteCategory(Long id);

    Response getCategory();
}
