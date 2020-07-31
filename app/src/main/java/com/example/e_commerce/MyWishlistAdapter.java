package com.example.e_commerce;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyWishlistAdapter extends RecyclerView.Adapter<MyWishlistAdapter.ViewHolder> {

    private boolean wishlist;
    List<MyWishlistModel> myWishlistModelList ;
    public MyWishlistAdapter(List<MyWishlistModel> myWishlistModelList,boolean wishlist) {
        this.myWishlistModelList = myWishlistModelList;
        this.wishlist = wishlist;
    }

    @NonNull
    @Override
    public MyWishlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyWishlistAdapter.ViewHolder holder, int position) {
        String title = myWishlistModelList.get(position).getProductTitle();
        String  cuttedPrice= myWishlistModelList.get(position).getCutted_price();
        String price = myWishlistModelList.get(position).getProductPrice();
        String totalRating = myWishlistModelList.get(position).getTotalRating();
        String paymentMethod = myWishlistModelList.get(position).getPaymentMethod();
        int productImage = myWishlistModelList.get(position).getProductImage();
        int freeCoupenNo = myWishlistModelList.get(position).getFreeCoupen();
        double rating = myWishlistModelList.get(position).getRating();
        holder.setData(productImage,title,freeCoupenNo,rating,totalRating,price,cuttedPrice,paymentMethod);
    }

    @Override
    public int getItemCount() {
        return myWishlistModelList.size();
    }
    public  class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView productImage;
        private TextView productTitle;
        private ImageView freeCoupenIcon;
        private TextView freeCoupen;
        private TextView rating;
        private TextView totalRating;
        private TextView price;
        private TextView cuttedPrice;
        private TextView paymentMethod;
        private View priceCut;
        private ImageButton deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            freeCoupenIcon = itemView.findViewById(R.id.free_cuopen_icon);
            freeCoupen = itemView.findViewById(R.id.free_coupen);
            rating  = itemView.findViewById(R.id.rating);
            totalRating = itemView.findViewById(R.id.total_rating);
            priceCut = itemView.findViewById(R.id.price_cut);
            price = itemView.findViewById(R.id.product_price);
            cuttedPrice = itemView.findViewById(R.id.cutted_price);
            paymentMethod = itemView.findViewById(R.id.payement_method);
            deleteBtn = itemView.findViewById(R.id.delete_btn);
        }
        private void setData(int resource,String title,int freeCoupenNo,double rating,String total_rating,String priceText,String cuttedPriceText,String paymentmode){
            productImage.setImageResource(resource);
            productTitle.setText(title);
            if(freeCoupenNo > 0){
                freeCoupen.setVisibility(View.VISIBLE);
                freeCoupenIcon.setVisibility(View.VISIBLE);
                if(freeCoupenNo>1){

                    freeCoupen.setText("Free "+freeCoupenNo+" Coupen");
                }else{
                    freeCoupen.setText("Free "+freeCoupenNo+" Coupens");
                }
            }else{
                freeCoupen.setVisibility(View.INVISIBLE);
                freeCoupenIcon.setVisibility(View.INVISIBLE);
            }
            this.rating.setText(rating+"");
            totalRating.setText(total_rating);
            price.setText(priceText);
            cuttedPrice.setText(cuttedPriceText);
            paymentMethod.setText(paymentmode);
            if(wishlist){
                deleteBtn.setVisibility(View.VISIBLE);
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(itemView.getContext(), "delete", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                deleteBtn.setVisibility(View.INVISIBLE);
            }

        }
    }
}
