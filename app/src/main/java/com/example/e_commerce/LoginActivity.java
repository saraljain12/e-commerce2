package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static androidx.navigation.Navigation.findNavController;

public class LoginActivity extends AppCompatActivity {


    private EditText mmobile;
    private EditText mPass;
    private Button mLogin,msignup;
    private TextView mforgotpass;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mFireBaseAuthSateListener;
    private DatabaseReference muserdatabse;

    private ImageView go;

    Animation btt, btt1;

    @Override
    protected void onStart() {
        super.onStart();
       // mAuth.addAuthStateListener(mFireBaseAuthSateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //mAuth.removeAuthStateListener(mFireBaseAuthSateListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();



        mmobile= findViewById(R.id.loginmobile);
        mPass= findViewById(R.id.pass);
        mLogin= findViewById(R.id.signin);
        mforgotpass= findViewById(R.id.forget);
        msignup = findViewById(R.id.signup);

        go=findViewById(R.id.goid);

        LinearLayout content=findViewById(R.id.contentid);


        btt= AnimationUtils.loadAnimation(this, R.anim.btt);
        btt1= AnimationUtils.loadAnimation(this, R.anim.btt1);

        content.startAnimation(btt);
        msignup.startAnimation(btt1);



        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mobile = "+91" + mmobile.getText().toString();
                final String password = mPass.getText().toString();
                final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


                muserdatabse = FirebaseDatabase.getInstance().getReference();
                if (muserdatabse != null) {


                    muserdatabse.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                String email =snapshot.child("users").child(mobile).child("email").getValue().toString();
                                firebaseAuth.signInWithEmailAndPassword(email,password)
                                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if(task.isSuccessful()){
                                                    Intent start_intent = new Intent(LoginActivity.this, MainActivity.class);
                                                    startActivity(start_intent);
                                                    finish();
                                                }else{

                                                }
                                            }
                                        });


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
                } else {

                }


            }
        });

        mforgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openResetpage();
            }
        });

//        mFireBaseAuthSateListener=new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//
//                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
//
//                if(user!=null)
//                {
//                    Intent intent=new Intent(getApplicationContext(), MainActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//                    finish();
//                    return;
//                }
//            }
//        };





    }

    private void openResetpage() {
        Intent li = new Intent(LoginActivity.this, ForgetNumber.class);
        startActivity(li);

    }
}
