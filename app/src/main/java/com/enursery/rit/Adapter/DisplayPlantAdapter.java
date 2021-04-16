package com.enursery.rit.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.enursery.rit.R;
import com.enursery.rit.SharedPreference;
import com.enursery.rit.add_plant;
import com.enursery.rit.plant_add;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DisplayPlantAdapter extends RecyclerView.Adapter<DisplayPlantAdapter.ItemViewHolder> {
    private final String userType;
    private List<plant_add> plantlist = new ArrayList<>();
    private Context mContext;

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_layout, parent, false);
        return new ItemViewHolder(view);
    }

    public DisplayPlantAdapter(Context mContext, List<plant_add> nplantlist, String userType) {
        this.mContext = mContext;
        this.plantlist = nplantlist;
        this.userType = userType;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        final plant_add plant = plantlist.get(position);
        holder.plantname.setText(plant.getpName());
        holder.plantdesc.setText(plant.getpDesc());
        String orderplacedBy = plant.getpOrderByName();

        if (orderplacedBy != null) {
            holder.orderPlacedBy.setVisibility(View.VISIBLE);
            holder.orderPlacedBy.setText("Ordered By : "+orderplacedBy);
        }
        holder.uploadedBy.setText("Uploaded By : " + plant.getpUploadByName());
        Glide.with(mContext).load(plant.getpImage()).into(holder.plantImage);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userType.equalsIgnoreCase("customer")) {
                    showDetails(plant);
                }
            }
        });
    }

    private void showDetails(final plant_add plant) {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.plant_details_layout);
        ImageView plantImage = dialog.findViewById(R.id.plantImage);
        TextView uploadedBy = dialog.findViewById(R.id.uploadedBy);
        TextView plantname = dialog.findViewById(R.id.plantname);
        TextView plantdesc = dialog.findViewById(R.id.plantdesc);
        Button place_order = dialog.findViewById(R.id.place_order);
        plantname.setText(plant.getpName());
        plantdesc.setText(plant.getpDesc());
        uploadedBy.setText(plant.getpUploadByName());
        Glide.with(mContext).load(plant.getpImage()).into(plantImage);
        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userType.equalsIgnoreCase("customer")) {
//                    showDetails(plant);
                    dialog.dismiss();
                    placeOrder(plant);
                }
            }
        });

        dialog.show();
    }

    private void placeOrder(plant_add plant) {
        String UserName = new SharedPreference().getUser(mContext).getUname();
        String customerUID = new SharedPreference().getUser(mContext).getUserid();
        String supplierID = plant.getpUploadByID();
        DatabaseReference databaserefCus = FirebaseDatabase.getInstance().getReference("users").child(customerUID).child("orders");
        DatabaseReference databaserefSup = FirebaseDatabase.getInstance().getReference("users").child(supplierID).child("orders");
        String id = databaserefCus.push().getKey();
        databaserefCus.child(id).setValue(plant);
        plant.setpOrderByName(UserName);
        plant.setpOrderedById(customerUID);
        databaserefSup.child(id).setValue(plant);
        Toast.makeText(mContext, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return plantlist == null ? 0 : plantlist.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView plantname, plantdesc, uploadedBy, orderPlacedBy;
        ImageView plantImage;
        CardView cardView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            plantname = itemView.findViewById(R.id.plantname);
            plantdesc = itemView.findViewById(R.id.plantdesc);
            uploadedBy = itemView.findViewById(R.id.uploadedBy);
            cardView = itemView.findViewById(R.id.cardView);
            plantImage = itemView.findViewById(R.id.plantImage);
            orderPlacedBy = itemView.findViewById(R.id.orderPlacedBy);

        }
    }
}