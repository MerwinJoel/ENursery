package com.enursery.rit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.enursery.rit.Adapter.DisplayPlantAdapter;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class view_plants extends AppCompatActivity {

    private RecyclerView mRvData;
    private DisplayPlantAdapter allDataAdapter;
    private DatabaseReference mDatabase;
    private TextView mTvEmpty;
    LinearLayout noPlants;
    private FirebaseDatabase mFirebaseInstance;
    private Button addNewPlant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_plants);

        noPlants = findViewById(R.id.noPlants);
        addNewPlant = findViewById(R.id.addNewPlant);
        addNewPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), add_plant.class);
                startActivity(intent);
            }
        });
        mRvData = findViewById(R.id.plantViewList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvData.setLayoutManager(linearLayoutManager);
        final ArrayList<plant_add> list = new ArrayList<>();
        final DisplayPlantAdapter adapter = new DisplayPlantAdapter(this, list,"supplier");
        mRvData.setAdapter(adapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userreference = FirebaseDatabase.getInstance().getReference("users");

        reference.addValueEventListener(new  ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        list.clear();
                                                        for (DataSnapshot snapshot1 : snapshot.child("plants").getChildren()) {
                                                            plant_add plant = snapshot1.getValue(plant_add.class);
                                                            if (plant.pUploadByID.equalsIgnoreCase(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                                                                list.add(plant);
                                                        }
                                                        if (list.size() == 0) {
                                                            noPlants.setVisibility(View.VISIBLE);
                                                            mRvData.setVisibility(View.GONE);
                                                            return;
                                                        }
                                                        allDataAdapter = new DisplayPlantAdapter(view_plants.this, list,"supplier");
                                                        mRvData.setAdapter(allDataAdapter);
                                                        adapter.notifyDataSetChanged();
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });

    }
}