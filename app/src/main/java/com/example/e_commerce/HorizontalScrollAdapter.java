package com.example.e_commerce;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

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
      String HorizontalScrollImage = horizontalScrollModelList.get(position).getHorizontalScrollImage();
      String HorizontalScrollTitle = horizontalScrollModelList.get(position).getHorizontalScrollTitle();
        String HorizontalScrollDescription = horizontalScrollModelList.get(position).getHorizontalScrollDescription();
        String HorizontalScrollPrice = horizontalScrollModelList.get(position).getHorizontalScrollPrice();
        holder.setHorizontalScrollImage(HorizontalScrollImage,HorizontalScrollDescription,HorizontalScrollTitle,HorizontalScrollPrice);
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
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            HorizontalScrollImage = itemView.findViewById(R.id.h_s_product_image);
            HorizontalScrollTitle = itemView.findViewById(R.id.h_s_product_title);
            HorizontalScrollDescription = itemView.findViewById(R.id.h_s_product_description);
            HorizontalScrollPrice = itemView.findViewById(R.id.h_s_product_price);



        }

        public void setHorizontalScrollImage(String horizontalScrollImage,String horizontalScrollDescription,String horizontalScrollTitle,String horizontalScrollPrice) {
            Glide.with(itemView.getContext()).load(horizontalScrollImage).apply(new RequestOptions().placeholder(R.mipmap.house)).into(HorizontalScrollImage);
            HorizontalScrollDescription.setText(horizontalScrollDescription);
            HorizontalScrollTitle.setText(horizontalScrollTitle);
            if(!horizontalScrollTitle.equals("")) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent productdetailsintent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                        itemView.getContext().startActivity(productdetailsintent);
                    }
                });
            }
            HorizontalScrollPrice.setText(horizontalScrollPrice);
        }

    }
}
