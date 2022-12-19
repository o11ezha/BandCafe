package com.kursovaya.BandCafe.Entities;

public class MemberPosition {
    private String memberID;
    private Integer positionCode;

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public Integer getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(Integer positionCode) {
        this.positionCode = positionCode;
    }
}
