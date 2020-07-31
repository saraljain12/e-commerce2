package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import static com.example.e_commerce.TestActivityForDelivery.SELECT_ADDRESS;


public class MyAdressesActivity extends AppCompatActivity {

    private static AddressesAdapter addressesAdapter;
    private RecyclerView myaddressesrecyclerview;
    private Button deliverherebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_adresses);

        Toolbar toolbar = findViewById(R.id.toolbarx);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Addresses");
        myaddressesrecyclerview = findViewById(R.id.adresses_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        myaddressesrecyclerview.setLayoutManager(linearLayoutManager);

        deliverherebtn = findViewById(R.id.deliver_here_btn);



        List<AdressesModel> adressesModelList = new ArrayList<>();

        adressesModelList.add(new AdressesModel("john ","2,nolaipura Ratlam", "457001",true ));
        adressesModelList.add(new AdressesModel("saral","2,nolaipura Ratlam", "457001",false));
        adressesModelList.add(new AdressesModel("john ","2,nolaipura Ratlam", "457001",false));
        adressesModelList.add(new AdressesModel("saral","2,nolaipura Ratlam", "457001",false));
        adressesModelList.add(new AdressesModel("john ","2,nolaipura Ratlam", "457001",false));
        adressesModelList.add(new AdressesModel("saral","2,nolaipura Ratlam", "457001",false));
        adressesModelList.add(new AdressesModel("john ","2,nolaipura Ratlam", "457001",false));
        adressesModelList.add(new AdressesModel("saral","2,nolaipura Ratlam", "457001",false));
        adressesModelList.add(new AdressesModel("john ","2,nolaipura Ratlam", "457001",false));
        adressesModelList.add(new AdressesModel("saral","2,nolaipura Ratlam", "457001",false));
        adressesModelList.add(new AdressesModel("john ","2,nolaipura Ratlam", "457001",false));
        adressesModelList.add(new AdressesModel("saral","2,nolaipura Ratlam", "457001",false));
        adressesModelList.add(new AdressesModel("john ","2,nolaipura Ratlam", "457001",false));

        int mode = getIntent().getIntExtra("MODE",-1);
        if(mode == SELECT_ADDRESS){
            deliverherebtn.setVisibility(View.GONE);
        }else {
            deliverherebtn.setVisibility(View.VISIBLE);
        }


        addressesAdapter = new AddressesAdapter(adressesModelList,mode);
        ((SimpleItemAnimator)myaddressesrecyclerview.getItemAnimator()).setSupportsChangeAnimations(false);
        myaddressesrecyclerview.setAdapter(addressesAdapter);
        addressesAdapter.notifyDataSetChanged();
    }
    public  static void refreshitem(int select, int deslect){
    addressesAdapter.notifyItemChanged(select);
    addressesAdapter.notifyItemChanged(deslect);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}