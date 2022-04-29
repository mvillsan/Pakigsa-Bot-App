package com.example.pakigsabot.PremiumApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.pakigsabot.Profile.Profile;
import com.example.pakigsabot.R;

public class GoPremiumCA extends AppCompatActivity {

    Button getPremiumBtnGP, cancelBtnGP;
    ImageView backBtnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_premium_ca);

        //References:
        refs();

        getPremiumBtnGP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upgradePremium();
            }
        });

        cancelBtnGP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileScreen();
            }
        });

        backBtnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileScreen();
            }
        });
    }

    public void refs(){
        getPremiumBtnGP = findViewById(R.id.getPremiumBtnGP);
        cancelBtnGP = findViewById(R.id.cancelBtnGP);
        backBtnProfile = findViewById(R.id.backBtnProfile);
    }

    public void upgradePremium(){
        Intent intent = new Intent(getApplicationContext(), UpgradeScreen.class);
        startActivity(intent);
    }

    public void profileScreen(){
        Intent intent = new Intent(getApplicationContext(), Profile.class);
        startActivity(intent);
    }
}