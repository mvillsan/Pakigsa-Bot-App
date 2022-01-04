package com.example.pakigsabot.Resorts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pakigsabot.R;

public class ResortConfirmationDetails extends AppCompatActivity {

    Button paypalBtnRConfirmation, dragonPayBtnRConfirmation, directBtnRConfirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resort_confirmation_details);

        refs();

        paypalBtnRConfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmandPayDialog();
            }
        });

        dragonPayBtnRConfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmandPayDialog();
            }
        });

        directBtnRConfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmandPayDialog();
            }
        });
    }

    public void refs(){
        paypalBtnRConfirmation = findViewById(R.id.paypalBtnRConfirmation);
        dragonPayBtnRConfirmation = findViewById(R.id.dragonPayBtnRConfirmation);
        directBtnRConfirmation = findViewById(R.id.directBtnRConfirmation);
    }

    private void confirmandPayDialog(){
        Intent intent = new Intent(getApplicationContext(), ConfirmandPay.class);
        startActivity(intent);
    }
}

