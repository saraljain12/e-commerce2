package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    private Button mlogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);
          mAuth = FirebaseAuth.getInstance();
          mlogout = findViewById(R.id.logout);

          mlogout.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  FirebaseAuth.getInstance().signOut();
                  sendtoStart();
              }
          });


    }
    @Override
    protected void onStart() {
        super.onStart();


        FirebaseUser currentUser = mAuth.getCurrentUser();
        //System.out.println(currentUser.getUid().toString());
        if(currentUser == null){
            sendtoStart();
        }

    }
    private void sendtoStart() {
        Intent startintent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(startintent);
        finish();
    }
 }