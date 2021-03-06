package com.bais1.domain;

import java.io.Serializable;

public class Supplier implements Serializable {
    private String supplierId;
    private String supplierName;
    private String address;
    private String phone;

    public Supplier(String supplierId, String name) {
        this.supplierId = supplierId;
        this.supplierName = name;
    }

    public Supplier() {

    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierId='" + supplierId + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Supplier(String supplierId, String supplierName, String address, String phone) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.address = address;
        this.phone = phone;
    }
}
