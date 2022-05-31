package com.example.pakigsabot.Feedbacks.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pakigsabot.Feedbacks.Models.FeedbackModel;
import com.example.pakigsabot.R;
import com.example.pakigsabot.Resorts.Models.ResortModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder> {
    // creating variables for our ArrayList and context
    Context context;
    ArrayList<FeedbackModel> feedbackArrayList;

    //Initializing variables
    ProgressDialog progressDialog;
    String customerName, fName, lName;

    //Firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    StorageReference storageRef;
    DocumentReference docRef;

    public FeedbackAdapter() {
        //empty constructor needed
    }

    public FeedbackAdapter(Context context, ArrayList<FeedbackModel> feedbackArrayList) {
        this.context = context;
        this.feedbackArrayList = feedbackArrayList;
        this.progressDialog = new ProgressDialog(context);
        this.fStore = FirebaseFirestore.getInstance();
        this.fAuth = FirebaseAuth.getInstance();
        this.storageRef = FirebaseStorage.getInstance().getReference();
    }

    @NonNull
    @Override
    public FeedbackAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        View itemView = LayoutInflater.from(context).inflate(R.layout.est_feedback_item,parent,false);
        return new FeedbackAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull FeedbackAdapter.MyViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        FeedbackModel list = feedbackArrayList.get(position);
        holder.date.setText(list.getFeedbackDate());
        holder.review.setText(list.getFeedbackReview());
        holder.rating.setRating(Float.parseFloat(list.getFeedbackRating()));
        holder.desc.setText(list.getFeedbackDesc());

        //Getting Customer ID Image::
        StorageReference profileRef = storageRef.child("customers/profile_pictures/"+list.getCustID()+"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).into(holder.img);
            }
        });
        holder.name.setText(list.getCustID());
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return feedbackArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // creating variables for our textviews, imageview.
        TextView date, review, desc, name;
        ImageView img;
        RatingBar rating;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //References::
            date = itemView.findViewById(R.id.feedbackDateTV);
            review = itemView.findViewById(R.id.feedbackReviewTV);
            img = itemView.findViewById(R.id.imgCustomer);
            rating = itemView.findViewById(R.id.estRating);
            desc = itemView.findViewById(R.id.feedbackDescTV);
            name = itemView.findViewById(R.id.customerName);
        }
    }

    public void filterList(List<FeedbackModel> filteredList){
        feedbackArrayList = (ArrayList<FeedbackModel>) filteredList;
        notifyDataSetChanged();
    }
}
