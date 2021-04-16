package com.enursery.rit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enursery.rit.Adapter.DisplayPlantAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class customer_cart extends AppCompatActivity {

    private RecyclerView mRvData;
    private DisplayPlantAdapter allDataAdapter;
    private DatabaseReference mDatabase;
    private TextView noPlantsText;
    LinearLayout noPlants;
    private FirebaseDatabase mFirebaseInstance;
    private Button addNewPlant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_cart);

        noPlants = findViewById(R.id.noPlants);
        addNewPlant = findViewById(R.id.addNewPlant);
        noPlantsText = findViewById(R.id.noPlantsText);
        addNewPlant.setText("Place New Orders");
        addNewPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), customer_buy_plants.class);
                startActivity(intent);
            }
        });
        mRvData = findViewById(R.id.plantViewList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvData.setLayoutManager(linearLayoutManager);
        final ArrayList<plant_add> list = new ArrayList<>();
        final DisplayPlantAdapter adapter = new DisplayPlantAdapter(this, list, "");
        mRvData.setAdapter(adapter);
        String UserID = new SharedPreference().getUser(this).getUserid();
        String UserName = new SharedPreference().getUser(this).getUname();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(UserID).child("orders");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    plant_add plant = snapshot1.getValue(plant_add.class);
                    list.add(plant);
                }
                if (list.size() == 0) {
                    noPlants.setVisibility(View.VISIBLE);
                    noPlantsText.setText("No Order have been Placed");
                    mRvData.setVisibility(View.GONE);
                    return;
                }
                allDataAdapter = new DisplayPlantAdapter(customer_cart.this, list, "");
                mRvData.setAdapter(allDataAdapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}