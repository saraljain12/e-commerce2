package com.example.e_commerce;

public class HorizontalScrollModel {
    private  int HorizontalScrollImage;
    private String HorizontalScrollTitle;
    private String HorizontalScrollDescription;
    private String HorizontalScrollPrice;

    public HorizontalScrollModel(int horizontalScrollImage, String horizontalScrollTitle, String horizontalScrollDescription, String horizontalScrollPrice) {
        HorizontalScrollImage = horizontalScrollImage;
        HorizontalScrollTitle = horizontalScrollTitle;
        HorizontalScrollDescription = horizontalScrollDescription;
        HorizontalScrollPrice = horizontalScrollPrice;
    }

    public int getHorizontalScrollImage() {
        return HorizontalScrollImage;
    }

    public void setHorizontalScrollImage(int horizontalScrollImage) {
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
