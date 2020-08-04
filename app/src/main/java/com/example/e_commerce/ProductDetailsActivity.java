package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    private ViewPager productImagesviewpager,productsdetailsviewpager;
    private TabLayout viewpagerIndiacator,productsdetailstablayout;
    private FloatingActionButton addfloatwishlistbtn;
    private static boolean ALREADY_ADDED_TO_WISHLIST = false;
    ////ratings layout
    private LinearLayout rateNowContainer;
    ///////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        productImagesviewpager =  findViewById(R.id.product_images_viewpager);
        viewpagerIndiacator = findViewById(R.id.viewpager_indicator);
        productsdetailstablayout = findViewById(R.id.product_details_tablayout);
        productsdetailsviewpager = findViewById(R.id.products_details_viewpager);



        addfloatwishlistbtn = findViewById(R.id.add_to_wishlist_btn);

        List<Integer> productimages = new ArrayList<>();
        productimages.add(R.drawable.ic_launcher_background);
        productimages.add(R.drawable.common_google_signin_btn_icon_dark);
        productimages.add(R.drawable.ic_menu_gallery);
        productimages.add(R.drawable.ic_menu_camera);

        ProductmagesAdapter productmagesAdapter = new ProductmagesAdapter(productimages);
        productImagesviewpager.setAdapter(productmagesAdapter);

        viewpagerIndiacator.setupWithViewPager(productImagesviewpager,true);

        addfloatwishlistbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ALREADY_ADDED_TO_WISHLIST){
                    addfloatwishlistbtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#B6B2B2")));
                    ALREADY_ADDED_TO_WISHLIST = false;
                }
                else{
                    addfloatwishlistbtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#FFF42010")));
                    ALREADY_ADDED_TO_WISHLIST = true;
                }
            }
        });

        productsdetailsviewpager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(),productsdetailstablayout.getTabCount()));
        productsdetailsviewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productsdetailstablayout));
        productsdetailstablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productsdetailsviewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        ///// ratings layout
            rateNowContainer = findViewById(R.id.rate_now_container);
            for(int i =0 ;i<rateNowContainer.getChildCount();i++){
                final int starposition = i;
                rateNowContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setRating(starposition);
                    }
                });
            }

        /////

    }

    private void setRating(int starposition) {
        for(int i =0; i<rateNowContainer.getChildCount();i++){
            ImageView starbtn = (ImageView) rateNowContainer.getChildAt(i);
            starbtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if(i<=starposition){
                starbtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}