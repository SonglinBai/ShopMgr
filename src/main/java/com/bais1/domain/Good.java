package com.bais1.domain;

import java.io.Serializable;

public class Good implements Serializable {
    private String goodId;
    private String goodName;
    private String categoryId;
    private float retailPrice;
    private float purchasePrice;
    private int inventory;
    private String supplierId;
    private boolean isEnable;
    private String description;

    private Category category;
    private Supplier supplier;

    public Good() {
    }

    public Good(String goodId, String goodName) {
        this.goodId = goodId;
        this.goodName = goodName;
    }

    public Good(String goodId, String goodName, String categoryId, boolean enable, String description, Category category) {
        this.goodId = goodId;
        this.goodName = goodName;
        this.categoryId = categoryId;
        this.isEnable = enable;
        this.description = description;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Good{" +
                "goodId='" + goodId + '\'' +
                ", goodName='" + goodName + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", retailPrice=" + retailPrice +
                ", purchasePrice=" + purchasePrice +
                ", inventory=" + inventory +
                ", supplierId='" + supplierId + '\'' +
                ", isEnable=" + isEnable +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", supplier=" + supplier +
                '}';
    }

    public Good(String goodId, String goodName, String categoryId, float retailPrice, float purchasePrice, int inventory, String supplierId, boolean isEnable, String description, Category category, Supplier supplier) {
        this.goodId = goodId;
        this.goodName = goodName;
        this.categoryId = categoryId;
        this.retailPrice = retailPrice;
        this.purchasePrice = purchasePrice;
        this.inventory = inventory;
        this.supplierId = supplierId;
        this.isEnable = isEnable;
        this.description = description;
        this.category = category;
        this.supplier = supplier;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public float getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(float retailPrice) {
        this.retailPrice = retailPrice;
    }

    public float getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
