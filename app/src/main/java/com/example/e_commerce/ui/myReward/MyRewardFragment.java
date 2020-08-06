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

        MyRewardAdapter rewardAdapter = new MyRewardAdapter(rewardModelList,false);
        rewardsrecyclerview.setAdapter(rewardAdapter);
        rewardAdapter.notifyDataSetChanged();
        return view;
    }


}