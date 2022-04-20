package com.example.pakigsabot.LoyaltyTier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pakigsabot.Profile.Profile;
import com.example.pakigsabot.R;

public class SeeAllTiers extends AppCompatActivity {

    TextView classicTxt, silverTxt, goldTxt, platinumTxt;
    ImageView backBtnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_tiers);

        //References::
        refs();

        classicTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classic();
            }
        });

        silverTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                silver();
            }
        });

        goldTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gold();
            }
        });

        platinumTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                platinum();
            }
        });

        backBtnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile();
            }
        });
    }

    private void refs(){
        classicTxt = findViewById(R.id.classicTxt);
        silverTxt = findViewById(R.id.silverTxt);
        goldTxt = findViewById(R.id.goldTxt);
        platinumTxt = findViewById(R.id.platinumTxt);
        backBtnProfile = findViewById(R.id.backBtnProfile);
    }

    private void classic(){
        Intent intent = new Intent(getApplicationContext(), SeeAllTiers.class);
        startActivity(intent);
    }

    private void silver(){
        Intent intent = new Intent(getApplicationContext(), SeeAllTiers2.class);
        startActivity(intent);
    }

    private void gold(){
        Intent intent = new Intent(getApplicationContext(), SeeAllTiers3.class);
        startActivity(intent);
    }

    private void platinum(){
        Intent intent = new Intent(getApplicationContext(), SeeAllTiers4.class);
        startActivity(intent);
    }

    private void profile(){
        Intent intent = new Intent(getApplicationContext(), Profile.class);
        startActivity(intent);
    }
}