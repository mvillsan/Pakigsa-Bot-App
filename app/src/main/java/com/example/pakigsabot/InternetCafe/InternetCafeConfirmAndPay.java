package com.example.pakigsabot.InternetCafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pakigsabot.R;

public class InternetCafeConfirmAndPay extends AppCompatActivity {

    Button confirmandPayBtnIC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_cafe_confirm_and_pay);

        refs();

        confirmandPayBtnIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iCafeSubmittedReservationSuccessfully();
            }
        });
    }

    public void refs(){
        confirmandPayBtnIC = findViewById(R.id.confirmandPayBtnIC);
    }

    private void iCafeSubmittedReservationSuccessfully(){
        Intent intent = new Intent(getApplicationContext(), InternetCafeReservationSuccess.class);
        startActivity(intent);
    }

}