package com.enursery.rit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Objects;

public class add_plant extends AppCompatActivity implements View.OnClickListener {
    EditText etpid, etpdesc, etpfamily, etpname, etplocation, pqty;
    Button btn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Button pChoose, pUpload;
    ImageView pImage;

    Uri filePath;

    private static final int PICK_IMAGE_REQUEST = 234;

    String pname, pDesc, plocation, pquantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);
        etpname = findViewById(R.id.pname);
        etplocation = findViewById(R.id.plocation);
        etpdesc = findViewById(R.id.pdesc);
        pqty = findViewById(R.id.pquantity);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        btn = findViewById(R.id.button);
        pChoose = findViewById(R.id.buttonChooseImage);
        pUpload = findViewById(R.id.buttonAddImage);
        pImage = findViewById(R.id.addImage);

        pChoose.setOnClickListener(this);
        btn.setOnClickListener(this);

        /*btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchactivity();

            }
        });*/
    }

    private void launchactivity() {

        if (TextUtils.isEmpty(pname)) {
            Toast.makeText(this, "Please Enter plant name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pDesc)) {
            Toast.makeText(this, "Please Enter plant Description", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(plocation)) {
            Toast.makeText(this, "Please Enter plant location", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pquantity)) {
            Toast.makeText(this, "Please Enter plant quantity", Toast.LENGTH_SHORT).show();
        }
        else {
            /*Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show();*/
        }

    }

    private void uploadPlantDetails(String imageURL) {
        pname = etpname.getText().toString().trim();
        pDesc = etpdesc.getText().toString().trim();
        plocation = etplocation.getText().toString().trim();
        pquantity = pqty.getText().toString().trim();

        String pUploadedByID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        String pUploadedByName = new SharedPreference().getUser(this).getUname(); // TODO Fetch Name from Local DB SQL LITE
        String id = databaseReference.push().getKey();
        add_plant plant = new add_plant();
        String pimage;
        plant_add plant_add = new plant_add(
                id, pname, plocation, pDesc,  imageURL, pUploadedByID, pUploadedByName);
        databaseReference.child("plants").child(id).setValue(plant_add);
        Cleartxt();
    }

    private void Cleartxt() {
        etpdesc.setText("");
        etplocation.setText("");
        etpname.setText("");
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                pImage.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    } 

    @Override
    public void onClick(View view) {
        //if the clicked button is choose
        if (view == pChoose) {
            showFileChooser();
        }
        //if the clicked button is upload
        else if (view == btn) {
            /*if (TextUtils.isEmpty(pname)) {
                Toast.makeText(this, "Please Enter plant name", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(pDesc)) {
                Toast.makeText(this, "Please Enter plant Description", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(plocation)) {
                Toast.makeText(this, "Please Enter plant location", Toast.LENGTH_SHORT).show();
            } else {*/
            uploadFile();
//            }
        }
    }

    //this method will upload the file
    private void uploadFile() {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();
            final StorageReference riversRef = storageReference.child("plants/" + System.currentTimeMillis() + ".jpg");
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri downloadPhotoUrl) {
                                    //Now play with downloadPhotoUrl
                                    uploadPlantDetails(downloadPhotoUrl.toString());
                                }
                            });
                            progressDialog.dismiss();
                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), "Plant Uploaded Successfully", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }
}