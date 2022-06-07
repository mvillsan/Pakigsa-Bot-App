package com.example.pakigsabot.CoworkingSpaceBO.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pakigsabot.CoworkingSpaceBO.Model.CoworkingSpaceFacilities;
import com.example.pakigsabot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CoworkingSpaceAdapter extends RecyclerView.Adapter<CoworkingSpaceAdapter.MyViewHolder> {
    //creating variables for Context and ArrayList
    Context context;
    ArrayList<CoworkingSpaceFacilities> coSpaceFacArrayList;

    //initializing variables
    ProgressDialog progressDialog;

    //Firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public CoworkingSpaceAdapter() {
        //Empty constructor needed
    }

    //creating constructor for the adapter class
    public CoworkingSpaceAdapter(Context context, ArrayList<CoworkingSpaceFacilities> coSpaceFacArrayList) {
        this.context = context;
        this.coSpaceFacArrayList = coSpaceFacArrayList;
        this.progressDialog = new ProgressDialog(context);
        this.fStore = FirebaseFirestore.getInstance();
        this.fAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public CoworkingSpaceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //passing layout file to display the items
        View itemView = LayoutInflater.from(context).inflate(R.layout.coworking_facilities_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull CoworkingSpaceAdapter.MyViewHolder holder, int position) {
        //creating  object of DentalClinicProcedures class and setting data to the textviews from the DentalClinicProcedures class
        CoworkingSpaceFacilities coworkFacList = coSpaceFacArrayList.get(position);

        holder.name.setText(coworkFacList.getCoWorkFacName());
        holder.cap.setText(coworkFacList.getCoWorkFacCap());
        holder.desc.setText(coworkFacList.getCoWorkFacDesc());
        holder.rate.setText(coworkFacList.getCoWorkFacRate());
        Picasso.get()
                .load(coworkFacList.getCoWorkFacImgUrl())
                .fit()
                .centerCrop()
                .into(holder.img);

        //Update an item from the dental procedures
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_coworking_space_popup))
                        .setExpanded(true, 1300)
                        .create();

                dialogPlus.show();

                //References
                View viewDP = dialogPlus.getHolderView();
                TextInputLayout facNameLayout = viewDP.findViewById(R.id.updateFacilityNameLayout);
                TextInputLayout facCapLayout = viewDP.findViewById(R.id.updateFacilityCapLayout);
                TextInputLayout facDescLayout = viewDP.findViewById(R.id.updateFacilityDescLayout);
                TextInputLayout facRateLayout = viewDP.findViewById(R.id.updateFacilityRateLayout);
                TextInputLayout facImgLayout = viewDP.findViewById(R.id.imgLayout);
                TextInputEditText facNameTxt = viewDP.findViewById(R.id.nameTxt);
                TextInputEditText facCapTxt = viewDP.findViewById(R.id.capTxt);
                TextInputEditText facDescTxt = viewDP.findViewById(R.id.descTxt);
                TextInputEditText facRateTxt = viewDP.findViewById(R.id.updateRateTxt);
                TextInputEditText imgTxt = viewDP.findViewById(R.id.imgTxt);
                Button updateBtnPopUp = viewDP.findViewById(R.id.updateBtnPopUp);

                //Retrieving Data
                facNameTxt.setText(coworkFacList.getCoWorkFacName());
                facDescTxt.setText(coworkFacList.getCoWorkFacDesc());
                facCapTxt.setText(coworkFacList.getCoWorkFacCap());
                facRateTxt.setText(coworkFacList.getCoWorkFacRate());
                imgTxt.setText(coworkFacList.getCoWorkFacImgUrl());

                updateBtnPopUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Initialization of variables
                        String txtFacName = facNameTxt.getText().toString();
                        String txtFacDesc = facDescTxt.getText().toString();
                        String txtFacCap = facCapTxt.getText().toString();
                        String txtFacRate = facRateTxt.getText().toString();
                        String txtImg = imgTxt.getText().toString();
                        Glide.with(context).load(txtImg).into(holder.img);

                        //Validations
                        if (txtFacName.isEmpty() || txtFacCap.isEmpty() || txtFacDesc.isEmpty() || txtFacRate.isEmpty()) {
                            Toast.makeText(context, "Some fields are EMPTY.", Toast.LENGTH_SHORT).show();
                        } else {
                            if (txtFacName.isEmpty()) {
                                facNameLayout.setError("Enter Name of Facility");
                            } else {
                                Boolean validName = txtFacName.matches("[A-Za-z][A-Za-z ]*+");
                                if (!validName) {
                                    facNameLayout.setError("Invalid Facility Name");
                                } else {
                                    facNameLayout.setErrorEnabled(false);
                                    facNameLayout.setError("");
                                }
                            }
                            if (txtFacDesc.isEmpty()) {
                                facDescLayout.setError("Enter Description");
                            } else {
                                facDescLayout.setErrorEnabled(false);
                                facDescLayout.setError("");
                            }
                            if (txtFacCap.isEmpty()) {
                                facCapLayout.setError("Enter Capacity");
                            } else {
                                facCapLayout.setErrorEnabled(false);
                                facCapLayout.setError("");
                            }
                            if (txtFacRate.isEmpty()) {
                                facRateLayout.setError("Enter Rate");
                            } else {
                                facRateLayout.setErrorEnabled(false);
                                facRateLayout.setError("");
                            }
                            if (txtImg.isEmpty()) {
                                facImgLayout.setError("No image selected");
                            } else {
                                facImgLayout.setErrorEnabled(false);
                                facImgLayout.setError("");
                            }

                            //To save updates to the database
                            Map<String, Object> coworkFac = new HashMap<>();
                            coworkFac.put("fac_name", txtFacName);
                            coworkFac.put("fac_desc", txtFacDesc);
                            coworkFac.put("fac_cap", txtFacCap);
                            coworkFac.put("fac_rate", txtFacRate);
                            coworkFac.put("fac_image", imgTxt.getText().toString().trim());
                            coworkFac.put("fac_id", coworkFacList.getCoWorkFacId());

                            String userId = fAuth.getCurrentUser().getUid();
                            fStore.collection("establishments").document(userId).collection("coworking-space-facilities").document(coworkFacList.getCoWorkFacId()).set(coworkFac)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(context, " Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, " Error While Updating!", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                        }
                                    });
                        }
                    }
                });
            }
        });

        //Delete from dental-procedures
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context)
                        .setTitle("DELETE SERVICE")
                        .setMessage("Do you want to Delete " + coworkFacList.getCoWorkFacName() + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                progressDialog = new ProgressDialog(context);
                                progressDialog.setCancelable(false);
                                progressDialog.setTitle("Deleting...");
                                progressDialog.show();

                                String userId = fAuth.getCurrentUser().getUid();
                                fStore.collection("establishments").document(userId).collection("internet-cafe-facilities").document(coworkFacList.getCoWorkFacId())
                                        .delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                progressDialog.dismiss();
                                                Toast.makeText(context, coworkFacList.getCoWorkFacName() + " Successfully Deleted", Toast.LENGTH_SHORT).show();
                                                coSpaceFacArrayList.remove(position);
                                                notifyItemRemoved(position);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(context, "Failed to Delete!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                alert.show();
            }
        });
    }

    //item count of the items
    @Override
    public int getItemCount() {

        return coSpaceFacArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        //refer all elements where data will be populated
        TextView name, cap, desc, rate;
        ImageView img, update, delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.coworkingRVName);
            desc = itemView.findViewById(R.id.coworkingRVDesc);
            cap = itemView.findViewById(R.id.coworkingRVCap);
            rate = itemView.findViewById(R.id.coworkingRVRate);
            img = itemView.findViewById(R.id.imgCoworking);
            update = itemView.findViewById(R.id.coworkingUpdateBtn);
            delete = itemView.findViewById(R.id.coworkingDeleteBtn);
        }
    }
}
