package com.example.e_commerce.ui.myReward;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.ProductDetailsActivity;
import com.example.e_commerce.R;

import java.util.List;

public class MyRewardAdapter extends RecyclerView.Adapter<MyRewardAdapter.ViewHolder> {

    private List<MyRewardViewModel> rewardModelList;
    public Boolean useminilayout = false;

    public MyRewardAdapter(List<MyRewardViewModel> rewardModelList,Boolean useminilayout) {
        this.rewardModelList = rewardModelList;
        this.useminilayout = useminilayout;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(useminilayout ){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mini_rewards_item_layout,parent,false);

        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rewards_item_layout,parent,false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = rewardModelList.get(position).getTitle();
        String date = rewardModelList.get(position).getExpiryDate();
        String body = rewardModelList.get(position).getCouponbody();
        holder.setData(title,date,body);
    }

    @Override
    public int getItemCount() {
        return rewardModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView coupentitle,couponexpirydate,couponbody;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coupentitle = itemView.findViewById(R.id.coupon_title);
            couponexpirydate = itemView.findViewById(R.id.coupon_validity);
            couponbody = itemView.findViewById(R.id.coupon_body);




        }
        private void setData(final String title, final String date, final String body){
            coupentitle.setText(title);
            couponexpirydate.setText(date);
            couponbody.setText(body);

            if(useminilayout){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProductDetailsActivity.couponTitle.setText(title);
                        ProductDetailsActivity.couponExpiryDate.setText(date);
                        ProductDetailsActivity.couponBody.setText(body);
                        ProductDetailsActivity.showdialogcyclerview();
                    }
                });
            }

        };
    }

}
