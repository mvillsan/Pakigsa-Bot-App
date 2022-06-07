package com.example.pakigsabot.Reservations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.pakigsabot.NavigationFragments.EstFavoritesFragment;
import com.example.pakigsabot.NavigationFragments.HelpCenterFragment;
import com.example.pakigsabot.NavigationFragments.HomeFragment;
import com.example.pakigsabot.NavigationFragments.NearbyFragment;
import com.example.pakigsabot.NavigationFragments.ReservationsFragment;
import com.example.pakigsabot.R;
import com.example.pakigsabot.Reservations.Adapter.RHistCancelledAdapter;
import com.example.pakigsabot.Reservations.Adapter.RHistCompletedAdapter;
import com.example.pakigsabot.Reservations.Model.RHistCancelledModel;
import com.example.pakigsabot.Reservations.Model.RHistCompletedModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ReservationsHistory extends AppCompatActivity {

    //Initialization of Variables::
    MeowBottomNavigation bottomNavigation;
    ImageView imgBackBtn;

    // creating variables for our recycler view,
    // array list, adapter, firebase firestore,
    // and our progress bar.
    // initializing our variable for firebase
    // firestore and getting its instance.
    RecyclerView cancelledRV,completedRV;
    ArrayList<RHistCancelledModel> rCancelledArrayList;
    ArrayList<RHistCompletedModel> rCompletedArrayList;
    RHistCancelledAdapter rCancelledAdapter;
    RHistCompletedAdapter rCompletedAdapter;
    FirebaseFirestore fStore;
    FirebaseUser user;
    String userID;
    ProgressDialog progressDialog;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations_history);

        //References::
        refs();

        fStore=FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        //recyclerview initialization
        cancelledRV = findViewById(R.id.cancelledRV);
        cancelledRV.setHasFixedSize(true);
        cancelledRV.setLayoutManager(new LinearLayoutManager(this));
        // adding our array list to our recycler view adapter class.
        rCancelledArrayList = new ArrayList<>();
        //initializing the arraylist where all data is stored
        rCancelledArrayList = new ArrayList<RHistCancelledModel>();
        //initializing the adapter
        rCancelledAdapter = new RHistCancelledAdapter(ReservationsHistory.this, rCancelledArrayList);
        cancelledRV.setAdapter(rCancelledAdapter);

        //recyclerview initialization
        completedRV = findViewById(R.id.completedRV);
        completedRV.setHasFixedSize(true);
        completedRV.setLayoutManager(new LinearLayoutManager(this));
        // adding our array list to our recycler view adapter class.
        rCompletedArrayList = new ArrayList<>();
        //initializing the arraylist where all data is stored
        rCompletedArrayList = new ArrayList<RHistCompletedModel>();
        //initializing the adapter
        rCompletedAdapter = new RHistCompletedAdapter(ReservationsHistory.this, rCompletedArrayList);
        completedRV.setAdapter(rCompletedAdapter);


        // below line is use to get the data from Firebase Firestore.
        getCancelledReservations();

        // below line is use to get the data from Firebase Firestore.
        getCompletedReservations();

        //Refresh the recyclerview::
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCancelledReservations();
                getCompletedReservations();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        //Back to Reservations Fragment::
        imgBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reservationsFragment();
            }
        });
    }

    private void refs(){
        imgBackBtn = findViewById(R.id.imgBackBtn);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
    }

    private void reservationsFragment() {
        setContentView(R.layout.activity_bottom_navigation);
        //Bottom Nav
        //Assign variable
        bottomNavigation = findViewById(R.id.bottom_nav_bo);

        //Add menu item
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_nearby));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_reserve));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_favorites));
        bottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.ic_help_cust));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                //Initialize fragment
                Fragment fragment = null;

                //Check condition
                switch (item.getId()) {
                    case 1: //When id is 1, initialize nearby fragment
                        fragment = new NearbyFragment();
                        break;

                    case 2: //When id is 2, initialize reservations fragment
                        fragment = new ReservationsFragment();
                        break;

                    case 3: //When id is 3, initialize home fragment
                        fragment = new HomeFragment();
                        break;

                    case 4: //When id is 4, initialize favorites fragment
                        fragment = new EstFavoritesFragment();
                        break;

                    case 5: //When id is 5, initialize help center fragment
                        fragment = new HelpCenterFragment();
                        break;
                }
                //Load fragment
                loadFragment(fragment);
            }
        });

        /*//Set notification count
        bottomNavigation.setCount(3,"10");*/
        //Set reservations fragment initially selected
        bottomNavigation.show(2, true);

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

    //Display Cancelled Reservations List
    public void getCancelledReservations() {
        //Progress::
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        fStore.collection("cancelled-reservations")
                .whereEqualTo("cancelReserv_cust_ID", userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        rCancelledArrayList.clear();

                        for(DocumentSnapshot doc : task.getResult()){
                            RHistCancelledModel list = new RHistCancelledModel(doc.getString("cancelReserv_id"),
                                    doc.getString("cancelReserv_status"),
                                    doc.getString("cancelReserv_pax"),
                                    doc.getString("cancelReserv_name"),
                                    doc.getString("cancelReserv_dateIn"),
                                    doc.getString("cancelReserv_dateCheckout"),
                                    doc.getString("cancelReserv_timeCheckIn"),
                                    doc.getString("cancelReserv_cust_ID"),
                                    doc.getString("cancelReserv_est_ID"),
                                    doc.getString("cancelReserv_est_Name"),
                                    doc.getString("cancelReserv_est_emailAdd"),
                                    doc.getString("cancelReserv_cust_FName"),
                                    doc.getString("cancelReserv_cust_LName"),
                                    doc.getString("cancelReserv_adultPax"),
                                    doc.getString("cancelReserv_childPax"),
                                    doc.getString("cancelReserv_infantPax"),
                                    doc.getString("cancelReserv_petPax"),
                                    doc.getString("cancelReserv_fee"),
                                    doc.getString("cancelReserv_notes"),
                                    doc.getString("cancelReserv_reason"),
                                    doc.getString("cancelReserv_cancelledDate"),
                                    doc.getString("cancelReserv_transactionDate"),
                                    doc.getString("cancelReserv_cust_phoneNum"),
                                    doc.getString("cancelReserv_cust_emailAdd"));
                            rCancelledArrayList.add(list);
                        }
                        rCancelledAdapter = new RHistCancelledAdapter(ReservationsHistory.this,rCancelledArrayList);
                        // setting adapter to our recycler view.
                        cancelledRV.setAdapter(rCancelledAdapter);
                        rCancelledAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ReservationsHistory.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Display Cancelled Reservations List
    public void getCompletedReservations() {
        fStore.collection("completed-reservations")
                .whereEqualTo("completeReserv_cust_ID", userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        rCompletedArrayList.clear();

                        for(DocumentSnapshot doc : task.getResult()){
                            RHistCompletedModel list = new RHistCompletedModel(doc.getString("completeReserv_id"),
                                    doc.getString("completeReserv_status"),
                                    doc.getString("completeReserv_pax"),
                                    doc.getString("completeReserv_name"),
                                    doc.getString("completeReserv_dateIn"),
                                    doc.getString("completeReserv_dateCheckOut"),
                                    doc.getString("completeReserv_timeCheckIn"),
                                    doc.getString("completeReserv_cust_ID"),
                                    doc.getString("completeReserv_est_ID"),
                                    doc.getString("completeReserv_est_Name"),
                                    doc.getString("completeReserv_est_emailAdd"),
                                    doc.getString("completeReserv_cust_FName"),
                                    doc.getString("completeReserv_cust_LName"),
                                    doc.getString("completeReserv_adultPax"),
                                    doc.getString("completeReserv_childPax"),
                                    doc.getString("completeReserv_infantPax"),
                                    doc.getString("completeReserv_petPax"),
                                    doc.getString("completeReserv_fee"),
                                    doc.getString("completeReserv_transactionDate"),
                                    doc.getString("completeReserv_notes"));
                            rCompletedArrayList.add(list);
                        }
                        rCompletedAdapter = new RHistCompletedAdapter(ReservationsHistory.this,rCompletedArrayList);
                        // setting adapter to our recycler view.
                        completedRV.setAdapter(rCompletedAdapter);
                        rCompletedAdapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ReservationsHistory.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}