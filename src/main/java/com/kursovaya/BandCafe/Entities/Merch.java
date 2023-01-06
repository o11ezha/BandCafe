package com.kursovaya.BandCafe.Entities;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class Merch {
    private String merchID;
    @NotNull (message = "Имя товара не может быть пустым")
    private String merchName;
    @NotNull (message = "Цена товара не может быть пустой")
    @Range(min = 1, max = 1000000, message = "Цена товара должна быть больше 0")
    private BigDecimal merchPrice;
    private Boolean merchStatus;
    @NotNull (message = "Количество товара не может быть пустым")
    @Range(min = 1, max = 1000, message = "Количество товара должно быть от 1 до 1000")
    private Integer merchAmount;
    private String merchDescSource;
    @NotNull (message = "Группа, которой принадлежит товар, не может быть пустой")
    private String groupID;

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

    public BigDecimal getMerchPrice() {
        return merchPrice;
    }

    public void setMerchPrice(BigDecimal merchPrice) {
        this.merchPrice = merchPrice;
    }

    public Boolean getMerchStatus() {
        return merchStatus;
    }

    public void setMerchStatus(Boolean merchStatus) {
        this.merchStatus = merchStatus;
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

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

}
