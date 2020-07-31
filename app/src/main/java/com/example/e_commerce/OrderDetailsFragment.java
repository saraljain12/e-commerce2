package com.example.e_commerce;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class OrderDetailsFragment extends Fragment {

    private  LinearLayout starLayout;
    public OrderDetailsFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Order Detail");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
         View view = inflater.inflate(R.layout.fragment_order_details, container, false);
           starLayout = view.findViewById(R.id.order_rate_layout);
        for (int i = 0; i < starLayout.getChildCount(); i++) {
            final int starPosition = i;
            starLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRating(starPosition);
                }
            });
        }

        return view;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
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