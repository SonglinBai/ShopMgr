package com.bais1.dao.impl;

import com.bais1.dao.CategoryDao;
import com.bais1.domain.Category;
import com.bais1.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

// 用于操作数据库中的分类表
public class CategoryDaoImpl implements CategoryDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    // 通过分类ID获取分类
    @Override
    public Category getById(String categoryId) {
        String sql = "select * from tb_category where categoryId=?";

        return template.queryForObject(sql,new BeanPropertyRowMapper<Category>(Category.class),categoryId);
    }

    // 通过分类ID获取分类名
    @Override
    public String getNameById(String categoryId) {
        String sql = "select categoryName from tb_category where categoryId=?";
        return template.queryForObject(sql,String.class,categoryId);
    }

    // 获取所有分类
    @Override
    public List<Category> getAll() {
        String sql = "select * from tb_category";
        return template.query(sql,new BeanPropertyRowMapper<Category>(Category.class));
    }

    // 创建一个分类
    @Override
    public boolean creat(String categoryId, String name, String description) {
        String sql = "insert into tb_category (categoryId, categoryName, description) values (?,?,?)";
        try {
            template.update(sql,categoryId,name,description);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 更新一个分类
    @Override
    public boolean update(String oldCategoryId, String categoryId, String name, String description) {
        String sql = "update tb_category set categoryId=?,categoryName=?,description=? where categoryId=?";
        try {
            template.update(sql,categoryId,name,description,oldCategoryId);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 判断分类是否存在
    @Override
    public boolean isExist(String categoryId) {
        String sql = "select * from tb_category where categoryId=?";

        try {
            template.queryForObject(sql,new BeanPropertyRowMapper<Category>(Category.class),categoryId);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    // 通过分类ID删除分类
    @Override
    public boolean deleteById(String categoryId) {
        String sql = "delete from tb_category where categoryId=?";

        try {
            template.update(sql,categoryId);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    // 判断分类是否被使用
    @Override
    public boolean isUsed(String categoryId) {
        String sql = "select count(goodId) from tb_good where categoryId=?";

        Integer count = template.queryForObject(sql, Integer.class, categoryId);

        if(count!=0) return true;
        else return false;
    }

}
