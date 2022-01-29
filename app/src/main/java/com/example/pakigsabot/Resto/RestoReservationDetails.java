package com.example.pakigsabot.Resto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.pakigsabot.R;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class RestoReservationDetails extends AppCompatActivity {

    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resto_reservation_details);

        //References:
        refs();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmationDetails();
            }
        });
    }

    public void refs(){
        submitBtn = findViewById(R.id.submitBtnRDetailsResto);
    }

    private void confirmationDetails(){
        Intent intent = new Intent (getApplicationContext(), RestoConfirmationDetails.class);
        startActivity(intent);
    }
}