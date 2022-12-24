package com.kursovaya.BandCafe.Entities;

import org.springframework.data.relational.core.sql.In;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

public class ShoppingOrder {
    public String orderID;
    public String orderAddDate;
    public Integer orderStatus;
    public Boolean orderConfrim;
    @NotNull(message = "Введите адрес доставки")
    @Pattern(regexp = "[А-Яа-я0-9\\s\\-,.():;'\"]+", message = "Адрес доставки должен содержать только буквы, цифры и знаки препинания")
    public String orderAddress;
    @NotNull(message = "Введите количество заказываемых вещей ")
    @Pattern(regexp = "[0-9]+", message = "Количество должно быть целым числом")
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
