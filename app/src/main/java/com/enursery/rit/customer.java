package com.enursery.rit;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class customer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
       /* ImageView ivGraphRepoting = findViewById(R.id.ivGraphReporting);
        ImageView ivProgressMonitoring = findViewById(R.id.ivProgressReporting);*/
        ImageView logout = findViewById(R.id.logout1);
        logout.bringToFront();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SharedPreference().clearSharedPreference(customer.this);
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(customer.this, "Logged out Successfuly", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(customer.this, menus.class));
            }
        });
        LinearLayout viewPlant = findViewById(R.id.view_plants);
        LinearLayout viewCart = findViewById(R.id.viewCart);
        viewPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), customer_buy_plants.class);
                startActivity(intent);
            }
        });

        viewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), customer_cart.class);
                startActivity(intent);
            }
        });
   /*     ivGraphRepoting.setOnClickListener(new View.OnClickListener() {
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
        });
*/

    }
}
