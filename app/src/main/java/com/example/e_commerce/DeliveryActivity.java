package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DeliveryActivity extends AppCompatActivity {
    private RecyclerView deliveryRecyclerView;
    private Button ChangeOrAddNewAddressBtn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        deliveryRecyclerView = findViewById(R.id.delivery_recyclerview);
        ChangeOrAddNewAddressBtn = findViewById(R.id.change_or_add_address_btn);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        deliveryRecyclerView.setLayoutManager(layoutManager);
        List<CartItemModel> cartItemModelList = new ArrayList<>();
        cartItemModelList.add(new CartItemModel(0,R.mipmap.ic_launcher,"PIXEL 2",2,"Rs 49999/-","Rs 59999/-",2,2,2));
        cartItemModelList.add(new CartItemModel(0,R.mipmap.ic_launcher,"PIXEL 2",1,"Rs 49999/-","Rs 59999/-",2,0,2));
        cartItemModelList.add(new CartItemModel(0,R.mipmap.ic_launcher,"PIXEL 2",0,"Rs 49999/-","Rs 59999/-",2,2,0));
        cartItemModelList.add(new CartItemModel("2","Rs 49999/-","Free","Rs 999/-","Rs 49999/-",1));
        cartItemModelList.add(new CartItemModel("2","Rs 49999/-","Free","Rs 999/-","Rs 49999/-",1));
        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        deliveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        ChangeOrAddNewAddressBtn.setVisibility(View.VISIBLE);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            finish();
            return  true ;
        }
        return super.onOptionsItemSelected(item);
    }
}