package com.example.pakigsabot.InternetCafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pakigsabot.R;

public class InternetCafeCDetails extends AppCompatActivity {

    Button paypalBtnRConfirmationIC, dragonPayBtnRConfirmationIC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_cafe_cdetails);

        refs();

        paypalBtnRConfirmationIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                internetCafeConfirmAndPay();
            }
        });

        dragonPayBtnRConfirmationIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                internetCafeConfirmAndPay();
            }
        });
    }

    public void refs(){
        paypalBtnRConfirmationIC = findViewById(R.id.paypalBtnRConfirmationIC);
        dragonPayBtnRConfirmationIC = findViewById(R.id.dragonPayBtnRConfirmationIC);
    }

    private void internetCafeConfirmAndPay(){
        Intent intent = new Intent(getApplicationContext(), InternetCafeConfirmAndPay.class);
        startActivity(intent);
    }


}