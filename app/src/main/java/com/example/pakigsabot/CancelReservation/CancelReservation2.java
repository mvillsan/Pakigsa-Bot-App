package com.example.pakigsabot.CancelReservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.pakigsabot.R;

public class CancelReservation2 extends AppCompatActivity {

    ImageView confirmImageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_reservation2);

        refs();

        confirmImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelReservationReasons();
            }
        });
    }

    public void refs(){
        confirmImageView2 = findViewById(R.id.confirmImageView2);
    }

    private void cancelReservationReasons(){
        Intent intent = new Intent(getApplicationContext(), CancelReservation3.class);
        startActivity(intent);
    }
}