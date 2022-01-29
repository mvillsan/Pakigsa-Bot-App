package com.example.pakigsabot.Resto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.pakigsabot.R;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class ConfirmAndPayResto extends AppCompatActivity {

    Button confirmAndPayBtnResto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_and_pay_resto);

        //References:
        refs();

        confirmAndPayBtnResto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { restoSubmittedReservationSuccess(); }
        });
    }

    public void refs(){ confirmAndPayBtnResto = findViewById(R.id.confirmandPayBtnResto);
    }

    private void restoSubmittedReservationSuccess(){
        Intent intent = new Intent(getApplicationContext(), RestoReservationSuccess.class);
        startActivity(intent);
    }
}