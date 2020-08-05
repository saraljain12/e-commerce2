package com.example.e_commerce;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {
    private List<CartItemModel> cartItemModelList;

    public CartAdapter(List<CartItemModel> cartItemModelList) {
        this.cartItemModelList = cartItemModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (cartItemModelList.get(position).getType()){
            case 0:
                return CartItemModel.CART_ITEM;
            case 1:
                return CartItemModel.TOTAL_AMOUNT;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case CartItemModel.CART_ITEM:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);
                return new CartItemViewholder(view);
            case CartItemModel.TOTAL_AMOUNT:
                View cartTotalAmountView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_total_amount_layout,parent,false);
                return new CartTotalAmountViewholder(cartTotalAmountView);
            default:
                return null;

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
       switch (cartItemModelList.get(position).getType()){
           case CartItemModel.CART_ITEM:
               int resource = cartItemModelList.get(position).getProductImage();
               String title = cartItemModelList.get(position).getProductTitle();
               int freeCoupens = cartItemModelList.get(position).getFreeCoupens();
               String productPrice = cartItemModelList.get(position).getProductPrice();
               String cuttedPrice = cartItemModelList.get(position).getCuttedPrice();
               int offersApplied =cartItemModelList.get(position).getOffersApplied();
               ((CartItemViewholder)holder).setItemsDetails(resource,title,freeCoupens,productPrice,cuttedPrice,offersApplied);
               break;
           case CartItemModel.TOTAL_AMOUNT:
                String totalItems = cartItemModelList.get(position).getTotalItems();
                String totalItemsPrice = cartItemModelList.get(position).getTotalItemsPrice();
                String deliveryPrice = cartItemModelList.get(position).getDeliveryPrice();
                String totalAmount = cartItemModelList.get(position).getTotalAmount();
                String savedAmount = cartItemModelList.get(position).getSavedAmount();
               ((CartTotalAmountViewholder)holder).setTotalAmount(totalItems,totalItemsPrice,deliveryPrice,totalAmount,savedAmount);
               break;
           default:
               return;
       }
    }

    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }

    public class CartItemViewholder extends RecyclerView.ViewHolder{
        private ImageView productImage;
        private ImageView freeCoupensIcon;
        private TextView productTitle;
        private TextView freeCoupens;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView offersApplied;
        private TextView coupensApplied;
        private TextView productQuantity;


        public CartItemViewholder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            freeCoupensIcon = itemView.findViewById(R.id.free_cuopen_icon);
            freeCoupens = itemView.findViewById(R.id.tv_free_coupen);
            productPrice = itemView.findViewById(R.id.product_price);
            cuttedPrice = itemView.findViewById(R.id.cutted_price);
            offersApplied = itemView.findViewById(R.id.offers_applied);
            coupensApplied = itemView.findViewById(R.id.coupens_applied);
            productQuantity = itemView.findViewById(R.id.product_quantity);
        }
        private void setItemsDetails(int resource,String title,int freeCoupensNo,String productPriceTextString,String cuttedPriceTextString,int offersAppliedNo){
            productImage.setImageResource(resource);
            productTitle.setText(title);
            if(freeCoupensNo>0){
                freeCoupens.setVisibility(View.VISIBLE);
                freeCoupensIcon.setVisibility(View.VISIBLE);
                if(freeCoupensNo==1){
                    freeCoupens.setText("Free "+freeCoupensNo+" Coupen");
                }else{
                    freeCoupens.setText("Free "+freeCoupensNo+" Coupens");
                }

            }else{
                freeCoupens.setVisibility(View.INVISIBLE);
                freeCoupensIcon.setVisibility(View.INVISIBLE);
            }
            productPrice.setText(productPriceTextString);
            cuttedPrice.setText(cuttedPriceTextString);
            if(offersAppliedNo > 0){
                offersApplied.setVisibility(View.VISIBLE);
                offersApplied.setText(offersAppliedNo + " Offers applied");
            }else{
                offersApplied.setVisibility(View.INVISIBLE);
            }
            productQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog quantityDialog = new Dialog(itemView.getContext());
                    quantityDialog.setContentView(R.layout.quantity_dialog);
                    quantityDialog.setCancelable(false);
                    quantityDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    final EditText quantityNo = quantityDialog.findViewById(R.id.quantity_number);
                    Button cancel_btn = quantityDialog.findViewById(R.id.cancel_btn);
                    Button confirm_btn = quantityDialog.findViewById(R.id.confirm_btn);
                    cancel_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            quantityDialog.dismiss();
                        }
                    });
                    confirm_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            productQuantity.setText("Qty: "+quantityNo.getText().toString());
                            quantityDialog.dismiss();
                        }
                    });
                    quantityDialog.show();
                }
            });

        }
    }
    public class CartTotalAmountViewholder extends RecyclerView.ViewHolder{
        private TextView totalItems;
        private TextView totalItemPrice;
        private TextView deliveryPrice;
        private TextView totalAmount;
        private TextView savedAmount;
        public CartTotalAmountViewholder(@NonNull View itemView) {
            super(itemView);
            totalItems = itemView.findViewById(R.id.total_items);
            totalItemPrice = itemView.findViewById(R.id.total_items_price);
            deliveryPrice = itemView.findViewById(R.id.delivery_price);
            totalAmount = itemView.findViewById(R.id.total_price);
            savedAmount = itemView.findViewById(R.id.saved_amount);
        }
        private void setTotalAmount(String totalItemsText,String totalItemPriceText,String deliveryPriceText,String totalAmountText,String savedAmountText){
            totalItems.setText(totalItemsText);
            totalItemPrice.setText(totalItemPriceText);
            deliveryPrice.setText(deliveryPriceText);
            totalAmount.setText(totalAmountText);
            savedAmount.setText(savedAmountText);
        }
    }
}