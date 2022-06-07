package com.example.pakigsabot.CoworkingSpaceBO;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pakigsabot.CoworkingSpaceBO.CancellationPolicies.CancellationPolicyCoworkingSpace;
import com.example.pakigsabot.CoworkingSpaceBO.PromosAndDeals.PromosAndDealsCoworking;
import com.example.pakigsabot.CoworkingSpaceBO.Rules.RulesCoworkingSpace;
import com.example.pakigsabot.ServicesBO.ServicesCoworkingSpace;
import com.example.pakigsabot.R;

public class SettingUpEstablishmentCoworking extends AppCompatActivity {
    Button servicesBtn, promoAndDealsBtn, rulesBtn, cancelPolicyBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_up_establishment_coworking);

        //References
        servicesBtn = findViewById(R.id.setUpServBtn);
        promoAndDealsBtn = findViewById(R.id.setUpPromoAndDealsBtn);
        rulesBtn = findViewById(R.id.setUpRulesBtn);

        servicesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ServicesCoworkingSpace.class);
                startActivity(in);
            }
        });
        promoAndDealsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), PromosAndDealsCoworking.class);
                startActivity(in);
            }
        });

        rulesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), RulesCoworkingSpace.class);
                startActivity(in);
            }
        });
        cancelPolicyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), CancellationPolicyCoworkingSpace.class);
                startActivity(in);
            }
        });
    }
}