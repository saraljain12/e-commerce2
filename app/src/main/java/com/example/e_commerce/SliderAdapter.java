package com.example.e_commerce;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public  class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.ViewHolder>{
     private  List<SliderModel> sliderModelList;
     public SliderAdapter(List<SliderModel> sliderModelList){
         this.sliderModelList = sliderModelList;

     }
    @NonNull
    @Override
    public SliderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout,parent,false);
         ViewHolder viewHolder= new ViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SliderAdapter.ViewHolder holder, int position) {
        int banner = sliderModelList.get(position).getBanner();
        holder.sliderLayout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(sliderModelList.get(position).getBackgroundColor())));
        holder.setImageView(banner);
    }

    @Override
    public int getItemCount() {
        return sliderModelList.size();
    }
    public class  ViewHolder extends  RecyclerView.ViewHolder{
     private  ImageView imageView;
     private ConstraintLayout sliderLayout ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.banner_slide);
            sliderLayout = itemView.findViewById(R.id.slider_layout);
        }
        private  void setImageView(int banner){
            imageView.setImageResource(banner);
        }
    }

}


