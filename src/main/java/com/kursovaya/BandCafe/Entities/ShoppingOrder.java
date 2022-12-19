package com.kursovaya.BandCafe.Entities;

import org.springframework.data.relational.core.sql.In;

import java.util.Date;

public class ShoppingOrder {
    public String orderID;
    public Date orderAddDate;
    public Integer orderStatus;
    public String orderAddress;
    public Integer orderAmount;
    public String ShoppingCardID;
    public String merchID;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public Date getOrderAddDate() {
        return orderAddDate;
    }

    public void setOrderAddDate(Date orderAddDate) {
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

    public String getShoppingCardID() {
        return ShoppingCardID;
    }

    public void setShoppingCardID(String shoppingCardID) {
        ShoppingCardID = shoppingCardID;
    }

    public String getMerchID() {
        return merchID;
    }

    public void setMerchID(String merchID) {
        this.merchID = merchID;
    }
}
