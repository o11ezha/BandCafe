package com.kursovaya.BandCafe.Entities;

import org.springframework.data.relational.core.sql.In;

import java.util.Date;

public class ShoppingOrder {
    public String orderID;
    public String orderAddDate;
    public Integer orderStatus;
    public Boolean orderConfrim;
    public String orderAddress;
    public Integer orderAmount;
    public String ShoppingCartID;
    public String merchID;

    public Boolean getOrderConfrim() {
        return orderConfrim;
    }

    public void setOrderConfrim(Boolean orderConfrim) {
        this.orderConfrim = orderConfrim;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderAddDate() {
        return orderAddDate;
    }

    public void setOrderAddDate(String orderAddDate) {
        this.orderAddDate = orderAddDate;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public Integer getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getShoppingCartID() {
        return ShoppingCartID;
    }

    public void setShoppingCartID(String shoppingCartID) {
        ShoppingCartID = shoppingCartID;
    }

    public String getMerchID() {
        return merchID;
    }

    public void setMerchID(String merchID) {
        this.merchID = merchID;
    }
}
