package com.example.pakigsabot.Resorts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.pakigsabot.R;

public class ResortReservation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resort_reservation);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("resortsReserve") != null){
                Toast.makeText(getApplicationContext(), "data: " + bundle.getString("resortsReserve"),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}