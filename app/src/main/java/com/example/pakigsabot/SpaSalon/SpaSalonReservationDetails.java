package com.example.pakigsabot.SpaSalon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pakigsabot.R;

public class SpaSalonReservationDetails extends AppCompatActivity {

    Button submitBtnRDetailsSS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spa_salon_reservation_details);

        refs();

        submitBtnRDetailsSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spaSalonConfirmationDetails();
            }
        });
    }

    public void refs(){
        submitBtnRDetailsSS = findViewById(R.id.submitBtnRDetailsSS);
    }

    public void spaSalonConfirmationDetails(){
        Intent intent = new Intent(getApplicationContext(), SpaSalonConfirmationDetails.class);
        startActivity(intent);
    }
}