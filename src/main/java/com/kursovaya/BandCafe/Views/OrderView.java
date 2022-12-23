package com.kursovaya.BandCafe.Views;

import java.math.BigDecimal;

public class OrderView {
    private String accountLogin;
    private String orderID;
    private String orderAddDate;
    private Integer orderStatus;
    private Boolean orderConfirm;
    private String orderAddress;
    private Integer orderAmount;
    private String merchID;
    private String merchName;
    private Boolean merchStatus;
    private BigDecimal totalPrice;
    private Integer merchAmount;
    private String merchDescSource;

    public Boolean getMerchStatus() {
        return merchStatus;
    }

    public void setMerchStatus(Boolean merchStatus) {
        this.merchStatus = merchStatus;
    }

    public String getAccountLogin() {
        return accountLogin;
    }

    public void setAccountLogin(String accountLogin) {
        this.accountLogin = accountLogin;
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

    public Boolean getOrderConfirm() {
        return orderConfirm;
    }

    public void setOrderConfirm(Boolean orderConfirm) {
        this.orderConfirm = orderConfirm;
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

    public String getMerchID() {
        return merchID;
    }

    public void setMerchID(String merchID) {
        this.merchID = merchID;
    }

    public String getMerchName() {
        return merchName;
    }

    public void setMerchName(String merchName) {
        this.merchName = merchName;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getMerchAmount() {
        return merchAmount;
    }

    public void setMerchAmount(Integer merchAmount) {
        this.merchAmount = merchAmount;
    }

    public String getMerchDescSource() {
        return merchDescSource;
    }

    public void setMerchDescSource(String merchDescSource) {
        this.merchDescSource = merchDescSource;
    }

    @Override
    public String toString() {
        return "OrderView{" +
                "accountLogin='" + accountLogin + '\'' +
                ", orderID='" + orderID + '\'' +
                ", orderAddDate='" + orderAddDate + '\'' +
                ", orderStatus=" + orderStatus +
                ", orderConfirm=" + orderConfirm +
                ", orderAddress='" + orderAddress + '\'' +
                ", orderAmount=" + orderAmount +
                ", merchID='" + merchID + '\'' +
                ", merchName='" + merchName + '\'' +
                ", totalPrice=" + totalPrice +
                ", merchAmount=" + merchAmount +
                ", merchDescSource='" + merchDescSource + '\'' +
                '}';
    }
}
