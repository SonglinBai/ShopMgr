package com.bais1.service.impl;

import com.bais1.dao.CategoryDao;
import com.bais1.dao.impl.CategoryDaoImpl;
import com.bais1.domain.Category;
import com.bais1.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    CategoryDao categoryDao = new CategoryDaoImpl();
    @Override
    public String getNameById(String categoryId) {
        return categoryDao.getNameById(categoryId);
    }

    @Override
    public List<Category> getAll() {
        return categoryDao.getAll();
    }

    @Override
    public Category getById(String categoryId) {
        return categoryDao.getById(categoryId);
    }

    @Override
    public boolean create(String categoryId, String name, String description) {
        if (!categoryDao.isExist(categoryId)) {
            if(categoryDao.creat(categoryId, name, description)) return true;
            else return false;
        }else return false;
    }

    @Override
    public boolean edit(String oldCategoryId, String categoryId, String name, String description) {
        if (categoryDao.isExist(oldCategoryId) && !categoryDao.isExist(categoryId)) {
            if(categoryDao.update(oldCategoryId, categoryId, name, description)) return true;
            else return false;
        } else return false;
    }

    @Override
    public boolean delete(String categoryId) {
        if(categoryDao.isExist(categoryId)){
            categoryDao.deleteById(categoryId);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean isIdExist(String categoryId) {
        return categoryDao.isExist(categoryId);
    }

    @Override
    public boolean isUsed(String categoryId) {
        return categoryDao.isUsed(categoryId);
    }
}
