package com.example.pakigsabot.ServicesBO;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pakigsabot.CoworkingSpaceBO.Adapter.CoworkingSpaceAdapter;
import com.example.pakigsabot.CoworkingSpaceBO.AddFacilityCS;
import com.example.pakigsabot.CoworkingSpaceBO.Model.CoworkingSpaceFacilities;
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

public class ServicesCoworkingSpace extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;
    ImageView addServBtn, backBtn;
    RecyclerView coSpaceFacRecyclerView;
    ArrayList<CoworkingSpaceFacilities> coSpaceFacilitiesArrayList;
    CoworkingSpaceAdapter coSpaceAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    FirebaseFirestore fStore;
    FirebaseUser user;
    String userId;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_coworking_space);

        //References:
        refs();

        //SwipeRefreshLayout init
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);

        //recyclerview initialization
        coSpaceFacRecyclerView = findViewById(R.id.coworkSpaceRecyclerView);
        coSpaceFacRecyclerView.setHasFixedSize(true);
        coSpaceFacRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //adding array list to recyclerview adapter
        fStore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        coSpaceFacilitiesArrayList = new ArrayList<>();

        //initializing the arraylist where all data is stored
        coSpaceFacilitiesArrayList = new ArrayList<CoworkingSpaceFacilities>();

        //initializing the adapter
        coSpaceAdapter = new CoworkingSpaceAdapter(ServicesCoworkingSpace.this, coSpaceFacilitiesArrayList);
        coSpaceFacRecyclerView.setAdapter(coSpaceAdapter);

        //to get the data from Firebase Firestore
        getCoworkinsSpaceFacilities();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeFragment();
            }
        });

        addServBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFacility();
            }
        });
    }

    public void refs(){

        addServBtn = findViewById(R.id.addCSFacilityBtn);
        backBtn = findViewById(R.id.backBtnCSFacility);
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

    private void addFacility(){
        Intent intent = new Intent(getApplicationContext(), AddFacilityCS.class);
        startActivity(intent);
    }

    private void getCoworkinsSpaceFacilities() {
        //Showing progressdialog while fetching the data
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        fStore.collection("establishments").document(userId).collection("coworking-space-facilities")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        coSpaceFacilitiesArrayList.clear();

                        for(DocumentSnapshot doc : task.getResult()){
                            CoworkingSpaceFacilities facList = new CoworkingSpaceFacilities(doc.getString("fac_id"),
                                    doc.getString("fac_name"),
                                    doc.getString("fac_cap"),
                                    doc.getString("fac_desc"),
                                    doc.getString("fac_rate"),
                                    doc.getString("fac_image"));
                            coSpaceFacilitiesArrayList.add(facList);
                        }
                        coSpaceAdapter = new CoworkingSpaceAdapter(ServicesCoworkingSpace.this, coSpaceFacilitiesArrayList);
                        // setting adapter to our recycler view.
                        coSpaceFacRecyclerView.setAdapter(coSpaceAdapter);
                        coSpaceAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ServicesCoworkingSpace.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Refresh the recyclerview::
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCoworkinsSpaceFacilities();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

}