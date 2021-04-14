package com.relativecoding.mycartecommerce.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.relativecoding.mycartecommerce.HomeActivity;
import com.relativecoding.mycartecommerce.R;
import com.relativecoding.mycartecommerce.SignInActivity;
import com.relativecoding.mycartecommerce.SignUpActivity;
import com.relativecoding.mycartecommerce.databinding.ActivitySignInWithPhoneBinding;

import java.util.concurrent.TimeUnit;

public class SignInWithPhoneActivity extends AppCompatActivity {

    ActivitySignInWithPhoneBinding binding;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth auth;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivitySignInWithPhoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        database=FirebaseDatabase.getInstance();
        reference=database.getReference();
        auth=FirebaseAuth.getInstance();

        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(binding.btnSignIn.getText().toString().equals("SEND OTP")){
                    String phoneNumber=binding.etPhoneNumber.getText().toString();

                    if(phoneNumber.isEmpty()){
                        Toast.makeText(SignInWithPhoneActivity.this, "Enter your phone number", Toast.LENGTH_SHORT).show();
                    }else{
                        PhoneAuthOptions options=PhoneAuthOptions.newBuilder(auth)
                                .setPhoneNumber("+91"+phoneNumber)
                                .setTimeout(60L,TimeUnit.SECONDS)
                                .setActivity(SignInWithPhoneActivity.this)
                                .setCallbacks(callbacks)
                                .build();

                        PhoneAuthProvider.verifyPhoneNumber(options);


                    }
                }
                else{
                    verifyOtp();
                }

            }
        });


        binding.createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInWithPhoneActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.tvSignInWithEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignInWithPhoneActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        callbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signIn(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(SignInWithPhoneActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                binding.etOtp.setVisibility(View.VISIBLE);
                binding.btnSignIn.setText("VERIFY");
                binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(binding.btnSignIn.getText().toString().equals("VERIFY")){
                            String otp=binding.etOtp.getText().toString();
                            if(!otp.isEmpty()){
                                PhoneAuthCredential phoneAuthCredential=PhoneAuthProvider.getCredential(s,otp);
                                signIn(phoneAuthCredential);
                            }else {
                                Toast.makeText(SignInWithPhoneActivity.this, "Enter OTP", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        };

    }

    private void verifyOtp() {

    }

    private  void signIn(PhoneAuthCredential phoneAuthCredential){
        auth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent=new Intent(SignInWithPhoneActivity.this, HomeActivity.class);
                    startActivity(intent);
                    binding.btnSignIn.setText("SEND OTP");
                    binding.etOtp.setText("");
                    binding.etOtp.setVisibility(View.GONE);

                    finish();
                }else {
                    Toast.makeText(SignInWithPhoneActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}