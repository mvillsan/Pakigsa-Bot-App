package com.example.pakigsabot.Reservations.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pakigsabot.R;
import com.example.pakigsabot.Reservations.Model.RHistCompletedModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RHistCompletedAdapter extends RecyclerView.Adapter<RHistCompletedAdapter.MyViewHolder>{
    // creating variables for our ArrayList and context
    Context context;
    ArrayList<RHistCompletedModel> reservCompletedArrayList;

    //Initializing variables
    ProgressDialog progressDialog;
    String overallRating, numberOfRatings, newRating, numRatingStr;
    Double overallR;
    int numberR;

    //Firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    DocumentReference docRef;

    public RHistCompletedAdapter() {
        //empty constructor needed
    }

    public RHistCompletedAdapter(Context context, ArrayList<RHistCompletedModel> reservCompletedArrayList) {
        this.context = context;
        this.reservCompletedArrayList = reservCompletedArrayList;
        this.progressDialog = new ProgressDialog(context);
        this.fStore = FirebaseFirestore.getInstance();
        this.fAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public RHistCompletedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        View itemView = LayoutInflater.from(context).inflate(R.layout.reservations_completedhist_item,parent,false);
        return new RHistCompletedAdapter.MyViewHolder(itemView);    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull RHistCompletedAdapter.MyViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        RHistCompletedModel list = reservCompletedArrayList.get(position);
        //Set Color Status:: Green for Completed
        if(list.getrStatus_default().equalsIgnoreCase("Completed")){
            holder.stats.setTextColor(Color.GREEN);
        }
        holder.stats.setText(list.getrStatus_default());
        holder.capacity.setText(list.getTotalPax());
        holder.reservationName.setText(list.getrName());
        holder.date1.setText(list.getCheckINDate());
        holder.date2.setText(list.getCheckOUTDate());
        holder.time.setText(list.getCheckINTime());
        holder.estName.setText(list.getEstName());

        holder.feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.reservationName.getContext())
                        .setContentHolder(new ViewHolder(R.layout.completed_reserve_feedback))
                        .setExpanded(true, 700)
                        .create();

                dialogPlus.show();

                //References::
                View viewDP = dialogPlus.getHolderView();
                TextView estNameTV = viewDP.findViewById(R.id.estNameTV);
                RatingBar ratingBar = viewDP.findViewById(R.id.ratingBar);
                TextView rateDescriptionTV = viewDP.findViewById(R.id.rateDescriptionTV);
                EditText reviewET = viewDP.findViewById(R.id.reviewET);
                Button submitRRBtn = viewDP.findViewById(R.id.submitRRBtn);

                //Retrieving Data
                estNameTV.setText(list.getEstName());

                //Rating Bar
                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean user) {
                        if (rating == 0) {
                            rateDescriptionTV.setText("Not at all Satisfied");
                        } else if (rating == 1) {
                            rateDescriptionTV.setText("Dissatisfied");
                        } else if (rating == 2) {
                            rateDescriptionTV.setText("Partly Satisfied");
                        } else if (rating == 3) {
                            rateDescriptionTV.setText("Satisfied");
                        } else if (rating == 4) {
                            rateDescriptionTV.setText("More than Satisfied");
                        } else if (rating == 5) {
                            rateDescriptionTV.setText("Very Satisfied");
                        } else {
                            rateDescriptionTV.setText("");
                        }
                    }
                });

                //Get Est Total Overall Ratings and Total Rating Counts::
                //Fetching Data from FireStore DB::

                DocumentReference documentReference = fStore.collection("establishments").document(list.getEstID());
                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@androidx.annotation.Nullable DocumentSnapshot value, @androidx.annotation.Nullable FirebaseFirestoreException error) {
                        overallRating = value.getString("est_overallrating");
                        numberOfRatings = value.getString("est_totalratingcount");
                        overallR = Double.parseDouble(overallRating);
                        numberR = Integer.parseInt(numberOfRatings);
                    }
                });

                //Button
                submitRRBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (reviewET.getText().toString().isEmpty()) {
                            Toast.makeText(view.getContext(), "Please Enter your Review", Toast.LENGTH_SHORT).show();
                        } else {
                            //Rating Bar
                            float rate = ratingBar.getRating();
                            String ratingStr = "" + rate;

                            //Getting the date today for transaction date.
                            Date date = Calendar.getInstance().getTime();
                            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                            String dateToday = df.format(date);

                            //Check if feedback already exists
                            docRef = fStore.collection("feedback").document(list.getAutoReserveID());
                            docRef.get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot document = task.getResult();
                                                if (document.exists()) {
                                                    Toast.makeText(view.getContext(), "You Already Submitted a Feedback", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    //Save reservation feedback to the Feedback collection::
                                                    Map<String, Object> feedback = new HashMap<>();
                                                    feedback.put("feedbackID", list.getAutoReserveID());
                                                    feedback.put("feedbackEstID", list.getEstID());
                                                    feedback.put("feedbackEstName", list.getEstName());
                                                    feedback.put("feedbackCustID", list.getCust_ID());
                                                    feedback.put("feedbackRating", ratingStr);
                                                    feedback.put("feedbackDesc", rateDescriptionTV.getText().toString());
                                                    feedback.put("feedbackReview", reviewET.getText().toString());
                                                    feedback.put("feedbackDate", dateToday);
                                                    fStore.collection("feedback").document(list.getAutoReserveID()).set(feedback);

                                                    Log.d("tag", overallRating + numberOfRatings + overallR + numberR);

                                                    overallR = overallR + ratingBar.getRating();
                                                    numberR = numberR + 1;
                                                    newRating = ""+overallR/numberR;
                                                    numRatingStr = ""+numberR;

                                                    DocumentReference docuRef = fStore.collection("establishments").document(list.getEstID());
                                                    //Save Count of Ratings and Ratings equivalent::
                                                    Map<String, Object> ratings = new HashMap<>();
                                                    ratings.put("est_overallrating", newRating);
                                                    ratings.put("est_totalratingcount", numRatingStr);
                                                    docuRef.update(ratings).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            Log.d("TAG","Updated the Total Rating and Count");
                                                        }
                                                    });

                                                    Toast.makeText(view.getContext(), "Feedback Submitted Successfully", Toast.LENGTH_SHORT).show();
                                                    dialogPlus.dismiss();
                                                }
                                            } else {
                                                Log.d("TAG", "Failed with: ", task.getException());
                                            }
                                        }
                                    });
                        }
                    }
                });
            }
        });

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.reservationName.getContext())
                        .setContentHolder(new ViewHolder(R.layout.completed_reservations_details))
                        .setExpanded(true, 600)
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

                //Retrieving Data
                checkInDateET.setText(checkInDateET.getText().toString() + list.getCheckINDate() + "   Time: " + list.getCheckINTime());
                customerName.setText(customerName.getText().toString() + list.getCustFName() + " " + list.getCustLName());
                reservedET.setText(reservedET.getText().toString() + list.getrName());
                adultPaxET.setText(adultPaxET.getText().toString() + list.getAdultPax() + "   Children: " + list.getChildPax()
                        + "   Infants: " + list.getInfantPax());
                petPaxET.setText(petPaxET.getText().toString() + list.getPetPax());
                feeET.setText(feeET.getText().toString() + list.getFee());
                notesTxt.setText(notesTxt.getText().toString() + list.getNotes());
                dateTransacET.setText(dateTransacET.getText().toString() + list.getDateOfCompletion());
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
                                fStore.collection("completed-reservations").document(list.getAutoReserveID())
                                        .delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                reservCompletedArrayList.remove(position);
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
        return reservCompletedArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // creating variables for our textviews and imageview.
        TextView date1,date2,estName,reservationName,capacity,time,stats;
        ImageView feedback, details, delete;

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
            feedback = itemView.findViewById(R.id.feedbackBtn);
            details = itemView.findViewById(R.id.infoBtn);
            delete = itemView.findViewById(R.id.deleteBtn);
        }
    }
}
