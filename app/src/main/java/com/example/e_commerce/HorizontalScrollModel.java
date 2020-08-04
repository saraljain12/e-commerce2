package com.example.e_commerce;

public class HorizontalScrollModel {
    private String productID;
    private  String HorizontalScrollImage;
    private String HorizontalScrollTitle;
    private String HorizontalScrollDescription;
    private String HorizontalScrollPrice;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public HorizontalScrollModel(String productID, String horizontalScrollImage, String horizontalScrollTitle, String horizontalScrollDescription, String horizontalScrollPrice) {
        HorizontalScrollImage = horizontalScrollImage;
        HorizontalScrollTitle = horizontalScrollTitle;
        HorizontalScrollDescription = horizontalScrollDescription;
        HorizontalScrollPrice = horizontalScrollPrice;
        this.productID = productID;
    }

    public String getHorizontalScrollImage() {
        return HorizontalScrollImage;
    }

    public void setHorizontalScrollImage(String horizontalScrollImage) {
        HorizontalScrollImage = horizontalScrollImage;
    }

    public String getHorizontalScrollTitle() {
        return HorizontalScrollTitle;
    }

    public void setHorizontalScrollTitle(String horizontalScrollTitle) {
        HorizontalScrollTitle = horizontalScrollTitle;
    }

    public String getHorizontalScrollDescription() {
        return HorizontalScrollDescription;
    }

    public void setHorizontalScrollDescription(String horizontalScrollDescription) {
        HorizontalScrollDescription = horizontalScrollDescription;
    }

    public String getHorizontalScrollPrice() {
        return HorizontalScrollPrice;
    }

    public void setHorizontalScrollPrice(String horizontalScrollPrice) {
        HorizontalScrollPrice = horizontalScrollPrice;
    }
}
