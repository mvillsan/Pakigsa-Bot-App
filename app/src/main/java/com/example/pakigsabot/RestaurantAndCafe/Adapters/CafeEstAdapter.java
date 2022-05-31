package com.example.pakigsabot.RestaurantAndCafe.Adapters;

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
import com.example.pakigsabot.Feedbacks.EstFeedback;
import com.example.pakigsabot.R;
import com.example.pakigsabot.RestaurantAndCafe.CafeDetails;
import com.example.pakigsabot.RestaurantAndCafe.Models.CafeEstModel;
import com.example.pakigsabot.SignUpRequirements.AgreementScreen;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CafeEstAdapter extends RecyclerView.Adapter<CafeEstAdapter.MyViewHolder>{
    //creating variables for Context and ArrayList
    Context context;
    ArrayList<CafeEstModel> cafeArrayList;

    //Initializing variables
    ProgressDialog progressDialog;

    //Firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public CafeEstAdapter() {
        //Empty constructor needed
    }

    public CafeEstAdapter(Context context, ArrayList<CafeEstModel> cafeArrayList) {
        this.context = context;
        this.cafeArrayList = cafeArrayList;
        this.progressDialog = new ProgressDialog(context);
        this.fStore = FirebaseFirestore.getInstance();
        this.fAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public CafeEstAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //passing layout file to display the items
        View itemView = LayoutInflater.from(context).inflate(R.layout.cafe_est_item,parent, false);
        return  new CafeEstAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull CafeEstAdapter.MyViewHolder holder, int position) {
        CafeEstModel list = cafeArrayList.get(position);
        holder.name.setText(list.getEst_Name());
        holder.address.setText(list.getEst_address());
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
                Intent intent = new Intent(view.getContext(), CafeDetails.class);
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
        return cafeArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our textviews, imageview, and button
        TextView name, address, reviews;
        ImageView img;
        Button selectBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //References
            name = itemView.findViewById(R.id.restoCafeName);
            address = itemView.findViewById(R.id.restoCafeAdd);
            img = itemView.findViewById(R.id.restoCafeImg);
            selectBtn = itemView.findViewById(R.id.selectBtnCafe);
            reviews = itemView.findViewById(R.id.resortEstFeedback);
        }
    }

    public void filterList(List<CafeEstModel> filteredList){
        cafeArrayList = (ArrayList<CafeEstModel>) filteredList;
        notifyDataSetChanged();
    }
}
