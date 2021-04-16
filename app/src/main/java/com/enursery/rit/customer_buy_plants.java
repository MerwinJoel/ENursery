package com.enursery.rit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.enursery.rit.Adapter.DisplayPlantAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class customer_buy_plants extends AppCompatActivity {
    private RecyclerView mRvData;
    private DisplayPlantAdapter allDataAdapter;
    private DatabaseReference mDatabase;
    private TextView mTvEmpty;
    private FirebaseDatabase mFirebaseInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_buy_plants);

        mRvData = findViewById(R.id.plantViewList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvData.setLayoutManager(linearLayoutManager);
        final ArrayList<plant_add> list = new ArrayList<>();
        final DisplayPlantAdapter adapter = new DisplayPlantAdapter(this, list,"customer");
        mRvData.setAdapter(adapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userreference = FirebaseDatabase.getInstance().getReference("users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.child("plants").getChildren()) {
                    list.add(snapshot1.getValue(plant_add.class));
                }
                allDataAdapter = new DisplayPlantAdapter(customer_buy_plants.this, list,"customer");
                mRvData.setAdapter(allDataAdapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}