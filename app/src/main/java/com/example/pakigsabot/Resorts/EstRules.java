package com.example.pakigsabot.Resorts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import com.example.pakigsabot.Resorts.Adapters.ResortAdapter;
import com.example.pakigsabot.Resorts.Adapters.RulesAdapter;
import com.example.pakigsabot.Resorts.Models.ResortModel;
import com.example.pakigsabot.Resorts.Models.RulesModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EstRules extends AppCompatActivity {

    ImageView imgBackBtn;
    // creating variables for our recycler view,
    // array list, adapter, firebase firestore,
    // and our progress bar.
    // initializing our variable for firebase
    // firestore and getting its instance.
    RecyclerView rulesRV;
    ArrayList<RulesModel> rulesArrayList;
    RulesAdapter rulesAdapter;
    FirebaseFirestore fStore;
    FirebaseUser user;
    String estID;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_est_rules);

        //References::
        refs();

        //Get est id::
        getEst();

        //recyclerview initialization
        rulesRV = findViewById(R.id.recyclerView);
        rulesRV.setHasFixedSize(true);
        rulesRV.setLayoutManager(new LinearLayoutManager(this));

        // adding our array list to our recycler view adapter class.
        fStore=FirebaseFirestore.getInstance();
        rulesArrayList = new ArrayList<>();

        //initializing the arraylist where all data is stored
        rulesArrayList = new ArrayList<RulesModel>();

        //initializing the adapter
        rulesAdapter = new RulesAdapter(EstRules.this, rulesArrayList);

        rulesRV.setAdapter(rulesAdapter);

        // below line is use to get the data from Firebase Firestore.
        getResortRulesList();

        imgBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                estList();
            }
        });

    }

    private void refs(){
        imgBackBtn = findViewById(R.id.imgBackBtn);
    }

    private void estList(){
        Intent intent = new Intent(getApplicationContext(), ResortDetails.class);
        startActivity(intent);
    }

    //Display Resort Rules List
    private void getResortRulesList(){
        //Progress::
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        fStore.collection("establishments").document(estID).collection("resort-rules")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        rulesArrayList.clear();

                        for(DocumentSnapshot doc : task.getResult()){
                            RulesModel list = new RulesModel(doc.getString("resort_ruleID"),
                                    doc.getString("resort_desc"));
                            rulesArrayList.add(list);
                        }
                        rulesAdapter = new RulesAdapter(EstRules.this,rulesArrayList);
                        // setting adapter to our recycler view.
                        rulesRV.setAdapter(rulesAdapter);
                        rulesAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EstRules.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getEst() {
        //Getting data from recyclerview::
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            estID = extra.getString("EstID");
        }
    }
}