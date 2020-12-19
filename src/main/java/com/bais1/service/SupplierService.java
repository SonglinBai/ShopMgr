package com.bais1.service;

import com.bais1.domain.Category;
import com.bais1.domain.Supplier;

import java.util.List;

public interface SupplierService {
    String getNameById(String supplierId);
    List<Supplier> getAll();
    Supplier getById(String supplierId);
    boolean create(String supplierId, String name, String addr, String phone);
    boolean edit(String oldSupplierId, String supplierId, String name, String addr, String phone);
    boolean delete(String supplierId);
    boolean isIdExist(String supplierId);
    boolean isUsed(String supplierId);
}
