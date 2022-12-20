package com.kursovaya.BandCafe.Entities;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class Member {
    private String memberID;
    @NotNull(message = "Введите имя")
    private String memberName;
    @NotNull(message = "Введите сценическое имя")
    private String memberStageName;
    @NotNull(message = "Введите дату рождения")
    private String memberDateOfBirth;
    @NotNull(message = "Введите страну")
    private String memberCountry;
    @NotNull(message = "Введите город")
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

    public String getMemberDateOfBirth() {
        return memberDateOfBirth;
    }

    public void setMemberDateOfBirth(String memberDateOfBirth) {
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

    @Override
    public String toString() {
        return "Member{" +
                "memberID='" + memberID + '\'' +
                ", memberName='" + memberName + '\'' +
                ", memberStageName='" + memberStageName + '\'' +
                ", memberDateOfBirth='" + memberDateOfBirth + '\'' +
                ", memberCountry='" + memberCountry + '\'' +
                ", memberCity='" + memberCity + '\'' +
                ", memberHeight=" + memberHeight +
                ", memberDescSource='" + memberDescSource + '\'' +
                ", labelID='" + labelID + '\'' +
                ", groupID='" + groupID + '\'' +
                '}';
    }
}
