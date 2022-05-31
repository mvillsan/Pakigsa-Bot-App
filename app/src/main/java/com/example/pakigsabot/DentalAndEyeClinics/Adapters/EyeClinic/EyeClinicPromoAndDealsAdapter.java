package com.example.pakigsabot.DentalAndEyeClinics.Adapters.EyeClinic;

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
import com.example.pakigsabot.DentalAndEyeClinics.Models.EyeClinic.EyeClinicPromoAndDealsModel;
import com.example.pakigsabot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class EyeClinicPromoAndDealsAdapter extends RecyclerView.Adapter<EyeClinicPromoAndDealsAdapter.MyViewHolder> {
    // creating variables for our ArrayList and context
    Context context;
    ArrayList<EyeClinicPromoAndDealsModel> opticalPADArrayList;

    //Initializing variables
    ProgressDialog progressDialog;

    //Firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public EyeClinicPromoAndDealsAdapter() {
        //empty constructor needed
    }

    public EyeClinicPromoAndDealsAdapter(Context context, ArrayList<EyeClinicPromoAndDealsModel> opticalPADArrayList) {
        this.context = context;
        this.opticalPADArrayList = opticalPADArrayList;
        this.progressDialog = new ProgressDialog(context);
        this.fStore = FirebaseFirestore.getInstance();
        this.fAuth = FirebaseAuth.getInstance();
    }


    @NonNull
    @Override
    public EyeClinicPromoAndDealsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //passing layout file to display the items
        View itemView = LayoutInflater.from(context).inflate(R.layout.promo_and_deals_items,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EyeClinicPromoAndDealsAdapter.MyViewHolder holder, int position) {
        EyeClinicPromoAndDealsModel ecPADList = opticalPADArrayList.get(position);

        holder.name.setText(ecPADList.getOpticalPADName());
        holder.desc.setText(ecPADList.getOpticalPADDesc());
        holder.startDate.setText(ecPADList.getOpticalPADStartDate());
        holder.endDate.setText(ecPADList.getOpticalPADEndDate());
    }

    @Override
    public int getItemCount() {
        return opticalPADArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
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
