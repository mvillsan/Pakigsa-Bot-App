package com.example.pakigsabot.RestaurantBO;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.pakigsabot.R;
import com.example.pakigsabot.ServicesBO.InputLocation;

public class InEstLocationOrNot extends AppCompatActivity {
    Button yesBtn, noBtn;
    ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_est_location_or_not);

        //References
        refs();

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), PlotLocationResto.class);
                startActivity(in);
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), InputLocation.class);
                startActivity(in);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), SettingUpEstablishmentRestaurant.class);
                startActivity(in);
            }
        });
    }

    private void refs() {
        yesBtn = findViewById(R.id.yesEstLocBtn);
        noBtn = findViewById(R.id.noEstLocBtn);
        backBtn = findViewById(R.id.backBtnSetUpEstResto);
    }


}