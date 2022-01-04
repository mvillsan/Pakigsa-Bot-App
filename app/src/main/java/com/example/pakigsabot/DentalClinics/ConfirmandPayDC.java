package com.example.pakigsabot.DentalClinics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pakigsabot.R;
import com.example.pakigsabot.Resorts.CheckInTimeReserve2;

public class ConfirmandPayDC extends AppCompatActivity {

    Button confirmandPayBtnDC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmand_pay_dc);

        refs();

        confirmandPayBtnDC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dentalClinicSubmittedReservationSuccess();
            }
        });
    }

    public void refs(){
        confirmandPayBtnDC = findViewById(R.id.confirmandPayBtnDC);
    }

    private void dentalClinicSubmittedReservationSuccess(){
        Intent intent = new Intent(getApplicationContext(), DentalClinicReservationSuccess.class);
        startActivity(intent);
    }
}