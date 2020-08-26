package com.example.e_commerce;

import android.app.Dialog;
import android.graphics.Color;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import static com.example.e_commerce.MyCartFragment.cartTotal;

public class CartAdapter extends RecyclerView.Adapter {
    private List<CartItemModel> cartItemModelList;
    public static  TextView cartTotalAmount;
    private  boolean showDeleteBtn;


    public CartAdapter(List<CartItemModel> cartItemModelList,TextView cartTotalAmount,boolean showDeleteBtn) {
        this.cartItemModelList = cartItemModelList;
        this.cartTotalAmount = cartTotalAmount;
        this.showDeleteBtn = showDeleteBtn;
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
                String prodcutID = cartItemModelList.get(position).getProductID();
                String resource = cartItemModelList.get(position).getProductImage();
                String title = cartItemModelList.get(position).getProductTitle();
                Long freeCoupens = cartItemModelList.get(position).getFreeCoupens();
                String productPrice = cartItemModelList.get(position).getProductPrice();
                String cuttedPrice = cartItemModelList.get(position).getCuttedPrice();
                Long offersApplied =cartItemModelList.get(position).getOffersApplied();
                boolean inStock = cartItemModelList.get(position).isInStock();
                ((CartItemViewholder)holder).setItemsDetails(prodcutID,resource,title,freeCoupens,productPrice,cuttedPrice,offersApplied,position,inStock);
                break;
            case CartItemModel.TOTAL_AMOUNT:
                int totalItems = 0;
                int totalItemsPrice =0;
                String deliveryPrice;
                int totalAmount = 0;
                int savedAmount =0;



                for(int x=0;x<cartItemModelList.size();x++){
                    if(cartItemModelList.get(x).getType() == CartItemModel.CART_ITEM && cartItemModelList.get(x).isInStock()){
                        totalItems++;
                        totalItemsPrice =totalItemsPrice + Integer.parseInt(cartItemModelList.get(x).getProductPrice());
                    }
                }
                if(totalItemsPrice > 500){
                    deliveryPrice = "FREE";
                    totalAmount = totalItemsPrice;
                }else{
                    deliveryPrice = "60";
                    totalAmount = totalItemsPrice + 60;
                }


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
        private LinearLayout deletebtn;
        private LinearLayout coupenRedemptionLayout;


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
            deletebtn = itemView.findViewById(R.id.remove_item_btn);
            coupenRedemptionLayout = itemView.findViewById(R.id.coupen_redemption_layout);

        }
        private void setItemsDetails(String productID, String resource, String title, Long freeCoupensNo, String productPriceTextString, String cuttedPriceTextString, Long offersAppliedNo, final Integer position,boolean inStock){
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.ic_menu_camera)).into(productImage);
            productTitle.setText(title);

            if(inStock){
                productPrice.setText(productPriceTextString);
                cuttedPrice.setText(cuttedPriceTextString);
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
            }else{
               productPrice.setText("Out of Stock");
               productPrice.setTextColor(Color.parseColor("#ff0000"));
               cuttedPrice.setText("");
               freeCoupens.setVisibility(View.INVISIBLE);
               freeCoupensIcon.setVisibility(View.INVISIBLE);
               productQuantity.setVisibility(View.INVISIBLE);
               offersApplied.setVisibility(View.INVISIBLE);
               coupensApplied.setVisibility(View.INVISIBLE);
               coupenRedemptionLayout.setVisibility(View.INVISIBLE);

            }

            if(offersAppliedNo > 0){
                offersApplied.setVisibility(View.VISIBLE);
                offersApplied.setText(offersAppliedNo + " Offers applied");
            }else{
                offersApplied.setVisibility(View.INVISIBLE);
            }


            if(showDeleteBtn){
                deletebtn.setVisibility(View.VISIBLE);
            }else{
                deletebtn.setVisibility(View.GONE);
            }

            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  if(!ProductDetailsActivity.running_cart_query) {
                      DBqueries.removeFromCart(position, itemView.getContext());
                  }

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
        private void setTotalAmount(int totalItemsText,int totalItemPriceText,String deliveryPriceText,int totalAmountText,int savedAmountText){
            totalItems.setText("Price("+totalItemsText+" items)");
            totalItemPrice.setText("Rs."+totalItemPriceText+"/-");
            if(deliveryPriceText.equals("FREE")){
                deliveryPrice.setText(deliveryPriceText);
            }
            else{
                deliveryPrice.setText("Rs."+ deliveryPriceText + "/-");
            }
            LinearLayout total = (LinearLayout) cartTotalAmount.getParent().getParent();
            if(totalItemPriceText == 0){
                DBqueries.cartModelList.remove(DBqueries.cartModelList.size()-1);
                total.setVisibility(View.GONE);
            }else{
                total.setVisibility(View.VISIBLE);
                totalAmount.setText("Rs."+totalAmountText+"/-");
                cartTotalAmount.setText("Rs "+totalAmountText+"/-");
                savedAmount.setText("You Saved Rs."+savedAmountText+"/- on this order");
            }

        }
    }
}
