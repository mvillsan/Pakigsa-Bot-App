package com.example.pakigsabot.SpaSalon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pakigsabot.DentalAndEyeClinics.DentalClinicReservation;
import com.example.pakigsabot.R;
import com.example.pakigsabot.SpaSalon.Adapter.SalonPromoDealsAdapter;
import com.example.pakigsabot.SpaSalon.Adapter.SalonServiceAdapter;
import com.example.pakigsabot.SpaSalon.Models.SalonPromoDealsModels;
import com.example.pakigsabot.SpaSalon.Models.SalonServiceModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SalonDetails extends AppCompatActivity {
    //Initialization of variables
    ImageView estIcon, imgBackBtn, gmapBtn;
    Button reserveBtn;
    ImageButton favBtn;
    TextView lblProcedures, lblPolicy, lblEst, lblAddress, lblContactNum, datePicker;
    String estID, autoId, estName, estAdd, estImageUrl, estPhoneNum;
    DatePickerDialog.OnDateSetListener dateSetListener;

    // creating variables for our recycler view,
    // array list, adapter, firebase firestore,
    // and our progress bar.
    // initializing our variable for firebase
    // firestore and getting its instance.
    RecyclerView recyclerView, promoAndDealsRecyclerView, selectedRecyclerView;
    ArrayList<SalonServiceModel> arrayList;
    SalonServiceAdapter adapter;
    ArrayList<SalonPromoDealsModels> padArrayList;
    SalonPromoDealsAdapter padAdapter;
    FirebaseFirestore fStoreRef = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_details);

        //references
        refs();

        fAuth = FirebaseAuth.getInstance();
        fStoreRef = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        autoId = UUID.randomUUID().toString();


        //Set details
        setDetails();


        //Get Promo and Deals List
        getPromoAndDealsList();

        //Get List
        getList();

        //Procedures recyclerview initialization
        recyclerView = findViewById(R.id.proceduresRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // adding our array list to our recycler view adapter class.
        arrayList = new ArrayList<>();
        //initializing the arraylist where all data is stored
        arrayList = new ArrayList<SalonServiceModel>();
        //initializing the adapter
        adapter = new SalonServiceAdapter(SalonDetails.this, arrayList);
        recyclerView.setAdapter(adapter);

        //Promo and Deals recyclerview initialization
        promoAndDealsRecyclerView = findViewById(R.id.promoDealsRecyclerView);
        promoAndDealsRecyclerView.setHasFixedSize(true);
        promoAndDealsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // adding our array list to our recycler view adapter class.
        padArrayList = new ArrayList<>();
        //initializing the arraylist where all data is stored
        padArrayList = new ArrayList<SalonPromoDealsModels>();
        //initializing the adapter
        padAdapter = new SalonPromoDealsAdapter(SalonDetails.this, padArrayList);
        promoAndDealsRecyclerView.setAdapter(padAdapter);

        //Get directions of the resort::
        gmapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });

        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavorites();
            }
        });

        //Back to Dental and Eye Clinic Establishments List
        imgBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salonList();
            }
        });

        lblPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(SalonDetails.this);
                builder.setTitle("My title");
                builder.setMessage("This is my message.");

                // add a button
                builder.setPositiveButton("OK", null);

                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    public void refs() {
        estIcon = findViewById(R.id.estIcon);
        lblProcedures = findViewById(R.id.diningTxt);
        lblEst = findViewById(R.id.label);
        lblAddress = findViewById(R.id.lblAddress);
        lblPolicy = findViewById(R.id.policyTxt);
        favBtn = findViewById(R.id.favoriteBtn);
        reserveBtn = findViewById(R.id.reserveBtnDC);
        imgBackBtn = findViewById(R.id.imgBackBtn);
        gmapBtn = findViewById(R.id.gmapBtn);
        lblContactNum = findViewById(R.id.lblContactNum);
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

        //Display Dental Clinic Details::
        lblEst.setText(estName);
        lblAddress.setText(estAdd);
        lblContactNum.setText("Contact Number > " + estPhoneNum);
        Picasso.get().load(estImageUrl).into(estIcon);
    }

    private void getList() {
        fStoreRef.collection("establishments").document(estID).collection("salon-services")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        arrayList.clear();

                        for (DocumentSnapshot doc : task.getResult()) {
                            SalonServiceModel list = new SalonServiceModel(doc.getString("serv_id"),
                                    doc.getString("serv_name"),
                                    doc.getString("serv_desc"),
                                    doc.getString("serv_rate"),
                                    doc.getString("serv_image"),
                                    doc.getString("estId"));
                            arrayList.add(list);
                        }
                        adapter = new SalonServiceAdapter(SalonDetails.this, arrayList);
                        // setting adapter to our recycler view.
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SalonDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPromoAndDealsList() {
        fStoreRef.collection("establishments").document(estID).collection("salon-promo-and-deals")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        padArrayList.clear();

                        for(DocumentSnapshot doc : task.getResult()){
                            SalonPromoDealsModels dcPADList = new SalonPromoDealsModels(doc.getString("salonPADId"),
                                    doc.getString("salonPADName"),
                                    doc.getString("salonPADDesc"),
                                    doc.getString("salonPADStartDate"),
                                    doc.getString("salonPADEndDate"));
                            padArrayList.add(dcPADList);
                        }
                        padAdapter = new SalonPromoDealsAdapter(SalonDetails.this, padArrayList);
                        // setting adapter to our recycler view.
                        promoAndDealsRecyclerView.setAdapter(padAdapter);
                        padAdapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SalonDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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


    private void addToFavorites(){

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            estID = extra.getString("EstID");
            estName = extra.getString("EstName");
            estAdd = extra.getString("EstAddress");
            estImageUrl = extra.getString("EstImageUrl");
        }

        //Store to favorite list
        Map<String,Object> favoriteEst = new HashMap<>();
        favoriteEst.put("favEstId", autoId);
        favoriteEst.put("estId", estID);
        favoriteEst.put("custId", userId);
        favoriteEst.put("estName", estName);
        favoriteEst.put("estAddress", estAdd);
        favoriteEst.put("estImageUrl", estImageUrl);

        Toast.makeText(SalonDetails.this, estName +"added to Favorites!", Toast.LENGTH_SHORT).show();

        fStoreRef.collection("customers").document(userId).collection("favorites").document(autoId).set(favoriteEst);
    }

    private void salonList() {
        Intent intent = new Intent(getApplicationContext(), SalonEstList.class);
        startActivity(intent);
    }

}