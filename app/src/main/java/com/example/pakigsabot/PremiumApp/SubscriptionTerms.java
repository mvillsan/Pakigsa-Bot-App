package com.example.pakigsabot.PremiumApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pakigsabot.R;

public class SubscriptionTerms extends AppCompatActivity {

    Button prevUpgradeScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_terms);

        //References::
        refs();

        prevUpgradeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goPremium();
            }
        });
    }

    public void refs(){
        prevUpgradeScreen = findViewById(R.id.goPremiumBtn);
    }

    //GoPremium
    public void goPremium(){
        Intent intent = new Intent(getApplicationContext(), UpgradeScreen.class);
        startActivity(intent);
    }
}