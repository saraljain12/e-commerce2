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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class ForgetPassOtp extends AppCompatActivity {

    private Button verify;
    private EditText phn;
    private ProgressBar pb;
    private String id;
    DatabaseReference mfirebasedatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass_otp);

        verify=findViewById(R.id.forget_verify_btn);
        phn=findViewById(R.id.forget_verification_code_entered_by_user);
        pb=findViewById(R.id.forget_progress_bar);
        pb.setVisibility(View.GONE);
         System.out.println("hello");
        final String phonenum = getIntent().getStringExtra("phonenum");
        send(phonenum);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code=phn.getText().toString();
                if (code.isEmpty()||code.length()<6)
                {
                    phn.setError("wrong otp");
                    phn.requestFocus();
                    return;
                }
                else{
                    pb.setVisibility(View.VISIBLE);
                    verifycode(code);




                }}

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


            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(ForgetPassOtp.this,e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    };
    private void verifycode(final String vcode)
    {
        final PhoneAuthCredential credential=PhoneAuthProvider.getCredential(id,vcode);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(ForgetPassOtp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   firebaseAuth.signOut();

                   String phonenum = getIntent().getStringExtra("phonenum");
                   Intent resetpasswordintent = new Intent(ForgetPassOtp.this,ResetPassword.class);
                   resetpasswordintent.putExtra("phonenum",phonenum);
                   resetpasswordintent.putExtra("id",id);
                   resetpasswordintent.putExtra("code",vcode);
                   startActivity(resetpasswordintent);
                   finish();

               }else{
                   Toast.makeText(ForgetPassOtp.this,task.getException().toString(), Toast.LENGTH_SHORT).show();
               }
            }
        });

    }
}