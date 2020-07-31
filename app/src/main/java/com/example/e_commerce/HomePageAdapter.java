package com.example.e_commerce;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdapter extends RecyclerView.Adapter {
    private List<HomePageModel> homePageModelList;
    private  RecyclerView.RecycledViewPool recycledViewPool;
    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    public int getItemViewType(int position) {
        switch (homePageModelList.get(position).getType()){
            case 0:
                return HomePageModel.BANNER_SLIDER;
            case 1:
                return HomePageModel.STRIP_AD_BANNER;
            case 2:
                return HomePageModel.HORIZONTAL_SCROLL_PRODUCT;
            case 3:
                return  HomePageModel.GRID_PRODUCT_LAYOUT;
            default:
                return -1;
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case HomePageModel.BANNER_SLIDER:
                View bannerSliderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sliding_ad_layout,parent,false);
                return new BannerSliderViewHolder(bannerSliderView);
            case HomePageModel.STRIP_AD_BANNER:
                View StripAdView = LayoutInflater.from(parent.getContext()).inflate(R.layout.strip_ad_layout,parent,false);
                return new StripAdViewHolder(StripAdView);
            case HomePageModel.HORIZONTAL_SCROLL_PRODUCT:
                View horizontalScrollView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_layout,parent,false);
                return new HorizontalScrollViewHolder(horizontalScrollView);
            case HomePageModel.GRID_PRODUCT_LAYOUT:
                View gridProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product_layout,parent,false);
                return new GridProductViewHolder(gridProductView);

            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (homePageModelList.get(position).getType()){
            case HomePageModel.BANNER_SLIDER:
                 List<SliderModel> sliderModelList = homePageModelList.get(position).getSliderModelList();
                ((BannerSliderViewHolder)holder).setBannerSliderViewPager(sliderModelList);
                break;
            case HomePageModel.STRIP_AD_BANNER:
                int resource = homePageModelList.get(position).getResource();
                String color = homePageModelList.get(position).getBackgroundColor();
                ((StripAdViewHolder)holder).setStripAd(resource,color);
                break;
            case HomePageModel.HORIZONTAL_SCROLL_PRODUCT:
                List<HorizontalScrollModel> horizontalScrollModelList = homePageModelList.get(position).getHorizontalScrollModelList();
                String title = homePageModelList.get(position).getTitle();
                ((HorizontalScrollViewHolder)holder).setHorizontalLayout(horizontalScrollModelList,title);
                break;
             case HomePageModel.GRID_PRODUCT_LAYOUT:
                List<HorizontalScrollModel> gridLayoutModelList = homePageModelList.get(position).getHorizontalScrollModelList();
                String gridLayoutTitle = homePageModelList.get(position).getTitle();
                 ((GridProductViewHolder)holder).setGridProductLayout(gridLayoutModelList,gridLayoutTitle);
                break;
            default:
                return;
        }


    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }






    public  class BannerSliderViewHolder extends  RecyclerView.ViewHolder{
        private ViewPager2 bannerSliderViewPager;
        private  int currentpage ;
        private Timer timer;
        private  List<SliderModel> arrangedList;
        final private long  DELAY_TIME =3000;
        final private long  PERIOD_TIME =3000;
        public BannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            bannerSliderViewPager = itemView.findViewById(R.id.banner_slider_view_pager);

        }

        public void setBannerSliderViewPager(final List<SliderModel>sliderModelList) {
            currentpage = 2 ;
            if(timer!=null){
                timer.cancel();
            }
            arrangedList = new ArrayList<>();
            SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
            bannerSliderViewPager.setAdapter(sliderAdapter);
//            new TabLayoutMediator(bannerSlidertablayout, bannerSliderViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
//                @Override
//                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
//                              bannerSliderViewPager.setCurrentItem(position,true);
//                }
//            }).attach();
//            bannerSliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//                @Override
//                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                    super.onPageScrolled(position, positionOffset, positionOffsetPixels);
//                    new TabLayout.TabLayoutOnPageChangeListener(bannerSlidertablayout);
//                }
//            });
//            bannerSlidertablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//                @Override
//                public void onTabSelected(TabLayout.Tab tab) {
//                    bannerSliderViewPager.setCurrentItem(bannerSlidertablayout.getSelectedTabPosition());
//                }
//
//                @Override
//                public void onTabUnselected(TabLayout.Tab tab) {
//
//                }
//
//                @Override
//                public void onTabReselected(TabLayout.Tab tab) {
//
//                }
//            });
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
                        pageLooper(sliderModelList);
                }
            });
            startbannerSlideShow(sliderModelList);
            bannerSliderViewPager.getChildAt(0).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    pageLooper(sliderModelList);
                    stopbannerSlideShow();
                    if(event.getAction()==MotionEvent.ACTION_UP)
                        startbannerSlideShow(sliderModelList);
                    return false;
                }
            });

        }

        private  void pageLooper(final List<SliderModel>sliderModelList){
            if(currentpage == sliderModelList.size()-2){
                currentpage = 2;
                bannerSliderViewPager.setCurrentItem(currentpage,false);
            }
            if(currentpage == 1){
                currentpage = sliderModelList.size()-3;
                bannerSliderViewPager.setCurrentItem(currentpage,false);
            }


        }
        private void startbannerSlideShow(final List<SliderModel>sliderModelList){
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
    }

    public  class StripAdViewHolder extends RecyclerView.ViewHolder{
        private ImageView stripAdImage;
        private ConstraintLayout stripAdLayout;
        public StripAdViewHolder(@NonNull View itemView) {
            super(itemView);
            stripAdLayout = itemView.findViewById(R.id.strip_ad_container);
            stripAdImage = itemView.findViewById(R.id.strip_ad_image);

        }

        public void setStripAd(int resource,String color) {
            stripAdImage.setImageResource(resource);
            stripAdLayout.setBackgroundColor(Color.parseColor(color));
        }
    }

    public class HorizontalScrollViewHolder extends RecyclerView.ViewHolder{
        private RecyclerView HorizontalScrollRecyclerView;
        private TextView HorizontalLayoutTitle;
        private Button HorizontalLayoutButton;
        public HorizontalScrollViewHolder(@NonNull View itemView) {
            super(itemView);
            HorizontalLayoutTitle = itemView.findViewById(R.id.horizontal_scroll_layout_title);
            HorizontalLayoutButton = itemView.findViewById(R.id.horizontal_scroll_layout_btn);
            HorizontalScrollRecyclerView = itemView.findViewById(R.id.horizontal_scroll_layout_recyclerView);
            HorizontalScrollRecyclerView.setRecycledViewPool(recycledViewPool);
        }

        public void setHorizontalLayout(List<HorizontalScrollModel> horizontalScrollModelList,String title) {
            HorizontalLayoutTitle.setText((title));
            if(horizontalScrollModelList.size()>8){
                HorizontalLayoutButton.setVisibility(View.VISIBLE);
                HorizontalLayoutButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent ViewAllIntent = new Intent(itemView.getContext(),ViewAllActivity.class);
                        ViewAllIntent.putExtra("layout",0);
                        itemView.getContext().startActivity(ViewAllIntent);
                    }
                });
            }else{
                HorizontalLayoutButton.setVisibility(View.INVISIBLE);
            }
            HorizontalScrollAdapter horizontalScrollAdapter = new HorizontalScrollAdapter(horizontalScrollModelList);
            LinearLayoutManager layoutManager1 = new LinearLayoutManager(itemView.getContext());
            layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
            HorizontalScrollRecyclerView.setLayoutManager(layoutManager1);
            HorizontalScrollRecyclerView.setAdapter(horizontalScrollAdapter);
            horizontalScrollAdapter.notifyDataSetChanged();
        }
    }

    public class GridProductViewHolder extends RecyclerView.ViewHolder{
        TextView gridLayoutTitle;
        Button gridLayoutViewAllBtn;
        private GridLayout gridLayout;
        public GridProductViewHolder(@NonNull View itemView) {
            super(itemView);
             gridLayoutTitle = itemView.findViewById(R.id.grid_product_layout_title);
             gridLayoutViewAllBtn = itemView.findViewById(R.id.grid_product_layout_viewall_btn);
             gridLayout = itemView.findViewById(R.id.grid_layout);
         }
        public  void setGridProductLayout(List<HorizontalScrollModel>horizontalScrollModelList,String title){
            gridLayoutTitle.setText(title);
            for(int i =0;i<4;i++){
                ImageView productImage = gridLayout.getChildAt(i).findViewById(R.id.h_s_product_image);
                TextView productTitle = gridLayout.getChildAt(i).findViewById(R.id.h_s_product_title);
                TextView productDescription = gridLayout.getChildAt(i).findViewById(R.id.h_s_product_description);
                TextView productPrice = gridLayout.getChildAt(i).findViewById(R.id.h_s_product_price);
                gridLayout.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
                productImage.setImageResource(horizontalScrollModelList.get(i).getHorizontalScrollImage());
                productTitle.setText(horizontalScrollModelList.get(i).getHorizontalScrollTitle());
                productDescription.setText(horizontalScrollModelList.get(i).getHorizontalScrollDescription());
                productPrice.setText(horizontalScrollModelList.get(i).getHorizontalScrollPrice());

            }
            gridLayoutViewAllBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ViewAllIntent = new Intent(itemView.getContext(),ViewAllActivity.class);
                    ViewAllIntent.putExtra("layout",1);
                    itemView.getContext().startActivity(ViewAllIntent);
                }
            });
        }
    }

}
