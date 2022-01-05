package com.example.pakigsabot.SpaSalon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pakigsabot.R;

public class SpaSalonConfirmationDetails extends AppCompatActivity {

    Button paypalBtnRConfirmationSS, dragonPayBtnRConfirmationSS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spa_salon_confirmation_details);

        refs();

        paypalBtnRConfirmationSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spaSalonConfirmandPay();
            }
        });

        dragonPayBtnRConfirmationSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spaSalonConfirmandPay();
            }
        });
    }

    public void refs(){
        paypalBtnRConfirmationSS = findViewById(R.id.paypalBtnRConfirmationSS);
        dragonPayBtnRConfirmationSS = findViewById(R.id.dragonPayBtnRConfirmationSS);
    }

    private void spaSalonConfirmandPay(){
        Intent intent = new Intent(getApplicationContext(), SpaSalonConfirmAndPay.class);
        startActivity(intent);
    }
}