package com.example.pakigsabot.SpaBO;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pakigsabot.SpaBO.PromoAndDeals.PromoAndDealsSpa;
import com.example.pakigsabot.ServicesBO.ServicesSpa;
import com.example.pakigsabot.R;
import com.example.pakigsabot.SpaBO.Rules.RulesSpa;

public class SettingUpEstablishmentSpa extends AppCompatActivity {
    Button servicesBtn, promoAndDealsBtn, rulesBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_up_establishment_spa);

        //References
        servicesBtn = findViewById(R.id.setUpServBtn);
        promoAndDealsBtn = findViewById(R.id.setUpPromoAndDealsBtn);
        rulesBtn = findViewById(R.id.setUpRulesBtn);


        servicesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ServicesSpa.class);
                startActivity(in);
            }
        });
        promoAndDealsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), PromoAndDealsSpa.class);
                startActivity(in);
            }
        });
        rulesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), RulesSpa.class);
                startActivity(in);
            }
        });

    }
}