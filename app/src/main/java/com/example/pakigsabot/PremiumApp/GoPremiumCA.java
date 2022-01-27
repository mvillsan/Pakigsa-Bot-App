package com.example.pakigsabot.PremiumApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pakigsabot.R;

public class GoPremiumCA extends AppCompatActivity {

    Button getPremiumBtnGP;

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
    }

    public void refs(){
        getPremiumBtnGP = findViewById(R.id.getPremiumBtnGP);
    }

    public void upgradePremium(){
        Intent intent = new Intent(getApplicationContext(), UpgradeScreen.class);
        startActivity(intent);
    }
}