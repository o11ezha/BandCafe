package com.kursovaya.BandCafe.Entities;

import org.springframework.data.relational.core.sql.In;

import java.util.Date;

public class ShoppingOrder {
    public String orderID;
    public Date orderAddDate;
    public Integer orderStatus;
    public String orderAddress;
    public String orderAmount;
    public String ShoppingCardID;
    public String merchID;
}
