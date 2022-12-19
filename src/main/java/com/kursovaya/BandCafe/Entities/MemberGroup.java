package com.kursovaya.BandCafe.Entities;

import java.util.Date;

public class MemberGroup {
    private String groupID;
    private String groupName;
    private String groupCountry;
    private Date groupDebutDate;
    private Date groupDisbandDate;
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

    public Date getGroupDebutDate() {
        return groupDebutDate;
    }

    public void setGroupDebutDate(Date groupDebutDate) {
        this.groupDebutDate = groupDebutDate;
    }

    public Date getGroupDisbandDate() {
        return groupDisbandDate;
    }

    public void setGroupDisbandDate(Date groupDisbandDate) {
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
