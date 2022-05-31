package com.example.pakigsabot.Resorts.Adapters;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pakigsabot.R;
import com.example.pakigsabot.Resorts.Models.FacilityModel;
import com.example.pakigsabot.Resorts.Models.PromoandDealsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class PromoDealsAdapter extends RecyclerView.Adapter<PromoDealsAdapter.MyViewHolder> {
    // creating variables for our ArrayList and context
    Context context;
    ArrayList<PromoandDealsModel> resortPromoDealsArrayList;

    //Initializing variables
    ProgressDialog progressDialog;

    //Firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public PromoDealsAdapter() {
        //empty constructor needed
    }

    public PromoDealsAdapter(Context context, ArrayList<PromoandDealsModel> resortPromoDealsArrayList) {
        this.context = context;
        this.resortPromoDealsArrayList = resortPromoDealsArrayList;
        this.progressDialog = new ProgressDialog(context);
        this.fStore = FirebaseFirestore.getInstance();
        this.fAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public PromoDealsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        View itemView = LayoutInflater.from(context).inflate(R.layout.resort_promo_deals_item,parent,false);
        return new PromoDealsAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull PromoDealsAdapter.MyViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        PromoandDealsModel list = resortPromoDealsArrayList.get(position);
        holder.name.setText(list.getPromoDealsName());
        holder.desc.setText(list.getPromoDealsDesc());
        holder.startDate.setText(list.getPromoDealsStartDate());
        holder.endDate.setText(list.getPromoDealsEndDate());
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return resortPromoDealsArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // creating variables for our textviews.
        TextView name,desc,startDate,endDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //References::
            name = itemView.findViewById(R.id.promodealsName);
            desc = itemView.findViewById(R.id.promodealsDesc);
            startDate = itemView.findViewById(R.id.startDatePD);
            endDate = itemView.findViewById(R.id.endDatePD);
        }
    }
}
