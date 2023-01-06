package com.kursovaya.BandCafe.Entities;

import javax.validation.constraints.NotNull;

public class MemberGroup {
    private String groupID;
    @NotNull(message = "Введите имя группы")
    private String groupName;
    private String groupCountry;
    private String groupDebutDate;
    private String groupDisbandDate;
    @NotNull(message = "Введите название фандома группы")
    private String groupFandom;
    private String groupDescSource;
    private String groupManager;

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupCountry() {
        return groupCountry;
    }

    public void setGroupCountry(String groupCountry) {
        this.groupCountry = groupCountry;
    }

    public String getGroupDebutDate() {
        return groupDebutDate;
    }

    public void setGroupDebutDate(String groupDebutDate) {
        this.groupDebutDate = groupDebutDate;
    }

    public String getGroupDisbandDate() {
        return groupDisbandDate;
    }

    public void setGroupDisbandDate(String groupDisbandDate) {
        this.groupDisbandDate = groupDisbandDate;
    }

    public String getGroupFandom() {
        return groupFandom;
    }

    public void setGroupFandom(String groupFandom) {
        this.groupFandom = groupFandom;
    }

    public String getGroupDescSource() {
        return groupDescSource;
    }

    public void setGroupDescSource(String groupDescSource) {
        this.groupDescSource = groupDescSource;
    }

    public String getGroupManager() {
        return groupManager;
    }

    public void setGroupManager(String groupManager) {
        this.groupManager = groupManager;
    }

}
