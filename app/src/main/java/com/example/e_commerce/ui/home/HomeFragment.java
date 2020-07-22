package com.example.e_commerce.ui.home;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

import com.example.e_commerce.CategoryAdapter;
import com.example.e_commerce.CategoryModel;
import com.example.e_commerce.GridProductLayoutAdapter;
import com.example.e_commerce.HomePageAdapter;
import com.example.e_commerce.HomePageModel;
import com.example.e_commerce.HorizontalScrollAdapter;
import com.example.e_commerce.HorizontalScrollModel;
import com.example.e_commerce.R;
import com.example.e_commerce.SliderAdapter;
import com.example.e_commerce.SliderModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private List<SliderModel>  sliderModelList ;


    public static HomeFragment newInstance() {

        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        categoryRecyclerView = view.findViewById(R.id.category_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);
        List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();
        categoryModelList.add(new CategoryModel("link","Home"));
        categoryModelList.add(new CategoryModel("link","Aryan"));
        categoryModelList.add(new CategoryModel("link","Home"));
        categoryModelList.add(new CategoryModel("link","Home"));
        categoryModelList.add(new CategoryModel("link","Home"));
        categoryModelList.add(new CategoryModel("link","Home"));
        categoryModelList.add(new CategoryModel("link","Home"));
        categoryAdapter = new CategoryAdapter(categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

        sliderModelList = new ArrayList<>();
        sliderModelList.add(new SliderModel(R.drawable.ic_menu_gallery,"#ffffff"));
        sliderModelList.add(new SliderModel(R.drawable.googleg_standard_color_18,"#ffffff"));

        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#ffffff"));
        sliderModelList.add(new SliderModel(R.drawable.ic_menu_camera,"#ffffff"));
        sliderModelList.add(new SliderModel(R.drawable.common_full_open_on_phone,"#ffffff"));
        sliderModelList.add(new SliderModel(R.drawable.ic_menu_gallery,"#ffffff"));
        sliderModelList.add(new SliderModel(R.drawable.googleg_standard_color_18,"#ffffff"));

        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#ffffff"));
        sliderModelList.add(new SliderModel(R.drawable.ic_menu_camera,"#ffffff"));








        List<HorizontalScrollModel> horizontalScrollModelList = new ArrayList<>();
        horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.back_button,"Redmi 5A","Hello","5999"));
        horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.ic_menu_camera,"Redmi 5A","Hello","5999"));
        horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.ic_menu_slideshow,"Redmi 5A","Hello","5999"));
        horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.googleg_standard_color_18,"Redmi 5A","Hello","5999"));
        horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.ic_menu_gallery,"Redmi 5A","Hello","5999"));
        horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.submit,"Redmi 5A","Hello","5999"));
        horizontalScrollModelList.add(new HorizontalScrollModel(R.mipmap.ic_launcher,"Redmi 5A","Hello","5999"));


        ///////////////////////
        RecyclerView testing =  view.findViewById(R.id.home_page_recyclerview);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        testing.setLayoutManager(testingLayoutManager);
        List<HomePageModel> homePageModelList = new ArrayList<>();
        homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.mipmap.ic_launcher,"#ffffff"));
        homePageModelList.add(new HomePageModel(2,horizontalScrollModelList,"title"));
        homePageModelList.add(new HomePageModel(3,horizontalScrollModelList,"title"));
        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);
        testing.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        ///////////////////////
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel

    }
}