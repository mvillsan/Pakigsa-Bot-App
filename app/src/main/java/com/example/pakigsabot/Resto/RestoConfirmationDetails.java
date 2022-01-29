package com.example.pakigsabot.Resto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.pakigsabot.R;
import android.content.Intent;
import android.view.View;
import android.widget.Button;


public class RestoConfirmationDetails extends AppCompatActivity {

    Button paypalBtnRConfirmationDC, dragonPayBtnRConfirmationDC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resto_confirmation_details);

        //References:
        refs();

        paypalBtnRConfirmationDC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmAndPay();
            }
        });

        dragonPayBtnRConfirmationDC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmAndPay();
            }
        });
    }

    public void refs(){
        paypalBtnRConfirmationDC = findViewById(R.id.paypalBtnRConfirmationResto);
        dragonPayBtnRConfirmationDC = findViewById(R.id.dragonPayBtnRConfirmationResto);
    }

    private void confirmAndPay(){
        Intent intent = new Intent (getApplicationContext(), ConfirmAndPayResto.class);
        startActivity(intent);
    }
}