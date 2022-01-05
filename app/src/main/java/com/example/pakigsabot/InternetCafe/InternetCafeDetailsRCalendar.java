package com.example.pakigsabot.InternetCafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pakigsabot.R;

public class InternetCafeDetailsRCalendar extends AppCompatActivity {

    Button reserveBtnRDIC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_cafe_details_rcalendar);

        refs();

        reserveBtnRDIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                internetCafeSelectTime();
            }
        });
    }

    public void refs(){
        reserveBtnRDIC = findViewById(R.id.reserveBtnRDIC);
    }

    private void internetCafeSelectTime(){
        Intent intent = new Intent(getApplicationContext(), InternetCafeSelectTime.class);
        startActivity(intent);
    }
}