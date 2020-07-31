package com.example.e_commerce;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GridProductLayoutAdapter extends BaseAdapter {
    List<HorizontalScrollModel> horizontalScrollModelList;

    public GridProductLayoutAdapter(List<HorizontalScrollModel> horizontalScrollModelList) {
        this.horizontalScrollModelList = horizontalScrollModelList;
    }

    @Override
    public int getCount() {
        return horizontalScrollModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        View view;
        if(convertView==null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,null);
            view.setBackgroundColor(Color.parseColor("#ffffff"));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent addressdetailsintent = new Intent(parent.getContext(),TestActivityForDelivery.class);
                    parent.getContext().startActivity(addressdetailsintent);
                }
            });
            ImageView productImage = view.findViewById(R.id.h_s_product_image);
            TextView productTitle = view.findViewById(R.id.h_s_product_title);
            TextView productDescription = view.findViewById(R.id.h_s_product_description);
            TextView productPrice = view.findViewById(R.id.h_s_product_price);
            productImage.setImageResource(horizontalScrollModelList.get(position).getHorizontalScrollImage());
            productTitle.setText(horizontalScrollModelList.get(position).getHorizontalScrollTitle());
            productDescription.setText(horizontalScrollModelList.get(position).getHorizontalScrollDescription());
            productPrice.setText(horizontalScrollModelList.get(position).getHorizontalScrollPrice());
        }else{
          view =convertView;
        }
        return view ;
    }
}
