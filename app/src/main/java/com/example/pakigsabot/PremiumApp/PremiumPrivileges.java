package com.example.pakigsabot.PremiumApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pakigsabot.Profile.Profile;
import com.example.pakigsabot.R;

public class PremiumPrivileges extends AppCompatActivity {

    ImageView backBtnProfile;
    TextView termsTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium_privileges);

        //References::
        refs();

        termsTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                terms();
            }
        });

        backBtnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileScreen();
            }
        });
    }

    private void refs(){
        backBtnProfile = findViewById(R.id.backBtnProfile);
        termsTxt = findViewById(R.id.termsTxt);
    }

    private void terms(){
        Intent intent = new Intent(getApplicationContext(), SubscriptionTerms.class);
        startActivity(intent);
    }

    private void profileScreen(){
        Intent intent = new Intent(getApplicationContext(), Profile.class);
        startActivity(intent);
    }
}