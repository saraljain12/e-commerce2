package com.example.e_commerce;

public class CategoryModel {
    private String CategoryIconLink;
    private String categoryName;
    public  CategoryModel(String s1,String s2){
        CategoryIconLink = s1;
        categoryName =s2;

    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCategoryIconLink(String categoryIconLink) {
        CategoryIconLink = categoryIconLink;
    }

    public String getCategoryIconLink() {
        return CategoryIconLink;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
