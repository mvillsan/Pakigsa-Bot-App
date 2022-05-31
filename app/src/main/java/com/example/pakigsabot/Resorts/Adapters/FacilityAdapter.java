package com.example.pakigsabot.Resorts.Adapters;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pakigsabot.R;
import com.example.pakigsabot.Resorts.Models.FacilityModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class FacilityAdapter extends RecyclerView.Adapter<FacilityAdapter.MyViewHolder> {
    // creating variables for our ArrayList and context
    Context context;
    ArrayList<FacilityModel> resortFArrayList;

    //Initializing variables
    ProgressDialog progressDialog;

    //Firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public FacilityAdapter() {
        //empty constructor needed
    }

    public FacilityAdapter(Context context, ArrayList<FacilityModel> resortFArrayList) {
        this.context = context;
        this.resortFArrayList = resortFArrayList;
        this.progressDialog = new ProgressDialog(context);
        this.fStore = FirebaseFirestore.getInstance();
        this.fAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public FacilityAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        View itemView = LayoutInflater.from(context).inflate(R.layout.resort_facility_item,parent,false);
        return new FacilityAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull FacilityAdapter.MyViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        FacilityModel list = resortFArrayList.get(position);
        holder.name.setText(list.getResortFName());
        holder.capacity.setText(list.getResortFCapac());
        holder.desc.setText(list.getResortFDesc());
        holder.rate.setText(list.getResortFRate());
        Glide.with(context).load(list.getResortFImgUrl()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return resortFArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // creating variables for our textviews and imageview.
        TextView name, capacity,desc,rate;
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //References::
            name = itemView.findViewById(R.id.resortRFNameTV);
            capacity = itemView.findViewById(R.id.resortCapacTV);
            desc = itemView.findViewById(R.id.resortDescTV);
            rate = itemView.findViewById(R.id.resortRateTV);
            img = itemView.findViewById(R.id.imgResort);
        }
    }
}
