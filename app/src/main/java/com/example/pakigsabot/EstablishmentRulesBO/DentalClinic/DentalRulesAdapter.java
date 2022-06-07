package com.example.pakigsabot.EstablishmentRulesBO.DentalClinic;

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

public class DentalRulesAdapter extends RecyclerView.Adapter<DentalRulesAdapter.MyViewHolder>{
    //creating variables for Context and ArrayList
    Context context;
    ArrayList<DentalRulesModel> dentalERArrayList;

    //initializing variables
    ProgressDialog progressDialog;

    //Firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public DentalRulesAdapter() {
    }

    public DentalRulesAdapter(Context context, ArrayList<DentalRulesModel> dentalERArrayList) {
        this.context = context;
        this.dentalERArrayList = dentalERArrayList;
        this.progressDialog = new ProgressDialog(context);
        this.fStore =FirebaseFirestore.getInstance();
        this.fAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public DentalRulesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //passing layout file to display the items
        View itemView = LayoutInflater.from(context).inflate(R.layout.rules_items,parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull DentalRulesAdapter.MyViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        DentalRulesModel list = dentalERArrayList.get(position);
        holder.desc.setText(list.getDentalRuleDesc());

        //Update an item to the Resort Rules
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.desc.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_rules_popup))
                        .setExpanded(true,500)
                        .create();

                dialogPlus.show();

                //References::
                View viewDP = dialogPlus.getHolderView();
                EditText descTxt = viewDP.findViewById(R.id.descTxt);
                Button updateBtnPopUp = viewDP.findViewById(R.id.updateBtnPopUp);

                //Retrieving Data
                descTxt.setText(list.getDentalRuleDesc());

                updateBtnPopUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Initialization of variables
                        String descTxtStr = descTxt.getText().toString();

                        //Validations::
                        if (descTxtStr.isEmpty()) {
                            Toast.makeText(context, "Enter A Rule", Toast.LENGTH_SHORT).show();
                        } else{
                            //To save updates to the database
                            Map<String,Object> rules = new HashMap<>();
                            rules.put("dental_ruleId", list.getDentalRuleId());
                            rules.put("dental_desc", descTxtStr);
                            rules.put("estId", list.getEstId());


                            String userID = fAuth.getCurrentUser().getUid();
                            fStore.collection("establishments").document(userID).collection("dental-est-rules").document(list.getDentalRuleId()).set(rules)
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

        //Delete an item to the Resort Rules
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context)
                        .setTitle("DELETE A RULE")
                        .setMessage("Do you want to Delete this Rule?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                progressDialog = new ProgressDialog(context);
                                progressDialog.setCancelable(false);
                                progressDialog.setTitle("Deleting...");
                                progressDialog.show();

                                String userID = fAuth.getCurrentUser().getUid();
                                fStore.collection("establishments").document(userID).collection("dental-est-rules").document(list.getDentalRuleId())
                                        .delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                progressDialog.dismiss();
                                                Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                                dentalERArrayList.remove(position);
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
        return dentalERArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //refer all elements where data will be populated
        TextView desc;
        ImageView update, delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.estRulesRVDesc);
            update = itemView.findViewById(R.id.estRulesUpdateBtn);
            delete = itemView.findViewById(R.id.estRulesDeleteBtn);
        }
    }
}
