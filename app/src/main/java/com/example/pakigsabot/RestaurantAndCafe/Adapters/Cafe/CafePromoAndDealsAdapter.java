package com.example.pakigsabot.RestaurantAndCafe.Adapters.Cafe;

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
import com.example.pakigsabot.RestaurantAndCafe.Adapters.Restaurant.RestaurantPromoAndDealsAdapter;
import com.example.pakigsabot.RestaurantAndCafe.Models.Cafe.CafeMenuItemsModel;
import com.example.pakigsabot.RestaurantAndCafe.Models.Cafe.CafePromoAndDealsModel;
import com.example.pakigsabot.RestaurantAndCafe.Models.Restaurant.RestaurantPromoAndDealsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class CafePromoAndDealsAdapter extends RecyclerView.Adapter<CafePromoAndDealsAdapter.MyViewHolder> {
    // creating variables for our ArrayList and context
    Context context;
    ArrayList<CafePromoAndDealsModel> promoDealsArrayList;

    //Initializing variables
    ProgressDialog progressDialog;

    //Firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public CafePromoAndDealsAdapter() {
        //empty constructor needed
    }

    public CafePromoAndDealsAdapter(Context context, ArrayList<CafePromoAndDealsModel> promoDealsArrayList) {
        this.context = context;
        this.promoDealsArrayList = promoDealsArrayList;
        this.progressDialog = new ProgressDialog(context);
        this.fStore = FirebaseFirestore.getInstance();
        this.fAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        View itemView = LayoutInflater.from(context).inflate(R.layout.cafe_promo_deals_item,parent,false);
        return new CafePromoAndDealsAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull CafePromoAndDealsAdapter.MyViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        CafePromoAndDealsModel list = promoDealsArrayList.get(position);
        holder.name.setText(list.getCafePDName());
        holder.desc.setText(list.getCafePDDesc());
        holder.startDate.setText(list.getCafePDStartDate());
        holder.endDate.setText(list.getCafePDEndDate());
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
