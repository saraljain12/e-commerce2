package com.example.e_commerce;

import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

public class HomePageModel {
    public static final int BANNER_SLIDER = 0;
    public static final int STRIP_AD_BANNER =1;
    public static final int HORIZONTAL_SCROLL_PRODUCT=2;
    public  static final  int GRID_PRODUCT_LAYOUT =3;
    private  int type;
    private String backgroundColor;
    ////////// Banner Slider
    private List<SliderModel> sliderModelList ;

    public HomePageModel(int type, List<SliderModel> sliderModelList) {
        this.type = type;
        this.sliderModelList = sliderModelList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<SliderModel> getSliderModelList() {
        return sliderModelList;
    }

    public void setSliderModelList(List<SliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }
    ////////// Banner Slider

    ////////// strip ad
    private String resource;


    public HomePageModel(int type, String resource, String backgroundColor) {
        this.type = type;
        this.resource = resource;
        this.backgroundColor = backgroundColor;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    ////////// strip ad

    ////////// Horizontal Scroll
    private List<HorizontalScrollModel> horizontalScrollModelList;
    private List<MyWishlistModel> ViewAllProductList;
    private String title;
    public HomePageModel(int type, List<HorizontalScrollModel> horizontalScrollModelList, String title,String backgroundColor,List<MyWishlistModel> ViewAllProductList) {
        this.type = type;
        this.horizontalScrollModelList = horizontalScrollModelList;
        this.title = title;
        this.backgroundColor = backgroundColor;
        this.ViewAllProductList = ViewAllProductList;
    }

    public HomePageModel(int type, List<HorizontalScrollModel> horizontalScrollModelList, String title,String backgroundColor) {
        this.type = type;
        this.horizontalScrollModelList = horizontalScrollModelList;
        this.title = title;
        this.backgroundColor = backgroundColor;
    }

    public List<MyWishlistModel> getViewAllProductList() {
        return ViewAllProductList;
    }

    public void setViewAllProductList(List<MyWishlistModel> viewAllProductList) {
        ViewAllProductList = viewAllProductList;
    }

    public List<HorizontalScrollModel> getHorizontalScrollModelList() {
        return horizontalScrollModelList;
    }

    public void setHorizontalScrollModelList(List<HorizontalScrollModel> horizontalScrollModelList) {
        this.horizontalScrollModelList = horizontalScrollModelList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    ////////// Horizontal Scroll

    ////////// Grid Product Layout

    ////////// Same as above

    ////////// Grid Product Layout


}
