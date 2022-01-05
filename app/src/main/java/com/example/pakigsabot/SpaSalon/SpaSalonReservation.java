package com.example.pakigsabot.SpaSalon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pakigsabot.DentalClinics.CheckInTimeReserveDC;
import com.example.pakigsabot.R;

public class SpaSalonReservation extends AppCompatActivity {

    Button laEsparanzaSpaSelectBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spa_salon_reservation);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("spaSalonReserve") != null){
                Toast.makeText(getApplicationContext(), "data: " + bundle.getString("spaSalonReserve"),
                        Toast.LENGTH_SHORT).show();
            }
        }

        refs();

        laEsparanzaSpaSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                laEsparanzaDetailsandRCalendar();
            }
        });
    }

    public void refs(){
        laEsparanzaSpaSelectBtn = findViewById(R.id.laEsparanzaSpaSelectBtn);
    }

    private void laEsparanzaDetailsandRCalendar(){
        Intent intent = new Intent(getApplicationContext(), SpaSalonDetailsRCalendar.class);
        startActivity(intent);
    }
}