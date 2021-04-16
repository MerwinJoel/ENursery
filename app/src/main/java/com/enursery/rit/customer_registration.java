package com.enursery.rit;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class customer_registration extends AppCompatActivity {
    private EditText etuname, etemail, etpassword, etcpassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        auth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        etuname = (EditText) findViewById(R.id.uname);
        etemail = (EditText) findViewById(R.id.email);
        etpassword = (EditText) findViewById(R.id.password);
        etcpassword = (EditText) findViewById(R.id.cpassword);
        Button btn = (Button) findViewById(R.id.button);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etuname.getText().toString().trim();
                final String email = etemail.getText().toString().trim();
                final String password = etpassword.getText().toString().trim();
                String cpassword = etcpassword.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter The Name!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter Email Address!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter Password!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (password.length() < 6) {
                    Toast.makeText(customer_registration.this, "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(customer_registration.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    Toast.makeText(customer_registration.this, "Registration unsuccessfull",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(customer_registration.this, "Registration successfull", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    String userID = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getUser()).getUid();
                                    registration_supplier supplier = new registration_supplier(
                                            userID, etuname.getText().toString(), email, password, "customer"
                                    );
                                    databaseReference.child(userID).setValue(supplier);
                                    startActivity(new Intent(customer_registration.this, customer.class));
                                    finish();
                                }
                            }
                        });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}



