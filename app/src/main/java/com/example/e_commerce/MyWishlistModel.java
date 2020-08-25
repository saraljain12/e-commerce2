package com.example.e_commerce;

public class MyWishlistModel {
    private String product_ID;
    private String productImage;
    private String productTitle;
    private long freeCoupen;
    private long totalRating;
    private  String rating;
    private String productPrice;
    private String cutted_price;
    private boolean COD;

    public MyWishlistModel(String product_ID,String productImage, String productTitle, long freeCoupen, long totalRating, String rating, String productPrice, String cutted_price, boolean COD) {
        this.product_ID = product_ID;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.freeCoupen = freeCoupen;
        this.totalRating = totalRating;
        this.rating = rating;
        this.productPrice = productPrice;
        this.cutted_price = cutted_price;
        this.COD = COD;
    }

    public String getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(String product_ID) {
        this.product_ID = product_ID;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public long getFreeCoupen() {
        return freeCoupen;
    }

    public void setFreeCoupen(long freeCoupen) {
        this.freeCoupen = freeCoupen;
    }

    public long getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(long totalRating) {
        this.totalRating = totalRating;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
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

    public boolean getCOD() {
        return COD;
    }

    public void setCOD(boolean COD) {
        this.COD = COD;
    }
}
