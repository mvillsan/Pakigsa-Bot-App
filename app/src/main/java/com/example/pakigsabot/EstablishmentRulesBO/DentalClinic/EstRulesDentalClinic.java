package com.example.pakigsabot.EstablishmentRulesBO.DentalClinic;

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

import com.example.pakigsabot.DentalClinicBO.SettingUpEstablishmentDentalClinic;
import com.example.pakigsabot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class EstRulesDentalClinic extends AppCompatActivity {
    ImageView backBtn, addBtn;
    RecyclerView estRulesRecyclerView;
    ArrayList<DentalRulesModel> dentalERArrayList;
    DentalRulesAdapter dentalERAdapter;
    FirebaseFirestore fStore;
    FirebaseUser user;
    String userId;
    ProgressDialog progressDialog;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_est_rules_dental_clinic);

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
        dentalERArrayList = new ArrayList<>();

        //initializing the arraylist where all data is stored
        dentalERArrayList = new ArrayList<DentalRulesModel>();

        //initializing the adapter
        dentalERAdapter = new DentalRulesAdapter(EstRulesDentalClinic.this, dentalERArrayList);
        estRulesRecyclerView.setAdapter(dentalERAdapter);

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
                Intent intent = new Intent(getApplicationContext(), AddEstRulesDentalClinic.class);
                startActivity(intent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingUpEstablishmentDentalClinic.class);
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

        fStore.collection("establishments").document(userId).collection("dental-est-rules")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        dentalERArrayList.clear();

                        for(DocumentSnapshot doc : task.getResult()){
                            DentalRulesModel restoERList = new DentalRulesModel(doc.getString("dental_ruleId"),
                                    doc.getString("dental_desc"),
                                    doc.getString("estId"));
                            dentalERArrayList.add(restoERList);
                        }
                        dentalERAdapter = new DentalRulesAdapter(EstRulesDentalClinic.this, dentalERArrayList);
                        // setting adapter to our recycler view.
                        estRulesRecyclerView.setAdapter(dentalERAdapter);
                        dentalERAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EstRulesDentalClinic.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}