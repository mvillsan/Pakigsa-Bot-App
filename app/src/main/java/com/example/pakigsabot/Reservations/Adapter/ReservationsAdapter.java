package com.example.pakigsabot.Reservations.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pakigsabot.EmailMessage.EstReservationList.EmailEst;
import com.example.pakigsabot.R;
import com.example.pakigsabot.Reservations.Model.ReservationsModel;
import com.example.pakigsabot.RestaurantAndCafe.RestoReserveDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationsAdapter extends RecyclerView.Adapter<ReservationsAdapter.MyViewHolder>{
    // creating variables for our ArrayList and context
    Context context;
    ArrayList<ReservationsModel> reservationsArrayList;

    //Initializing variables
    ProgressDialog progressDialog;

    //Firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public ReservationsAdapter() {
        //empty constructor needed
    }

    // creating constructor for our adapter class
    public ReservationsAdapter(Context context, ArrayList<ReservationsModel> reservationsArrayList) {
        this.context = context;
        this.reservationsArrayList = reservationsArrayList;
        this.progressDialog = new ProgressDialog(context);
        this.fStore = FirebaseFirestore.getInstance();
        this.fAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ReservationsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        View itemView = LayoutInflater.from(context).inflate(R.layout.reservations_item,parent,false);
        return new MyViewHolder(itemView);
    }


    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ReservationsAdapter.MyViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        ReservationsModel list = reservationsArrayList.get(position);
        //Set Color Status:: Red for Pending; Green for Confirmed
        if(list.getrStatus_default().equalsIgnoreCase("Pending")){
            holder.stats.setTextColor(Color.RED);
        }else if(list.getrStatus_default().equalsIgnoreCase("Confirmed")){
            holder.stats.setTextColor(Color.GREEN);
        }
        holder.stats.setText(list.getrStatus_default());
        holder.capacity.setText(list.getTotalPax());
        holder.reservationName.setText(list.getRName());
        holder.date1.setText(list.getCheckINDate());
        holder.date2.setText(list.getCheckOUTDate());
        holder.time.setText(list.getCheckINTime());
        holder.estName.setText(list.getEstName());

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.reservationName.getContext())
                        .setContentHolder(new ViewHolder(R.layout.reservation_details_popup))
                        .setExpanded(true,1000)
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
                TextView dateTransacET = viewDP.findViewById(R.id.dateTransacET);
                TextView notesTV = viewDP.findViewById(R.id.notesTxt);
                Button preOrderDB = viewDP.findViewById(R.id.preOrderDB);
                Button cancelBtnPopUp = viewDP.findViewById(R.id.cancelBtnPopUp);
                TextView reasonTV = viewDP.findViewById(R.id.reasonTV);
                EditText reasonET = viewDP.findViewById(R.id.reasonET);
                Button submitReasonBtn = viewDP.findViewById(R.id.submitReasonBtn);
                reasonTV.setVisibility(View.GONE);
                reasonET.setVisibility(View.GONE);
                submitReasonBtn.setVisibility(View.GONE);

                if(list.getPreOrders().isEmpty() || list.getPreOrders() == null){
                    preOrderDB.setVisibility(View.GONE);
                }else{
                    preOrderDB.setVisibility(View.VISIBLE);
                }

                //Retrieving Data
                checkInDateET.setText(checkInDateET.getText().toString() + list.getCheckINDate() + "   Time: " + list.getCheckINTime());
                customerName.setText(customerName.getText().toString() + list.getCustFName() + " " + list.getCustLName());
                reservedET.setText(reservedET.getText().toString() + list.getRName());
                adultPaxET.setText(adultPaxET.getText().toString() + list.getAdultPax() + "   Children: " + list.getChildPax()
                        + "   Infants: " + list.getInfantPax());
                petPaxET.setText(petPaxET.getText().toString() + list.getPetPax());
                feeET.setText(feeET.getText().toString() + list.getFee());
                dateTransacET.setText(dateTransacET.getText().toString() + list.getDateOfTransaction());
                notesTV.setText(notesTV.getText().toString() + list.getNotes());

                preOrderDB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Display it in alert dialog::
                        AlertDialog.Builder alert = new AlertDialog.Builder(context)
                                .setTitle("Pre-Order Details")
                                .setMessage( list.getPreOrders() + "\n\nTotal: Php " + list.getTotalAmt())
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                        alert.show();
                    }
                });

                cancelBtnPopUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        reasonTV.setVisibility(View.VISIBLE);
                        reasonET.setVisibility(View.VISIBLE);
                        submitReasonBtn.setVisibility(View.VISIBLE);

                        //Getting the date today for transaction date.
                        Date date = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                        String dateToday = df.format(date);

                        submitReasonBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(reasonET.getText().toString().isEmpty()){
                                    Toast.makeText(view.getContext(), "Enter Reason for Cancellation", Toast.LENGTH_SHORT).show();
                                }else{
                                    //Check whether check-out date is not earlier than check-in date
                                    try {
                                        Date date1, date2;
                                        SimpleDateFormat dates = new SimpleDateFormat("MM/dd/yyyy");
                                        date1 = dates.parse(list.getCheckINDate());
                                        date2 = dates.parse(dateToday);
                                        long difference = date1.getTime() - date2.getTime();
                                        if (difference == 0) {
                                            Toast.makeText(view.getContext(), "CANCELLATION NOT ALLOWED!", Toast.LENGTH_SHORT).show();

                                        } else {
                                            //Delete the reservation from the reservations collection
                                            fStore.collection("reservations").document(list.getAutoReserveID())
                                                    .delete()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            progressDialog.dismiss();
                                                            reservationsArrayList.remove(position);
                                                            notifyItemRemoved(position);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(context, "Failed to Deleted!", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                            //Save reservation to the Cancelled Reservations collection::
                                            Map<String, Object> cancelled = new HashMap<>();
                                            cancelled.put("cancelReserv_id", list.getAutoReserveID());
                                            cancelled.put("cancelReserv_status", "Cancelled");
                                            cancelled.put("cancelReserv_pax", list.getTotalPax());
                                            cancelled.put("cancelReserv_name", list.getRName());
                                            cancelled.put("cancelReserv_dateIn", list.getCheckINDate());
                                            cancelled.put("cancelReserv_dateCheckout", list.getCheckOUTDate());
                                            cancelled.put("cancelReserv_timeCheckIn", list.getCheckINTime());
                                            cancelled.put("cancelReserv_cust_ID", list.getCust_ID());
                                            cancelled.put("cancelReserv_est_ID", list.getEstID());
                                            cancelled.put("cancelReserv_est_Name", list.getEstName());
                                            cancelled.put("cancelReserv_est_emailAdd", list.getEstEmailAddress());
                                            cancelled.put("cancelReserv_cust_FName", list.getCustFName());
                                            cancelled.put("cancelReserv_cust_LName", list.getCustLName());
                                            cancelled.put("cancelReserv_adultPax", list.getAdultPax());
                                            cancelled.put("cancelReserv_childPax", list.getChildPax());
                                            cancelled.put("cancelReserv_infantPax", list.getInfantPax());
                                            cancelled.put("cancelReserv_petPax", list.getPetPax());
                                            cancelled.put("cancelReserv_fee", list.getFee());
                                            cancelled.put("cancelReserv_notes", list.getNotes());
                                            cancelled.put("cancelReserv_reason", reasonET.getText().toString());
                                            cancelled.put("cancelReserv_cancelledDate", dateToday);
                                            cancelled.put("cancelReserv_transactionDate", list.getDateOfTransaction());
                                            cancelled.put("cancelReserv_cust_phoneNum", list.getCustPhoneNum());
                                            cancelled.put("cancelReserv_cust_emailAdd", list.getCustEmailAdd());
                                            fStore.collection("cancelled-reservations").document(list.getAutoReserveID()).set(cancelled);
                                            Toast.makeText(view.getContext(), "Reservation Cancellation Success", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                        }
                                    } catch (Exception exception) {
                                        Toast.makeText(view.getContext(), "Are you sure you want to CANCEL?", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                    }
                });
            }
        });

        holder.email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String estID = list.getEstID();
                String name = list.getEstName();
                String email = list.getEstEmailAddress();

                Toast.makeText(context, "Email to " + list.getEstName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), EmailEst.class);
                intent.putExtra("EstablishmentID", estID);
                intent.putExtra("EstablishmentName", name);
                intent.putExtra("EstablishmentEmail", email);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return reservationsArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // creating variables for our textviews and imageview.
        TextView date1,date2,estName,reservationName,capacity,time,stats;
        ImageView details, email;

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
            details = itemView.findViewById(R.id.infoBtn);
            email = itemView.findViewById(R.id.emailBtn);
        }
    }

    public void filterList(List<ReservationsModel> filteredList){
        reservationsArrayList = (ArrayList<ReservationsModel>) filteredList;
        notifyDataSetChanged();
    }
}
