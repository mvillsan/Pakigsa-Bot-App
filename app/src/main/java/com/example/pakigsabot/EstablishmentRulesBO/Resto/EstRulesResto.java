package com.example.pakigsabot.EstablishmentRulesBO.Resto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pakigsabot.R;
import com.example.pakigsabot.RestaurantBO.SettingUpEstablishmentRestaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class EstRulesResto extends AppCompatActivity {
    ImageView backBtn, addBtn;
    RecyclerView estRulesRecyclerView;
    ArrayList<RestoRulesModel> restoERArrayList;
    RestoRulesAdapter restoERAdapter;
    FirebaseFirestore fStore;
    FirebaseUser user;
    String userId;
    ProgressDialog progressDialog;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_est_rules_resto);

        //References
        refs();

        //recyclerview initialization
        estRulesRecyclerView = findViewById(R.id.estRulesRecyclerView);
        estRulesRecyclerView.setHasFixedSize(true);
        estRulesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //adding array list to recyclerview adapter
        fStore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        restoERArrayList = new ArrayList<>();

        //initializing the arraylist where all data is stored
        restoERArrayList = new ArrayList<RestoRulesModel>();

        //initializing the adapter
        restoERAdapter = new RestoRulesAdapter(EstRulesResto.this, restoERArrayList);
        estRulesRecyclerView.setAdapter(restoERAdapter);

        //to get the data from Firebase Firestore
        getEstRules();

        //Refresh the recyclerview::
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getEstRules();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddEstRulesResto.class);
                startActivity(intent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingUpEstablishmentRestaurant.class);
                startActivity(intent);
            }
        });
    }

    private void refs() {
        backBtn = findViewById(R.id.backBtnRestoER);
        addBtn = findViewById(R.id.addERBtn);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
    }

    private void getEstRules() {
        //Showing progressdialog while fetching the data
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        fStore.collection("establishments").document(userId).collection("resto-est-rules")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        restoERArrayList.clear();

                        for(DocumentSnapshot doc : task.getResult()){
                            RestoRulesModel restoERList = new RestoRulesModel(doc.getString("resto_ruleId"),
                                    doc.getString("resto_desc"),
                                    doc.getString("estId"));
                            restoERArrayList.add(restoERList);
                        }
                        restoERAdapter = new RestoRulesAdapter(EstRulesResto.this, restoERArrayList);
                        // setting adapter to our recycler view.
                        estRulesRecyclerView.setAdapter(restoERAdapter);
                        restoERAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EstRulesResto.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}