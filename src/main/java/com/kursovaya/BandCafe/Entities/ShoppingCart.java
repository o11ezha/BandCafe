package com.kursovaya.BandCafe.Entities;

public class ShoppingCart {
    private String shoppingCartID;
    private Boolean confirmPayment;
    private Integer userMoney;
    private String accountLogin;

    public String getShoppingCartID() {
        return shoppingCartID;
    }

    public void setShoppingCartID(String shoppingCartID) {
        this.shoppingCartID = shoppingCartID;
    }

    public Boolean getConfirmPayment() {
        return confirmPayment;
    }

    public void setConfirmPayment(Boolean confirmPayment) {
        this.confirmPayment = confirmPayment;
    }

    public Integer getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(Integer userMoney) {
        this.userMoney = userMoney;
    }

    public String getAccountLogin() {
        return accountLogin;
    }

    public void setAccountLogin(String accountLogin) {
        this.accountLogin = accountLogin;
    }
}
