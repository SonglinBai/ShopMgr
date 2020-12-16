package com.bais1.service;

import com.bais1.domain.Category;

import java.util.List;

public interface CategoryService {
    String getNameById(String categoryId);
    List<Category> getAll();
    Category getById(String categoryId);
    boolean create(String categoryId,String name,String description);
    boolean edit(String oldCategoryId,String categoryId,String name, String description);
    boolean delete(String categoryId);

    boolean isIdExist(String categoryId);

    boolean isUsed(String categoryId);
}
