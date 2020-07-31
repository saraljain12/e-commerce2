package com.example.e_commerce;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class MyWishlistFragment extends Fragment {

    private RecyclerView recyclerView;
    public MyWishlistFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_wishlist, container, false);
        recyclerView = view.findViewById(R.id.wishlist_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        List<MyWishlistModel> myWishlistModelList = new ArrayList<>();
        myWishlistModelList.add(new MyWishlistModel(R.drawable.ic_menu_camera,"PIXEL XL",2,"(35) Rating",3.5,"Rs 499999/-","Rs 59999/-","Cash on Delivery!!!"));
        myWishlistModelList.add(new MyWishlistModel(R.drawable.ic_menu_camera,"PIXEL XL",0,"(35) Rating",3.5,"Rs 499999/-","Rs 59999/-","Cash on Delivery!!!"));
        myWishlistModelList.add(new MyWishlistModel(R.drawable.ic_menu_camera,"PIXEL XL",2,"(35) Rating",3.5,"Rs 499999/-","Rs 59999/-","Cash on Delivery!!!"));
        myWishlistModelList.add(new MyWishlistModel(R.drawable.ic_menu_camera,"PIXEL XL",2,"(35) Rating",3.5,"Rs 499999/-","Rs 59999/-","Cash on Delivery!!!"));
        myWishlistModelList.add(new MyWishlistModel(R.drawable.ic_menu_camera,"PIXEL XL",2,"(35) Rating",3.5,"Rs 499999/-","Rs 59999/-","Cash on Delivery!!!"));
        MyWishlistAdapter myWishlistAdapter = new MyWishlistAdapter(myWishlistModelList,true);
        recyclerView.setAdapter(myWishlistAdapter);
        myWishlistAdapter.notifyDataSetChanged();
        return view;
    }
}