package com.bais1.domain;

import java.io.Serializable;
import java.util.Date;

public class Document implements Serializable {
    private String documentId;
    private float price;
    private float discount;
    private Date orderTime;
    private String userAccount;
    private DocumentType type;
    private String note;

    private User user;

    @Override
    public String toString() {
        return "Document{" +
                "documentId='" + documentId + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", orderTime=" + orderTime +
                ", userAccount='" + userAccount + '\'' +
                ", type=" + type +
                ", note='" + note + '\'' +
                ", user=" + user +
                '}';
    }

    public Document(String documentId, float price, float discount, Date orderTime, String userAccount, DocumentType type, String note, User user) {
        this.documentId = documentId;
        this.price = price;
        this.discount = discount;
        this.orderTime = orderTime;
        this.userAccount = userAccount;
        this.type = type;
        this.note = note;
        this.user = user;
    }

    public Document() {

    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public DocumentType getType() {
        return type;
    }

    public void setType(DocumentType type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
