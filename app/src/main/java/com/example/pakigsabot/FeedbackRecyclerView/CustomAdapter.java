package com.example.pakigsabot.FeedbackRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pakigsabot.R;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder>{
    private Context context;
    private List<Model> list;
    // Define an integer array to hold the image recourse ids

    public CustomAdapter(Context context, List<Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.single_items, parent, false));
     }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.textName.setText(list.get(position).getName());
        holder.textAddress.setText(list.get(position).getAddress());
        holder.restoIcon.setImageResource(list.get(position).estImages);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
