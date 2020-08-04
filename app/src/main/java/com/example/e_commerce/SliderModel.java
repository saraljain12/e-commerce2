package com.example.e_commerce;

public class SliderModel {
    private  String banner;
    private  String backgroundColor;
    public SliderModel(String banner,String backgroundColor){

        this.banner = banner;
        this.backgroundColor = backgroundColor;
    }
    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }
}
