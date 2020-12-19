package com.bais1.dao;

import com.bais1.domain.Supplier;

import java.util.List;

public interface SupplierDao {

    Supplier getById(String supplierId);

    String getNameById(String supplierId);

    List<Supplier> getAll();

    boolean create(String supplierId, String name, String addr, String phone);

    boolean update(String oldSupplierId, String supplierId, String name, String addr, String phone);

    boolean isExist(String supplierId);

    boolean deleteById(String supplierId);

    boolean isUsed(String supplierId);
}
