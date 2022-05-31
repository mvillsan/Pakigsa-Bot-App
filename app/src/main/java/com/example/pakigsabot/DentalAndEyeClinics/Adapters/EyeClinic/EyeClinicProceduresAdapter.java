package com.example.pakigsabot.DentalAndEyeClinics.Adapters.EyeClinic;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pakigsabot.DentalAndEyeClinics.DentalClinicSelectedProcedures;
import com.example.pakigsabot.DentalAndEyeClinics.EyeClinicSelectedProcedures;
import com.example.pakigsabot.DentalAndEyeClinics.Models.EyeClinic.EyeClinicProceduresModel;
import com.example.pakigsabot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class EyeClinicProceduresAdapter extends RecyclerView.Adapter<EyeClinicProceduresAdapter.MyViewHolder> {
    // creating variables for our ArrayList and context
    Context context;
    ArrayList<EyeClinicProceduresModel> opticalProceduresArrayList;

    //Initializing variables
    ProgressDialog progressDialog;

    //Firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public EyeClinicProceduresAdapter() {
        //empty constructor needed
    }

    //creating constructor for thea adapter class
    public EyeClinicProceduresAdapter(Context context, ArrayList<EyeClinicProceduresModel> opticalProceduresArrayList) {
        this.context = context;
        this.opticalProceduresArrayList = opticalProceduresArrayList;
        this.progressDialog = new ProgressDialog(context);
        this.fStore = FirebaseFirestore.getInstance();
        this.fAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public EyeClinicProceduresAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //passing layout file to display the items
        View itemView = LayoutInflater.from(context).inflate(R.layout.dent_and_eye_procedures_items,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EyeClinicProceduresAdapter.MyViewHolder holder, int position) {
        //creating  object of EyeClinicProceduresModel class class and setting data to the textviews from the EyeClinicProceduresModel class
        EyeClinicProceduresModel ecpList = opticalProceduresArrayList.get(position);

        holder.name.setText(ecpList.getOpticalPRName());
        holder.desc.setText(ecpList.getOpticalPRDesc());
        holder.rate.setText(ecpList.getOpticalPRRate());
        Glide.with(context).load(ecpList.getOpticalPRImgUrl()).into(holder.img);

        //Select an item to the Eye Clinic
        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String procId = ecpList.getOpticalPRId();
                String procName = ecpList.getOpticalPRName();
                String procRate = ecpList.getOpticalPRRate();
                String procImgUrl = ecpList.getOpticalPRImgUrl();
                String estId = ecpList.getEstId();


                Toast.makeText(context, ecpList.getOpticalPRName() + " Selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), EyeClinicSelectedProcedures.class);
                intent.putExtra("ProcId", procId);
                intent.putExtra("ProcName", procName);
                intent.putExtra("ProcRate", procRate);
                intent.putExtra("ProcImageUrl", procImgUrl);
                intent.putExtra("ProcEstId", estId);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return opticalProceduresArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        //refer all elements where data will be populated
        TextView name, desc, rate;
        ImageView img;
        Button select;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.dentEyeRVName);
            desc = itemView.findViewById(R.id.dentEyeRVDesc);
            rate = itemView.findViewById(R.id.dentEyeRVRate);
            img = itemView.findViewById(R.id.dentEyeRVProcImg);
            select = itemView.findViewById(R.id.selectProcRVBtn);
        }
    }
}
