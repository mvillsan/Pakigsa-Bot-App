package com.example.pakigsabot.Resorts.Adapters;

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
import com.example.pakigsabot.Resorts.Models.RoomModel;
import com.example.pakigsabot.Resorts.ResortRoomReservDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.MyViewHolder> {
    // creating variables for our ArrayList and context
    Context context;
    ArrayList<RoomModel> resortRoomArrayList;

    //Initializing variables
    ProgressDialog progressDialog;

    //Firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public RoomAdapter() {
        //empty constructor needed
    }

    public RoomAdapter(Context context, ArrayList<RoomModel> resortRoomArrayList) {
        this.context = context;
        this.resortRoomArrayList = resortRoomArrayList;
        this.progressDialog = new ProgressDialog(context);
        this.fStore = FirebaseFirestore.getInstance();
        this.fAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public RoomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        View itemView = LayoutInflater.from(context).inflate(R.layout.resort_room_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull RoomAdapter.MyViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        RoomModel list = resortRoomArrayList.get(position);
        holder.name.setText(list.getResortRFName());
        holder.capacity.setText(list.getResortCapac());
        holder.desc.setText(list.getResortDesc());
        holder.rate.setText(list.getResortRate());
        Glide.with(context).load(list.getResortImgUrl()).into(holder.img);

        //Select an establishment
        holder.reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String roomID = list.getResortRFID();
                String roomName = list.getResortRFName();
                String capac = list.getResortCapac();
                String descrip = list.getResortDesc();
                String rate = list.getResortRate();
                String roomImgUrl = list.getResortImgUrl();
                String establishmentID = list.getEstablishmentID();

                Toast.makeText(context, list.getResortRFName() + " Selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), ResortRoomReservDetails.class);
                intent.putExtra("RoomID", roomID);
                intent.putExtra("RoomName", roomName);
                intent.putExtra("RoomCapacity", capac);
                intent.putExtra("RoomDesc", descrip);
                intent.putExtra("RoomRate", rate);
                intent.putExtra("RoomImg", roomImgUrl);
                intent.putExtra("RoomEstID", establishmentID);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return resortRoomArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // creating variables for our textviews and imageview.
        TextView name, capacity,desc,rate;
        ImageView img;
        Button reserve;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //References::
            name = itemView.findViewById(R.id.resortRFNameTV);
            capacity = itemView.findViewById(R.id.resortCapacTV);
            desc = itemView.findViewById(R.id.resortDescTV);
            rate = itemView.findViewById(R.id.resortRateTV);
            img = itemView.findViewById(R.id.imgResort);
            reserve = itemView.findViewById(R.id.reserveRoomBtn);
        }
    }

}
