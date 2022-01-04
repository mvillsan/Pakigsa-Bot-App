package com.example.pakigsabot.Resorts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pakigsabot.R;

public class ResortReservationDetails extends AppCompatActivity {

    Button submitBtnRDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resort_reservation_details);

        refs();

        submitBtnRDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resortConfirmation();
            }
        });
    }

    public void refs(){
        submitBtnRDetails = findViewById(R.id.submitBtnRDetails);
    }

    private void resortConfirmation(){
        Intent intent = new Intent(getApplicationContext(), ResortConfirmationDetails.class);
        startActivity(intent);
    }
}