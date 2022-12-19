package com.kursovaya.BandCafe.Entities;

import javax.validation.constraints.NotNull;


public class GroupLabel {
    private String labelID;
    @NotNull(message = "Введите название агентства")
    private String labelName;
    private String labelDirector;
    @NotNull(message = "Введите страну агентства")
    private String labelCountry;
    @NotNull(message = "Введите город агентства")
    private String labelCity;
    @NotNull(message = "Введите адрес агентства")
    private String labelMainAddress;
    private String labelDate;
    private String labelDescSource;

    public String getLabelID() {
        return labelID;
    }

    public void setLabelID(String labelID) {
        this.labelID = labelID;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getLabelDirector() {
        return labelDirector;
    }

    public void setLabelDirector(String labelDirector) {
        this.labelDirector = labelDirector;
    }

    public String getLabelCountry() {
        return labelCountry;
    }

    public void setLabelCountry(String labelCountry) {
        this.labelCountry = labelCountry;
    }

    public String getLabelCity() {
        return labelCity;
    }

    public void setLabelCity(String labelCity) {
        this.labelCity = labelCity;
    }

    public String getLabelMainAddress() {
        return labelMainAddress;
    }

    public void setLabelMainAddress(String labelMainAddress) {
        this.labelMainAddress = labelMainAddress;
    }

    public String getLabelDate() {
        return labelDate;
    }

    public void setLabelDate(String labelDate) {
        this.labelDate = labelDate;
    }

    public String getLabelDescSource() {
        return labelDescSource;
    }

    public void setLabelDescSource(String labelDescSource) {
        this.labelDescSource = labelDescSource;
    }

    @Override
    public String toString() {
        return "GroupLabel{" +
                "labelID='" + labelID + '\'' +
                ", labelName='" + labelName + '\'' +
                ", labelDirector='" + labelDirector + '\'' +
                ", labelCountry='" + labelCountry + '\'' +
                ", labelCity='" + labelCity + '\'' +
                ", labelMainAddress='" + labelMainAddress + '\'' +
                ", labelDate='" + labelDate + '\'' +
                ", labelDescSource='" + labelDescSource + '\'' +
                '}';
    }
}
