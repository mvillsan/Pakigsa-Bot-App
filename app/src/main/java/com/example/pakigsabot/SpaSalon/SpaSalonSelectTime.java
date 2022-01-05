package com.example.pakigsabot.SpaSalon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pakigsabot.DentalClinics.DentalCReservationDetails;
import com.example.pakigsabot.R;

public class SpaSalonSelectTime extends AppCompatActivity {

    Button am89BtnSpaSalon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spa_salon_select_time);

        refs();

        am89BtnSpaSalon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spaSalonReservationDetails();
            }
        });
    }

    public void refs(){
        am89BtnSpaSalon = findViewById(R.id.am89BtnSpaSalon);
    }

    private void spaSalonReservationDetails(){
        Intent intent = new Intent(getApplicationContext(), SpaSalonReservationDetails.class);
        startActivity(intent);
    }
}