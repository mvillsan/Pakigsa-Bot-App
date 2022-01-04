package com.example.pakigsabot.DentalClinics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pakigsabot.R;
import com.example.pakigsabot.Resorts.ResortDetailsRCalendar;

public class DentalClinicReservation extends AppCompatActivity {

    Button selectSmileSpecialistBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dental_clinic_reservation);

        refs();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("dentalClinicReserve") != null){
                Toast.makeText(getApplicationContext(), "data: " + bundle.getString("dentalClinicReserve"),
                        Toast.LENGTH_SHORT).show();
            }
        }

        selectSmileSpecialistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smileSpecialistScreen();
            }
        });
    }

    public void refs(){
        selectSmileSpecialistBtn = findViewById(R.id.selectSmileSpecialistBtn);
    }

    private void smileSpecialistScreen(){
        Intent intent = new Intent(getApplicationContext(), DentalDetailsRCalendar.class);
        startActivity(intent);
    }
}