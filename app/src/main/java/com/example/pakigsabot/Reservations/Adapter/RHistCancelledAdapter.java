package com.example.pakigsabot.Reservations.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pakigsabot.R;
import com.example.pakigsabot.Reservations.Model.RHistCancelledModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;

public class RHistCancelledAdapter extends RecyclerView.Adapter<RHistCancelledAdapter.MyViewHolder>{
    // creating variables for our ArrayList and context
    Context context;
    ArrayList<RHistCancelledModel> reservCancelledArrayList;

    //Initializing variables
    ProgressDialog progressDialog;

    //Firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public RHistCancelledAdapter() {
        //empty constructor needed
    }

    // creating constructor for our adapter class
    public RHistCancelledAdapter(Context context, ArrayList<RHistCancelledModel> reservCancelledArrayList) {
        this.context = context;
        this.reservCancelledArrayList = reservCancelledArrayList;
        this.progressDialog = new ProgressDialog(context);
        this.fStore = FirebaseFirestore.getInstance();
        this.fAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public RHistCancelledAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        View itemView = LayoutInflater.from(context).inflate(R.layout.reservations_cancelledhist_item,parent,false);
        return new RHistCancelledAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull RHistCancelledAdapter.MyViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        RHistCancelledModel list = reservCancelledArrayList.get(position);
        //Set Color Status:: Red for Cancelled
        if(list.getrStatus_default().equalsIgnoreCase("Cancelled")){
            holder.stats.setTextColor(Color.RED);
        }
        holder.stats.setText(list.getrStatus_default());
        holder.capacity.setText(list.getTotalPax());
        holder.reservationName.setText(list.getrName());
        holder.date1.setText(list.getCheckINDate());
        holder.date2.setText(list.getCheckOUTDate());
        holder.time.setText(list.getCheckINTime());
        holder.estName.setText(list.getEstName());

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.reservationName.getContext())
                        .setContentHolder(new ViewHolder(R.layout.cancelled_reservations_details))
                        .setExpanded(true, 650)
                        .create();

                dialogPlus.show();

                //References::
                View viewDP = dialogPlus.getHolderView();
                TextView checkInDateET = viewDP.findViewById(R.id.checkInDateET);
                TextView customerName = viewDP.findViewById(R.id.customerName);
                TextView reservedET = viewDP.findViewById(R.id.reservedET);
                TextView adultPaxET = viewDP.findViewById(R.id.adultPaxET);
                TextView petPaxET = viewDP.findViewById(R.id.petPaxET);
                TextView feeET = viewDP.findViewById(R.id.feeET);
                TextView notesTxt = viewDP.findViewById(R.id.notesTxt);
                TextView dateTransacET = viewDP.findViewById(R.id.dateTransacET);
                TextView reasonET = viewDP.findViewById(R.id.reasonET);

                //Retrieving Data
                checkInDateET.setText(checkInDateET.getText().toString() + list.getCheckINDate() + "   Time: " + list.getCheckINTime());
                customerName.setText(customerName.getText().toString() + list.getCustFName() + " " + list.getCustLName());
                reservedET.setText(reservedET.getText().toString() + list.getrName());
                adultPaxET.setText(adultPaxET.getText().toString() + list.getAdultPax() + "   Children: " + list.getChildPax()
                        + "   Infants: " + list.getInfantPax());
                petPaxET.setText(petPaxET.getText().toString() + list.getPetPax());
                feeET.setText(feeET.getText().toString() + list.getFee());
                notesTxt.setText(notesTxt.getText().toString() + list.getNotes());
                dateTransacET.setText(dateTransacET.getText().toString() + list.getDateOfCancellation());
                reasonET.setText(reasonET.getText().toString() + list.getReason());
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context)
                        .setTitle("DELETE A RESERVATION")
                        .setMessage("Do you want to Delete the " + list.getEstName() + " Reservation?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Delete the reservation from the cancelled-reservations collection
                                fStore.collection("cancelled-reservations").document(list.getAutoReserveID())
                                        .delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                progressDialog.dismiss();
                                                reservCancelledArrayList.remove(position);
                                                notifyItemRemoved(position);
                                                Toast.makeText(view.getContext(), "Reservation Deleted!", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Failed to Deleted!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return reservCancelledArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // creating variables for our textviews and imageview.
        TextView date1,date2,estName,reservationName,capacity,time,stats;
        ImageView details, delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //References::
            date1 = itemView.findViewById(R.id.dateCheckINTV);
            date2 = itemView.findViewById(R.id.dateCheckOUTTV);
            estName = itemView.findViewById(R.id.estNameTV);
            reservationName = itemView.findViewById(R.id.reserveNameTV);
            capacity = itemView.findViewById(R.id.paxTV);
            time = itemView.findViewById(R.id.timeCheckInTV);
            stats = itemView.findViewById(R.id.statusTV);
            details = itemView.findViewById(R.id.infoCancelledRBtn);
            delete = itemView.findViewById(R.id.deleteBtn);
        }
    }
}
