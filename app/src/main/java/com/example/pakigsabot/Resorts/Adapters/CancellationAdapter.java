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
import com.example.pakigsabot.Resorts.Models.CancellationModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CancellationAdapter extends RecyclerView.Adapter<CancellationAdapter.MyViewHolder> {
    // creating variables for our ArrayList and context
    Context context;
    ArrayList<CancellationModel> canpolArrayList;

    //Initializing variables
    ProgressDialog progressDialog;

    //Firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public CancellationAdapter() {
        //empty constructor needed
    }

    public CancellationAdapter(Context context, ArrayList<CancellationModel> canpolArrayList) {
        this.context = context;
        this.canpolArrayList = canpolArrayList;
        this.progressDialog = new ProgressDialog(context);
        this.fStore = FirebaseFirestore.getInstance();
        this.fAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public CancellationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        View itemView = LayoutInflater.from(context).inflate(R.layout.resort_canpol_items,parent,false);
        return new CancellationAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull CancellationAdapter.MyViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        CancellationModel list = canpolArrayList.get(position);
        holder.name.setText(list.getResortCPolDesc());
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return canpolArrayList.size();
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
