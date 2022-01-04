package com.example.pakigsabot.DentalClinics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pakigsabot.R;

public class DentalDetailsRCalendar extends AppCompatActivity {

    Button reserveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dental_details_rcalendar);

        refs();

        reserveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTime();
            }
        });
    }

    public void refs(){
        reserveBtn = findViewById(R.id.reserveBtnRDDC);
    }

    private void selectTime(){
        Intent intent = new Intent(getApplicationContext(), CheckInTimeReserveDC.class);
        startActivity(intent);
    }
}