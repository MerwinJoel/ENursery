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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class login extends AppCompatActivity {
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
                Intent intent1 = new Intent(login.this, registration.class);
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
            Toast.makeText(login.this, "Enter Email!", Toast.LENGTH_SHORT).show();

            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(login.this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }


        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(login.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(login.this, customer.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(login.this, "Login Unsuccessfull", Toast.LENGTH_SHORT).show();
                }
                cleartxt();

            }


        });
    }
    public void cleartxt(){
        etemail.setText("");
        etpassword.setText("");

    }

}

