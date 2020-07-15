package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {


    private TextInputEditText mEmail;
    private TextInputEditText mPass;
    private TextInputEditText mName,mphone;

    private Button mGo,malreadyreg;

    private ImageView go;

    Animation btt, btt1;



    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        mName=findViewById(R.id.name);
        mEmail= findViewById(R.id.email);
        mPass= findViewById(R.id.password);
        mphone = findViewById(R.id.phone);
        mGo=(Button) findViewById(R.id.GO);
        malreadyreg = findViewById(R.id.alreadyreg);

        go=findViewById(R.id.goid);

        btt= AnimationUtils.loadAnimation(this, R.anim.btt);
        btt1= AnimationUtils.loadAnimation(this, R.anim.btt1);

        LinearLayout content=findViewById(R.id.contentid);

        content.startAnimation(btt);
        malreadyreg.startAnimation(btt1);


        malreadyreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginintent = new Intent(RegisterActivity.this, LoginActivity.class);
                loginintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginintent);
            }
        });



        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    final String name = mName.getText().toString();
                    final String email = mEmail.getText().toString();
                    final String pas = mPass.getText().toString();
                    final String phone = "+91" + "" + mphone.getText().toString();
                    final String mmemail = email.replaceAll("\\.",",");
                    final DatabaseReference muserdatabse ;
                if(!name.isEmpty() && !email.isEmpty() && !pas.isEmpty() && !phone.isEmpty()) {
                    muserdatabse = FirebaseDatabase.getInstance().getReference();
                       muserdatabse.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.child("emails").child(mmemail).getValue()==null){
                                    if(snapshot.child("users").child(phone).getValue()==null){
                                        Intent intent = new Intent(RegisterActivity.this, VerifyPhone.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.putExtra("phone", phone);
                                        intent.putExtra("email", email);
                                        intent.putExtra("password", pas);
                                        intent.putExtra("name", name);
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        mphone.setError("Phone Number Already in use");
                                    }

                                }else {
                                    mEmail.setError("Email Already in use");
                                }

//                            if(snapshot.child("users").child(mobile).child("password").getValue()!=null){
//                                String mpassword =snapshot.child("users").child(mobile).child("password").getValue().toString();
//                                if (mpassword.equals(password)) {
//
//
//                                    Intent start_intent = new Intent(LoginActivity.this, MainActivity.class);
//
//                                    startActivity(start_intent);
//
//                                    Toast.makeText(LoginActivity.this, "Hello", Toast.LENGTH_LONG).show();
//                                } else {
//                                    Toast.makeText(LoginActivity.this, "bye", Toast.LENGTH_LONG).show();
//                                }
//                            }else{
//
//                            }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
//                       Intent intent = new Intent(getApplicationContext(), VerifyPhone.class);
//                       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                       intent.putExtra("phone", phone);
//                       intent.putExtra("email", email);
//                       intent.putExtra("password", pas);
//                       intent.putExtra("name", name);
//                       startActivity(intent);
//                       finish();
//                       return;
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"please fill all the fields before proceeding",Toast.LENGTH_LONG).show();
                }
            }
        });


            }
        }


