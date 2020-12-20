package com.bais1.domain;

import javax.print.Doc;

public class DocumentDetail {
    private String documentId;
    private String goodId;
    private int amount;
    private float price;

    private Good good;

    @Override
    public String toString() {
        return "DocumentDetail{" +
                "documentId='" + documentId + '\'' +
                ", goodId='" + goodId + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                ", good=" + good +
                '}';
    }

    public DocumentDetail(String documentId, String goodId, int amount, float price, Good good) {
        this.documentId = documentId;
        this.goodId = goodId;
        this.amount = amount;
        this.price = price;
        this.good = good;
    }
    public DocumentDetail(){}

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }
}
