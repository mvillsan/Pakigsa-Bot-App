package com.example.pakigsabot.Resorts;

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
import com.example.pakigsabot.Resorts.Adapters.CancellationAdapter;
import com.example.pakigsabot.Resorts.Models.CancellationModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class EstCancellationPolicy extends AppCompatActivity {
    //Initialize Variables
    ImageView backBtn, addCancelPol;

    // creating variables for our recycler view,
    // array list, adapter, firebase firestore,
    // and our progress bar.
    // initializing our variable for firebase
    // firestore and getting its instance.
    RecyclerView cancellationPolRV;
    ArrayList<CancellationModel> canpolArrayList;
    CancellationAdapter canpolAdapter;
    FirebaseFirestore fStore;
    FirebaseUser user;
    String estID;
    ProgressDialog progressDialog;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_est_cancellation_policy);

        //References:
        refs();

        //Get est id::
        getEst();

        //recyclerview initialization
        cancellationPolRV = findViewById(R.id.cancellationPolRV);
        cancellationPolRV.setHasFixedSize(true);
        cancellationPolRV.setLayoutManager(new LinearLayoutManager(this));

        // adding our array list to our recycler view adapter class.
        fStore=FirebaseFirestore.getInstance();
        canpolArrayList = new ArrayList<>();

        //initializing the arraylist where all data is stored
        canpolArrayList = new ArrayList<CancellationModel>();

        //initializing the adapter
        canpolAdapter = new CancellationAdapter(EstCancellationPolicy.this, canpolArrayList);

        cancellationPolRV.setAdapter(canpolAdapter);

        // below line is use to get the data from Firebase Firestore.
        getResortPolicy();

        //Refresh the recyclerview::
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getResortPolicy();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                estList();
            }
        });
    }

    private void refs(){
        backBtn = findViewById(R.id.backBtn);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
    }

    private void estList(){
        Intent intent = new Intent(getApplicationContext(), ResortDetails.class);
        startActivity(intent);
    }

    //Display Resort Rules List
    private void getResortPolicy(){
        //Progress::
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        fStore.collection("establishments").document(estID).collection("resort-cancellation-pol")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        canpolArrayList.clear();

                        for(DocumentSnapshot doc : task.getResult()){
                            CancellationModel list = new CancellationModel(doc.getString("resort_polID"),
                                    doc.getString("resort_desc"));
                            canpolArrayList.add(list);
                        }
                        canpolAdapter = new CancellationAdapter(EstCancellationPolicy.this,canpolArrayList);
                        // setting adapter to our recycler view.
                        cancellationPolRV.setAdapter(canpolAdapter);
                        canpolAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EstCancellationPolicy.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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