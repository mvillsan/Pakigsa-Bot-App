package com.example.pakigsabot.Resorts.Adapters;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pakigsabot.Feedbacks.EstFeedback;
import com.example.pakigsabot.R;
import com.example.pakigsabot.Resorts.Models.ResortModel;
import com.example.pakigsabot.Resorts.ResortDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResortAdapter extends RecyclerView.Adapter<ResortAdapter.MyViewHolder>{
    // creating variables for our ArrayList and context
    Context context;
    ArrayList<ResortModel> resortEstArrayList;

    //Initializing variables
    ProgressDialog progressDialog;
    List<String> ratingList;
    Float rate;

    //Firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public ResortAdapter() {
        //empty constructor needed
    }

    // creating constructor for our adapter class
    public ResortAdapter(Context context, ArrayList<ResortModel> resortEstArrayList) {
        this.context = context;
        this.resortEstArrayList = resortEstArrayList;
        this.progressDialog = new ProgressDialog(context);
        this.fStore = FirebaseFirestore.getInstance();
        this.fAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ResortAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        View itemView = LayoutInflater.from(context).inflate(R.layout.resort_est_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ResortAdapter.MyViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        ResortModel list = resortEstArrayList.get(position);
        holder.name.setText(list.getEst_Name());
        holder.address.setText(list.getEst_address());
        Glide.with(context).load(list.getEst_image()).into(holder.img);

        //Select an establishment
        holder.selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String estID = list.getEst_id();
                String estName = list.getEst_Name();
                String estAddress = list.getEst_address();
                String estImgUrl = list.getEst_image();
                String estPhoneNum = list.getEst_phoneNum();

                Toast.makeText(context, list.getEst_Name() + " Selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), ResortDetails.class);
                intent.putExtra("EstID", estID);
                intent.putExtra("EstName", estName);
                intent.putExtra("EstAddress", estAddress);
                intent.putExtra("EstImageUrl", estImgUrl);
                intent.putExtra("EstPhoneNum", estPhoneNum);
                view.getContext().startActivity(intent);
            }
        });

        //View Reviews and Rating
        holder.feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String estID = list.getEst_id();

                Intent intent = new Intent(view.getContext(), EstFeedback.class);
                intent.putExtra("EstID", estID);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return resortEstArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // creating variables for our textviews, imageview, button and rating bar.
        TextView name, address, feedback;
        ImageView img;
        Button selectBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //References::
            name = itemView.findViewById(R.id.resortEstNameTV);
            address = itemView.findViewById(R.id.resortEstAddTV);
            img = itemView.findViewById(R.id.imgResort);
            selectBtn = itemView.findViewById(R.id.selectBtn);
            feedback = itemView.findViewById(R.id.resortEstFeedback);
        }
    }

    public void filterList(List<ResortModel> filteredList){
        resortEstArrayList = (ArrayList<ResortModel>) filteredList;
        notifyDataSetChanged();
    }
}
