package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Gravity;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.e_commerce.DBqueries.cartModelList;
import static com.example.e_commerce.DBqueries.cartlist;
import static com.example.e_commerce.DBqueries.wishlistModelList;

public class ProductDetailsActivity extends AppCompatActivity {

    public static boolean running_wishlist_query = false ;
    public static boolean running_cart_query = false;

    private ViewPager productImagesviewpager;
    private TabLayout viewpagerIndiacator;
    public static FloatingActionButton addfloatwishlistbtn;
    private Button couuponredeembtn;
    private LinearLayout coupenRedemptionLayout;
    public static boolean ALREADY_ADDED_TO_WISHLIST = false;
    public static boolean ALREADY_ADDED_TO_CART = false;
    public static MenuItem cartitem;
    private TextView badge_count;
    public static TextView addtocart;

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
    public static LinearLayout rateNowContainer;
    private LinearLayout ratingsNoContainer,ratingsProgressbarContainer;
    private TextView totalRatings,totalratingsfigure,averageRating;
    public static int initialRating;
    public static boolean running_rating_query = false ;
    ///////


    private TextView productTitle;
    private TextView averageratingminiview,totalratingminiview,originalPrice,CuttedPrice,tvCodIndicator,rewardtitle,rewardbody;
    private ImageView codIndicator;

    private Dialog loadingDialog;
    //////////  coupondialog
    public static TextView couponTitle,couponBody,couponExpiryDate;
    private static RecyclerView coupenrecyclerview;
    private static LinearLayout selectedcouponlayout;
    //////////

    public static String productID;
    private FirebaseUser currentUser;
    private FirebaseFirestore firebaseFirestore;
    private DocumentSnapshot documentSnapshot;
    private Button buyNowBtn;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initialRating =-1;

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
        averageRating = findViewById(R.id.average_rating);

        ratingsNoContainer = findViewById(R.id.rating_numbers_container);
        totalratingsfigure = findViewById(R.id.total_rating_figure);
        ratingsProgressbarContainer = findViewById(R.id.ratings_progressbar_container);
        coupenRedemptionLayout = findViewById(R.id.coupon_redemption_layout);
        buyNowBtn = findViewById(R.id.buy_now_btn);
        addfloatwishlistbtn = findViewById(R.id.add_to_wishlist_btn);
        linearLayout = findViewById(R.id.linearLayout);
        addtocart = findViewById(R.id.add_to_cart_btn);

        firebaseFirestore = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final List<String> productimages = new ArrayList<>();

        productID = getIntent().getStringExtra("productID");

        /////////////// loading dialog
        loadingDialog = new Dialog(ProductDetailsActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();


        ////////////
        ////////////// SignInDialog
        final Dialog SignInDialog = new Dialog(ProductDetailsActivity.this);
        SignInDialog.setContentView(R.layout.sign_in_dialog);
        SignInDialog.setCancelable(true);
        SignInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        Button sign_in = SignInDialog.findViewById(R.id.sign_in_btn);
        Button sign_up = SignInDialog.findViewById(R.id.sign_up_btn);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInDialog.dismiss();
                FirebaseAuth.getInstance().signOut();
                Intent login_intent = new Intent(ProductDetailsActivity.this,LoginActivity.class);
                startActivity(login_intent);
                finishAffinity();
            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInDialog.dismiss();
                 FirebaseAuth.getInstance().signOut();
                Intent login_intent = new Intent(ProductDetailsActivity.this,RegisterActivity.class);
                startActivity(login_intent);
                finishAffinity();
            }
        });


        ////////////// SignInDialog

        firebaseFirestore.collection("PRODUCTS").document(productID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                     documentSnapshot = task.getResult();

                    for (long x = 1; x < (long) documentSnapshot.get("no_of_product_images") + 1; x++) {
                        productimages.add(documentSnapshot.get("product_image_" + x).toString());
                    }
                    ProductmagesAdapter productmagesAdapter = new ProductmagesAdapter(productimages);
                    productImagesviewpager.setAdapter(productmagesAdapter);


                    productTitle.setText(documentSnapshot.get("product_title").toString());
                    averageratingminiview.setText(documentSnapshot.get("average_rating").toString());
                    totalratingminiview.setText("(" + (long) documentSnapshot.get("total_ratings") + ")ratings");
                    originalPrice.setText("Rs." + documentSnapshot.get("product_price") + "/-");
                    CuttedPrice.setText("Rs." + documentSnapshot.get("cutted_price") + "/-");
                    averageRating.setText(documentSnapshot.get("average_rating").toString());

                    if ((boolean) documentSnapshot.get("COD")) {
                        codIndicator.setVisibility(View.VISIBLE);
                        tvCodIndicator.setVisibility(View.VISIBLE);

                    } else {
                        codIndicator.setVisibility(View.INVISIBLE);
                        tvCodIndicator.setVisibility(View.INVISIBLE);
                    }
                    rewardtitle.setText((long) documentSnapshot.get("free_coupon") + documentSnapshot.get("free_coupon_title").toString());
                    rewardbody.setText(documentSnapshot.get("free_coupon_body").toString());

                    if ((boolean) documentSnapshot.get("use_tab_layout")) {
                        productDetailsOnly.setVisibility(View.GONE);
                        productDetailsTab.setVisibility(View.VISIBLE);

                        productDescription = documentSnapshot.get("product_description").toString();

                        productOtherDetails = documentSnapshot.get("product_other_details").toString();

                        for (long x = 1; x < (long) documentSnapshot.get("total_spec_title") + 1; x++) {
                            productSpecificationModelList.add(new ProductSpecificationModel(0, documentSnapshot.get("spec_title_" + x).toString()));
                            for (long y = 1; y < Integer.parseInt(String.valueOf(documentSnapshot.get("spec_title_" + x + "_total_fields"))) + 1; y++) {
                                productSpecificationModelList.add(new ProductSpecificationModel(1, (String) documentSnapshot.get("spec_title_" + x + "_field" + y + "_name"), documentSnapshot.get("spec_title_" + x + "_field" + y + "_value").toString()));

                            }
                        }

                    } else {
                        productDetailsTab.setVisibility(View.GONE);
                        productDetailsOnly.setVisibility(View.VISIBLE);
                        productOnlyDescriptionBody.setText(documentSnapshot.get("product_description").toString());

                    }

                    totalRatings.setText(documentSnapshot.get("total_ratings") + " ratings");
                    for (int x = 0; x < 5; x++) {
                        TextView rating = (TextView) ratingsNoContainer.getChildAt(x);
                        rating.setText(String.valueOf(documentSnapshot.get(5 - x + "_star")));
                        ProgressBar progressBar = (ProgressBar) ratingsProgressbarContainer.getChildAt(x);
                        int maxprogress = Integer.parseInt(String.valueOf((long) documentSnapshot.get("total_ratings")));
                        progressBar.setMax(maxprogress);
                        progressBar.setProgress(Integer.parseInt(String.valueOf(documentSnapshot.get(5 - x + "_star"))));

                    }
                    totalratingsfigure.setText(String.valueOf(documentSnapshot.get("total_ratings")));
                    productsdetailsviewpager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(), productsdetailstablayout.getTabCount(), productDescription, productOtherDetails, productSpecificationModelList));
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        DBqueries.loadRatingList(ProductDetailsActivity.this);
                    } else {

                    }
                    if(DBqueries.myRatedIds.contains(productID)){
                        setRating(Integer.parseInt(String.valueOf(DBqueries.myratings.get(DBqueries.myRatedIds.indexOf(productID)))));
                    }
                    if(currentUser != null) {
                        if (DBqueries.wishList.size() == 0) {
                            DBqueries.loadWishlist(ProductDetailsActivity.this, loadingDialog, false);

                        } else {
                            loadingDialog.dismiss();
                        }
                    }
                    else{
                        loadingDialog.dismiss();
                    }
                    if(DBqueries.wishList.contains(productID)){
                        ALREADY_ADDED_TO_WISHLIST = true;
                        addfloatwishlistbtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#FFF42010")));
//
                    }
                    else{
                        ALREADY_ADDED_TO_WISHLIST = false;
                        addfloatwishlistbtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#B6B2B2")));
                    }
                    if(DBqueries.cartlist.contains(productID)){
                        ALREADY_ADDED_TO_CART = true;
                    }
                    else{
                        ALREADY_ADDED_TO_CART = false;

                    }
                    if((boolean)documentSnapshot.get("in_stock")){
                        linearLayout.setVisibility(View.VISIBLE);
                        addtocart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(ALREADY_ADDED_TO_CART){
                                    Toast.makeText(getApplicationContext(),"Already Added to Cart!",Toast.LENGTH_LONG).show();
                                }
                                else{
                                    if(!running_cart_query){
                                        running_cart_query = true;
                                        final Map<String,Object> addProduct = new HashMap<>();
                                        addProduct.put("PRODUCT_ID_"+String.valueOf(DBqueries.cartlist.size()),productID);
                                        addProduct.put("list_size", (long) (DBqueries.cartlist.size()+1));

                                        firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA")
                                                .document("MY_CART").update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){

                                                    if(cartModelList.size() != 0){

                                                        cartModelList.add(0,new CartItemModel(CartItemModel.CART_ITEM,productID,documentSnapshot.get("product_image_1").toString()
                                                                , documentSnapshot.get("product_title").toString()
                                                                , (long) documentSnapshot.get("free_coupon")
                                                                ,  documentSnapshot.get("product_price").toString()
                                                                , documentSnapshot.get("cutted_price").toString()
                                                                , (long)1
                                                                , (long)0
                                                                ,(long)0
                                                                ,(boolean)documentSnapshot.get("in_stock")));

                                                    }
                                                    ALREADY_ADDED_TO_CART = true;
                                                    DBqueries.cartlist.add(productID);
                                                    Toast.makeText(getApplicationContext(),"Added to cart successfully", Toast.LENGTH_SHORT).show();
                                                    invalidateOptionsMenu();
                                                    running_cart_query = false;

                                                }else{
                                                    running_cart_query = false;
                                                    String error = task.getException().getMessage();
                                                    Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });


                                    }
                                }
                            }
                        });
                    }else{

                        buyNowBtn.setVisibility(View.GONE);
                        addtocart.setText("Out of Stock");
                        addtocart.setTextColor(getResources().getColor(R.color.colorPrimary));
                        linearLayout.setVisibility(View.VISIBLE);
                    }



                }

                else{
                    String error = task.getException().getMessage();
                    Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();
                }




            }
        });





//        List<String > productimages = new ArrayList<>();
//        productimages.add(R.drawable.ic_launcher_background);
//        productimages.add(R.drawable.common_google_signin_btn_icon_dark);
//        productimages.add(R.drawable.ic_menu_gallery);
//        productimages.add(R.drawable.ic_menu_camera);


        viewpagerIndiacator.setupWithViewPager(productImagesviewpager,true);

        addfloatwishlistbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser ==null) {
                    SignInDialog.show();
                }
                else{
                    if(!running_wishlist_query) {
                        running_wishlist_query =true;
                        if (ALREADY_ADDED_TO_WISHLIST) {
                            int index = DBqueries.wishList.indexOf(productID);
                            DBqueries.removeFromWishlist(index, getApplicationContext());
                            addfloatwishlistbtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#B6B2B2")));

                        }
                        else {
                            Map<String, Object> addProduct = new HashMap<>();
                            addfloatwishlistbtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#FFF42010")));
                            addProduct.put("PRODUCT_ID_" + String.valueOf(DBqueries.wishList.size()), productID);

                            firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA")
                                    .document("MY_WISHLIST").update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {


                                        Map<String, Object> updateListSize = new HashMap<>();
                                        updateListSize.put("list_size", (long) (DBqueries.wishList.size() + 1));

                                        firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA")
                                                .document("MY_WISHLIST").update(updateListSize).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    if (wishlistModelList.size() != 0) {

                                                        DBqueries.wishlistModelList.add(new MyWishlistModel(productID
                                                                , documentSnapshot.get("product_image_1").toString()
                                                                , documentSnapshot.get("product_title").toString()
                                                                , (long) documentSnapshot.get("free_coupens")
                                                                , (long) documentSnapshot.get("total_rating")
                                                                , documentSnapshot.get("average_rating").toString()
                                                                , documentSnapshot.get("product_price").toString()
                                                                , documentSnapshot.get("cutted_price").toString()
                                                                , (boolean) documentSnapshot.get("COD")
                                                        ));

                                                    }
                                                    addfloatwishlistbtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#FFF42010")));
                                                    ALREADY_ADDED_TO_WISHLIST = true;
                                                    DBqueries.wishList.add(productID);
                                                    Toast.makeText(getApplicationContext(), "added to wishlist successfully", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    addfloatwishlistbtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#B6B2B2")));
                                                    String error = task.getException().getMessage();
                                                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                                                }
                                                running_wishlist_query = false;
                                            }
                                        });
                                    } else {
                                        running_wishlist_query = false;
                                        String error = task.getException().getMessage();
                                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                                    }
                                }
                            });


                        }
                    }
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
                        if(currentUser == null){
                            SignInDialog.show();
                        }else{
                            if(!running_rating_query && initialRating!=starposition){
                                running_rating_query = true ;
                           setRating(starposition);
                                Map<String,Object> updateRating = new HashMap<>();

                           if(DBqueries.myRatedIds.contains(productID)){
                               TextView oldRating = (TextView) ratingsNoContainer.getChildAt(4-initialRating);
                               TextView newRating = (TextView) ratingsNoContainer.getChildAt(4-starposition);
                               updateRating.put(initialRating+1+"_star",Long.parseLong(oldRating.getText().toString())-1);
                               updateRating.put(starposition+1+"_star",Long.parseLong(newRating.getText().toString())+1);
                               updateRating.put("average_rating", String.valueOf(calculateRating(starposition -initialRating,true)));

                           }else {
                               updateRating.put("total_ratings", (long) documentSnapshot.get("total_ratings") + 1);
                               updateRating.put((1 + starposition) + "_star", (long) documentSnapshot.get((1 + starposition) + "_star") + 1);
                               updateRating.put("average_rating", String.valueOf(calculateRating(starposition + 1,false)));
                           }
                                firebaseFirestore.collection("PRODUCTS").document(productID)
                                        .update( updateRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            final Map<String,Object> rating = new HashMap<>();
                                            if(DBqueries.myRatedIds.contains(productID)){
                                                rating.put("rating_"+(DBqueries.myRatedIds.indexOf(productID)),starposition+1);
                                            }else{
                                                rating.put("product_ID_"+DBqueries.myRatedIds.size(),productID);
                                                rating.put("rating_"+DBqueries.myRatedIds.size(),starposition+1);
                                                rating.put("list_size",DBqueries.myRatedIds.size()+1);

                                            }
                                            firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_RATING")
                                                    .update(rating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        TextView newRating = (TextView) ratingsNoContainer.getChildAt(4-starposition);

                                                        newRating.setText(String.valueOf(Long.parseLong(newRating.getText().toString())+1));
                                                        if(DBqueries.myRatedIds.contains(productID)){
                                                            TextView oldRating = (TextView) ratingsNoContainer.getChildAt(4-initialRating);
                                                            DBqueries.myratings.set(DBqueries.myRatedIds.indexOf(productID),(long)starposition+1);
                                                            oldRating.setText(String.valueOf(Long.parseLong(oldRating.getText().toString())-1));
                                                        }else{
                                                            DBqueries.myratings.add((long)starposition+1);
                                                            DBqueries.myRatedIds.add(productID);
                                                            totalRatings.setText(((long)documentSnapshot.get("total_ratings")+1) + " ratings");
                                                            totalratingminiview.setText("(" + ((long) documentSnapshot.get("total_ratings") +1)+ ") ratings");
                                                            totalratingsfigure.setText(String.valueOf((long)documentSnapshot.get("total_ratings")+1));

                                                        }



                                                        for (int x = 0; x < 5; x++) {
                                                            TextView ratingFigure = (TextView) ratingsNoContainer.getChildAt(x);
                                                            ProgressBar progressBar = (ProgressBar) ratingsProgressbarContainer.getChildAt(x);
                                                            int maxprogress = Integer.parseInt(totalratingsfigure.getText().toString());
                                                            progressBar.setMax(maxprogress);
                                                            progressBar.setProgress(Integer.parseInt(ratingFigure.getText().toString()));

                                                        }
                                                        initialRating = starposition;
                                                        averageratingminiview.setText(String.valueOf(calculateRating(0,true)));
                                                        averageRating.setText(String.valueOf(calculateRating(0,true)));
                                                        Toast.makeText(ProductDetailsActivity.this, "Thanks for Rating", Toast.LENGTH_SHORT).show();

                                                    }else{
                                                        setRating(initialRating);
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_LONG).show();
                                                    }
                                                    running_rating_query =false;
                                                }
                                            });
                                        }else{
                                            setRating(initialRating);
                                            String error = task.getException().getMessage();
                                            Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_LONG).show();
                                            running_rating_query = false;
                                        }
                                    }
                                });
                            }
                           }
                        }

                });
            }

        ///// rating layout
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
        /////// coupon dialog
        couuponredeembtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               checkcouponpricedialog.show();
            }
        });

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.show();
                DeliveryActivity.cartModelList = new ArrayList<>();
                DeliveryActivity.cartModelList.add(new CartItemModel(CartItemModel.CART_ITEM,productID,documentSnapshot.get("product_image_1").toString()
                        , documentSnapshot.get("product_title").toString()
                        , (long) documentSnapshot.get("free_coupon")
                        ,  documentSnapshot.get("product_price").toString()
                        , documentSnapshot.get("cutted_price").toString()
                        , (long)1
                        , (long)0
                        ,(long)0
                        ,(boolean)documentSnapshot.get("in_stock")));
                DeliveryActivity.cartModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT));
                if(DBqueries.myAddressList.size()==0){
                    DBqueries.loadAddress(ProductDetailsActivity.this,loadingDialog);
                }else{
                    loadingDialog.dismiss();
                    Navigation.findNavController(v).navigate(R.id.addAddressActivity);
                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){
           coupenRedemptionLayout.setVisibility(View.GONE);
        }else{
           coupenRedemptionLayout.setVisibility(View.VISIBLE);
        }
        if(currentUser!=null){
            if(DBqueries.myRatedIds.size()==0){
              DBqueries.loadRatingList(ProductDetailsActivity.this);
            }else {
              loadingDialog.dismiss();
            }

            }else{
            loadingDialog.dismiss();
        }
        if(DBqueries.myRatedIds.contains(productID)){
            setRating(Integer.parseInt(String.valueOf(DBqueries.myratings.get(DBqueries.myRatedIds.indexOf(productID)))));
        }
        if(currentUser != null) {
            if (DBqueries.wishList.size() == 0) {
                DBqueries.loadWishlist(ProductDetailsActivity.this, loadingDialog, false);

            } else {
               loadingDialog.dismiss();
            }
        }
        else{
            loadingDialog.dismiss();
        }
        if(DBqueries.wishList.contains(productID)){
            ALREADY_ADDED_TO_WISHLIST = true;
            addfloatwishlistbtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#FFF42010")));
//
        }
        else{
            ALREADY_ADDED_TO_WISHLIST = false;
            addfloatwishlistbtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#B6B2B2")));
        }
        if(DBqueries.cartlist.contains(productID)){
            ALREADY_ADDED_TO_CART = true;
        }
        else{
            ALREADY_ADDED_TO_CART = false;
        }
        invalidateOptionsMenu();
    }

    private double calculateRating(long currentUserRating, boolean update){
        long totalStar=0;
        for(int i=1;i<6;i++){
            TextView ratingX = (TextView) ratingsNoContainer.getChildAt(5-i);
            totalStar += Long.parseLong(ratingX.getText().toString())*i;
        }
        totalStar += currentUserRating;
        if(update){
            return (Math.floor((double) totalStar / (Long.parseLong(totalratingsfigure.getText().toString())) * 100) / 100);
        }else {
            return (Math.floor((double) totalStar / (Long.parseLong(totalratingsfigure.getText().toString()) + 1) * 100) / 100);
        }
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

    public static void setRating(int starposition) {
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
        cartitem = menu.findItem(R.id.main_cart_icon);
        cartitem.setActionView(R.layout.badge_layout);
        ImageView badge_icon = cartitem.getActionView().findViewById(R.id.badge_icon);
        badge_icon.setImageResource(R.mipmap.cart_white);
        badge_count = cartitem.getActionView().findViewById(R.id.badge_count);
        if(currentUser != null){
            if (DBqueries.cartlist.size() == 0) {
                DBqueries.loadCartlist(ProductDetailsActivity.this, loadingDialog, false, badge_count);
            }
            else
            {
                badge_count.setVisibility(View.VISIBLE);
                if(DBqueries.cartlist.size()<99) {
                    badge_count.setText(String.valueOf(cartlist.size()));
                }
                else {badge_count.setText("99");
                }
            }
        }
        cartitem.getActionView();
//                    .setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////
////                }
////            });


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