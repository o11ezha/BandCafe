package com.kursovaya.BandCafe.Entities;

public class Merch {
    private String merchID;
    private String merchName;
    private Integer merchPrice;
    private Boolean merchStatus;
    private Integer merchAmount;
    private String merchDescSource;
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

    public Integer getMerchPrice() {
        return merchPrice;
    }

    public void setMerchPrice(Integer merchPrice) {
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
