package com.example.pakigsabot.RestaurantAndCafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.pakigsabot.R;

public class FilterAct extends AppCompatActivity {

    ImageView starBtn, nearbyBtn, categoryBtn, bookedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        //References::
        refs();

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
    }

    private void refs(){
        starBtn = findViewById(R.id.starBtn);
        nearbyBtn = findViewById(R.id.nearbyBtn);
        categoryBtn = findViewById(R.id.categoryBtn);
        bookedBtn = findViewById(R.id.bookedBtn);
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
}