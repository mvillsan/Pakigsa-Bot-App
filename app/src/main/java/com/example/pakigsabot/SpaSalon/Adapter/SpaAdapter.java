package com.example.pakigsabot.SpaSalon.Adapter;

import android.annotation.SuppressLint;
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
import com.example.pakigsabot.R;
import com.example.pakigsabot.SpaSalon.Models.SpaModels;
import com.example.pakigsabot.SpaSalon.SpaDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SpaAdapter extends RecyclerView.Adapter<SpaAdapter.MyViewHolder>{
    // creating variables for our ArrayList and context
    Context context;
    ArrayList<SpaModels> spaServiceArrayList;

    //Initializing variables
    ProgressDialog progressDialog;

    //Firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public SpaAdapter() {
        //empty constructor needed
    }

    public SpaAdapter(Context context, ArrayList<SpaModels> spaServiceArrayList) {
        this.context = context;
        this.spaServiceArrayList = spaServiceArrayList;
        this.progressDialog = new ProgressDialog(context);
        this.fStore = FirebaseFirestore.getInstance();
        this.fAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public SpaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //passing layout file to display the items
        View itemView = LayoutInflater.from(context).inflate(R.layout.spa_est_items,parent,false);
        return new SpaAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull SpaAdapter.MyViewHolder holder, int position) {
        //creating  object of DentalAndEyeClinicsModel class and setting data to the textviews from the DentalAndEyeClinicsModel class
        SpaModels daecList = spaServiceArrayList.get(position);
        holder.name.setText(daecList.getEst_Name());
        holder.address.setText(daecList.getEst_address());
        Glide.with(context).load(daecList.getEst_image()).into(holder.img);

        //Select an item to the Dental Clinic
        holder.selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String estID = daecList.getEst_id();
                String estName = daecList.getEst_Name();
                String estAddress = daecList.getEst_address();
                String estImgUrl = daecList.getEst_image();
                String estPhoneNum = daecList.getEst_phoneNum();

                Toast.makeText(context, daecList.getEst_Name() + " Selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), SpaDetails.class);
                intent.putExtra("EstID", estID);
                intent.putExtra("EstName", estName);
                intent.putExtra("EstAddress", estAddress);
                intent.putExtra("EstImageUrl", estImgUrl);
                intent.putExtra("EstPhoneNum", estPhoneNum);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return spaServiceArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our textviews, imageview, button and rating bar.
        TextView name, address, reviews;
        ImageView img;
        Button selectBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //References
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.add);
            img = itemView.findViewById(R.id.img);
            selectBtn = itemView.findViewById(R.id.selectBtn);
            reviews = itemView.findViewById(R.id.estFeedback);
        }
    }

    public void filterList(List<SpaModels> filteredList){
        spaServiceArrayList = (ArrayList<SpaModels>) filteredList;
        notifyDataSetChanged();
    }
}
