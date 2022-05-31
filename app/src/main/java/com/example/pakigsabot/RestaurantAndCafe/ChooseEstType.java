package com.example.pakigsabot.RestaurantAndCafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.pakigsabot.NavBar.BottomNavigation;
import com.example.pakigsabot.NavigationFragments.EstFavoritesFragment;
import com.example.pakigsabot.NavigationFragments.HelpCenterFragment;
import com.example.pakigsabot.NavigationFragments.HomeFragment;
import com.example.pakigsabot.NavigationFragments.NearbyFragment;
import com.example.pakigsabot.NavigationFragments.ReservationsFragment;
import com.example.pakigsabot.R;
import com.example.pakigsabot.SignUpRequirements.AgreementScreen;

public class ChooseEstType extends AppCompatActivity {

    //Initialization of variables::
    MeowBottomNavigation bottomNavigation;
    ImageView backBtn, imageView3, starBtn, nearbyBtn, categoryBtn, bookedBtn, seatingCapacBtn;
    TextView restoTxt, cafeTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_est_type);

        //References::
        refs();

        //Restaurant Est::
        restoTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RestaurantEstList.class);
                startActivity(intent);
            }
        });

        //Restaurant Est::
        cafeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CafeEstList.class);
                startActivity(intent);
            }
        });

        //Display Nearby::
        nearbyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nearby();
            }
        });

        //View Top Rated Resto::
        starBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topRated();
            }
        });

        //View Resto By Category::
        categoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restoCategory();
            }
        });

        //View Resto by Most Reserved/Booked::
        bookedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restoMostReserved();
            }
        });

        //Display Price Range Resto::
        seatingCapacBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restoCapac();
            }
        });

        //Back to Home Page::
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment();
            }
        });
    }

    private void refs(){
        backBtn = findViewById(R.id.backBtn);
        restoTxt = findViewById(R.id.restoTxt);
        cafeTxt = findViewById(R.id. cafeTxt);
        imageView3 = findViewById(R.id.imageView3);
        cafeTxt.setVisibility(View.GONE);
        imageView3.setVisibility(View.GONE);
        starBtn = findViewById(R.id.starBtn);
        nearbyBtn = findViewById(R.id.nearbyBtn);
        categoryBtn = findViewById(R.id.categoryBtn);
        bookedBtn = findViewById(R.id.bookedBtn);
        seatingCapacBtn = findViewById(R.id.seatingCapacBtn);
    }

    private void HomeFragment(){
        setContentView(R.layout.activity_bottom_navigation);

        //Assign variable
        bottomNavigation = findViewById(R.id.bottom_nav);

        //Add menu item
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_nearby));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_reserve));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_favorites));
        bottomNavigation.add(new MeowBottomNavigation.Model(5,R.drawable.ic_help));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                //Initialize fragment
                Fragment fragment = null;

                //Check condition
                switch (item.getId()){
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

                    case 5: //When id is 5, initialize chatbot fragment
                        fragment = new HelpCenterFragment();
                        break;
                }
                //Load fragment
                loadFragment(fragment);
            }
        });

        /*Set notification count
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

    private void nearby(){
        Intent intent = new Intent(getApplicationContext(), NearbyResto.class);
        startActivity(intent);
    }

    private void topRated(){
        Intent intent = new Intent(getApplicationContext(), TopRatedResto.class);
        startActivity(intent);
    }

    private void restoCategory(){
        Intent intent = new Intent(getApplicationContext(), CategoryResto.class);
        startActivity(intent);
    }

    private void restoMostReserved(){
        Intent intent = new Intent(getApplicationContext(), MostReservedResto.class);
        startActivity(intent);
    }

    private void restoCapac(){
        Intent intent = new Intent(getApplicationContext(), RestoSeatingCapac.class);
        startActivity(intent);
    }
}