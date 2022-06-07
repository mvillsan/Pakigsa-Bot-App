package com.example.pakigsabot.SpaBO.Rules;

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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pakigsabot.NavigationFragmentsBO.HistoryFragment;
import com.example.pakigsabot.NavigationFragmentsBO.HomeFragment;
import com.example.pakigsabot.NavigationFragmentsBO.ReservationsFragment;
import com.example.pakigsabot.NavigationFragmentsBO.ServicesFragment;
import com.example.pakigsabot.R;
import com.example.pakigsabot.SpaBO.AddRulesSpa;
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

public class RulesSpa extends AppCompatActivity {
    MeowBottomNavigation bottomNavigation;
    ImageView addRuleBtn, backRuleBtn;
    RecyclerView rulesRecyclerView;
    ArrayList<SpaRulesModel> spaRulesArrayList;
    SpaRulesAdapter spaRulesAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    FirebaseFirestore fStore;
    FirebaseUser user;
    String userId;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_spa);

        References:
        refs();

        //SwipeRefreshLayout init
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);

        //recyclerview initialization
        rulesRecyclerView = findViewById(R.id.rulesRecyclerView);
        rulesRecyclerView.setHasFixedSize(true);
        rulesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //adding array list to recyclerview adapter
        fStore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        spaRulesArrayList = new ArrayList<>();

        //initializing the arraylist where all data is stored
        spaRulesArrayList = new ArrayList<SpaRulesModel>();

        //initializing the adapter
        spaRulesAdapter = new SpaRulesAdapter(RulesSpa.this, spaRulesArrayList);
        rulesRecyclerView.setAdapter(spaRulesAdapter);

        //to get the data from Firebase Firestore
        getSpaRules();

        backRuleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeFragment();
            }
        });

        addRuleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRules();
            }
        });
    }

    private void refs() {
        addRuleBtn = findViewById(R.id.addSpaRuleBtn);
        backRuleBtn = findViewById(R.id.backBtnSpaRules);
    }
    private void homeFragment() {
        setContentView(R.layout.activity_bottom_navigation);

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

                    /*case 5: //When id is 5, initialize reservation chatbot fragment
                        fragment = new HelpCenter();
                        break;*/
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

    private void addRules(){
        Intent intent = new Intent(getApplicationContext(), AddRulesSpa.class);
        startActivity(intent);
    }

    private void getSpaRules() {
        //Showing progressdialog while fetching the data
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        fStore.collection("establishments").document(userId).collection("spa-rules")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        spaRulesArrayList.clear();

                        for(DocumentSnapshot doc : task.getResult()){
                            SpaRulesModel spaRulesList = new SpaRulesModel(doc.getString("rule_id"),
                                    doc.getString("rule_desc"));
                            spaRulesArrayList.add(spaRulesList);
                        }

                        spaRulesAdapter = new SpaRulesAdapter(RulesSpa.this, spaRulesArrayList);
                        // setting adapter to our recycler view.
                        rulesRecyclerView.setAdapter(spaRulesAdapter);
                        spaRulesAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RulesSpa.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Refresh the recyclerview::
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSpaRules();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

}