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

import com.example.pakigsabot.R;
import com.example.pakigsabot.Resorts.Models.ResortModel;
import com.example.pakigsabot.Resorts.Models.RulesModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class RulesAdapter extends RecyclerView.Adapter<RulesAdapter.MyViewHolder>{
    // creating variables for our ArrayList and context
    Context context;
    ArrayList<RulesModel> rulesArrayList;

    //Initializing variables
    ProgressDialog progressDialog;

    //Firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public RulesAdapter() {
        //empty constructor needed
    }

    public RulesAdapter(Context context, ArrayList<RulesModel> rulesArrayList) {
        this.context = context;
        this.rulesArrayList = rulesArrayList;
        this.progressDialog = new ProgressDialog(context);
        this.fStore = FirebaseFirestore.getInstance();
        this.fAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public RulesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        View itemView = LayoutInflater.from(context).inflate(R.layout.resort_rules_item,parent,false);
        return new RulesAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull RulesAdapter.MyViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        RulesModel list = rulesArrayList.get(position);
        holder.name.setText(list.getDesc());

    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return rulesArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // creating variables for our textviews.
        TextView name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //References::
            name = itemView.findViewById(R.id.descTV);
        }
    }
}
