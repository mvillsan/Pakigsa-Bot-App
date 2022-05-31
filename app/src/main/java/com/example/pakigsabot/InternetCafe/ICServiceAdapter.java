package com.example.pakigsabot.InternetCafe;

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
import com.example.pakigsabot.SpaSalon.SalonReserveService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ICServiceAdapter extends RecyclerView.Adapter<ICServiceAdapter.MyViewHolder> {
    //creating variables for Context and ArrayList
    Context context;
    ArrayList<ICServiceModel> arrayList;

    //Initializing variables
    ProgressDialog progressDialog;

    //Firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public ICServiceAdapter() {
    }

    public ICServiceAdapter(Context context, ArrayList<ICServiceModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.progressDialog = new ProgressDialog(context);
        this.fStore = FirebaseFirestore.getInstance();
        this.fAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ICServiceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //passing layout file to display the items
        View itemView = LayoutInflater.from(context).inflate(R.layout.ic_services_items,parent, false);
        return  new ICServiceAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ICServiceAdapter.MyViewHolder holder, int position) {
        //creating  object of DentalProceduresModel class and setting data to the textviews from the DentalProceduresModel class
        ICServiceModel dpList = arrayList.get(position);

        holder.name.setText(dpList.getName());
        holder.desc.setText(dpList.getDesc());
        holder.rate.setText(dpList.getRate());
        Glide.with(context).load(dpList.getImgurl()).into(holder.img);

        //Select an item to the Salon Services
        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = dpList.getId();
                String name = dpList.getName();
                String rate = dpList.getRate();
                String imgurl = dpList.getImgurl();
                String estId = dpList.getEstId();


                Toast.makeText(context, dpList.getName() + " Selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), ICReserveService.class);
                intent.putExtra("servId", id);
                intent.putExtra("servName", name);
                intent.putExtra("servRate", rate);
                intent.putExtra("servImgUrl", imgurl);
                intent.putExtra("estID", estId);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //refer all elements where data will be populated
        TextView name, desc, rate;
        ImageView img;
        Button select;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.dentEyeRVName);
            desc = itemView.findViewById(R.id.dentEyeRVDesc);
            rate = itemView.findViewById(R.id.dentEyeRVRate);
            img = itemView.findViewById(R.id.dentEyeRVProcImg);
            select = itemView.findViewById(R.id.selectProcRVBtn);
        }
    }
    public void filterList(List<ICServiceModel> filteredList){
        arrayList = (ArrayList<ICServiceModel>) filteredList;
        notifyDataSetChanged();
    }
}
