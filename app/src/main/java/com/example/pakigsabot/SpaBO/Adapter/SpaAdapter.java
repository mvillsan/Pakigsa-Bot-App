package com.example.pakigsabot.SpaBO.Adapter;

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
import com.example.pakigsabot.R;
import com.example.pakigsabot.SpaBO.Model.SpaServices;
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

public class SpaAdapter extends RecyclerView.Adapter<SpaAdapter.MyViewHolder> {
    //creating variables for Context and ArrayList
    Context context;
    ArrayList<SpaServices> spaServicesArrayList;

    //initializing variables
    ProgressDialog progressDialog;

    //Firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public SpaAdapter() {
        //Empty constructor needed
    }

    //creating constructor for the adapter class
    public SpaAdapter(Context context, ArrayList<SpaServices> spaServicesArrayList) {
        this.context = context;
        this.spaServicesArrayList = spaServicesArrayList;
        this.progressDialog = new ProgressDialog(context);
        this.fStore = FirebaseFirestore.getInstance();
        this.fAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public SpaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //passing layout file to display the items
        View itemView = LayoutInflater.from(context).inflate(R.layout.spa_salon_icafe_services_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull SpaAdapter.MyViewHolder holder, int position) {
        //creating  object of DentalClinicProcedures class and setting data to the textviews from the DentalClinicProcedures class
        SpaServices spaServicesList = spaServicesArrayList.get(position);

        holder.name.setText(spaServicesList.getSpaServiceName());
        holder.desc.setText(spaServicesList.getSpaServiceDesc());
        holder.rate.setText(spaServicesList.getSpaServiceRate());
        Picasso.get()
                .load(spaServicesList.getSpaServiceImg())
                .fit()
                .centerCrop()
                .into(holder.img);

        //Update an item from the dental procedures
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_spa_salon_icafe_popup))
                        .setExpanded(true, 1100)
                        .create();

                dialogPlus.show();

                //References
                View viewDP = dialogPlus.getHolderView();
                TextInputLayout servNameLayout = viewDP.findViewById(R.id.updateServiceNameLayout);
                TextInputLayout servDescLayout = viewDP.findViewById(R.id.updateServiceDescLayout);
                TextInputLayout servRateLayout = viewDP.findViewById(R.id.updateServiceRateLayout);
                TextInputLayout imgLayout = viewDP.findViewById(R.id.imgLayout);
                TextInputEditText servNameTxt = viewDP.findViewById(R.id.nameTxt);
                TextInputEditText servDescTxt = viewDP.findViewById(R.id.descTxt);
                TextInputEditText servRateTxt = viewDP.findViewById(R.id.rateTxt);
                TextInputEditText imgTxt = viewDP.findViewById(R.id.imgTxt);
                Button updateBtnPopUp = viewDP.findViewById(R.id.updateBtnPopUp);

                //Retrieving Data
                servNameTxt.setText(spaServicesList.getSpaServiceName());
                servDescTxt.setText(spaServicesList.getSpaServiceDesc());
                servRateTxt.setText(spaServicesList.getSpaServiceRate());
                imgTxt.setText(spaServicesList.getSpaServiceImg());

                updateBtnPopUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Initialization of variables
                        String txtServName = servNameTxt.getText().toString();
                        String txtServDesc = servDescTxt.getText().toString();
                        String txtServRate = servRateTxt.getText().toString();
                        String txtImg = imgTxt.getText().toString();
                        Glide.with(context).load(txtImg).into(holder.img);

                        //Validations
                        if (txtServName.isEmpty() || txtServDesc.isEmpty() || txtServRate.isEmpty()) {
                            Toast.makeText(context, "Some fields are EMPTY.", Toast.LENGTH_SHORT).show();
                        } else {
                            if (txtServName.isEmpty()) {
                                servNameLayout.setError("Enter Name of Service");
                            } else {
                                Boolean validName = txtServName.matches("[A-Za-z][A-Za-z ]*+");
                                if (!validName) {
                                    servNameLayout.setError("Invalid Service Name");
                                } else {
                                    servNameLayout.setErrorEnabled(false);
                                    servNameLayout.setError("");
                                }
                            }
                            if (txtServDesc.isEmpty()) {
                                servDescLayout.setError("Enter Description");
                            } else {
                                servDescLayout.setErrorEnabled(false);
                                servDescLayout.setError("");
                            }
                            if (txtServRate.isEmpty()) {
                                servRateLayout.setError("Enter Rate");
                            } else {
                                servRateLayout.setErrorEnabled(false);
                                servRateLayout.setError("");
                            }
                            if (txtImg.isEmpty()) {
                                imgLayout.setError("No image selected");
                            } else {
                                imgLayout.setErrorEnabled(false);
                                imgLayout.setError("");
                            }

                            //To save updates to the database
                            Map<String, Object> spaServices = new HashMap<>();
                            spaServices.put("serv_name", txtServName);
                            spaServices.put("serv_desc", txtServDesc);
                            spaServices.put("serv_rate", txtServRate);
                            spaServices.put("serv_image", imgTxt.getText().toString().trim());
                            spaServices.put("serv_id", spaServicesList.getSpaServiceId());

                            String userId = fAuth.getCurrentUser().getUid();
                            fStore.collection("establishments").document(userId).collection("spa-services").document(spaServicesList.getSpaServiceId()).set(spaServices)
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
                        .setMessage("Do you want to Delete " + spaServicesList.getSpaServiceName() + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                progressDialog = new ProgressDialog(context);
                                progressDialog.setCancelable(false);
                                progressDialog.setTitle("Deleting...");
                                progressDialog.show();

                                String userId = fAuth.getCurrentUser().getUid();
                                fStore.collection("establishments").document(userId).collection("spa-services").document(spaServicesList.getSpaServiceId())
                                        .delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                progressDialog.dismiss();
                                                Toast.makeText(context, spaServicesList.getSpaServiceName() + " Successfully Deleted", Toast.LENGTH_SHORT).show();
                                                spaServicesArrayList.remove(position);
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

        return spaServicesArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        //refer all elements where data will be populated
        TextView name, desc, rate;
        ImageView img, update, delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.estRVName);
            desc = itemView.findViewById(R.id.estRVDesc);
            rate = itemView.findViewById(R.id.estRVRate);
            img = itemView.findViewById(R.id.estImgService);
            update = itemView.findViewById(R.id.estUpdateBtn);
            delete = itemView.findViewById(R.id.estDeleteBtn);
        }
    }
}
