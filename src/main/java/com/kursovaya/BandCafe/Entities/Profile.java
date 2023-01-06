package com.kursovaya.BandCafe.Entities;

public class Profile {
    private String profileID;
    private String profileAvatarSource;
    private String profileDateOfBirth;
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

    public String getProfileDateOfBirth() {
        return profileDateOfBirth;
    }

    public void setProfileDateOfBirth(String profileDateOfBirth) {
        this.profileDateOfBirth = profileDateOfBirth;
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
