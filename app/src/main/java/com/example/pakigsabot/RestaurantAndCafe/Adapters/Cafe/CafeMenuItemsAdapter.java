package com.example.pakigsabot.RestaurantAndCafe.Adapters.Cafe;

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
import com.example.pakigsabot.RestaurantAndCafe.Adapters.Restaurant.RestaurantMenuItemsAdapter;
import com.example.pakigsabot.RestaurantAndCafe.Models.Cafe.CafeMenuItemsModel;
import com.example.pakigsabot.RestaurantAndCafe.Models.Restaurant.RestaurantMenuItemsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CafeMenuItemsAdapter extends RecyclerView.Adapter<CafeMenuItemsAdapter.MyViewHolder> {
    // creating variables for our ArrayList and context
    private LayoutInflater inflater;
    Context context;
    public static ArrayList<CafeMenuItemsModel> cafeMenuArrayList;

    //Initializing variables
    ProgressDialog progressDialog;
    int quantity;
    int totalPrice;
    String totString;

    //Firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public CafeMenuItemsAdapter() {
        //empty constructor needed
    }

    public CafeMenuItemsAdapter(Context context, ArrayList<CafeMenuItemsModel> cafeMenuArrayList) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.cafeMenuArrayList = cafeMenuArrayList;
        this.progressDialog = new ProgressDialog(context);
        this.fStore = FirebaseFirestore.getInstance();
        this.fAuth = FirebaseAuth.getInstance();
    }


    @NonNull
    @Override
    public CafeMenuItemsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //passing layout file to display the items
        View itemView = inflater.inflate(R.layout.cafe_menu_items,parent,false);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull CafeMenuItemsAdapter.MyViewHolder holder, int position) {
        CafeMenuItemsModel list = cafeMenuArrayList.get(position);
        holder.name.setText(list.getCafeFIName());
        holder.category.setText(list.getCafeFICategory());
        holder.price.setText(list.getCafeFIPrice());
        holder.quan.setText(list.getQuantity());
        Glide.with(context).load(list.getCafeFIImgUrl()).into(holder.img);

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity = Integer.parseInt(cafeMenuArrayList.get(position).getQuantity());
                quantity++;
                cafeMenuArrayList.get(position).setQuantity("" +quantity);
                notifyDataSetChanged();
                updateTotal();

                String item = list.getCafeFIName();
                String price = list.getCafeFIPrice();
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
        return cafeMenuArrayList.size();
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
        for(i=0; i<cafeMenuArrayList.size(); i++){
            sum = sum+(Integer.parseInt(cafeMenuArrayList.get(i).getCafeFIPrice())*Integer.parseInt(cafeMenuArrayList.get(i).getQuantity()));
        }
        totalPrice = sum;
        totString = ""+totalPrice;
        Log.d("total", "total " + totalPrice);
    }
}
