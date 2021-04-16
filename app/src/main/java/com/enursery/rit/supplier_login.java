package com.enursery.rit;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.enursery.rit.Adapter.DisplayPlantAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class supplier_login extends AppCompatActivity {

    private EditText etemail, etpassword;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_login);
        auth = FirebaseAuth.getInstance();
        etemail = (EditText) findViewById(R.id.email);
        etpassword = (EditText) findViewById(R.id.password);
        TextView tv = (TextView) findViewById(R.id.loginlink);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(supplier_login.this, registration.class);
                startActivity(intent1);
            }
        });
        Button btn = (Button) findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();
            }
        });
    }

    public void signin() {

        String email = etemail.getText().toString();
        String password = etpassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(supplier_login.this, "Enter Email!", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(supplier_login.this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }else if (password.length() < 6) {
            Toast.makeText(supplier_login.this, "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }


        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull final Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    DatabaseReference userreference = FirebaseDatabase.getInstance().getReference("users");
                    final SharedPreference sharedPreference = new SharedPreference();
                    userreference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            registration_supplier user = snapshot.child(task.getResult().getUser().getUid()).getValue(registration_supplier.class);
                            if (user != null) {
                                if (user.usertype.equalsIgnoreCase("supplier")) {
                                    sharedPreference.save(supplier_login.this, "name", user.uname);
                                    sharedPreference.save(supplier_login.this, "usertype", user.usertype);
                                    sharedPreference.save(supplier_login.this, "email", user.email);
                                    sharedPreference.save(supplier_login.this, "userid", user.userid);
                                    Toast.makeText(supplier_login.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(supplier_login.this, supplier.class);
                                    startActivity(intent);
                                } else
                                    Toast.makeText(supplier_login.this, "Customer cannot login as supplier", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    Toast.makeText(supplier_login.this, "Login Unsuccessfull", Toast.LENGTH_SHORT).show();
                }
                cleartxt();

            }


        });
    }

    public void cleartxt() {
        etemail.setText("");
        etpassword.setText("");

    }

}


