package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResetPassword extends AppCompatActivity {

    private TextInputEditText mpass1;
    private TextInputEditText mPass2;
    private Button setpassbtn;
    DatabaseReference mfirebasedatabase = FirebaseDatabase.getInstance().getReference().child("users");
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        mpass1 = findViewById(R.id.password1);
        mPass2 = findViewById(R.id.password2);
        setpassbtn = findViewById(R.id.submitpassword);



        setpassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pass1 = mpass1.getText().toString();
                final String pass2 = mPass2.getText().toString();
                //Toast.makeText(getApplicationContext(),pass1,Toast.LENGTH_LONG).show();
                if (pass1.equals(pass2)){
                    if(!pass1.isEmpty())
                    {

                        final String phonenum = getIntent().getStringExtra("phonenum");
                        mfirebasedatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                //final String password = snapshot.child(phonenum).child("password").getValue().toString();
                                final String email = snapshot.child(phonenum).child("email").getValue().toString();
                                firebaseAuth.getCurrentUser().updatePassword(pass1)
                                        .addOnCompleteListener(ResetPassword.this, new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Intent intent1=new Intent(ResetPassword.this, MainActivity.class);
                                                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent1);
                                                    finish();

//                                                    firebaseAuth.signOut();
//                                                      firebaseAuth.signInWithEmailAndPassword(email, pass1)
//                                                            .addOnCompleteListener(ResetPassword.this, new OnCompleteListener<AuthResult>() {
//                                                                @Override
//                                                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                                                    if (task.isSuccessful()) {
//                                                                             Intent intent1=new Intent(ResetPassword.this, MainActivity.class);
//                                                                             intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                                                             startActivity(intent1);
//                                                                             finish();
//                                                                    } else {
//                                                                        Toast.makeText(ResetPassword.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
//                                                                    }
//                                                                }
//                                                            });

                                                } else {
                                                    Toast.makeText(ResetPassword.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Password cannot be empty",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Password do not match",Toast.LENGTH_LONG).show();
                }




            }
        });








    }
}
