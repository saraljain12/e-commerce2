package com.example.e_commerce.ui.myOrder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.MyOrderAdapter;
import com.example.e_commerce.MyOrderItemModel;
import com.example.e_commerce.R;

import java.util.ArrayList;
import java.util.List;

public class MyOrderFragment extends Fragment {

    private MyOrderViewModel myOrderViewModel;
    private RecyclerView myOrderRecyclerView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        getActivity().findViewById(R.id.action_bar_logo).setVisibility(View.GONE);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("My Order");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        myOrderViewModel =
                ViewModelProviders.of(this).get(MyOrderViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my_order, container, false);
         myOrderRecyclerView = root.findViewById(R.id.my_order_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        myOrderRecyclerView.setLayoutManager(layoutManager);

        List<MyOrderItemModel> myOrderItemModelList = new ArrayList<>();
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.ic_menu_camera,4,"Pixel 2","24 th July,2020"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.ic_menu_camera,3,"Pixel 2","Cancelled"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.ic_menu_camera,2,"Pixel 2","24 th July,2020"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.ic_menu_camera,4,"Pixel 2","24 th July,2020"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.ic_menu_camera,4,"Pixel 2","24 th July,2020"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.ic_menu_camera,4,"Pixel 2","24 th July,2020"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.ic_menu_camera,4,"Pixel 2","24 th July,2020"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.ic_menu_camera,4,"Pixel 2","24 th July,2020"));
        MyOrderAdapter myOrderAdapter = new MyOrderAdapter(myOrderItemModelList);
        myOrderRecyclerView.setAdapter(myOrderAdapter);
        myOrderAdapter.notifyDataSetChanged();
        return root;
    }
    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
    }
}