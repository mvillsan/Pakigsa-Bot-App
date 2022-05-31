package com.example.pakigsabot.SpaSalon.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pakigsabot.DentalAndEyeClinics.Adapters.DentalClinic.DentalClinicPromoAndDealsAdapter;
import com.example.pakigsabot.DentalAndEyeClinics.Models.DentalClinic.DentalClinicPromoAndDealsModel;
import com.example.pakigsabot.R;
import com.example.pakigsabot.SpaSalon.Models.SalonPromoDealsModels;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class SalonPromoDealsAdapter  extends RecyclerView.Adapter<SalonPromoDealsAdapter.MyViewHolder> {
    // creating variables for our ArrayList and context
    Context context;
    ArrayList<SalonPromoDealsModels> arrayList;

    //Initializing variables
    ProgressDialog progressDialog;

    //Firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public SalonPromoDealsAdapter() {
        //empty constructor needed
    }

    public SalonPromoDealsAdapter(Context context, ArrayList<SalonPromoDealsModels> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.progressDialog = new ProgressDialog(context);
        this.fStore = FirebaseFirestore.getInstance();
        this.fAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public SalonPromoDealsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //passing layout file to display the items
        View itemView = LayoutInflater.from(context).inflate(R.layout.salon_pd_items,parent,false);
        return new SalonPromoDealsAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull SalonPromoDealsAdapter.MyViewHolder holder, int position) {
        SalonPromoDealsModels dcPADList = arrayList.get(position);

        holder.name.setText(dcPADList.getSalonPADName());
        holder.desc.setText(dcPADList.getSalonPADDesc());
        holder.startDate.setText(dcPADList.getSalonPADStartDate());
        holder.endDate.setText(dcPADList.getSalonPADEndDate());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //refer all elements where data will be populated
        TextView name, desc, startDate, endDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.promoDealsRVName);
            desc = itemView.findViewById(R.id.promoDealsRVDesc);
            startDate = itemView.findViewById(R.id.promoDealsRVStartDate);
            endDate = itemView.findViewById(R.id.promoDealsRVEndDate);
        }
    }
}
