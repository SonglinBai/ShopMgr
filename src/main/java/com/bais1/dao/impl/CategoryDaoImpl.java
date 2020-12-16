package com.bais1.dao.impl;

import com.bais1.dao.CategoryDao;
import com.bais1.domain.Category;
import com.bais1.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Category getById(String categoryId) {
        String sql = "select * from tb_category where categoryId=?";

        return template.queryForObject(sql,new BeanPropertyRowMapper<Category>(Category.class),categoryId);
    }

    @Override
    public String getNameById(String categoryId) {
        String sql = "select categoryName from tb_category where categoryId=?";
        return template.queryForObject(sql,String.class,categoryId);
    }

    @Override
    public List<Category> getAll() {
        String sql = "select * from tb_category";
        return template.query(sql,new BeanPropertyRowMapper<Category>(Category.class));
    }

    @Override
    public boolean creat(String categoryId, String name, String description) {
        String sql = "insert into tb_category (categoryId, categoryName, description) values (?,?,?)";
        try {
            template.update(sql,categoryId,name,description);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean update(String oldCategoryId, String categoryId, String name, String description) {
        String sql = "update tb_category set categoryId=?,categoryName=?,description=? where categoryId=?";
        try {
            template.update(sql,categoryId,name,description,oldCategoryId);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

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

    @Override
    public boolean isUsed(String categoryId) {
        String sql = "select count(goodId) from tb_good where categoryId=?";

        Integer count = template.queryForObject(sql, Integer.class, categoryId);

        if(count!=0) return true;
        else return false;
    }

}
