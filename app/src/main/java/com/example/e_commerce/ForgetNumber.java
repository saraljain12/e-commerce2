package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgetNumber extends AppCompatActivity {

    private EditText mnumber;
    private Button mbtn;
    DatabaseReference mfirebasedatabase = FirebaseDatabase.getInstance().getReference().child("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_number);
        mnumber = findViewById(R.id.forgetmobilenum);
        mbtn = findViewById(R.id.sendotp);


        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phonenum = mnumber.getText().toString();
                final String fullphonenum = "+91"+ phonenum;
                if(!phonenum.isEmpty())
                {
                    mfirebasedatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.child(fullphonenum).exists()){
                                Intent verifyotpintent = new Intent(ForgetNumber.this, ForgetPassOtp.class);
                                verifyotpintent.putExtra("phonenum",fullphonenum);
                                startActivity(verifyotpintent);
                                finish();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"This number is not registered with us kindly make a new account",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }
                else{
                    Toast.makeText(getApplicationContext(),"Please enter your registered mobile number",Toast.LENGTH_LONG).show();
                }
            }
        });



    }
}