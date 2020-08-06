package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class ProductDetailsAdapter extends FragmentPagerAdapter {

    private int totaltabs;
    private String productDescription,productOtherdetails;
    private List<ProductSpecificationModel> productSpecificationModelList;
    public ProductDetailsAdapter(FragmentManager fm,int totalTabs ,String productDescription, String productOtherdetails, List<ProductSpecificationModel> productSpecificationModelList){
        super(fm);
        this.totaltabs = totalTabs;
        this.productDescription = productDescription;
        this.productOtherdetails = productOtherdetails;
        this.productSpecificationModelList = productSpecificationModelList;
    }


    public ProductDetailsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                productDescriptionFragment productDescriptionFragment1 = new productDescriptionFragment();
                productDescriptionFragment1.body = productDescription;
                return productDescriptionFragment1;
            case 1:
                ProductSpecificationFragment productSpecificationFragment = new ProductSpecificationFragment();
                productSpecificationFragment.productSpecificationModelList = productSpecificationModelList;
                return  productSpecificationFragment;
            case 2:
                productDescriptionFragment productDescriptionFragment2 = new productDescriptionFragment();
                productDescriptionFragment2.body = productDescription;
                return productDescriptionFragment2;
            default:
                return null;


        }

    }

    @Override
    public int getCount() {
        return totaltabs;
    }
}
