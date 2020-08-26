package com.example.e_commerce;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.e_commerce.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.e_commerce.MyCartFragment.cartTotal;
import static com.example.e_commerce.ProductDetailsActivity.addfloatwishlistbtn;
import static com.example.e_commerce.ProductDetailsActivity.addtocart;
import static com.example.e_commerce.ProductDetailsActivity.productID;
import static com.example.e_commerce.ProductDetailsActivity.running_rating_query;

public class DBqueries {

    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModelList = new ArrayList<>();

    public static List<String> myRatedIds =  new ArrayList<>();
    public static List<Long> myratings = new ArrayList<>();

    public static HashMap<String,List<HomePageModel>> parentHashmap = new HashMap<>();

    public static List<AdressesModel> myAddressList = new ArrayList<>();
    public static int selectedAddress =-1;

    public static List<String> cartlist = new ArrayList<>();
    public static List<CartItemModel> cartModelList = new ArrayList<>();

    public static List<String> wishList = new ArrayList<>();
    public static List<MyWishlistModel> wishlistModelList = new ArrayList<>();
    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static FirebaseUser currentuser = firebaseAuth.getCurrentUser();

    public static void loadCategories(final RecyclerView categoryRecyclerView, final Context context){

        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            categoryModelList.clear();
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                categoryModelList.add(new CategoryModel(documentSnapshot.get("icon").toString(),documentSnapshot.get("categoryName").toString()));
                            }
                             CategoryAdapter categoryAdapter = new CategoryAdapter(categoryModelList);
                            categoryRecyclerView.setAdapter(categoryAdapter);
                            categoryAdapter.notifyDataSetChanged();
                        }else{
                            String error = task.getException().toString();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static  void loadFragmentData(final RecyclerView homePageRecyclerView, final  Context context, final String index){
        firebaseFirestore.collection("CATEGORIES").document(index.toUpperCase())
                .collection("TOP_DEALs").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                          // parentHashmap.put(index.toUpperCase(),new ArrayList<HomePageModel>());
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                Long l = new Long((long)documentSnapshot.get("view_type"));
                                switch (l.intValue()){
                                    case 0:
                                        List<SliderModel>sliderModelList = new ArrayList<>();
                                        long no_of_banners = (long)documentSnapshot.get("no_of_banner");
                                        for(int i=1;i<no_of_banners+1;i++){
                                            sliderModelList.add(new SliderModel(documentSnapshot.get("banner_"+i).toString(),documentSnapshot.get("banner_"+i+"_background").toString()));
                                        }
                                        parentHashmap.get(index.toUpperCase()).add(new HomePageModel(0,sliderModelList));
                                        break;
                                    case 1:
                                        parentHashmap.get(index.toUpperCase()).add(new HomePageModel(1,documentSnapshot.get("strip_ad_banner").toString()
                                                ,documentSnapshot.get("background").toString()));
                                        break;
                                    case 2:
                                        List<MyWishlistModel> ViewAllProductList = new ArrayList<>();
                                        List<HorizontalScrollModel> horizontalScrollModelList = new ArrayList<>();
                                        long no_of_products = (long)documentSnapshot.get("no_of_products");
                                        for(int i=1;i<no_of_products+1;i++){
                                            horizontalScrollModelList.add(new HorizontalScrollModel(documentSnapshot.get("product_ID_"+i).toString(),
                                                    documentSnapshot.get("product_image_"+i).toString(),
                                                    documentSnapshot.get("product_title_"+i).toString(),
                                                    documentSnapshot.get("product_subtitle_"+i).toString(),
                                                    documentSnapshot.get("product_price_"+i).toString()
                                            ));
                                            ViewAllProductList.add(new MyWishlistModel(documentSnapshot.get("product_ID_"+i).toString(),documentSnapshot.get("product_image_"+i).toString()
                                            ,documentSnapshot.get("product_full_title_"+i).toString()
                                            ,(long)documentSnapshot.get("free_coupens_"+i)
                                            ,(long)documentSnapshot.get("total_rating_"+i)
                                            ,documentSnapshot.get("average_rating_"+i).toString()
                                            ,documentSnapshot.get("product_price_"+i).toString()
                                            ,documentSnapshot.get("cutted_price_"+i).toString()
                                            ,(boolean)documentSnapshot.get("COD_"+i)
                                            ));
                                        }
                                        parentHashmap.get(index.toUpperCase()).add(new HomePageModel(2,horizontalScrollModelList,documentSnapshot.get("layout_title").toString()
                                                ,documentSnapshot.get("layout_background").toString(),ViewAllProductList));

                                        break;
                                    case 3:
                                        List<HorizontalScrollModel> horizontalScrollModels = new ArrayList<>();
                                        long no_of_product = (long)documentSnapshot.get("no_of_products");
                                        for(int i=1;i<no_of_product+1;i++){
                                            horizontalScrollModels.add(new HorizontalScrollModel(documentSnapshot.get("product_ID_"+i).toString(),
                                                    documentSnapshot.get("product_image_"+i).toString(),
                                                    documentSnapshot.get("product_title_"+i).toString(),
                                                    documentSnapshot.get("product_subtitle_"+i).toString(),
                                                    documentSnapshot.get("product_price_"+i).toString()
                                            ));
                                        }
                                        parentHashmap.get(index.toUpperCase()).add(new HomePageModel(3,horizontalScrollModels,documentSnapshot.get("layout_title").toString()
                                                ,documentSnapshot.get("layout_background").toString()));
                                        break;
                                    default:
                                      return;

                                }

                                HomePageAdapter adapter = new HomePageAdapter(parentHashmap.get(index.toUpperCase()));
                                homePageRecyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                HomeFragment.swipeRefreshLayout.setRefreshing(false);
                            }
                        }else{
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    public static void loadRatingList(final Context context){
        myRatedIds.clear();
        myratings.clear();
        if(!ProductDetailsActivity.running_rating_query) {
            ProductDetailsActivity.running_rating_query=true;
            firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                    .document("MY_RATING").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {
                            myRatedIds.add(task.getResult().get("product_ID_" + x).toString());
                            myratings.add((long) task.getResult().get("rating_" + x));
                            if (task.getResult().get("product_ID_" + x).toString().equals(ProductDetailsActivity.productID)) {
                                if(ProductDetailsActivity.rateNowContainer!=null) {
                                    ProductDetailsActivity.setRating(Integer.parseInt(String.valueOf((long) task.getResult().get("rating_" + x))) - 1);
                                }

                                ProductDetailsActivity.initialRating = Integer.parseInt(String.valueOf((long) task.getResult().get("rating_" + x))) - 1;

                            }
                        }
                    } else {

                        String error = task.getException().getMessage();
                        Toast.makeText(context, error, Toast.LENGTH_LONG).show();
                    }
                    ProductDetailsActivity.running_rating_query =false;
                }
            });
        }
    }

    public static void loadAddress(final Context context){
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                .document("MY_ADDRESS").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
              if(task.isSuccessful()){
                  myAddressList.clear();
                  Intent deliveryIntent;
                  if((long)task.getResult().get("list_size")==(long)0){
                      deliveryIntent = new Intent(context,AddAddressActivity.class);
                      deliveryIntent.putExtra("INTENT","deliveryIntent");
                  }else{
                      deliveryIntent = new Intent(context,DeliveryActivity.class);
                      for(int i=1;i<(long)task.getResult().get("list_size")+1;i++){
                          myAddressList.add(new AdressesModel(task.getResult().get("fullname_"+i).toString(),
                                  task.getResult().get("address_"+i).toString(),
                                  task.getResult().get("pincode_"+i).toString(),
                                  (boolean)task.getResult().get("selected_"+i)));
                          if((boolean)task.getResult().get("selected_"+i)){
                              selectedAddress = i-1 ;
                          }
                      }
                  }
                  context.startActivity(deliveryIntent);
              }else{
                  String error = task.getException().getMessage();
                  Toast.makeText(context, error, Toast.LENGTH_LONG).show();
              }
            }
        });

    }

    public  static void loadWishlist(final Context context, final Dialog dialog, final boolean loadproductData){
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_WISHLIST")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    wishList.clear();
                    wishlistModelList.clear();
                    for(long x = 0; x<(long)task.getResult().get("list_size");x++){
                        wishList.add(task.getResult().get("PRODUCT_ID_"+x).toString());
                        if(loadproductData) {
                            final long finalX = x;
                            final String product_ID = task.getResult().get("PRODUCT_ID_"+x).toString();
                            firebaseFirestore.collection("PRODUCTS").document(task.getResult().get("PRODUCT_ID_" + x).toString())
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if(DBqueries.wishList.contains(ProductDetailsActivity.productID))
                                        {
                                            ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = true;
                                            if(addfloatwishlistbtn != null) {
                                                addfloatwishlistbtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#FFF42010")));
                                            }
                                        }else{
                                            if(addfloatwishlistbtn != null) {
                                                ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = false;
                                                addfloatwishlistbtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#B6B2B2")));
                                            }
                                        }
                                        wishlistModelList.add(new MyWishlistModel(product_ID ,
                                                task.getResult().get("product_image_1").toString()
                                                , task.getResult().get("product_title").toString()
                                                , (long) task.getResult().get("free_coupon")
                                                , (long) task.getResult().get("total_ratings")
                                                , task.getResult().get("average_rating").toString()
                                                , task.getResult().get("product_price").toString()
                                                , task.getResult().get("cutted_price").toString()
                                                , (boolean) task.getResult().get("COD")
                                        ));
                                        MyWishlistFragment.myWishlistAdapter.notifyDataSetChanged();
                                    } else {
                                        String error = task.getException().toString();
                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }


                    }
                    if(DBqueries.wishList.contains(productID)){
                        ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = true;
                        if(!loadproductData) {
                            addfloatwishlistbtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#FFF42010")));
                        }
                    }
                    else{
                        ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = false;
                        if(!loadproductData) {
                            addfloatwishlistbtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#B6B2B2")));
                        }
                    }



                }
                else{
                    String error = task.getException().toString();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

    }

    public static void removeFromWishlist(final int index, final Context context){

        wishList.remove(index);
        Map<String ,Object> updateWishlist = new HashMap<>();
        for(int x= 0; x< wishList.size();x++){
            updateWishlist.put("product_ID_" + x,wishList.get(x));
        }
        updateWishlist.put("list_size",wishList.size());

        firebaseFirestore.collection("USERS").document(firebaseAuth.getUid()).collection("USER_DATA").
                document("MY_WISHLIST").set(updateWishlist).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    if(wishlistModelList.size() != 0){
                        wishlistModelList.remove(index);
                        MyWishlistFragment.myWishlistAdapter.notifyDataSetChanged();

                    }
                    Toast.makeText(context,"Removed Successfully",Toast.LENGTH_LONG).show();
                    ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = false;
                }
                else{
                    if(addfloatwishlistbtn != null) {
                        addfloatwishlistbtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#FFF42010")));
                    }
                    String error = task.getException().toString();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                ProductDetailsActivity.running_wishlist_query = false;
            }
        });


    }

    public static void loadCartlist(final Context context, final Dialog dialog, final boolean loadproductData, final TextView badge_count){

        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_CART")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    cartlist.clear();
                    cartModelList.clear();
                    final long size = (long)task.getResult().get("list_size")-1;
                    for(long x = 0; x<(long)task.getResult().get("list_size");x++){
                        cartlist.add(task.getResult().get("PRODUCT_ID_"+x).toString());
                        if(DBqueries.cartlist.contains(productID)){
                            ProductDetailsActivity.ALREADY_ADDED_TO_CART = true;
                        }
                        else{
                            ProductDetailsActivity.ALREADY_ADDED_TO_CART = false;
                        }


                        if(loadproductData) {
                            final String productID = task.getResult().get("PRODUCT_ID_" + x).toString();
                            final long finalX = x;
                            firebaseFirestore.collection("PRODUCTS").document(productID)
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        cartModelList.add(new CartItemModel(CartItemModel.CART_ITEM,task.getResult().get("product_image_1").toString(),productID
                                                , task.getResult().get("product_title").toString()
                                                , (long) task.getResult().get("free_coupon")
                                                ,  task.getResult().get("product_price").toString()
                                                , task.getResult().get("cutted_price").toString()
                                                , (long)1
                                                , (long)0
                                                ,(long)0
                                                ,(boolean)task.getResult().get("in_stock")));

                                        Log.d("saral123",String.valueOf(cartlist.size()));
                                        if(cartlist.size() == 0){
                                            cartModelList.clear();
                                        }
                                        if((long) finalX ==size){
                                            cartModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT)) ;
                                            MyCartFragment.cartTotal.setVisibility(View.VISIBLE);

                                        }
                                        MyCartFragment.cartAdapter.notifyDataSetChanged();

                                    } else {
                                        String error = task.getException().toString();
                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }


                    }

                    if(cartlist.size() != 0){
                        badge_count.setVisibility(View.VISIBLE);
                    }
                    else{
                        badge_count.setVisibility(View.INVISIBLE);
                    }
                    if(DBqueries.cartlist.size()<99) {
                        badge_count.setText(String.valueOf(cartlist.size()));
                    }
                    else {badge_count.setText("99");
                    }

                }
                else{
                    String error = task.getException().toString();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
    }

    public static void removeFromCart(final int index, final Context context){
        final String removedProductId = cartlist.get(index);
        cartlist.remove(index);
        Map<String ,Object> UpdateCartlist = new HashMap<>();
        for(int x= 0; x< cartlist.size();x++){
            UpdateCartlist.put("product_ID_" + x,cartlist.get(x));
        }
        UpdateCartlist.put("list_size",cartlist.size());

        firebaseFirestore.collection("USERS").document(firebaseAuth.getUid()).collection("USER_DATA").
                document("MY_CART").set(UpdateCartlist).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    if(cartModelList.size() != 0){
                        cartModelList.remove(index);
                        MyCartFragment.cartAdapter.notifyDataSetChanged();

                    }
                    if(cartlist.size() == 0){
                        cartModelList.clear();
                        cartTotal.setVisibility(View.INVISIBLE);
                    }

                    Toast.makeText(context,"Removed Successfully",Toast.LENGTH_LONG).show();

                }
                else{
                    cartlist.add(index,removedProductId);
                    String error = task.getException().toString();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }

            }
        });
        ProductDetailsActivity.running_cart_query = false;


    }

    public static void cleardata(){
        categoryModelList.clear();
        wishList.clear();
        wishlistModelList.clear();

    }
}
