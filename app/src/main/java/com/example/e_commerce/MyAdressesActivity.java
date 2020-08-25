package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.e_commerce.DeliveryActivity.SELECT_ADDRESS;


public class MyAdressesActivity extends AppCompatActivity {

    private static AddressesAdapter addressesAdapter;
    private RecyclerView myaddressesrecyclerview;
    private Button deliverherebtn;
    private TextView totalSavedAddress;
    private int previousAddress;
    private Dialog loadingDialog;
    private LinearLayout addNewAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_adresses);

        Toolbar toolbar = findViewById(R.id.toolbarx);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Addresses");

        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(this.getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);


        previousAddress = DBqueries.selectedAddress ;
        myaddressesrecyclerview = findViewById(R.id.adresses_recycler_view);
        totalSavedAddress = findViewById(R.id.address_saved);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        myaddressesrecyclerview.setLayoutManager(linearLayoutManager);

        deliverherebtn = findViewById(R.id.deliver_here_btn);
        addNewAddress = findViewById(R.id.add_new_address_btn);

        int mode = getIntent().getIntExtra("MODE",-1);



        if(mode == SELECT_ADDRESS){
            deliverherebtn.setVisibility(View.VISIBLE);
        }else {
            deliverherebtn.setVisibility(View.GONE);
        }
        deliverherebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(previousAddress!=DBqueries.selectedAddress){
                    loadingDialog.show();
                    final int previousAddressIndex = previousAddress;
                    Map<String,Object> updateSelection = new HashMap<>();
                    updateSelection.put("selected_"+(previousAddress+1),false);
                    updateSelection.put("selected_"+(DBqueries.selectedAddress+1),true);

                    previousAddress = DBqueries.selectedAddress;

                    FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                            .document("MY_ADDRESS").update(updateSelection)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        finish();
                                    }else{
                                        previousAddress = previousAddressIndex;
                                        String error = task.getException().getMessage();
                                        Toast.makeText(MyAdressesActivity.this, error, Toast.LENGTH_LONG).show();
                                    }
                                    loadingDialog.dismiss();
                                }
                            });
                        }else{
                             finish();
                        }

                      }
                });

        addressesAdapter = new AddressesAdapter(DBqueries.myAddressList,mode);
        ((SimpleItemAnimator)myaddressesrecyclerview.getItemAnimator()).setSupportsChangeAnimations(false);
        myaddressesrecyclerview.setAdapter(addressesAdapter);
        addressesAdapter.notifyDataSetChanged();

        addNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DBqueries.selectedAddress!=previousAddress) {
                    DBqueries.myAddressList.get(DBqueries.selectedAddress).setSelectedAddress(false);
                    DBqueries.myAddressList.get(previousAddress).setSelectedAddress(true);
                    DBqueries.selectedAddress = previousAddress;
                }

              Intent addAddressIntent = new Intent(MyAdressesActivity.this,AddAddressActivity.class);
              addAddressIntent.putExtra("INTENT","null");
              startActivity(addAddressIntent);
              finish();
            }
        });




    }
    public  static void refreshitem(int select, int deslect){
    addressesAdapter.notifyItemChanged(select);
    addressesAdapter.notifyItemChanged(deslect);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            if(DBqueries.selectedAddress!=previousAddress){
                DBqueries.myAddressList.get(DBqueries.selectedAddress).setSelectedAddress(false);
                DBqueries.myAddressList.get(previousAddress).setSelectedAddress(true);
                DBqueries.selectedAddress = previousAddress;
            }
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        totalSavedAddress.setText(DBqueries.myAddressList.size()+" saved address");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(DBqueries.selectedAddress!=previousAddress){
            DBqueries.myAddressList.get(DBqueries.selectedAddress).setSelectedAddress(false);
            DBqueries.myAddressList.get(previousAddress).setSelectedAddress(true);
            DBqueries.selectedAddress = previousAddress;
        }
    }
}