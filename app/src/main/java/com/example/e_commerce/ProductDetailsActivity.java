package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.ui.myReward.MyRewardAdapter;
import com.example.e_commerce.ui.myReward.MyRewardViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    private ViewPager productImagesviewpager;
    private TabLayout viewpagerIndiacator;
    private FloatingActionButton addfloatwishlistbtn;
    private Button couuponredeembtn;
    private static boolean ALREADY_ADDED_TO_WISHLIST = false;

    ////// product description layout
    private TextView productOnlyDescriptionBody;
    private ConstraintLayout productDetailsOnly,productDetailsTab;
    private ViewPager productsdetailsviewpager;
    private TabLayout productsdetailstablayout;
    private String productDescription;
    private String productOtherDetails;
    private Integer tabPosition = -1;
    private List<ProductSpecificationModel>  productSpecificationModelList = new ArrayList<>();

    ////// product description layout

    ////ratings layout
    private LinearLayout rateNowContainer,ratingsNoContainer,ratingsProgressbarContainer;
    private TextView totalRatings,totalratingsfigure;
    ///////


    private TextView productTitle;
    private TextView averageratingminiview,totalratingminiview,originalPrice,CuttedPrice,tvCodIndicator,rewardtitle,rewardbody;
    private ImageView codIndicator;


    //////////  coupondialog
    public static TextView couponTitle,couponBody,couponExpiryDate;
    private static RecyclerView coupenrecyclerview;
    private static LinearLayout selectedcouponlayout;
    //////////


    private FirebaseFirestore firebaseFirestore;

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
        couuponredeembtn = findViewById(R.id.coupon_redemption_btn);
        productTitle = findViewById(R.id.product_title);
        averageratingminiview = findViewById(R.id.tv_product_rating_miniview);
        totalratingminiview = findViewById(R.id.total_ratings_mini_view);
        originalPrice = findViewById(R.id.product_price);
        CuttedPrice = findViewById(R.id.cutted_price);
        codIndicator = findViewById(R.id.cod_indicator_image_view);
        tvCodIndicator = findViewById(R.id.tv_cod_indicator);
        rewardtitle = findViewById(R.id.reward_title);
        rewardbody = findViewById(R.id.reward_description);
        productDetailsTab = findViewById(R.id.product_details_tabs_container);
        productDetailsOnly = findViewById(R.id.product_details_container);
        productOnlyDescriptionBody = findViewById(R.id.product_details_body);
        totalRatings = findViewById(R.id.total_ratings);
        ratingsNoContainer = findViewById(R.id.rating_numbers_container);
        totalratingsfigure = findViewById(R.id.total_rating_figure);
        ratingsProgressbarContainer = findViewById(R.id.ratings_progressbar_container);

        firebaseFirestore = FirebaseFirestore.getInstance();

        final List<String> productimages = new ArrayList<>();

        firebaseFirestore.collection("PRODUCTS").document("EW5QkSFLItz9t4l5YgIt")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();

                    for(long x = 1; x<(long)documentSnapshot.get("no_of_product_images") + 1;x++){
                        productimages.add(documentSnapshot.get("product_image_" + x ).toString());
                    }
                    ProductmagesAdapter productmagesAdapter = new ProductmagesAdapter(productimages);
                    productImagesviewpager.setAdapter(productmagesAdapter);

                    productTitle.setText(documentSnapshot.get("product_title").toString());
                    averageratingminiview.setText(documentSnapshot.get("average_rating").toString());
                    totalratingminiview.setText("("+(long)documentSnapshot.get("total_ratings")+")ratings");
                    originalPrice.setText("Rs." + documentSnapshot.get("product_price") + "/-");
                    CuttedPrice.setText("Rs." + documentSnapshot.get("cutted_price") + "/-");

                    if((boolean) documentSnapshot.get("COD")){
                        codIndicator.setVisibility(View.VISIBLE);
                        tvCodIndicator.setVisibility(View.VISIBLE);

                    }
                    else{
                        codIndicator.setVisibility(View.INVISIBLE);
                        tvCodIndicator.setVisibility(View.INVISIBLE);
                    }
                    rewardtitle.setText((long)documentSnapshot.get("free_coupon") + documentSnapshot.get("free_coupon_title").toString());
                    rewardbody.setText(documentSnapshot.get("free_coupon_body").toString());

                    if((boolean) documentSnapshot.get("use_tab_layout")){
                        productDetailsOnly.setVisibility(View.GONE);
                        productDetailsTab.setVisibility(View.VISIBLE);

                        productDescription = documentSnapshot.get("product_description").toString();

                        productOtherDetails = documentSnapshot.get("product_other_details").toString();

                        for(long x = 1; x <(long) documentSnapshot.get("total_spec_title")+1;x++){
                            productSpecificationModelList.add(new ProductSpecificationModel(0,documentSnapshot.get("spec_title_"+ x) .toString()));
                            for(long y = 1; y < Integer.parseInt(String.valueOf(documentSnapshot.get("spec_title_"+x+"_total_fields")))+1; y++){
                                productSpecificationModelList.add(new ProductSpecificationModel(1, (String) documentSnapshot.get("spec_title_"+x+"_field"+y+"_name"), documentSnapshot.get("spec_title_"+x+"_field"+y+"_value").toString()));

                            }
                        }

                    }else{
                        productDetailsTab.setVisibility(View.GONE);
                        productDetailsOnly.setVisibility(View.VISIBLE);
                        productOnlyDescriptionBody.setText(documentSnapshot.get("product_description").toString());

                    }

                    totalRatings.setText(documentSnapshot.get("total_ratings") + "ratings");
                    for(int x =0; x<5;x++){
                        TextView rating = (TextView) ratingsNoContainer.getChildAt(x);
                        rating.setText(String.valueOf(documentSnapshot.get(5-x + "_star")));
                        ProgressBar progressBar = (ProgressBar) ratingsProgressbarContainer.getChildAt(x);
                        int maxprogress = Integer.parseInt(String.valueOf((long) documentSnapshot.get("total_ratings")));
                        progressBar.setMax(maxprogress);
                        progressBar.setProgress(Integer.parseInt(String.valueOf(documentSnapshot.get(5-x + "_star"))));

                    }
                    totalratingsfigure.setText(String.valueOf(documentSnapshot.get("total_ratings")));
                    productsdetailsviewpager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(),productsdetailstablayout.getTabCount(),productDescription,productOtherDetails,productSpecificationModelList));
                }
                else{
                    String error = task.getException().getMessage();
                    Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();
                }




            }
        });


        addfloatwishlistbtn = findViewById(R.id.add_to_wishlist_btn);

//        List<String > productimages = new ArrayList<>();
//        productimages.add(R.drawable.ic_launcher_background);
//        productimages.add(R.drawable.common_google_signin_btn_icon_dark);
//        productimages.add(R.drawable.ic_menu_gallery);
//        productimages.add(R.drawable.ic_menu_camera);


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


        productsdetailsviewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productsdetailstablayout));
        productsdetailstablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();
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
        //////// coupon dialog
        final Dialog checkcouponpricedialog =  new Dialog(ProductDetailsActivity.this);
        checkcouponpricedialog.setContentView(R.layout.coupon_redeem_dialog);
        checkcouponpricedialog.setCancelable(true);
        checkcouponpricedialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView togglerecyclerview = checkcouponpricedialog.findViewById(R.id.toggle_recyclerview);
        coupenrecyclerview = checkcouponpricedialog.findViewById(R.id.coupons_recyclerview);
        selectedcouponlayout = checkcouponpricedialog.findViewById(R.id.selected_coupen);
        couponTitle = checkcouponpricedialog.findViewById(R.id.coupon_title);
        couponExpiryDate = checkcouponpricedialog.findViewById(R.id.coupon_validity);
        couponBody = checkcouponpricedialog.findViewById(R.id.coupon_body);


        TextView originalprice = checkcouponpricedialog.findViewById(R.id.original_price);
        TextView discountedprice = checkcouponpricedialog.findViewById(R.id.discounted_price);

        LinearLayoutManager layoutManager = new LinearLayoutManager(ProductDetailsActivity.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        coupenrecyclerview.setLayoutManager(layoutManager);


        List<MyRewardViewModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new MyRewardViewModel("cashback","till 30 july 2020","Get 20% cashback on any amount above 2000 and get free home delivery also."));
        rewardModelList.add(new MyRewardViewModel("discount","till 30 july 2020","Get 20% cashback on any amount above 2000 and get free home delivery also."));
        rewardModelList.add(new MyRewardViewModel("buy1 get1","till 30 july 2020","Get 20% cashback on any amount above 2000 and get free home delivery also."));
        rewardModelList.add(new MyRewardViewModel("chutiya bnane ki scheme ","till 30 july 2020","Get 20% cashback on any amount above 2000 and get free home delivery also."));
        rewardModelList.add(new MyRewardViewModel("21 din m paise double","till 30 july 2020","Get 20% cashback on any amount above 2000 and get free home delivery also."));

        MyRewardAdapter rewardAdapter = new MyRewardAdapter(rewardModelList,true);
        coupenrecyclerview.setAdapter(rewardAdapter);
        rewardAdapter.notifyDataSetChanged();

        togglerecyclerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialogcyclerview();
            }
        });
        checkcouponpricedialog.show();
        /////// coupon dialog
        couuponredeembtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               checkcouponpricedialog.show();
            }
        });


    }

    public static void showdialogcyclerview(){
        if(coupenrecyclerview.getVisibility()==View.GONE){
            coupenrecyclerview.setVisibility(View.VISIBLE);
            selectedcouponlayout.setVisibility(View.GONE);
        }
        else{
            coupenrecyclerview.setVisibility(View.GONE);
            selectedcouponlayout.setVisibility(View.VISIBLE);

        }
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