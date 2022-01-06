package com.example.pakigsabot.CancelReservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.pakigsabot.R;

public class CancelReservation3 extends AppCompatActivity {

    ImageView submitImageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_reservation3);

        refs();

        submitImageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancellationSuccess();
            }
        });
    }

    public void refs(){
        submitImageView3 = findViewById(R.id.submitImageView3);
    }

    private void cancellationSuccess(){
        Intent intent = new Intent(getApplicationContext(), CancelReservationSuccessful.class);
        startActivity(intent);
    }
}