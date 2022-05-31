package com.example.pakigsabot.RestaurantAndCafe.Adapters.Restaurant;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.example.pakigsabot.RestaurantAndCafe.Adapters.RestoEstAdapter;
import com.example.pakigsabot.RestaurantAndCafe.Models.Restaurant.EstModelFilter;
import com.example.pakigsabot.RestaurantAndCafe.Models.RestoEstModel;
import com.example.pakigsabot.RestaurantAndCafe.RestoDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class RestoFilterAdapter extends RecyclerView.Adapter<RestoFilterAdapter.MyViewHolder> {
    //creating variables for Context and ArrayList
    Context context;
    ArrayList<EstModelFilter> restoArrayList;

    //Initializing variables
    ProgressDialog progressDialog;

    //Firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public RestoFilterAdapter() {
    }

    public RestoFilterAdapter(Context context, ArrayList<EstModelFilter> restoArrayList) {
        this.context = context;
        this.restoArrayList = restoArrayList;
        this.progressDialog = new ProgressDialog(context);
        this.fStore = FirebaseFirestore.getInstance();
        this.fAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public RestoFilterAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //passing layout file to display the items
        View itemView = LayoutInflater.from(context).inflate(R.layout.resto_est_distance_item,parent, false);
        return  new RestoFilterAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull RestoFilterAdapter.MyViewHolder holder, int position) {
        EstModelFilter list = restoArrayList.get(position);
        holder.name.setText(list.getEst_Name());
        holder.address.setText(list.getEst_address());
        holder.ratingBar.setRating(Float.parseFloat(list.getOverallRating()));
        holder.restoDistance.setText(list.getDistance());
        Glide.with(context).load(list.getEst_image()).into(holder.img);

        //Select an item to the List of establishments
        holder.selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String estID = list.getEst_id();
                String estName = list.getEst_Name();
                String estAddress = list.getEst_address();
                String estImgUrl = list.getEst_image();
                String estPhoneNum = list.getEst_phoneNum();

                Toast.makeText(context, list.getEst_Name() + " Selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), RestoDetails.class);
                intent.putExtra("EstID", estID);
                intent.putExtra("EstName", estName);
                intent.putExtra("EstAddress", estAddress);
                intent.putExtra("EstImageUrl", estImgUrl);
                intent.putExtra("EstPhoneNum", estPhoneNum);
                view.getContext().startActivity(intent);
            }
        });

        //View Reviews and Rating
        holder.reviews.setOnClickListener(new View.OnClickListener() {
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
        return restoArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our textviews, imageview, and button
        TextView name, address, reviews, restoDistance;
        ImageView img;
        Button selectBtn;
        RatingBar ratingBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //References
            name = itemView.findViewById(R.id.restoCafeName);
            address = itemView.findViewById(R.id.restoCafeAdd);
            img = itemView.findViewById(R.id.restoCafeImg);
            selectBtn = itemView.findViewById(R.id.selectBtnResto);
            reviews = itemView.findViewById(R.id.resortEstFeedback);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            restoDistance = itemView.findViewById(R.id.restoDistance);
        }
    }

    public void filterList(List<EstModelFilter> filteredList){
        restoArrayList = (ArrayList<EstModelFilter>) filteredList;
        notifyDataSetChanged();
    }
}
