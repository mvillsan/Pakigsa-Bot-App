package com.example.pakigsabot.ServicesBO;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pakigsabot.EyeClinicBO.AddServiceEyeClinic;
import com.example.pakigsabot.EyeClinicBO.Adapter.EyeClinicAdapter;
import com.example.pakigsabot.EyeClinicBO.Model.EyeClinicProcedures;
import com.example.pakigsabot.NavigationFragmentsBO.HelpCenterFragment;
import com.example.pakigsabot.NavigationFragmentsBO.HistoryFragment;
import com.example.pakigsabot.NavigationFragmentsBO.HomeFragment;
import com.example.pakigsabot.NavigationFragmentsBO.ReservationsFragment;
import com.example.pakigsabot.NavigationFragmentsBO.ServicesFragment;
import com.example.pakigsabot.R;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ServicesEyeClinic extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;
    ImageView addProcBtn, backBtn;
    RecyclerView opticalPRRecyclerView;
    ArrayList<EyeClinicProcedures> opticalProceduresArrayList;
    EyeClinicAdapter eyeClinicAdapter;
    FirebaseFirestore fStore;
    FirebaseUser user;
    String userId;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_eye_clinic);

        //References:
        refs();

        //recyclerview initialization
        opticalPRRecyclerView = findViewById(R.id.opticalRecyclerView);
        opticalPRRecyclerView.setHasFixedSize(true);
        opticalPRRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //adding array list to recyclerview adapter
        fStore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        opticalProceduresArrayList = new ArrayList<>();

        //initializing the arraylist where all data is stored
        opticalProceduresArrayList = new ArrayList<EyeClinicProcedures>();

        //initializing the adapter
        eyeClinicAdapter = new EyeClinicAdapter(ServicesEyeClinic.this, opticalProceduresArrayList);
        opticalPRRecyclerView.setAdapter(eyeClinicAdapter);

        //to get the data from Firebase Firestore
        getDentalProcedures();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeFragment();
            }
        });

        addProcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProcedure();
            }
        });
    }

    public void refs(){

        addProcBtn = findViewById(R.id.addProcBtnServices);
        backBtn = findViewById(R.id.backBtnOpticalServices);
    }

    private void homeFragment() {
        setContentView(R.layout.activity_bottom_navigation_bo);

        //Assign variable
        bottomNavigation = findViewById(R.id.bottom_nav_bo);

        //Add menu item
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_reserve_bo));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_services_bo));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_baseline_home_24_bo));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_history_bo));
        bottomNavigation.add(new MeowBottomNavigation.Model(5,R.drawable.ic_help));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                //Initialize fragment
                Fragment fragment = null;

                //Check condition
                switch (item.getId()){
                    case 1: //When id is 1, initialize reservations fragment
                        fragment = new ReservationsFragment();
                        break;

                    case 2: //When id is 2, initialize services fragment
                        fragment = new ServicesFragment();
                        break;

                    case 3: //When id is 3, initialize home fragment
                        fragment = new HomeFragment();
                        break;

                    case 4: //When id is 4, initialize reservations history fragment
                        fragment = new HistoryFragment();
                        break;

                    case 5: //When id is 5, initialize reservation chatbot fragment
                        fragment = new HelpCenterFragment();
                        break;
                }
                //Load fragment
                loadFragment(fragment);
            }
        });

        /*//Set notification count
        bottomNavigation.setCount(3,"10");*/
        //Set home fragment initially selected
        bottomNavigation.show(3,true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        //Replace fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,fragment)
                .commit();
    }

    private void addProcedure(){
        Intent intent = new Intent(getApplicationContext(), AddServiceEyeClinic.class);
        startActivity(intent);
    }

    private void getDentalProcedures() {
        //Showing progressdialog while fetching the data
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        fStore.collection("establishments").document(userId).collection("optical-procedures")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        opticalProceduresArrayList.clear();

                        for(DocumentSnapshot doc : task.getResult()){
                            EyeClinicProcedures opList = new EyeClinicProcedures(doc.getString("opticalPRId"),
                                    doc.getString("opticalPRName"),
                                    doc.getString("opticalPRDesc"),
                                    doc.getString("opticalPRRate"),
                                    doc.getString("opticalPRImgUrl"));
                            opticalProceduresArrayList.add(opList);
                        }
                        eyeClinicAdapter = new EyeClinicAdapter(ServicesEyeClinic.this, opticalProceduresArrayList);
                        // setting adapter to our recycler view.
                        opticalPRRecyclerView.setAdapter(eyeClinicAdapter);
                        eyeClinicAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ServicesEyeClinic.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}