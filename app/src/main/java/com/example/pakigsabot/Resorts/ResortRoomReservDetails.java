package com.example.pakigsabot.Resorts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pakigsabot.BuildConfig;
import com.example.pakigsabot.R;
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
import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.approve.Approval;
import com.paypal.checkout.approve.OnApprove;
import com.paypal.checkout.cancel.OnCancel;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.config.SettingsConfig;
import com.paypal.checkout.createorder.CreateOrder;
import com.paypal.checkout.createorder.CreateOrderActions;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.OrderIntent;
import com.paypal.checkout.createorder.UserAction;
import com.paypal.checkout.error.ErrorInfo;
import com.paypal.checkout.error.OnError;
import com.paypal.checkout.order.Amount;
import com.paypal.checkout.order.AppContext;
import com.paypal.checkout.order.CaptureOrderResult;
import com.paypal.checkout.order.OnCaptureComplete;
import com.paypal.checkout.order.Order;
import com.paypal.checkout.order.PurchaseUnit;
import com.paypal.checkout.paymentbutton.PayPalButton;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ResortRoomReservDetails extends AppCompatActivity {

    //Initialization of variables
    String roomID, roomName, capac, descrip, rate, roomImgUrl, establishmentID,
            autoReserveID, cust_ID, cust_FName, cust_LName, cust_EmailAdd, cust_MobileNum, custSubStatus,
            notes, rStatus_default, adultPaxStr, childPaxStr, infantsPaxStr,
            petPaxStr, checkINDate, checkOUTDate, checkINTime, amount, dateToday, totalPaxStr, estName, estEmailAdd;
    private int totalPax, cust_numReservations, est_numReservations;
    List<String> listEstablishments, listReservationName, listCheckInDates;
    TextView lblRoom,notesResortTxt,checkInDateTV, checkOutDateTV, checkInDate, checkOutDate, cITimeTxt,
            amounttoPayTxt, messageTxt, checkInTimeTV, whoseTxt, adultsTxt, childrenTxt, infantsTxt, petsTxt;
    ImageView roomIcon, imgBackBtn, selectCheckInDate, selectCheckOutDate, imageView18;
    EditText notesEditTxt, adultPax, childPax, infantsPax, petsPax;
    Button reserveBtn;
    DatePickerDialog.OnDateSetListener dateSetListenerCI;
    DatePickerDialog.OnDateSetListener dateSetListenerCO;
    PayPalButton payPalButton;

    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    DocumentReference documentReference, docRef, documentRef, docuRef;

    //Paypal Client ID::
    private static final String YOUR_CLIENT_ID ="AY2ERQska0p9RKv5PsknVYST7KmgiuIbOUXJT-Kk2IEP8mcBH_QaocNBwlAlt0y72WM3W5w6TKJfoxe7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resort_room_reserv_details);

        //References::
        refs();

        //Get Room Details::
        getRoomDetails();

        //Generation of random IDs
        autoReserveID = UUID.randomUUID().toString();

        //Getting customer's details::
        getCustomerData();

        //Getting establisment's total num of reservations::
        getEstNumReservations();

        //DatePicker Dialog::
        checkInDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ResortRoomReservDetails.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListenerCI,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //Previous dates will be disabled
                dialog.getDatePicker().setMinDate(cal.getTimeInMillis());
                dialog.show();
            }
        });

        dateSetListenerCI = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yr, int mon, int dy) {
                mon = mon+1;
                String month = ""+mon;
                String day = ""+dy;

                if(mon < 10){
                    month = "0" + mon;
                }
                if(dy < 10){
                    day  = "0" + dy ;
                }
                String date = month + "/" + day + "/" + yr;

                //Getting the date today for transaction date.
                Date date2 = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                String todayDateStr = df.format(date2);

                if(date.equalsIgnoreCase(todayDateStr)){
                    Toast.makeText(ResortRoomReservDetails.this, "Reserve a Slot Ahead of Time!", Toast.LENGTH_SHORT).show();
                }else{
                    checkInDate.setText(date);
                }
            }
        };

        //DatePicker Dialog::
        checkOutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ResortRoomReservDetails.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListenerCO,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //Previous dates will be disabled
                dialog.getDatePicker().setMinDate(cal.getTimeInMillis());
                dialog.show();
            }
        });

        dateSetListenerCO = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yr, int mon, int dy) {
                mon = mon+1;
                String month = ""+mon;
                String day = ""+dy;

                if(mon < 10){
                    month = "0" + mon;
                }
                if(dy < 10){
                    day  = "0" + dy ;
                }
                String date = month + "/" + day + "/" + yr;
                checkOutDate.setText(date);
            }
        };

        reserveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Parsing of variables::
                notes = notesEditTxt.getText().toString();
                adultPaxStr = adultPax.getText().toString();
                childPaxStr = childPax.getText().toString();
                infantsPaxStr = infantsPax.getText().toString();
                petPaxStr = petsPax.getText().toString();
                checkINDate = checkInDate.getText().toString();
                checkOUTDate = checkOutDate.getText().toString();

                //Check Missing Fields::
                if (notes.isEmpty() || adultPaxStr.isEmpty() || childPaxStr.isEmpty() || infantsPaxStr.isEmpty()
                        || petPaxStr.isEmpty() || checkINDate.isEmpty() || checkOUTDate.isEmpty()) {
                    Toast.makeText(ResortRoomReservDetails.this, "You MUST input ALL Fields", Toast.LENGTH_SHORT).show();
                }else{
                   checkDuplicateDates();
                }
            }
        });

        //Paypal Integration::
        CheckoutConfig config = new CheckoutConfig(
                getApplication(),
                YOUR_CLIENT_ID,
                Environment.SANDBOX,
                String.format("%s://paypalpay", BuildConfig.APPLICATION_ID),
                CurrencyCode.PHP,
                UserAction.PAY_NOW,
                new SettingsConfig(
                        true,
                        false
                )
        );
        PayPalCheckout.setConfig(config);

        payPalButton.setup(
                new CreateOrder() {
                    @Override
                    public void create(@NotNull CreateOrderActions createOrderActions) {
                        ArrayList<PurchaseUnit> purchaseUnits = new ArrayList<>();
                        purchaseUnits.add(
                                new PurchaseUnit.Builder()
                                        .amount(
                                                new Amount.Builder()
                                                        .currencyCode(CurrencyCode.PHP)
                                                        .value(amount)
                                                        .build()
                                        )
                                        .build()
                        );
                        Order order = new Order(
                                OrderIntent.CAPTURE,
                                new AppContext.Builder()
                                        .userAction(UserAction.PAY_NOW)
                                        .build(),
                                purchaseUnits
                        );
                        createOrderActions.create(order, (CreateOrderActions.OnOrderCreated) null);
                    }
                },
                new OnApprove() {
                    @Override
                    public void onApprove(@NotNull Approval approval) {
                        approval.getOrderActions().capture(new OnCaptureComplete() {
                            @Override
                            public void onCaptureComplete(@NotNull CaptureOrderResult result) {
                                Log.i("CaptureOrder", String.format("CaptureOrderResult: %s", result));
                                //Save reservation details to firestore::
                                saveReservationDB();
                                //Redirected to Success Page
                                success();
                            }
                        });
                    }
                },
                new OnCancel() {
                    @Override
                    public void onCancel() {
                        Log.d("OnCancel", "Buyer cancelled the PayPal experience.");
                    }
                },
                new OnError() {
                    @Override
                    public void onError(@NotNull ErrorInfo errorInfo) {
                        Log.d("OnError", String.format("Error: %s", errorInfo));
                    }
                }
        );

        //Back to Resort Establishment Details
        imgBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resortDetails();
            }
        });

    }

    private void getRoomDetails() {
        //Getting data from recyclerview::
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            roomID = extra.getString("RoomID");
            roomName = extra.getString("RoomName");
            capac = extra.getString("RoomCapacity");
            descrip = extra.getString("RoomDesc");
            rate = extra.getString("RoomRate");
            roomImgUrl = extra.getString("RoomImg");
            establishmentID = extra.getString("RoomEstID");
        }

        //Display Resort Details::
        lblRoom.setText(roomName);
        Picasso.get().load(roomImgUrl).into(roomIcon);
    }

    private void refs(){
        lblRoom = findViewById(R.id.lblRoom);
        imgBackBtn = findViewById(R.id.imgBackBtn);
        roomIcon = findViewById(R.id.roomIcon);
        notesEditTxt = findViewById(R.id.notesEditTxt);
        notesResortTxt = findViewById(R.id.notesResortTxt);
        checkInDateTV = findViewById(R.id.checkInDateTV);
        checkOutDateTV = findViewById(R.id.checkOutDateTV);
        checkInDate = findViewById(R.id.checkInDate);
        checkOutDate = findViewById(R.id.checkOutDate);
        selectCheckInDate = findViewById(R.id.selectCheckInDate);
        selectCheckOutDate = findViewById(R.id.selectCheckOutDate);
        checkInTimeTV = findViewById(R.id.checkInTimeTV);
        cITimeTxt = findViewById(R.id.cITimeTxt);
        imageView18 = findViewById(R.id.imageView18);
        whoseTxt = findViewById(R.id.whoseTxt);
        adultsTxt = findViewById(R.id.adultsTxt);
        childrenTxt = findViewById(R.id.childrenTxt);
        infantsTxt= findViewById(R.id.infantsTxt);
        petsTxt = findViewById(R.id.petsTxt);
        adultPax = findViewById(R.id.adultPax);
        childPax = findViewById(R.id.childPax);
        infantsPax = findViewById(R.id.infantsPax);
        petsPax = findViewById(R.id.petsPax);
        amounttoPayTxt = findViewById(R.id.amounttoPayTxt);
        messageTxt = findViewById(R.id.messageTxt);
        reserveBtn = findViewById(R.id.reserveBtnRDRC);
        payPalButton = findViewById(R.id.payPalButton);
        amounttoPayTxt.setVisibility(View.GONE);
        payPalButton.setVisibility(View.GONE);
        messageTxt.setVisibility(View.GONE);
    }

    private void resortDetails() {
        Intent intent = new Intent(getApplicationContext(), ResortDetails.class);
        startActivity(intent);
    }

    public void getCustomerData(){
        //Fetching Data from FireStore DB::
        cust_ID =  fAuth.getCurrentUser().getUid();

        documentReference = fStore.collection("customers").document(cust_ID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@androidx.annotation.Nullable DocumentSnapshot value, @androidx.annotation.Nullable FirebaseFirestoreException error) {
                cust_numReservations = value.getLong("cust_numOfReservations").intValue();
                cust_FName = value.getString("cust_fname");
                cust_LName = value.getString("cust_lname");
                cust_MobileNum = value.getString("cust_phoneNum");
                cust_EmailAdd = value.getString("cust_email");

                custSubStatus = value.getString("cust_status");
            }
        });
    }

    public void getEstNumReservations(){
        //Fetching Data from FireStore DB::

        documentRef = fStore.collection("establishments").document(establishmentID);
        documentRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@androidx.annotation.Nullable DocumentSnapshot value, @androidx.annotation.Nullable FirebaseFirestoreException error) {
                est_numReservations = value.getLong("est_numOfReservation").intValue();
                estName = value.getString("est_Name");
                estEmailAdd = value.getString("est_email");
            }
        });
    }

    public void checkDuplicateDates(){
        //Validate whether dates inputted are valid::
        //Check whether check-out date is not earlier than check-in date
        try {
            Date date1, date2;
            SimpleDateFormat dates = new SimpleDateFormat("MM/dd/yyyy");
            date1 = dates.parse(checkINDate);
            date2 = dates.parse(checkOUTDate);
            long difference = date1.getTime() - date2.getTime();
            if (difference < 0) {
                //Check first whether customer's number of reservations <= 3;
                //If customer's number of reservations > 3 cannot reserve more unless PREMIUM user.
                if(custSubStatus.equals("Free")){
                    if(cust_numReservations >= 3){
                        Toast.makeText(ResortRoomReservDetails.this, "Susbcribe to Pakigsa-Bot Premium NOW, to Reserve More!", Toast.LENGTH_SHORT).show();
                    } else { //List of establishments::
                        fStore.collection("reservations").get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            listEstablishments = new ArrayList<>();
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                listEstablishments.add(document.getString("reserv_est_Name"));
                                            }
                                            Log.d("TAG", listEstablishments.toString());
                                        } else {
                                            Log.d("TAG", "Error getting documents: ", task.getException());
                                        }
                                    }
                                });

                        //List of Reservation Name::
                        fStore.collection("reservations").get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            listReservationName = new ArrayList<>();
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                listReservationName.add(document.getString("reserv_name"));
                                            }
                                            Log.d("TAG", listReservationName.toString());
                                        } else {
                                            Log.d("TAG", "Error getting documents: ", task.getException());
                                        }
                                    }
                                });

                        //List of Check-in Dates::
                        fStore.collection("reservations").get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            listCheckInDates = new ArrayList<>();
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                listCheckInDates.add(document.getString("reserv_dateIn"));
                                            }
                                            Log.d("TAG", listCheckInDates.toString());
                                        } else {
                                            Log.d("TAG", "Error getting documents: ", task.getException());
                                        }
                                    }
                                });

                        if(listEstablishments.contains(estName) && listReservationName.contains(roomName) && listCheckInDates.contains(checkINDate)){
                            messageTxt.setVisibility(View.VISIBLE);
                            messageTxt.setText("FULLY BOOKED! Select Another DATES!");
                        }else{
                            messageTxt.setVisibility(View.VISIBLE);
                            messageTxt.setText("Room Available!");

                            //Setting Up amount to be paid
                            amounttoPayTxt.setVisibility(View.VISIBLE);
                            if (cust_numReservations <= 9) {
                                amounttoPayTxt.setText("100");
                            } else if (cust_numReservations >= 10 && cust_numReservations <= 24) {
                                amounttoPayTxt.setText("75");
                            } else if (cust_numReservations >= 25 && cust_numReservations <= 44) {
                                amounttoPayTxt.setText("50");
                            } else if (cust_numReservations >= 45) {
                                amounttoPayTxt.setText("40");
                            }
                            amount = amounttoPayTxt.getText().toString();
                            //Paypal Payment::
                            payPalButton.setVisibility(View.VISIBLE);
                        }
                    }
                }else{//Premium Subscription
                    //List of establishments::
                    fStore.collection("reservations").get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        listEstablishments = new ArrayList<>();
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            listEstablishments.add(document.getString("reserv_est_Name"));
                                        }
                                        Log.d("TAG", listEstablishments.toString());
                                    } else {
                                        Log.d("TAG", "Error getting documents: ", task.getException());
                                    }
                                }
                            });

                    //List of Reservation Name::
                    fStore.collection("reservations").get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        listReservationName = new ArrayList<>();
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            listReservationName.add(document.getString("reserv_name"));
                                        }
                                        Log.d("TAG", listReservationName.toString());
                                    } else {
                                        Log.d("TAG", "Error getting documents: ", task.getException());
                                    }
                                }
                            });

                    //List of Check-in Dates::
                    fStore.collection("reservations").get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        listCheckInDates = new ArrayList<>();
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            listCheckInDates.add(document.getString("reserv_dateIn"));
                                        }
                                        Log.d("TAG", listCheckInDates.toString());
                                    } else {
                                        Log.d("TAG", "Error getting documents: ", task.getException());
                                    }
                                }
                            });

                    if(listEstablishments.contains(estName) && listReservationName.contains(roomName) && listCheckInDates.contains(checkINDate)){
                        messageTxt.setVisibility(View.VISIBLE);
                        messageTxt.setText("FULLY BOOKED! Select Another DATES!");
                    }else{
                        messageTxt.setVisibility(View.VISIBLE);
                        messageTxt.setText("Room Available!");

                        //Setting Up amount to be paid
                        amounttoPayTxt.setVisibility(View.VISIBLE);
                        if (cust_numReservations <= 9) {
                            amounttoPayTxt.setText("100");
                        } else if (cust_numReservations >= 10 && cust_numReservations <= 24) {
                            amounttoPayTxt.setText("75");
                        } else if (cust_numReservations >= 25 && cust_numReservations <= 44) {
                            amounttoPayTxt.setText("50");
                        } else if (cust_numReservations >= 45) {
                            amounttoPayTxt.setText("40");
                        }
                        amount = amounttoPayTxt.getText().toString();
                        //Paypal Payment::
                        payPalButton.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                checkOutDate.setText("Invalid! Select another Date");
            }
        } catch (Exception exception) {
            Toast.makeText(ResortRoomReservDetails.this, "Please Re-check your Reservation Details!", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveReservationDB() {
        //Parsing of variables::
        rStatus_default = "Pending";
        checkINTime = cITimeTxt.getText().toString();
        totalPax = Integer.parseInt(adultPaxStr) + Integer.parseInt(childPaxStr) + Integer.parseInt(infantsPaxStr);
        totalPaxStr = "" + totalPax;

        //Getting the date today for transaction date.
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        dateToday = df.format(date);

        //Updating customer's number of reservations::
        docRef = fStore.collection("customers").document(cust_ID);
        //Increment +1 the current number of reservations::
        cust_numReservations = cust_numReservations + 1;

        Map<String, Object> editedNumReservations = new HashMap<>();
        editedNumReservations.put("cust_numOfReservations", cust_numReservations);
        docRef.update(editedNumReservations).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ResortRoomReservDetails.this, "No Changes has been made", Toast.LENGTH_SHORT).show();
            }
        });

        //Updating establishment's number of reservations::
        docuRef = fStore.collection("establishments").document(establishmentID);
        //Increment +1 the current number of reservations::
        est_numReservations = est_numReservations + 1;
        Map<String, Object> editedNumReservationsEst = new HashMap<>();
        editedNumReservationsEst.put("est_numOfReservation", est_numReservations);
        docuRef.update(editedNumReservationsEst).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ResortRoomReservDetails.this, "No Changes has been made", Toast.LENGTH_SHORT).show();
            }
        });

        //Save reservation details::
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("reserv_id", autoReserveID);
        reservation.put("reserv_status", rStatus_default);
        reservation.put("reserv_fee", amount);
        reservation.put("reserv_adultPax", adultPaxStr);
        reservation.put("reserv_childPax", childPaxStr);
        reservation.put("reserv_infantPax", infantsPaxStr);
        reservation.put("reserv_petPax", petPaxStr);
        reservation.put("reserv_pax", totalPaxStr);
        reservation.put("reserv_name", roomName);
        reservation.put("reserv_notes", notes);
        reservation.put("reserv_dateIn", checkINDate);
        reservation.put("reserv_dateCheckOut", checkOUTDate);
        reservation.put("reserv_timeCheckIn", checkINTime);
        reservation.put("reserv_cust_ID", cust_ID);
        reservation.put("reserv_cust_FName", cust_FName);
        reservation.put("reserv_cust_LName", cust_LName);
        reservation.put("reserv_cust_phoneNum", cust_MobileNum);
        reservation.put("reserv_cust_emailAdd", cust_EmailAdd);
        reservation.put("reserv_est_ID", establishmentID);
        reservation.put("reserv_est_Name", estName);
        reservation.put("reserv_est_emailAdd", estEmailAdd);
        reservation.put("reserv_confirmedDate", "");
        reservation.put("reserv_transactionDate", dateToday);
        reservation.put("reserv_preOrder_items", "");
        reservation.put("reserv_totalAmt", "");

        fStore.collection("reservations").document(autoReserveID).set(reservation);
        Toast.makeText(ResortRoomReservDetails.this, "Reservation Request Sent", Toast.LENGTH_SHORT).show();
    }

    private void success(){
        Intent intent = new Intent(getApplicationContext(), ResortReservationSuccess.class);
        intent.putExtra("EstablishmentID", establishmentID);
        intent.putExtra("EstablishmentName", estName);
        intent.putExtra("EstablishmentEmail", estEmailAdd);
        startActivity(intent);
    }
}