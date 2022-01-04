package com.example.pakigsabot.Resorts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pakigsabot.R;

public class ResortDetailsRCalendar extends AppCompatActivity {

    Button reserveBtn;
    TextView roomsDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resort_details_rcalendar);

        refs();

        reserveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInDialog();
            }
        });Intent intent = new Intent(getApplicationContext(), CheckInTimeReserve2.class);
        startActivity(intent);

        roomsDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomsDetailsSpecific();
            }
        });
    }

    public void refs(){
        reserveBtn = findViewById(R.id.reserveBtnRDRC);
        roomsDetails = findViewById(R.id.roomsTxt);
    }

    private void checkInDialog(){
        Intent intent = new Intent(getApplicationContext(), CheckInTimeReserve2.class);
        startActivity(intent);
    }

    private void roomsDetailsSpecific(){
        Intent intent = new Intent(getApplicationContext(), ResortSpecificDetails.class);
        startActivity(intent);
    }
}