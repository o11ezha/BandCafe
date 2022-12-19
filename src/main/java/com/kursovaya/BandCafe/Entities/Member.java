package com.kursovaya.BandCafe.Entities;

import java.util.Date;

public class Member {
    private String memberID;
    private String memberName;
    private String memberStageName;
    private Date memberDateOfBirth;
    private String memberCountry;
    private String memberCity;
    private Integer memberHeight;
    private String memberDescSource;
    private String labelID;
    private String groupID;

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberStageName() {
        return memberStageName;
    }

    public void setMemberStageName(String memberStageName) {
        this.memberStageName = memberStageName;
    }

    public Date getMemberDateOfBirth() {
        return memberDateOfBirth;
    }

    public void setMemberDateOfBirth(Date memberDateOfBirth) {
        this.memberDateOfBirth = memberDateOfBirth;
    }

    public String getMemberCountry() {
        return memberCountry;
    }

    public void setMemberCountry(String memberCountry) {
        this.memberCountry = memberCountry;
    }

    public String getMemberCity() {
        return memberCity;
    }

    public void setMemberCity(String memberCity) {
        this.memberCity = memberCity;
    }

    public Integer getMemberHeight() {
        return memberHeight;
    }

    public void setMemberHeight(Integer memberHeight) {
        this.memberHeight = memberHeight;
    }

    public String getMemberDescSource() {
        return memberDescSource;
    }

    public void setMemberDescSource(String memberDescSource) {
        this.memberDescSource = memberDescSource;
    }

    public String getLabelID() {
        return labelID;
    }

    public void setLabelID(String labelID) {
        this.labelID = labelID;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }
}
