package com.example.e_commerce.ui.myReward;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.e_commerce.R;

import java.util.ArrayList;
import java.util.List;


public class MyRewardFragment extends Fragment {


    public MyRewardFragment(){

    }
    private RecyclerView rewardsrecyclerview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_my_rewards, container, false);

        rewardsrecyclerview = view.findViewById(R.id.my_rewards_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rewardsrecyclerview.setLayoutManager(layoutManager);

        List<MyRewardViewModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new MyRewardViewModel("cashback","till 30 july 2020","Get 20% cashback on any amount above 2000 and get free home delivery also."));
        rewardModelList.add(new MyRewardViewModel("discount","till 30 july 2020","Get 20% cashback on any amount above 2000 and get free home delivery also."));
        rewardModelList.add(new MyRewardViewModel("buy1 get1","till 30 july 2020","Get 20% cashback on any amount above 2000 and get free home delivery also."));
        rewardModelList.add(new MyRewardViewModel("chutiya bnane ki scheme ","till 30 july 2020","Get 20% cashback on any amount above 2000 and get free home delivery also."));
        rewardModelList.add(new MyRewardViewModel("21 din m paise double","till 30 july 2020","Get 20% cashback on any amount above 2000 and get free home delivery also."));

        MyRewardAdapter rewardAdapter = new MyRewardAdapter(rewardModelList);
        rewardsrecyclerview.setAdapter(rewardAdapter);
        rewardAdapter.notifyDataSetChanged();
        return view;
    }

    public static class MyRewardAdapter extends RecyclerView.Adapter<MyRewardAdapter.ViewHolder> {

        private List<MyRewardViewModel> rewardModelList;

        public MyRewardAdapter(List<MyRewardViewModel> rewardModelList) {
            this.rewardModelList = rewardModelList;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
           View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rewards_item_layout,parent,false);
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
            private void setData(String title, String date,String body){
                coupentitle.setText(title);
                couponexpirydate.setText(date);
                couponbody.setText(body);
            };
        }

    }
}