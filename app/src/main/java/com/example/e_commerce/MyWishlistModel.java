package com.example.e_commerce;

public class MyWishlistModel {
    private int productImage;
    private String productTitle;
    private int freeCoupen;
    private String totalRating;
    private  double rating;
    private String productPrice;
    private String cutted_price;
    private String paymentMethod;

    public MyWishlistModel(int productImage, String productTitle, int freeCoupen, String totalRating, double rating, String productPrice, String cutted_price, String paymentMethod) {
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.freeCoupen = freeCoupen;
        this.totalRating = totalRating;
        this.rating = rating;
        this.productPrice = productPrice;
        this.cutted_price = cutted_price;
        this.paymentMethod = paymentMethod;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public int getFreeCoupen() {
        return freeCoupen;
    }

    public void setFreeCoupen(int freeCoupen) {
        this.freeCoupen = freeCoupen;
    }

    public String getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(String totalRating) {
        this.totalRating = totalRating;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getCutted_price() {
        return cutted_price;
    }

    public void setCutted_price(String cutted_price) {
        this.cutted_price = cutted_price;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
