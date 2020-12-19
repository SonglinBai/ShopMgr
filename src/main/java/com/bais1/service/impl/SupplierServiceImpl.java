package com.bais1.service.impl;

import com.bais1.dao.SupplierDao;
import com.bais1.dao.impl.SupplierDaoImpl;
import com.bais1.domain.Category;
import com.bais1.domain.Supplier;
import com.bais1.service.SupplierService;

import java.util.List;

public class SupplierServiceImpl implements SupplierService {
    SupplierDao supplierDao = new SupplierDaoImpl();
    @Override
    public String getNameById(String supplierId) {
        return supplierDao.getNameById(supplierId);
    }

    @Override
    public List<Supplier> getAll() {
        return supplierDao.getAll();
    }

    @Override
    public Supplier getById(String supplierId) {
        return supplierDao.getById(supplierId);
    }

    @Override
    public boolean create(String supplierId, String name, String addr, String phone) {
        if (!supplierDao.isExist(supplierId)) {
            if(supplierDao.create(supplierId, name, addr, phone)) return true;
            else return false;
        }else return false;
    }

    @Override
    public boolean edit(String oldSupplierId, String supplierId, String name, String addr, String phone) {
        if (true) {
            if (supplierDao.update(oldSupplierId, supplierId, name, addr, phone)) return true;
            else return false;
        } else return false;
    }

    @Override
    public boolean delete(String supplierId) {
        if (supplierDao.isExist(supplierId)) {
            supplierDao.deleteById(supplierId);
            return true;
        } else return false;
    }

    @Override
    public boolean isIdExist(String supplierId) {
        return supplierDao.isExist(supplierId);
    }

    @Override
    public boolean isUsed(String supplierId) {
        return supplierDao.isExist(supplierId);
    }
}
