package com.bais1.dao.impl;

import com.bais1.dao.SupplierDao;
import com.bais1.domain.Supplier;
import com.bais1.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


import java.util.List;

// 用于操作数据库中的供应商表
public class SupplierDaoImpl implements SupplierDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    // 通过供应商ID获取供应商信息
    @Override
    public Supplier getById(String supplierId) {
        String sql = "select * from tb_supplier where supplierId=?";

        return template.queryForObject(sql, new BeanPropertyRowMapper<Supplier>(Supplier.class), supplierId);
    }

    // 通过供应商ID获取供应商名
    @Override
    public String getNameById(String supplierId) {
        String sql = "select supplierName from tb_supplier where supplierId=?";
        return template.queryForObject(sql, String.class, supplierId);
    }

    // 获取所有供应商信息
    @Override
    public List<Supplier> getAll() {
        String sql = "select * from tb_supplier";
        return template.query(sql, new BeanPropertyRowMapper<Supplier>(Supplier.class));
    }

    // 创建供应商信息
    @Override
    public boolean create(String supplierId, String name, String addr, String phone) {
        String sql = "insert into tb_supplier values (?,?,?,?)";
        try {
            template.update(sql, supplierId, name, addr, phone);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 更新供应商信息
    @Override
    public boolean update(String oldSupplierId, String supplierId, String name, String addr, String phone) {
        String sql = "update tb_supplier set supplierId=?, supplierName=?, address=?, phone=? where supplierId=?";
        try {
            template.update(sql, supplierId, name, addr, phone, oldSupplierId);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 判断供应商是否存在
    @Override
    public boolean isExist(String supplierId) {
        String sql = "select * from tb_supplier where supplierId=?";

        try {
            template.queryForObject(sql, new BeanPropertyRowMapper<Supplier>(Supplier.class), supplierId);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    // 通过供应商ID删除
    @Override
    public boolean deleteById(String supplierId) {
        String sql = "";
        try {
            template.update(sql, supplierId);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 判断供应商是否被使用
    @Override
    public boolean isUsed(String supplierId) {
        String sql = "select count(goodId) from tb_good where supplierId=?";

        Integer count = template.queryForObject(sql, Integer.class, supplierId);

        if(count!=0) return true;
        else return false;
    }
}
