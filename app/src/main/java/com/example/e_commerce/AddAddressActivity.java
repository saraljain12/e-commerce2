package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {
    private Button saveBtn;
    private EditText city;
    private EditText locality;
    private EditText flatNo;
    private EditText pincode;
    private EditText landmark;
    private EditText name;
    private EditText mobileNo;
    private EditText alternateMobileNo;
    private Spinner stateSpinner;

    private String [] stateList ;
    private String selectedState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Add a new address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        saveBtn = findViewById(R.id.save_btn);
        city = findViewById(R.id.city);
        locality = findViewById(R.id.locality);
        flatNo = findViewById(R.id.flat_no);
        pincode = findViewById(R.id.pincode);
        stateSpinner = findViewById(R.id.state_spinner);
        landmark = findViewById(R.id.landmark);
        name = findViewById(R.id.name);
        mobileNo = findViewById(R.id.mobile_no);
        alternateMobileNo = findViewById(R.id.alternate_mobile_no);
        stateList = getResources().getStringArray(R.array.india_states);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.india_states));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        stateSpinner.setAdapter(arrayAdapter);

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedState = stateList[position] ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(city.getText())){
                    if(!TextUtils.isEmpty(locality.getText())){
                        if(!TextUtils.isEmpty(flatNo.getText())){
                            if(!TextUtils.isEmpty(pincode.getText())&& pincode.getText().length()==6){
                                if(!TextUtils.isEmpty(name.getText())){
                                    if(!TextUtils.isEmpty(mobileNo.getText()) && mobileNo.getText().length()==10){
                                        final String fullAddress = flatNo.getText().toString()+" , "+locality.getText().toString()+" "+landmark.getText().toString()+" , "+city.getText().toString()+" , "+selectedState;
                                        Map<String,Object> addressMap = new HashMap<>();
                                        addressMap.put("list_size",DBqueries.myAddressList.size()+1);
                                        if(DBqueries.myAddressList.size()>0) {
                                            addressMap.put("selected_" + (DBqueries.selectedAddress + 1), false);
                                        }
                                        final String fullname;
                                        if(TextUtils.isEmpty(alternateMobileNo.getText())){
                                            addressMap.put("fullname_"+(DBqueries.myAddressList.size()+1),name.getText().toString()+"-"+mobileNo.getText().toString());
                                            fullname = name.getText().toString()+"-"+mobileNo.getText().toString();
                                        }else {
                                            addressMap.put("fullname_"+(DBqueries.myAddressList.size()+1),name.getText().toString()+"-"+mobileNo.getText().toString()+" or "+alternateMobileNo.getText().toString());
                                            fullname = name.getText().toString()+"-"+mobileNo.getText().toString()+" or "+alternateMobileNo.getText().toString();
                                        }
                                        addressMap.put("address_"+(DBqueries.myAddressList.size()+1),fullAddress);
                                        addressMap.put("pincode_"+(DBqueries.myAddressList.size()+1),pincode.getText().toString());
                                        addressMap.put("selected_"+(DBqueries.myAddressList.size()+1),true);

                                        FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                                                .document("MY_ADDRESS")
                                                .update(addressMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    if(DBqueries.myAddressList.size()>0){
                                                        DBqueries.myAddressList.get(DBqueries.selectedAddress).setSelectedAddress(false);
                                                    }
                                                    DBqueries.myAddressList.add(new AdressesModel(fullname,
                                                            fullAddress,pincode.getText().toString(),true));
                                                    if(getIntent().getStringExtra("INTENT").toString().equals("deliveryIntent")) {
                                                        Intent deliveryIntent = new Intent(AddAddressActivity.this, DeliveryActivity.class);
                                                        startActivity(deliveryIntent);
                                                    }else{
                                                        MyAdressesActivity.refreshitem(DBqueries.selectedAddress,DBqueries.myAddressList.size()-1);
                                                    }
                                                    DBqueries.selectedAddress = DBqueries.myAddressList.size()-1;
                                                    finish();

                                                }else{
                                                    String error = task.getException().getMessage();
                                                    Toast.makeText(AddAddressActivity.this, error, Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });


                                    }else{
                                       mobileNo.requestFocus();
                                        Toast.makeText(AddAddressActivity.this, "Please provide valid phone number", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                   name.requestFocus();
                                }
                            }else{
                                pincode.requestFocus();
                                Toast.makeText(AddAddressActivity.this, "Please provide valid Pincode", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                           flatNo.requestFocus();
                        }
                    }else{
                        locality.requestFocus();
                    }
                }else{
                   city.requestFocus();
                }
            }
        });

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