package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class VerifyPhone extends AppCompatActivity {
    Button verify;
    EditText phn;
    ProgressBar pb;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    String id;
    DatabaseReference mfirebasedatabase = FirebaseDatabase.getInstance().getReference();
    final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    boolean isverified = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        verify=findViewById(R.id.verify_btn);
        phn=findViewById(R.id.verification_code_entered_by_user);
        pb=findViewById(R.id.progress_bar);
        pb.setVisibility(View.GONE);




         String phone = getIntent().getStringExtra("phone");


        send(phone);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String code=phn.getText().toString();
                if (code.isEmpty()||code.length()<6)
                {
                    phn.setError("wrong otp");
                    phn.requestFocus();
                    return;
                }
                else{
                  //  final String code=phn.getText().toString();
                pb.setVisibility(View.VISIBLE);
                    verifycode(code);




            }}

        });
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            id=s;


        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


            String code= phoneAuthCredential.getSmsCode();
            if(code!=null)
            {
                pb.setVisibility(View.VISIBLE);
                //verifycode(code);

//                String email = getIntent().getStringExtra("email");
//                String password = getIntent().getStringExtra("password");
//                firebaseAuth.signInWithEmailAndPassword(email,password)
//                        .addOnCompleteListener(VerifyPhone.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if(task.isSuccessful()){
//                                    System.out.println(firebaseAuth.getCurrentUser().getUid().toString());
//                                    Intent intent=new Intent(getApplicationContext(), MainActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    startActivity(intent);
//                                }
//                                else{
//                                    Toast.makeText(VerifyPhone.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        })


            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
           Toast.makeText(VerifyPhone.this,e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    };

    private void verifycode(String vcode)
    {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(id,vcode);
        sign(credential);
//        final String email = getIntent().getStringExtra("email");
//        final String password = getIntent().getStringExtra("password");
//        firebaseAuth.createUserWithEmailAndPassword(email,password)
//                .addOnCompleteListener(VerifyPhone.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            final String phone = getIntent().getStringExtra("phone");
//
//                            String name = getIntent().getStringExtra("name");
//                            final String mmemail = email.replaceAll("\\.",",");
//                            final Map<String,String> user = new HashMap<String,String>();
//
//                            user.put("name",name);
//                            user.put("email",email);
//                            user.put("phone",phone);
//                            user.put("password",password);
//                            mfirebasedatabase.child("users").child(phone).setValue(user);
//                            mfirebasedatabase.child("emails").child(mmemail).setValue(email);
//                            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);
//                        }else{
//
//                            Toast.makeText(VerifyPhone.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });


//        String email = getIntent().getStringExtra("email");
//        String password = getIntent().getStringExtra("password");
//        firebaseAuth.signInWithEmailAndPassword(email,password)
//                .addOnCompleteListener(VerifyPhone.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                     if(task.isSuccessful()){
//                         System.out.println(firebaseAuth.getCurrentUser().getUid().toString());
//                         Intent intent=new Intent(getApplicationContext(), MainActivity.class);
//                         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                         startActivity(intent);
//                     }
//                     else{
//                         Toast.makeText(VerifyPhone.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
//                     }
//                    }
//                });

    }

    private void sign(PhoneAuthCredential credential) {
        final FirebaseAuth fbAuth= FirebaseAuth.getInstance();
        fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyPhone.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                           final String phone = getIntent().getStringExtra("phone");
                           final String email = getIntent().getStringExtra("email");
                            String password = getIntent().getStringExtra("password");
                            String name = getIntent().getStringExtra("name");
                            final String mmemail = email.replaceAll("\\.",",");
                            final Map<String,Object> user = new HashMap<String,Object>();

                            user.put("name",name);
                            user.put("email",email);
                            user.put("phone",phone);
//                            mfirebasedatabase.child("users").child(phone).setValue(user);
//                            mfirebasedatabase.child("emails").setValue(emails);

                            // mfirebasedatabase.setValue(user);

//                            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);

                            AuthCredential credential = EmailAuthProvider.getCredential(email, password);
                            fbAuth.getCurrentUser().linkWithCredential(credential).addOnCompleteListener(VerifyPhone.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                  if(task.isSuccessful()){
                                       firebaseFirestore.collection("USERS").document(fbAuth.getCurrentUser().getUid())
                                               .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                           @Override
                                           public void onComplete(@NonNull Task<Void> task) {
                                               if(task.isSuccessful()){
                                                   CollectionReference reference = firebaseFirestore.collection("USERS").document(fbAuth.getCurrentUser().getUid())
                                                           .collection("USER_DATA") ;
                                                   //////// MAPS
                                                   Map<String,Object> wishlistMap = new HashMap<>();
                                                   wishlistMap.put("list_size",(long)0);

                                                   Map<String,Object> ratingMap = new HashMap<>();
                                                   ratingMap.put("list_size",(long)0);

                                                   Map<String,Object> cartMap = new HashMap<>();
                                                   cartMap.put("list_size",(long)0);
                                                   Map<String,Object> myAddressMap = new HashMap<>();
                                                   myAddressMap.put("list_size",(long)0);
                                                   ////////// MAPS

                                                   /////////// LIST
                                                   final List<String> documentName = new ArrayList<>();
                                                   documentName.add("MY_WISHLIST");
                                                   documentName.add("MY_RATING");
                                                   documentName.add("MY_CART");
                                                   documentName.add("MY_ADDRESS");
                                                   List<Map<String, Object>> documentFields = new ArrayList<>();
                                                   documentFields.add(wishlistMap);
                                                   documentFields.add(ratingMap);
                                                   documentFields.add(cartMap);
                                                   documentFields.add(myAddressMap);
                                                   /////////// LIST
                                                   for (int i=0;i<documentName.size();i++){
                                                       final int finalI = i;
                                                       reference.document(documentName.get(i)).set(documentFields.get(i))
                                                               .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                   @Override
                                                                   public void onComplete(@NonNull Task<Void> task) {
                                                                       if(task.isSuccessful()){
                                                                           if(finalI == documentName.size()-1){
                                                                               mfirebasedatabase.child("users").child(phone).setValue(user);
                                                                               mfirebasedatabase.child("emails").child(mmemail).setValue(email);
                                                                               Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                                                                               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                               startActivity(intent);
                                                                           }
                                                                       }else{
                                                                           Toast.makeText(VerifyPhone.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                                                       }
                                                                   }
                                                               });
                                                   }

                                               }else{
                                                   Toast.makeText(VerifyPhone.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                               }
                                           }
                                       });

                                  }else{
                                      Toast.makeText(VerifyPhone.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        }
                        else
                        {
                            Toast.makeText(VerifyPhone.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    private void send(String phone){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);
    }
}

