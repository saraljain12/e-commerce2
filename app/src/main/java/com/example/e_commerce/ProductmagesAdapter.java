package com.example.e_commerce;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class ProductmagesAdapter extends PagerAdapter {

    private List<Integer> productimages;

    public ProductmagesAdapter(List<Integer> productimages) {
        this.productimages = productimages;
    }

    @Override
    public int getCount() {
        return productimages.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((View) object);

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView productimageview = new ImageView(container.getContext());
        productimageview.setImageResource(productimages.get(position));
        container.addView(productimageview,0);
        return productimageview;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

}
