package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TestActivityForDelivery extends AppCompatActivity {

    public static final int MANAGE_ADDRESS=1;

    private Button ChangeOrAddAddress;
    private Button ViewAllAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_for_delivery);
        ViewAllAddress = findViewById(R.id.button);
        ChangeOrAddAddress = findViewById(R.id.button2);

        ViewAllAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myaddressintent  = new Intent(TestActivityForDelivery.this,MyAdressesActivity.class);
              //  myaddressintent.putExtra("MODE",SELECT_ADDRESS);
                startActivity(myaddressintent);
            }
        });


        ChangeOrAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myaddressintent  = new Intent(TestActivityForDelivery.this,MyAdressesActivity.class);
                myaddressintent.putExtra("MODE",MANAGE_ADDRESS);
                startActivity(myaddressintent);
            }
        });

    }
}