package com.example.e_commerce;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.e_commerce.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBqueries {
    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModelList = new ArrayList<>();

    public static HashMap<String,List<HomePageModel>> parentHashmap = new HashMap<>();
    public static void loadCategories(final RecyclerView categoryRecyclerView, final Context context){

        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
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
                                            ViewAllProductList.add(new MyWishlistModel(documentSnapshot.get("product_image_"+i).toString()
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
                            String error = task.getException().toString();
                            Toast.makeText(context, error, Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}
