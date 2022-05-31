package com.example.pakigsabot.RestaurantAndCafe.Adapters.Restaurant;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pakigsabot.R;
import com.example.pakigsabot.RestaurantAndCafe.Models.Restaurant.RestaurantMenuItemsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RestaurantMenuItemsAdapter extends RecyclerView.Adapter<RestaurantMenuItemsAdapter.MyViewHolder>{
    // creating variables for our ArrayList and context
    private LayoutInflater inflater;
    Context context;
    public static ArrayList<RestaurantMenuItemsModel> restoMenuArrayList;

    //Initializing variables
    ProgressDialog progressDialog;
    int quantity;
    int totalPrice;
    String totString;

    //Firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public RestaurantMenuItemsAdapter() {
        //Empty Constructor needed
    }

    public RestaurantMenuItemsAdapter(Context context, ArrayList<RestaurantMenuItemsModel> restoMenuArrayList) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.restoMenuArrayList = restoMenuArrayList;
        this.progressDialog = new ProgressDialog(context);
        this.fStore = FirebaseFirestore.getInstance();
        this.fAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public RestaurantMenuItemsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //passing layout file to display the items
        View itemView = inflater.inflate(R.layout.resto_menu_items,parent,false);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull RestaurantMenuItemsAdapter.MyViewHolder holder, int position) {
        RestaurantMenuItemsModel list = restoMenuArrayList.get(position);
        holder.name.setText(list.getRestoFIName());
        holder.category.setText(list.getRestoFICategory());
        holder.price.setText(list.getRestoFIPrice());
        holder.quan.setText(list.getQuantity());
        Glide.with(context).load(list.getRestoFIImgUrl()).into(holder.img);

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity = Integer.parseInt(restoMenuArrayList.get(position).getQuantity());
                quantity++;
                restoMenuArrayList.get(position).setQuantity("" +quantity);
                notifyDataSetChanged();
                updateTotal();

                String item = list.getRestoFIName();
                String price = list.getRestoFIPrice();
                Intent intent = new Intent("custom-message");
                //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
                intent.putExtra("item",item);
                intent.putExtra("price",price);
                intent.putExtra("total",totString);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return restoMenuArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //refer all elements where data will be populated
        TextView name, price, category, quan;
        ImageView img;
        Button add;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.menuName);
            category = itemView.findViewById(R.id.menuCategory);
            price = itemView.findViewById(R.id.menuItemPrice);
            img = itemView.findViewById(R.id.menuImg);
            quan = itemView.findViewById(R.id.menuQuan);
            add = itemView.findViewById(R.id.addBtn);
        }
    }

    public void updateTotal(){
        int sum=0, i;
        for(i=0; i<restoMenuArrayList.size(); i++){
            sum = sum+(Integer.parseInt(restoMenuArrayList.get(i).getRestoFIPrice())*Integer.parseInt(restoMenuArrayList.get(i).getQuantity()));
        }
        totalPrice = sum;
        totString = ""+totalPrice;
        Log.d("total", "total " + totalPrice);
    }
}
