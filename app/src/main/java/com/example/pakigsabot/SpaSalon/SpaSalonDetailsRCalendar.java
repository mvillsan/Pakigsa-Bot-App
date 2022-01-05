package com.example.pakigsabot.SpaSalon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pakigsabot.R;

public class SpaSalonDetailsRCalendar extends AppCompatActivity {

    Button reserveBtnRDSS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spa_salon_details_rcalendar);

        refs();

        reserveBtnRDSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpaSalonSelectTime();
            }
        });
    }

    public void refs(){
        reserveBtnRDSS = findViewById(R.id.reserveBtnRDSS);
    }

    private void SpaSalonSelectTime(){
        Intent intent = new Intent(getApplicationContext(), SpaSalonSelectTime.class);
        startActivity(intent);
    }
}