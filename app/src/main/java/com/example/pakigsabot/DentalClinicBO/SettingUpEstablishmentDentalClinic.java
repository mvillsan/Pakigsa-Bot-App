package com.example.pakigsabot.DentalClinicBO;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.pakigsabot.DentalClinicBO.PromoAndDeals.PromoAndDealsDentalClinic;
import com.example.pakigsabot.EstablishmentRulesBO.DentalClinic.EstRulesDentalClinic;
import com.example.pakigsabot.NavigationFragmentsBO.HelpCenterFragment;
import com.example.pakigsabot.NavigationFragmentsBO.HistoryFragment;
import com.example.pakigsabot.NavigationFragmentsBO.HomeFragment;
import com.example.pakigsabot.NavigationFragmentsBO.ReservationsFragment;
import com.example.pakigsabot.NavigationFragmentsBO.ServicesFragment;
import com.example.pakigsabot.R;
import com.example.pakigsabot.ServicesBO.ServicesDentalClinic;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class SettingUpEstablishmentDentalClinic extends AppCompatActivity {
    //Initialization of Variables
    MeowBottomNavigation bottomNavigation;
    Button servicesBtn, promoAndDealsBtn, rulesBtn;
    ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_up_establishment_dental_clinic);

        //References
        backBtn = findViewById(R.id.backBtnSetUpEstDC);
        servicesBtn = findViewById(R.id.setUpProcBtn);
        promoAndDealsBtn = findViewById(R.id.setUpPromoAndDealsBtn);
        rulesBtn = findViewById(R.id.setUpRulesBtnDC);


        servicesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ServicesDentalClinic.class);
                startActivity(in);
            }
        });
        promoAndDealsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), PromoAndDealsDentalClinic.class);
                startActivity(in);
            }
        });

        rulesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), EstRulesDentalClinic.class);
                startActivity(in);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reservationsFragment();
            }
        });

    }

    private void reservationsFragment(){
        setContentView(R.layout.activity_bottom_navigation_bo);
        //Bottom Nav
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
                    case 1: //When id is 1, initialize nearby fragment
                        fragment = new ReservationsFragment();
                        break;

                    case 2: //When id is 2, initialize reservations fragment
                        fragment = new ServicesFragment();
                        break;

                    case 3: //When id is 3, initialize home fragment
                        fragment = new HomeFragment();
                        break;

                    case 4: //When id is 4, initialize favorites fragment
                        fragment = new HistoryFragment();
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

}