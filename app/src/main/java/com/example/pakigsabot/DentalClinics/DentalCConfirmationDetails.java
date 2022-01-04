package com.example.pakigsabot.DentalClinics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pakigsabot.R;

public class DentalCConfirmationDetails extends AppCompatActivity {

    Button paypalBtnRConfirmationDC, dragonPayBtnRConfirmationDC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dental_cconfirmation_details);

        refs();

        paypalBtnRConfirmationDC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmandPay();
            }
        });

        dragonPayBtnRConfirmationDC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmandPay();
            }
        });
    }

    public void refs(){
        paypalBtnRConfirmationDC = findViewById(R.id.paypalBtnRConfirmationDC);
        dragonPayBtnRConfirmationDC = findViewById(R.id.dragonPayBtnRConfirmationDC);
    }

    private void confirmandPay(){
        Intent intent = new Intent(getApplicationContext(), ConfirmandPayDC.class);
        startActivity(intent);
    }
}