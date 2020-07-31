package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ProductDetailsAdapter extends FragmentPagerAdapter {

    private int totaltabs;
    public ProductDetailsAdapter(@NonNull FragmentManager fm,int totalTabs) {
        super(fm);
        this.totaltabs=totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                productDescriptionFragment productDescriptionFragment1 = new productDescriptionFragment();
                return productDescriptionFragment1;
            case 1:
                ProductSpecificationFragment productSpecificationFragment = new ProductSpecificationFragment();
                return  productSpecificationFragment;
            case 2:
                productDescriptionFragment productDescriptionFragment2 = new productDescriptionFragment();
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
