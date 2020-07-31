package com.example.e_commerce;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.example.e_commerce.R.layout.fragment_order_details;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {
    private List<MyOrderItemModel>myOrderItemModelList;

    public MyOrderAdapter(List<MyOrderItemModel> myOrderItemModelList) {
        this.myOrderItemModelList = myOrderItemModelList;
    }

    @NonNull
    @Override
    public MyOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.ViewHolder holder, int position) {
       int resource = myOrderItemModelList.get(position).getProductImage();
       String productTitle = myOrderItemModelList.get(position).getProductTitle();
       String deliveryStatus = myOrderItemModelList.get(position).getDeliveryStatus();
       int rating = myOrderItemModelList.get(position).getRating();
       holder.setData(resource,productTitle,deliveryStatus,rating);
    }

    @Override
    public int getItemCount() {
        return myOrderItemModelList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView productImage;
        private TextView productTitle;
        private TextView deliveryStatus;
        private ImageView deliveryIndicator;
        private LinearLayout starLayout;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            deliveryStatus = itemView.findViewById(R.id.order_delivered_date);
            deliveryIndicator = itemView.findViewById(R.id.order_indicator);
            starLayout = itemView.findViewById(R.id.order_rate_layout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(itemView).navigate(R.id.order_detail);
                }
            });

        }

        private void setData(int resource,String title,String deliverdDate,int rating) {
            productImage.setImageResource(resource);
            productTitle.setText(title);
            if (deliverdDate.equals("Cancelled"))
                deliveryIndicator.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
            else
                deliveryIndicator.setImageTintList(ColorStateList.valueOf(Color.parseColor("#08B533")));


            setRating(rating);

            for (int i = 0; i < starLayout.getChildCount(); i++) {
                final int starPosition = i;
                starLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setRating(starPosition);
                    }
                });
            }

            deliveryStatus.setText(deliverdDate);
        }

        private void setRating(int starPosition){
            for(int i=0;i<starLayout.getChildCount();i++){
                ImageView starbtn = (ImageView)starLayout.getChildAt(i);
                starbtn.setColorFilter(Color.rgb(191,191,191));
                if(i<=starPosition)
                    starbtn.setColorFilter(Color.rgb(255,187,0));
            }

        }

    }
}
