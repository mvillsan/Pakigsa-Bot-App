package com.example.pakigsabot.SpaSalon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pakigsabot.R;

public class SpaSalonConfirmAndPay extends AppCompatActivity {

    Button confirmandPayBtnSS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spa_salon_confirm_and_pay);

        refs();

        confirmandPayBtnSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spaSalonSubmittedReservationSuccess();
            }
        });
    }

    public void refs(){
        confirmandPayBtnSS = findViewById(R.id.confirmandPayBtnSS);
    }

    private void spaSalonSubmittedReservationSuccess(){
        Intent intent = new Intent(getApplicationContext(), SpaSalonReservationSuccess.class);
        startActivity(intent);
    }
}