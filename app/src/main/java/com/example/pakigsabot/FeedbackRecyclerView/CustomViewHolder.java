package com.example.pakigsabot.FeedbackRecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pakigsabot.R;

public class CustomViewHolder extends RecyclerView.ViewHolder {

    public TextView textName;
    public TextView textAddress;
    public ImageView restoIcon;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);
        textName = itemView.findViewById(R.id.estTxtResto);
        textAddress = itemView.findViewById(R.id.estTxtAddress);
        restoIcon = itemView.findViewById(R.id.restoIconItem);
    }
}
