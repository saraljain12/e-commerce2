package com.example.e_commerce;

import android.app.ActionBar;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


public class MyCartFragment extends Fragment {

     public MyCartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().findViewById(R.id.action_bar_logo).setVisibility(View.GONE);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("My Cart");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);

        //((AppCompatActivity) getActivity()).getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }

    private RecyclerView cartItemsRecyclerView;
    private Button continueBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_my_cart, container, false);


        cartItemsRecyclerView = view.findViewById(R.id.cart_items_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        cartItemsRecyclerView.setLayoutManager(layoutManager);
        List<CartItemModel> cartItemModelList = new ArrayList<>();
        cartItemModelList.add(new CartItemModel(0,R.mipmap.ic_launcher,"PIXEL 2",2,"Rs 49999/-","Rs 59999/-",2,2,2));
        cartItemModelList.add(new CartItemModel(0,R.mipmap.ic_launcher,"PIXEL 2",1,"Rs 49999/-","Rs 59999/-",2,0,2));
        cartItemModelList.add(new CartItemModel(0,R.mipmap.ic_launcher,"PIXEL 2",0,"Rs 49999/-","Rs 59999/-",2,2,0));
        cartItemModelList.add(new CartItemModel("2","Rs 49999/-","Free","Rs 999/-","Rs 49999/-",1));
        cartItemModelList.add(new CartItemModel("2","Rs 49999/-","Free","Rs 999/-","Rs 49999/-",1));
        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        cartItemsRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        continueBtn = view.findViewById(R.id.cart_continue_btn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.addAddressActivity);
            }
        });
//       OnBackPressedCallback callback = new OnBackPressedCallback(true) {
//           @Override
//           public void handleOnBackPressed() {
//            Navigation.findNavController(view).navigate(getCallerFragment());
//           }
//        } ;
//        requireActivity().getOnBackPressedDispatcher().addCallback(this,callback);
        return view;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
    }


}