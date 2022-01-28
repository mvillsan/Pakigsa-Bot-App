package com.example.pakigsabot.CancelReservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pakigsabot.R;
import com.example.pakigsabot.Signup;

public class CancelReservation1 extends AppCompatActivity {

    ImageView cancelReservationBtn1, cannotCancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_reservation1);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("resDetails1") != null) {
                Toast.makeText(getApplicationContext(), "data: " + bundle.getString("resDetails1"),
                        Toast.LENGTH_SHORT).show();
            }
        }

        refs();

        cancelReservationBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelReservationConfirmation();
            }
        });

        cannotCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cannotCancel();
            }
        });
    }

    public void refs(){
        cancelReservationBtn1 = findViewById(R.id.cancelReservationBtn1);
        cannotCancelBtn = findViewById(R.id.imageView2);
    }

    private void cancelReservationConfirmation(){
        Intent intent = new Intent(getApplicationContext(), CancelReservation2.class);
        startActivity(intent);
    }

    private void cannotCancel(){
        Intent intent = new Intent(getApplicationContext(), CancellationNotAllowed.class);
        startActivity(intent);
    }
}