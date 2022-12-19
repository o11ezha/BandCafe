package com.kursovaya.BandCafe.Entities;

import java.util.Date;

public class Profile {
    private String profileID;
    private String profileAvatarSource;
    private Date profileDateOdBirth;
    private String profileDesc;
    private String accountLogin;

    public String getProfileID() {
        return profileID;
    }

    public void setProfileID(String profileID) {
        this.profileID = profileID;
    }

    public String getProfileAvatarSource() {
        return profileAvatarSource;
    }

    public void setProfileAvatarSource(String profileAvatarSource) {
        this.profileAvatarSource = profileAvatarSource;
    }

    public Date getProfileDateOdBirth() {
        return profileDateOdBirth;
    }

    public void setProfileDateOdBirth(Date profileDateOdBirth) {
        this.profileDateOdBirth = profileDateOdBirth;
    }

    public String getProfileDesc() {
        return profileDesc;
    }

    public void setProfileDesc(String profileDesc) {
        this.profileDesc = profileDesc;
    }

    public String getAccountLogin() {
        return accountLogin;
    }

    public void setAccountLogin(String accountLogin) {
        this.accountLogin = accountLogin;
    }
}
