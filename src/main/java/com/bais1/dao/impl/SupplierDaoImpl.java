package com.bais1.dao.impl;

import com.bais1.dao.SupplierDao;
import com.bais1.domain.Supplier;
import com.bais1.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


import java.util.List;

public class SupplierDaoImpl implements SupplierDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Supplier getById(String supplierId) {
        String sql = "select * from tb_supplier where supplierId=?";

        return template.queryForObject(sql, new BeanPropertyRowMapper<Supplier>(Supplier.class), supplierId);
    }

    @Override
    public String getNameById(String supplierId) {
        String sql = "select supplierName from tb_supplier where supplierId=?";
        return template.queryForObject(sql, String.class, supplierId);
    }

    @Override
    public List<Supplier> getAll() {
        String sql = "select * from tb_supplier";
        return template.query(sql, new BeanPropertyRowMapper<Supplier>(Supplier.class));
    }

    @Override
    public boolean create(String supplierId, String name, String addr, String phone) {
        String sql = "insert into tb_supplier values (?,?,?,?)";
        try {
            template.update(sql, supplierId, name, addr, phone);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

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

    @Override
    public boolean isExist(String supplierId) {
        String sql = "select * from tb_supplier where supplierId=?";

        try {
            template.queryForObject(sql, new BeanPropertyRowMapper<Supplier>(Supplier.class), supplierId);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

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

    @Override
    public boolean isUsed(String supplierId) {
        String sql = "select count(goodId) from tb_good where supplierId=?";

        Integer count = template.queryForObject(sql, Integer.class, supplierId);

        if(count!=0) return true;
        else return false;
    }
}
