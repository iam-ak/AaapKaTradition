package com.relativecoding.mycartecommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.relativecoding.mycartecommerce.Models.Users;
import com.relativecoding.mycartecommerce.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        progressDialog=new ProgressDialog(com.relativecoding.mycartecommerce.SignUpActivity.this);
        progressDialog.setTitle("Creating user");
        progressDialog.setMessage("We are creating your account");

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        reference=database.getReference();


        binding.tvAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(com.relativecoding.mycartecommerce.SignUpActivity.this, com.relativecoding.mycartecommerce.SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.etEmail.getText().toString().isEmpty() || binding.etPassword.getText().toString().isEmpty())
                {
                    Toast.makeText(com.relativecoding.mycartecommerce.SignUpActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.show();
                auth.createUserWithEmailAndPassword(binding.etEmail.getText().toString(),binding.etPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();

                                if(task.isSuccessful())
                                {
                                    Users user=new Users(binding.etName.getText().toString(),binding.etEmail.getText().toString(),binding.etPassword.getText().toString());
                                    String id=task.getResult().getUser().getUid();
                                    reference=reference.child("Users");
                                    reference.child(id).setValue(user);

                                    Toast.makeText(com.relativecoding.mycartecommerce.SignUpActivity.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(com.relativecoding.mycartecommerce.SignUpActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                );
            }
        });

    }
}