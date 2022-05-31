package com.example.pakigsabot.RestaurantAndCafe.Adapters.Restaurant;

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
import com.example.pakigsabot.RestaurantAndCafe.Models.Restaurant.RestaurantPromoAndDealsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RestaurantPromoAndDealsAdapter extends RecyclerView.Adapter<RestaurantPromoAndDealsAdapter.MyViewHolder> {
    // creating variables for our ArrayList and context
    Context context;
    ArrayList<RestaurantPromoAndDealsModel> promoDealsArrayList;

    //Initializing variables
    ProgressDialog progressDialog;

    //Firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public RestaurantPromoAndDealsAdapter() {
        //empty constructor needed
    }

    public RestaurantPromoAndDealsAdapter(Context context, ArrayList<RestaurantPromoAndDealsModel> promoDealsArrayList) {
        this.context = context;
        this.promoDealsArrayList = promoDealsArrayList;
        this.progressDialog = new ProgressDialog(context);
        this.fStore = FirebaseFirestore.getInstance();
        this.fAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public RestaurantPromoAndDealsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        View itemView = LayoutInflater.from(context).inflate(R.layout.resto_promo_deals_item,parent,false);
        return new RestaurantPromoAndDealsAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull RestaurantPromoAndDealsAdapter.MyViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        RestaurantPromoAndDealsModel list = promoDealsArrayList.get(position);
        holder.name.setText(list.getRestoPDName());
        holder.desc.setText(list.getRestoPDDesc());
        holder.startDate.setText(list.getRestoPDStartDate());
        holder.endDate.setText(list.getRestoPDEndDate());
    }

    @Override
    public int getItemCount() {
        return promoDealsArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // creating variables for our textviews.
        TextView name,desc,startDate, endDate;

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
