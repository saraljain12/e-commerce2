package com.example.e_commerce.ui.myReward;

public class MyRewardViewModel {
    private String title,expiryDate,couponbody;

    public MyRewardViewModel(String title, String expiryDate, String couponbody) {
        this.title = title;
        this.expiryDate = expiryDate;
        this.couponbody = couponbody;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCouponbody() {
        return couponbody;
    }

    public void setCouponbody(String couponbody) {
        this.couponbody = couponbody;
    }
}
