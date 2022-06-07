package com.example.pakigsabot.ResortBO;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.pakigsabot.NavigationFragmentsBO.HelpCenterFragment;
import com.example.pakigsabot.NavigationFragmentsBO.HistoryFragment;
import com.example.pakigsabot.NavigationFragmentsBO.HomeFragment;
import com.example.pakigsabot.NavigationFragmentsBO.ReservationsFragment;
import com.example.pakigsabot.NavigationFragmentsBO.ServicesFragment;
import com.example.pakigsabot.R;
import com.example.pakigsabot.ServicesBO.ServicesResort;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class SettingUpEstablishmentResort extends AppCompatActivity {
    MeowBottomNavigation bottomNavigation;
    Button promoAndDealsBtn, roomBtn, facilitiesBtn, rulesPolicyBtn, cancellationPolicyBtn;
    ImageView backBtnSetUpEst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_up_establishment_resort);

        //References::
        refs();

        roomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ServicesResort.class);
                startActivity(in);
            }
        });

        facilitiesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ResortFacilities.class);
                startActivity(in);
            }
        });

        rulesPolicyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resortRules();
            }
        });

        cancellationPolicyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resortCancellations();
            }
        });

        promoAndDealsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), PromoAndDealsResort.class);
                startActivity(in);
            }
        });

        backBtnSetUpEst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                servicesFragment();
            }
        });
    }

    public void refs(){
        roomBtn = findViewById(R.id.roomsBtn);
        facilitiesBtn = findViewById(R.id.facilitiesBtn);
        promoAndDealsBtn = findViewById(R.id.setUpPromoAndDealsBtn);
        backBtnSetUpEst = findViewById(R.id.backBtnSetUpEst);
        cancellationPolicyBtn = findViewById(R.id.cancellationPolicyBtn);
        rulesPolicyBtn = findViewById(R.id.rulesPolicyBtn);
    }

    private void servicesFragment() {
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
        //Set service fragment initially selected
        bottomNavigation.show(2,true);

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

    private void resortRules(){
        Intent intent = new Intent(getApplicationContext(), ResortRules.class);
        startActivity(intent);
    }

    private void resortCancellations(){
        Intent intent = new Intent(getApplicationContext(), ResortCancellationPol.class);
        startActivity(intent);
    }
}