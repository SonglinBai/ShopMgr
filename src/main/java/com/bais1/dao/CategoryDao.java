package com.bais1.dao;

import com.bais1.domain.Category;

import java.util.List;

public interface CategoryDao {
    /**
     * 通过id获取分类
     * @param categoryId
     * @return
     */
    Category getById(String categoryId);

    String getNameById(String categoryId);

    List<Category> getAll();

    boolean creat(String categoryId,String name,String description);

    boolean update(String oldCategoryId,String categoryId,String name,String description);

    boolean isExist(String categoryId);

    boolean deleteById(String categoryId);

    boolean isUsed(String categoryId);
}
