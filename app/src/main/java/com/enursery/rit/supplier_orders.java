package com.enursery.rit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enursery.rit.Adapter.DisplayPlantAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class supplier_orders extends AppCompatActivity {

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
        setContentView(R.layout.activity_supplier_orders);

        noPlants = findViewById(R.id.noPlants);
        addNewPlant = findViewById(R.id.addNewPlant);
        noPlantsText = findViewById(R.id.noPlantsText);
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
                    noPlantsText.setText("No Order have been Placed by Customer");
                    mRvData.setVisibility(View.GONE);
                    return;
                }
                allDataAdapter = new DisplayPlantAdapter(supplier_orders.this, list, "");
                mRvData.setAdapter(allDataAdapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}