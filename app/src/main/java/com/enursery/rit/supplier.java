package com.enursery.rit;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class supplier extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);

        /*ImageView ivGraphRepoting = findViewById(R.id.ivGraphReporting);
        ImageView ivProgressMonitoring = findViewById(R.id.ivProgressReporting);*/
        LinearLayout addPlant = findViewById(R.id.addPlant);
        LinearLayout viewPlant = findViewById(R.id.viewPlant);
        LinearLayout viewOrders = findViewById(R.id.viewOrders);
        ImageView logout = findViewById(R.id.logout);
        logout.bringToFront();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SharedPreference().clearSharedPreference(supplier.this);
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(supplier.this, "Logged out Successfuly", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(supplier.this, menus.class));
            }
        });
                /*ivGraphRepoting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GraphReportActivity.class);
                startActivity(intent);
            }
        });

        ivProgressMonitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProgressReportActivity.class);
                startActivity(intent);
            }
        });*/

        viewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), supplier_orders.class);
                startActivity(intent);
            }
        });
        addPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), add_plant.class);
                startActivity(intent);
            }
        });

        viewPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), view_plants.class);
                startActivity(intent);
            }
        });


    }
}
