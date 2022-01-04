package com.example.pakigsabot.DentalClinics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pakigsabot.R;
public class DentalCReservationDetails extends AppCompatActivity {

    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dental_creservation_details);

        refs();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmationDetails();
            }
        });
    }

    public void refs(){
        submitBtn = findViewById(R.id.submitBtnRDetailsDC);
    }

    private void confirmationDetails(){
        Intent intent = new Intent(getApplicationContext(), DentalCConfirmationDetails.class);
        startActivity(intent);
    }
}