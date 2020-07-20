package com.example.e_commerce;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HorizontalScrollAdapter extends RecyclerView.Adapter<HorizontalScrollAdapter.ViewHolder> {
    private List<HorizontalScrollModel> horizontalScrollModelList;

    public HorizontalScrollAdapter(List<HorizontalScrollModel> horizontalScrollModelList) {
        this.horizontalScrollModelList = horizontalScrollModelList;
    }

    @NonNull
    @Override
    public HorizontalScrollAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalScrollAdapter.ViewHolder holder, int position) {
      int HorizontalScrollImage = horizontalScrollModelList.get(position).getHorizontalScrollImage();
      String HorizontalScrollTitle = horizontalScrollModelList.get(position).getHorizontalScrollTitle();
        String HorizontalScrollDescription = horizontalScrollModelList.get(position).getHorizontalScrollDescription();
        String HorizontalScrollPrice = horizontalScrollModelList.get(position).getHorizontalScrollPrice();
        holder.setHorizontalScrollImage(HorizontalScrollImage);
        holder.setHorizontalScrollDescription(HorizontalScrollDescription);
        holder.setHorizontalScrollTitle(HorizontalScrollTitle);
        holder.setHorizontalScrollPrice(HorizontalScrollPrice);
    }

    @Override
    public int getItemCount() {
         if(horizontalScrollModelList.size()>8)
             return 8;
        return horizontalScrollModelList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView HorizontalScrollImage;
        private TextView HorizontalScrollTitle;
        private  TextView HorizontalScrollDescription;
        private  TextView HorizontalScrollPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            HorizontalScrollImage = itemView.findViewById(R.id.h_s_product_image);
            HorizontalScrollTitle = itemView.findViewById(R.id.h_s_product_title);
            HorizontalScrollDescription = itemView.findViewById(R.id.h_s_product_description);
            HorizontalScrollPrice = itemView.findViewById(R.id.h_s_product_price);

        }

        public void setHorizontalScrollImage(int horizontalScrollImage) {
            HorizontalScrollImage.setImageResource(horizontalScrollImage);
        }

        public void setHorizontalScrollDescription(String horizontalScrollDescription) {
            HorizontalScrollDescription.setText(horizontalScrollDescription);
        }
        public void setHorizontalScrollTitle(String horizontalScrollTitle) {
            HorizontalScrollTitle.setText(horizontalScrollTitle);
        }
        public void setHorizontalScrollPrice(String horizontalScrollPrice) {
            HorizontalScrollPrice.setText(horizontalScrollPrice);
        }
    }
}
