package com.example.pakigsabot.DentalClinics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pakigsabot.R;

public class CheckInTimeReserveDC extends AppCompatActivity {

    Button am89Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_time_reserve_dc);

        refs();

        am89Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dentalCReservationDetails();
            }
        });
    }

    public void refs(){
        am89Btn = findViewById(R.id.am89Btn);
    }

    private void dentalCReservationDetails(){
        Intent intent = new Intent(getApplicationContext(), DentalCReservationDetails.class);
        startActivity(intent);
    }
}