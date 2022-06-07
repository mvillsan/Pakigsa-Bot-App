package com.example.pakigsabot.EstCancellationPolicyBO.Resto;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pakigsabot.EstCancellationPolicyBO.RestoCancelPolModel;
import com.example.pakigsabot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RestoCancelPolAdapter extends RecyclerView.Adapter<RestoCancelPolAdapter.MyViewHolder> {
    //creating variables for ArrayList and context
    Context context;
    ArrayList<RestoCancelPolModel> cancelPolArrayList;

    //Initializing variables
    ProgressDialog progressDialog;

    //Firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public RestoCancelPolAdapter() {
        //empty constructor needed
    }

    public RestoCancelPolAdapter(Context context, ArrayList<RestoCancelPolModel> cancelPolArrayList) {
        this.context = context;
        this.cancelPolArrayList = cancelPolArrayList;
        this.progressDialog = new ProgressDialog(context);
        this.fStore = FirebaseFirestore.getInstance();
        this.fAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //passing our layout file for displaying card item
        View itemView = LayoutInflater.from(context).inflate(R.layout.resto_cancelpol_items,parent,false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //setting data to our text views from model class
        RestoCancelPolModel list = cancelPolArrayList.get(position);
        holder.name.setText(list.getRestoCancelPolDesc());

        //Update an item to the Resort Cancellation Policy
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_cancelpol_popup))
                        .setExpanded(true,500)
                        .create();

                dialogPlus.show();

                //References::
                View viewDP = dialogPlus.getHolderView();
                EditText nameTxt = viewDP.findViewById(R.id.nameTxt);
                Button updateBtnPopUp = viewDP.findViewById(R.id.updateBtnPopUp);

                //Retrieving Data
                nameTxt.setText(list.getRestoCancelPolDesc());

                updateBtnPopUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Initialization of variables
                        String nameTxtStr = nameTxt.getText().toString();

                        //Validations::
                        if (nameTxtStr.isEmpty()) {
                            Toast.makeText(context, "Enter A Policy", Toast.LENGTH_SHORT).show();
                        } else{
                            //To save updates to the database
                            Map<String,Object> rules = new HashMap<>();
                            rules.put("resort_polID", list.getRestoCancelPolId());
                            rules.put("resort_desc", nameTxtStr);

                            String userID = fAuth.getCurrentUser().getUid();
                            fStore.collection("establishments").document(userID).collection("resort-cancellation-pol").document(list.getRestoCancelPolId()).set(rules)
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

        //Delete an item to the Resort Cancellation Policy
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context)
                        .setTitle("DELETE A RULE")
                        .setMessage("Do you want to Delete this Policy?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                progressDialog = new ProgressDialog(context);
                                progressDialog.setCancelable(false);
                                progressDialog.setTitle("Deleting...");
                                progressDialog.show();

                                String userID = fAuth.getCurrentUser().getUid();
                                fStore.collection("establishments").document(userID).collection("resort-cancellation-pol").document(list.getRestoCancelPolId())
                                        .delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                progressDialog.dismiss();
                                                Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                                cancelPolArrayList.remove(position);
                                                notifyItemRemoved(position);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(context, "Failed to Deleted!", Toast.LENGTH_SHORT).show();
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

    @Override
    public int getItemCount() {
        //returning the size of ArrayList
        return cancelPolArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //creating variables for textviews
        TextView name;
        ImageView update, delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.descTV);
            update = itemView.findViewById(R.id.updateBtn);
            delete = itemView.findViewById(R.id.deleteBtn);
        }
    }
}
