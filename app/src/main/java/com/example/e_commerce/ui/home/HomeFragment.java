package com.example.e_commerce.ui.home;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.CategoryAdapter;
import com.example.e_commerce.CategoryModel;
import com.example.e_commerce.DBqueries;
import com.example.e_commerce.GridProductLayoutAdapter;
import com.example.e_commerce.HomePageAdapter;
import com.example.e_commerce.HomePageModel;
import com.example.e_commerce.HorizontalScrollAdapter;
import com.example.e_commerce.HorizontalScrollModel;
import com.example.e_commerce.LoginActivity;
import com.example.e_commerce.MainActivity;
import com.example.e_commerce.MyWishlistModel;
import com.example.e_commerce.ProductDetailsActivity;
import com.example.e_commerce.R;
import com.example.e_commerce.SliderAdapter;
import com.example.e_commerce.SliderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private ImageView noInternet;
    private Button retryBtn;
    private   boolean isConnected ;
    private  List<CategoryModel> categoryModelFakeList = new ArrayList<>();
    private RecyclerView homePageRecyclerView;
    private  HomePageAdapter adapter;
    private List<HomePageModel> homePageModelFakeList = new ArrayList<>();
    private ConnectivityManager connectivityManager;
    private NetworkInfo activeNetwork;
    public static SwipeRefreshLayout swipeRefreshLayout;
    public static HomeFragment newInstance() {

        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getActivity().findViewById(R.id.action_bar_logo).setVisibility(View.VISIBLE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        swipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        homePageRecyclerView = view.findViewById(R.id.home_page_recyclerview);
        categoryRecyclerView = view.findViewById(R.id.category_recyclerview);
        retryBtn = view.findViewById(R.id.retry_btn);

         connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
         activeNetwork = connectivityManager.getActiveNetworkInfo();
        noInternet = view.findViewById(R.id.no_internet_connection);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

         /////////////// categories fake list
        categoryModelFakeList.add(new CategoryModel("null",""));
        categoryModelFakeList.add(new CategoryModel("null",""));
        categoryModelFakeList.add(new CategoryModel("null",""));
        categoryModelFakeList.add(new CategoryModel("null",""));
        categoryModelFakeList.add(new CategoryModel("null",""));
        categoryModelFakeList.add(new CategoryModel("null",""));
        categoryModelFakeList.add(new CategoryModel("null",""));
        /////////////// categories fake list

        /////////////// home fake list
        List<SliderModel> sliderModelFakeList = new ArrayList<>();
        sliderModelFakeList.add(new SliderModel(null,"#ffffff"));
        sliderModelFakeList.add(new SliderModel(null,"#ffffff"));
        sliderModelFakeList.add(new SliderModel(null,"#ffffff"));
        sliderModelFakeList.add(new SliderModel(null,"#ffffff"));
        sliderModelFakeList.add(new SliderModel(null,"#ffffff"));

        List<HorizontalScrollModel> horizontalScrollModelFakeList = new ArrayList<>();
        horizontalScrollModelFakeList.add(new HorizontalScrollModel("","","","",""));
        horizontalScrollModelFakeList.add(new HorizontalScrollModel("","","","",""));
        horizontalScrollModelFakeList.add(new HorizontalScrollModel("","","","",""));
        horizontalScrollModelFakeList.add(new HorizontalScrollModel("","","","",""));
        horizontalScrollModelFakeList.add(new HorizontalScrollModel("","","","",""));
        horizontalScrollModelFakeList.add(new HorizontalScrollModel("","","","",""));
        horizontalScrollModelFakeList.add(new HorizontalScrollModel("","","","",""));

        homePageModelFakeList.add(new HomePageModel(0,sliderModelFakeList));
        homePageModelFakeList.add(new HomePageModel(3,horizontalScrollModelFakeList,"","#ffffff"));
        homePageModelFakeList.add(new HomePageModel(2,horizontalScrollModelFakeList,"","#ffffff",new ArrayList<MyWishlistModel>()));


        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homePageRecyclerView.setLayoutManager(testingLayoutManager);


        /////////////// home fake list

        categoryAdapter = new CategoryAdapter(categoryModelFakeList);


        adapter = new HomePageAdapter(homePageModelFakeList);
         homePageRecyclerView.setAdapter(adapter);
         adapter.notifyDataSetChanged();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if(isConnected){
            noInternet.setVisibility(View.GONE);
            retryBtn.setVisibility(View.GONE);
            categoryRecyclerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);
            if(DBqueries.categoryModelList.size()==0){
                DBqueries.loadCategories(categoryRecyclerView,getContext());
            }else{
                categoryAdapter = new CategoryAdapter(DBqueries.categoryModelList);
                categoryAdapter.notifyDataSetChanged();
            }
            categoryRecyclerView.setAdapter(categoryAdapter);


            if(DBqueries.parentHashmap.size() == 0){
                DBqueries.parentHashmap.put("HOME",new ArrayList<HomePageModel>());
                DBqueries.loadFragmentData(homePageRecyclerView,getContext(),"HOME");
                homePageRecyclerView.setAdapter(adapter);

            }else{

                adapter = new HomePageAdapter(DBqueries.parentHashmap.get("HOME"));
                adapter.notifyDataSetChanged();
            }
            homePageRecyclerView.setAdapter(adapter);
            OnBackPressedCallback callback = new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {

                    getActivity().finish();

                }
            };
            requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        }else{
            noInternet.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.VISIBLE);
            categoryRecyclerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);
        }
         ////////////////////// refresh layout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                reload();

            }
        });
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reload();
            }
        });
        /////////////////////// refresh layout
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel

    }
    private  void reload(){
        DBqueries.categoryModelList.clear();
        DBqueries.parentHashmap.clear();
        activeNetwork = connectivityManager.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnected();

        if(isConnected){
            noInternet.setVisibility(View.GONE);
            categoryRecyclerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.GONE);
            categoryAdapter = new CategoryAdapter(categoryModelFakeList);
            adapter = new HomePageAdapter(homePageModelFakeList);
            categoryRecyclerView.setAdapter(categoryAdapter);
            homePageRecyclerView.setAdapter(adapter);
            DBqueries.loadCategories(categoryRecyclerView,getContext());
            DBqueries.parentHashmap.put("HOME",new ArrayList<HomePageModel>());
            DBqueries.loadFragmentData(homePageRecyclerView,getContext(),"HOME");
        }else{
            noInternet.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.VISIBLE);
            categoryRecyclerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        }

    }


}