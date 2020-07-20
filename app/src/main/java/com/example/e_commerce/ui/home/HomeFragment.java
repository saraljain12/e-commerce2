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
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    ////////// Banner Slider
    private ViewPager2 bannerSliderViewPager;
    private List<SliderModel>  sliderModelList ;
    private  int currentpage = 2 ;
    private Timer timer;
    final private long  DELAY_TIME =3000;
    final private long  PERIOD_TIME =3000;
    ////////// Banner Slider

    ////////// strip ad
    private ImageView stripAdImage;
    private ConstraintLayout stripAdLayout;
    ////////// strip ad

    ////////// Horizontal Scroll
    private RecyclerView HorizontalScrollRecyclerView;
    private TextView HorizontalLayoutTitle;
    private Button HorizontalLayoutButton;
    ////////// Horizontal Scroll

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
        categoryModelList.add(new CategoryModel("link","Home"));
        categoryModelList.add(new CategoryModel("link","Home"));
        categoryModelList.add(new CategoryModel("link","Home"));
        categoryModelList.add(new CategoryModel("link","Home"));
        categoryModelList.add(new CategoryModel("link","Home"));
        categoryModelList.add(new CategoryModel("link","Home"));
        categoryAdapter = new CategoryAdapter(categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
        ////////// Banner Slider
        bannerSliderViewPager = view.findViewById(R.id.banner_slider_view_pager);
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

        SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
        bannerSliderViewPager.setAdapter(sliderAdapter);

     //   bannerSliderViewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        sliderAdapter.notifyDataSetChanged();
        bannerSliderViewPager.setClipToPadding(false);
        bannerSliderViewPager.setCurrentItem(currentpage);
        bannerSliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                currentpage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if(state == ViewPager2.SCROLL_STATE_IDLE)
                    pageLooper();
            }
        });
        startbannerSlideShow();
         bannerSliderViewPager.getChildAt(0).setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 pageLooper();
                 stopbannerSlideShow();
                 if(event.getAction()==MotionEvent.ACTION_UP)
                     startbannerSlideShow();
                 return false;
             }
         });


        ////////// Banner Slider

        ////////// strip ad
         stripAdLayout = view.findViewById(R.id.strip_ad_container);
         stripAdImage = view.findViewById(R.id.strip_ad_image);
         stripAdImage.setImageResource(R.mipmap.ic_launcher);
         stripAdLayout.setBackgroundColor(Color.parseColor("#000000"));
        ////////// strip ad

        ////////// Horizontal Scroll

         HorizontalLayoutTitle = view.findViewById(R.id.horizontal_scroll_layout_title);
         HorizontalLayoutButton = view.findViewById(R.id.horizontal_scroll_layout_btn);
        HorizontalScrollRecyclerView = view.findViewById(R.id.horizontal_scroll_layout_recyclerView);
        List<HorizontalScrollModel> horizontalScrollModelList = new ArrayList<>();
        horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.back_button,"Redmi 5A","Hello","5999"));
        horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.ic_menu_camera,"Redmi 5A","Hello","5999"));
        horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.ic_menu_slideshow,"Redmi 5A","Hello","5999"));
        horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.googleg_standard_color_18,"Redmi 5A","Hello","5999"));
        horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.ic_menu_gallery,"Redmi 5A","Hello","5999"));
        horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.submit,"Redmi 5A","Hello","5999"));
        horizontalScrollModelList.add(new HorizontalScrollModel(R.mipmap.ic_launcher,"Redmi 5A","Hello","5999"));

        HorizontalScrollAdapter horizontalScrollAdapter = new HorizontalScrollAdapter(horizontalScrollModelList);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        HorizontalScrollRecyclerView.setLayoutManager(layoutManager1);
        HorizontalScrollRecyclerView.setAdapter(horizontalScrollAdapter);
        horizontalScrollAdapter.notifyDataSetChanged();
        ////////// Horizontal Scroll

        ////////// Grid Product Layout
        TextView gridLayoutTitle = view.findViewById(R.id.grid_product_layout_title);
        Button gridLayoutViewAllBtn = view.findViewById(R.id.grid_product_layout_viewall_btn);
        GridView gridView = view.findViewById(R.id.grid_product_layout_gridview);

        gridView.setAdapter(new GridProductLayoutAdapter(horizontalScrollModelList));
        ////////// Grid Product Layout

        ///////////////////////
        RecyclerView testing =  view.findViewById(R.id.testing);
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
    ///////// Banner Slider
    private  void pageLooper(){
        if(currentpage == sliderModelList.size()-2){
            currentpage = 2;
            bannerSliderViewPager.setCurrentItem(currentpage,false);
        }
        if(currentpage == 1){
            currentpage = sliderModelList.size()-3;
            bannerSliderViewPager.setCurrentItem(currentpage,false);
        }


    }
    private void startbannerSlideShow(){
        final Handler handler  = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if(currentpage>=sliderModelList.size())
                    currentpage =1;
                bannerSliderViewPager.setCurrentItem(currentpage++,true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },DELAY_TIME,PERIOD_TIME);

    }
    private void stopbannerSlideShow(){
        timer.cancel();
    }
    ///////// Banner Slider
}