package com.example.pakigsabot.RestaurantAndCafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pakigsabot.R;
import com.example.pakigsabot.RestaurantAndCafe.Adapters.Cafe.CafeMenuItemsAdapter;
import com.example.pakigsabot.RestaurantAndCafe.Adapters.Cafe.CafePromoAndDealsAdapter;
import com.example.pakigsabot.RestaurantAndCafe.Models.Cafe.CafeMenuItemsModel;
import com.example.pakigsabot.RestaurantAndCafe.Models.Cafe.CafePromoAndDealsModel;
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

public class CafeDetails extends AppCompatActivity {
    //Initialization of variables
    ImageView estIcon, imgBackBtn, gmapBtn;
    Button reserveBtn, clearOrderBtn;
    ImageButton favBtn;
    TextView totalTxt,orderEdit, diningTxt;
    String total, item, listItem="", price;
    TextView label, lblPolicy, lblAddress, lblContactNum, preOrderLbl, totalLbl;
    String estID, autoId, estName, estAdd, estImageUrl, estPhoneNum, estType;

    // creating variables for our recycler view,
    // array list, adapter, firebase firestore,
    // and our progress bar.
    // initializing our variable for firebase
    // firestore and getting its instance.
    RecyclerView menuRecyclerView, promoDealsRecyclerView;
    ArrayList<CafeMenuItemsModel> menuArrayList;
    CafeMenuItemsAdapter menuAdapter;
    ArrayList<CafePromoAndDealsModel> promoDealsArrayList;
    CafePromoAndDealsAdapter promoDealsAdapter;

    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_details);

        //references
        refs();

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        autoId = UUID.randomUUID().toString();

        //Set details
        setDetails();

        //Get Promo and Deals List
        getPromoAndDealsList();

        //Get Menu List
        getMenu();

        //Menu Items recyclerview initialization
        menuRecyclerView.setHasFixedSize(true);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // adding our array list to our recycler view adapter class.
        menuArrayList = new ArrayList<>();
        //initializing the arraylist where all data is stored
        menuArrayList = new ArrayList<CafeMenuItemsModel>();
        //initializing the adapter
        menuAdapter = new CafeMenuItemsAdapter(CafeDetails.this, menuArrayList);
        menuRecyclerView.setAdapter(menuAdapter);

        //Promo and Deals recyclerview initialization
        promoDealsRecyclerView = findViewById(R.id.promoDealsCafeRV);
        promoDealsRecyclerView.setHasFixedSize(true);
        promoDealsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // adding our array list to our recycler view adapter class.
        promoDealsArrayList = new ArrayList<>();
        //initializing the arraylist where all data is stored
        promoDealsArrayList = new ArrayList<CafePromoAndDealsModel>();
        //initializing the adapter
        promoDealsAdapter = new CafePromoAndDealsAdapter(CafeDetails.this, promoDealsArrayList);
        promoDealsRecyclerView.setAdapter(promoDealsAdapter);

        //Get item from recyclerview onClick::
        // Register to receive messages.
        // We are registering an observer (mMessageReceiver) to receive Intents
        // with actions named "custom-message".
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));

        //Dining Options::
        diningTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(CafeDetails.this);
                builder.setTitle("Dining Options");
                builder.setMessage("- Indoor Dining -");

                // add a button
                builder.setPositiveButton("OK", null);

                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        //Get directions of the resort::
        gmapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });

        //Add to Favorites::
        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavorites();
            }
        });

        //Enable Pre-order::
        preOrderLbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuRecyclerView.setVisibility(View.VISIBLE);
                totalLbl.setVisibility(View.VISIBLE);
                totalTxt.setVisibility(View.VISIBLE);
                clearOrderBtn.setVisibility(View.VISIBLE);
            }
        });

        //Clear Order::
        clearOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMenu();
                orderEdit.setText("");
                listItem = "";
                totalTxt.setText("");
            }
        });

        //Reserve a table::
        reserveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CafeReserveDetails.class);
                intent.putExtra("EstID", estID);
                intent.putExtra("EstName", estName);
                intent.putExtra("EstImageUrl", estImageUrl);
                intent.putExtra("PreOrderItems", orderEdit.getText().toString());
                intent.putExtra("TotalPrice", totalTxt.getText().toString());
                startActivity(intent);
            }
        });

        //Back to Cafe Establishments List
        imgBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cafeList();
            }
        });

        lblPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(CafeDetails.this);
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
        label = findViewById(R.id.label);
        lblAddress = findViewById(R.id.lblAddress);
        lblPolicy = findViewById(R.id.policyTxt);
        favBtn = findViewById(R.id.favoriteBtn);
        reserveBtn = findViewById(R.id.reserveBtn);
        imgBackBtn = findViewById(R.id.imgBackBtn);
        gmapBtn = findViewById(R.id.gmapBtn);
        lblContactNum = findViewById(R.id.lblContactNum);
        orderEdit = findViewById(R.id.orderEdit);
        totalTxt = findViewById(R.id.totalTxt);
        totalLbl = findViewById(R.id.totalLbl);
        preOrderLbl = findViewById(R.id.preOrderLbl);
        clearOrderBtn = findViewById(R.id.clearOrder);
        menuRecyclerView = findViewById(R.id.cafeMenuRV);
        diningTxt = findViewById(R.id.diningTxt);
        menuRecyclerView.setVisibility(View.GONE);
        totalLbl.setVisibility(View.GONE);
        totalTxt.setVisibility(View.GONE);
        clearOrderBtn.setVisibility(View.GONE);
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
            estType = extra.getString("EstType");
        }

        //Display Details::
        label.setText(estName);
        lblAddress.setText(estAdd);
        lblContactNum.setText("Contact Number > " + estPhoneNum);
        Picasso.get().load(estImageUrl).into(estIcon);
    }

    private void getMenu() {
        fStore.collection("establishments").document(estID).collection("cafe-menu-items")
                .whereEqualTo("cafeFIAvail", "Available")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        menuArrayList.clear();

                        for (DocumentSnapshot doc : task.getResult()) {
                            CafeMenuItemsModel list = new CafeMenuItemsModel(doc.getString("cafeFIId"),
                                    doc.getString("cafeFIName"),
                                    doc.getString("cafeFICategory"),
                                    doc.getString("cafeFIPrice"),
                                    doc.getString("cafeFIImgUrl"),
                                    doc.getString("cafeFIAvail"),
                                    doc.getString("est_Id"),
                                    doc.getString("cafeFIQuantity"));
                            menuArrayList.add(list);
                            Log.d("tag", menuArrayList.toString());
                        }
                        menuAdapter = new CafeMenuItemsAdapter(CafeDetails.this, menuArrayList);
                        // setting adapter to our recycler view.
                        menuRecyclerView.setAdapter(menuAdapter);
                        menuAdapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CafeDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPromoAndDealsList() {
        fStore.collection("establishments").document(estID).collection("cafe-promo-and-deals")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        promoDealsArrayList.clear();

                        for(DocumentSnapshot doc : task.getResult()){
                            CafePromoAndDealsModel list = new CafePromoAndDealsModel(doc.getString("cafePADId"),
                                    doc.getString("cafePADName"),
                                    doc.getString("cafePADDesc"),
                                    doc.getString("cafePADStartDate"),
                                    doc.getString("cafePADEndDate"));
                            promoDealsArrayList.add(list);
                        }
                        promoDealsAdapter = new CafePromoAndDealsAdapter(CafeDetails.this, promoDealsArrayList);
                        // setting adapter to our recycler view.
                        promoDealsRecyclerView.setAdapter(promoDealsAdapter);
                        promoDealsAdapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CafeDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

        Toast.makeText(CafeDetails.this, estName +"added to Favorites!", Toast.LENGTH_SHORT).show();

        fStore.collection("customers").document(userId).collection("favorites").document(autoId).set(favoriteEst);
    }

    private void cafeList() {
        Intent intent = new Intent(getApplicationContext(), CafeEstList.class);
        startActivity(intent);
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            item = intent.getStringExtra("item");
            price = intent.getStringExtra("price");
            total = intent.getStringExtra("total");
            listItem = listItem + "\n" + item + " - Php " + price;
            orderEdit.setText(listItem);
            totalTxt.setText(total);
            Toast.makeText(CafeDetails.this,item +" PHP "+total,Toast.LENGTH_SHORT).show();
        }
    };
}