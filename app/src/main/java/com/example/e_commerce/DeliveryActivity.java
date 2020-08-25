package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DeliveryActivity extends AppCompatActivity {
    private RecyclerView deliveryRecyclerView;
    private Button ChangeOrAddNewAddressBtn ;
    private TextView fullName;
    private TextView address;
    private TextView pincode;
    private TextView totalAmount;
    public static List<CartItemModel>cartItemModelList;
    public static final int SELECT_ADDRESS=0;
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
        fullName = findViewById(R.id.fullname);
        address = findViewById(R.id.address);
        pincode = findViewById(R.id.pincode);
        totalAmount = findViewById(R.id.total_cart_amount);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        deliveryRecyclerView.setLayoutManager(layoutManager);
        List<CartItemModel> cartItemModelList = new ArrayList<>();

        CartAdapter cartAdapter = new CartAdapter(DBqueries.cartModelList,totalAmount,false);
        deliveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        ChangeOrAddNewAddressBtn.setVisibility(View.VISIBLE);
        fullName.setText(DBqueries.myAddressList.get(DBqueries.selectedAddress).getFullname());
        address.setText(DBqueries.myAddressList.get(DBqueries.selectedAddress).getAddress());
        pincode.setText(DBqueries.myAddressList.get(DBqueries.selectedAddress).getPincode());
        ChangeOrAddNewAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myAddressIntent = new Intent(DeliveryActivity.this,MyAdressesActivity.class);
                myAddressIntent.putExtra("MODE",SELECT_ADDRESS);
                startActivity(myAddressIntent);
            }
        });

    }

    @Override
    protected void onStart() {
        fullName.setText(DBqueries.myAddressList.get(DBqueries.selectedAddress).getFullname());
        address.setText(DBqueries.myAddressList.get(DBqueries.selectedAddress).getAddress());
        pincode.setText(DBqueries.myAddressList.get(DBqueries.selectedAddress).getPincode());

        super.onStart();
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