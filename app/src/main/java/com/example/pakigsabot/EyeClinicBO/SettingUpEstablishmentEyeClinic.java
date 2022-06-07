package com.example.pakigsabot.EyeClinicBO;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pakigsabot.EyeClinicBO.PromoAndDeals.PromoAndDealsEyeClinic;
import com.example.pakigsabot.R;
import com.example.pakigsabot.ServicesBO.ServicesEyeClinic;

public class SettingUpEstablishmentEyeClinic extends AppCompatActivity {
    Button servicesBtn, promoAndDealsBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_up_establishment_eye_clinic);

        //References
        servicesBtn = findViewById(R.id.setUpProcBtn);
        promoAndDealsBtn = findViewById(R.id.setUpPromoAndDealsBtn);

        servicesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ServicesEyeClinic.class);
                startActivity(in);
            }
        });
        promoAndDealsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), PromoAndDealsEyeClinic.class);
                startActivity(in);
            }
        });
    }
}