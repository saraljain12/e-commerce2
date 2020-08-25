package com.example.e_commerce;

public class CartItemModel {
    public static final int CART_ITEM =0;
    public static final int TOTAL_AMOUNT =1;

    private  int type;

    ////// cart item
    private String productImage;
    private String productID;
    private String productTitle;
    private Long freeCoupens;
    private String productPrice;
    private String cuttedPrice;
    private Long productQuantity;
    private Long offersApplied;
    private Long coupensApplied;

    public CartItemModel(int type, String productImage, String productID, String productTitle, Long freeCoupens, String productPrice, String cuttedPrice, Long productQuantity, Long offersApplied, Long coupensApplied) {
        this.type = type;
        this.productImage = productImage;
        this.productID = productID;
        this.productTitle = productTitle;
        this.freeCoupens = freeCoupens;
        this.productPrice = productPrice;
        this.cuttedPrice = cuttedPrice;
        this.productQuantity = productQuantity;
        this.offersApplied = offersApplied;
        this.coupensApplied = coupensApplied;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public Long getFreeCoupens() {
        return freeCoupens;
    }

    public void setFreeCoupens(Long freeCoupens) {
        this.freeCoupens = freeCoupens;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getCuttedPrice() {
        return cuttedPrice;
    }

    public void setCuttedPrice(String cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }

    public Long getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Long productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Long getOffersApplied() {
        return offersApplied;
    }

    public void setOffersApplied(Long offersApplied) {
        this.offersApplied = offersApplied;
    }

    public Long getCoupensApplied() {
        return coupensApplied;
    }

    public void setCoupensApplied(Long coupensApplied) {
        this.coupensApplied = coupensApplied;
    }
    ////// cart item

    ////// cart total

    public  CartItemModel(int type){
        this.type = type;
    }
    ////// cart total
}

