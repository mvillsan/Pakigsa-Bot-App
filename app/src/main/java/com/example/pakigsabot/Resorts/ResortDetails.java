package com.example.pakigsabot.Resorts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import com.example.pakigsabot.DentalAndEyeClinics.DentalClinicReservation;
import com.example.pakigsabot.R;
import com.example.pakigsabot.Resorts.Adapters.FacilityAdapter;
import com.example.pakigsabot.Resorts.Adapters.PromoDealsAdapter;
import com.example.pakigsabot.Resorts.Adapters.RoomAdapter;
import com.example.pakigsabot.Resorts.Models.FacilityModel;
import com.example.pakigsabot.Resorts.Models.PromoandDealsModel;
import com.example.pakigsabot.Resorts.Models.RoomModel;
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


public class ResortDetails extends AppCompatActivity {

    //Initialization of variables
    ImageView resortIcon, imgBackBtn, gmapBtn, favImgView;
    TextView roomsDetails, lblResort, lblAddress, lblContactNum, policyTxt, cancelTxt;
    String estID, estName, estAdd, estImageUrl, estPhoneNum, rule1, rule2, rule3,
            rule4, rule5, rule6, rule7, rule8, rule9, rule10, rule11, content, pol1,
            pol2, pol3, custID,autoID;
    RecyclerView roomRecyclerView, facilityRecyclerView, promoDealsRecyclerView;
    ArrayList<RoomModel> resortRoomArrayList;
    RoomAdapter roomAdapter;
    ArrayList<FacilityModel> resortFArrayList;
    FacilityAdapter resortFAdapter;
    ArrayList<PromoandDealsModel> resortPromoDealsArrayList;
    PromoDealsAdapter promoDealsAdapter;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resort_details);

        //References::
        refs();
        fAuth = FirebaseAuth.getInstance();
        custID = fAuth.getCurrentUser().getUid();
        autoID = UUID.randomUUID().toString();

        //Set details
        setDetails();

        //Room recyclerview initialization
        populateRoomRV();
        //Get Rooms List
        getRoomsList();

        //Facility recyclerview initialization
        populateFacilityRV();
        //Get Facility List
        getFacilityList();

        //Promo and Deals recyclerview initialization
        populatePromoDealsRV();
        //Get Facility List
        getPromoDealsList();

        //Get directions of the resort::
        gmapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });

        //View Resort Policies or Rules::
        policyTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPolicies();
            }
        });

        //View Cancellation Policy::
        cancelTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewCancelPolicy();
            }
        });

        //Add est to favorites::
        favImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToFavorites();
            }
        });

        //Back to Resort Establishments List
        imgBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resortList();
            }
        });

    }

    public void refs() {
        resortIcon = findViewById(R.id.resortIcon);
        lblResort = findViewById(R.id.lblResort);
        lblAddress = findViewById(R.id.lblAddress);
        roomsDetails = findViewById(R.id.roomsTxt);
        imgBackBtn = findViewById(R.id.imgBackBtn);
        gmapBtn = findViewById(R.id.gmapBtn);
        lblContactNum = findViewById(R.id.lblContactNum);
        policyTxt = findViewById(R.id.policyTxt);
        cancelTxt = findViewById(R.id.cancelTxt);
        favImgView = findViewById(R.id.favImgView);
    }

    private void setDetails() {
        //Getting data from recyclerview::
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            estID = extra.getString("EstID");
            estName = extra.getString("EstName");
            estAdd = extra.getString("EstAddress");
            estImageUrl = extra.getString("EstImageUrl");
            estPhoneNum = extra.getString("EstPhoneNum");
        }

        //Display Resort Details::
        lblResort.setText(estName);
        lblAddress.setText(estAdd);
        lblContactNum.setText("Contact Number > " + estPhoneNum);
        Picasso.get().load(estImageUrl).into(resortIcon);
    }

    private void populateRoomRV(){
        roomRecyclerView = findViewById(R.id.roomRecyclerView);
        roomRecyclerView.setHasFixedSize(true);
        roomRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // adding our array list to our recycler view adapter class.
        resortRoomArrayList = new ArrayList<>();
        //initializing the arraylist where all data is stored
        resortRoomArrayList = new ArrayList<RoomModel>();
        //initializing the adapter
        roomAdapter = new RoomAdapter(ResortDetails.this, resortRoomArrayList);
        roomRecyclerView.setAdapter(roomAdapter);
    }

    private void populateFacilityRV(){
        facilityRecyclerView = findViewById(R.id.facilityRecyclerView);
        facilityRecyclerView.setHasFixedSize(true);
        facilityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // adding our array list to our recycler view adapter class.
        resortFArrayList = new ArrayList<>();
        //initializing the arraylist where all data is stored
        resortFArrayList = new ArrayList<FacilityModel>();
        //initializing the adapter
        resortFAdapter = new FacilityAdapter(ResortDetails.this, resortFArrayList);
        facilityRecyclerView.setAdapter(resortFAdapter);
    }

    private void populatePromoDealsRV(){
        promoDealsRecyclerView = findViewById(R.id.promoDealsRecyclerView);
        promoDealsRecyclerView.setHasFixedSize(true);
        promoDealsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // adding our array list to our recycler view adapter class.
        resortPromoDealsArrayList = new ArrayList<>();
        //initializing the arraylist where all data is stored
        resortPromoDealsArrayList = new ArrayList<PromoandDealsModel>();
        //initializing the adapter
        promoDealsAdapter = new PromoDealsAdapter(ResortDetails.this, resortPromoDealsArrayList);
        promoDealsRecyclerView.setAdapter(promoDealsAdapter);
    }

    private void getRoomsList() {
        fStore.collection("establishments").document(estID).collection("resort-rooms")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        resortRoomArrayList.clear();

                        for (DocumentSnapshot doc : task.getResult()) {
                            RoomModel list = new RoomModel(doc.getString("resortRFID"),
                                    doc.getString("resortRFName"),
                                    doc.getString("resortCapac"),
                                    doc.getString("resortDesc"),
                                    doc.getString("resortRate"),
                                    doc.getString("resortImgUrl"),
                                    doc.getString("estID"));
                            resortRoomArrayList.add(list);
                        }
                        roomAdapter = new RoomAdapter(ResortDetails.this, resortRoomArrayList);
                        // setting adapter to our recycler view.
                        roomRecyclerView.setAdapter(roomAdapter);
                        roomAdapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ResortDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getFacilityList() {
        fStore.collection("establishments").document(estID).collection("resort-facilities")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        resortFArrayList.clear();

                        for (DocumentSnapshot doc : task.getResult()) {
                            FacilityModel list = new FacilityModel(doc.getString("resortFID"),
                                    doc.getString("resortFName"),
                                    doc.getString("resortFCapac"),
                                    doc.getString("resortFDesc"),
                                    doc.getString("resortFRate"),
                                    doc.getString("resortFImgUrl"));
                            resortFArrayList.add(list);
                        }
                        resortFAdapter = new FacilityAdapter(ResortDetails.this, resortFArrayList);
                        // setting adapter to our recycler view.
                        facilityRecyclerView.setAdapter(resortFAdapter);
                        resortFAdapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ResortDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPromoDealsList(){
        fStore.collection("establishments").document(estID).collection("resort-promo-and-deals")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        resortPromoDealsArrayList.clear();

                        for (DocumentSnapshot doc : task.getResult()) {
                            PromoandDealsModel list = new PromoandDealsModel(doc.getString("resortPADId"),
                                    doc.getString("resortPADName"),
                                    doc.getString("resortPADDesc"),
                                    doc.getString("resortPADStartDate"),
                                    doc.getString("resortPADEndDate"));
                            resortPromoDealsArrayList.add(list);
                        }
                        promoDealsAdapter = new PromoDealsAdapter(ResortDetails.this, resortPromoDealsArrayList);
                        // setting adapter to our recycler view.
                        promoDealsRecyclerView.setAdapter(promoDealsAdapter);
                        promoDealsAdapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ResortDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getLocation() {
        //When google map is installed
        //Initialize uri
        Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + lblAddress.getText().toString() + "/" + estAdd);
        //Intent with action view
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            intent.setPackage("com.google.android.app.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void viewPolicies() {
        Toast.makeText(this, "View Policies", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), EstRules.class);
        intent.putExtra("EstID", estID);
        startActivity(intent);
    }

    private void viewCancelPolicy(){
        Toast.makeText(this, "View Cancellation Policy", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), EstCancellationPolicy.class);
        intent.putExtra("EstID", estID);
        startActivity(intent);
    }

    private void resortList() {
        Intent intent = new Intent(getApplicationContext(), ResortReservation.class);
        startActivity(intent);
    }

    private void addToFavorites(){
        //Store to favorite list
        Map<String,Object> favoriteEst = new HashMap<>();
        favoriteEst.put("favEstId", autoID);
        favoriteEst.put("estId", estID);
        favoriteEst.put("custId", custID);
        favoriteEst.put("estName", estName);
        favoriteEst.put("estAddress", estAdd);
        favoriteEst.put("estImageUrl", estImageUrl);
        Toast.makeText(ResortDetails.this, estName +" added to Favorites!", Toast.LENGTH_SHORT).show();
        fStore.collection("customers").document(custID).collection("favorites").document(autoID).set(favoriteEst);
    }
}
