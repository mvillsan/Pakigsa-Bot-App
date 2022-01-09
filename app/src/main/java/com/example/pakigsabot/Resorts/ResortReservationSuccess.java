package com.example.pakigsabot.Resorts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.pakigsabot.EmailMessage.EmailMessageLayout;
import com.example.pakigsabot.R;

public class ResortReservationSuccess extends AppCompatActivity {

    TextView emailTxtResort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resort_reservation_success);

        refs();

        emailTxtResort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailMessageScreen();
            }
        });
    }

    public void refs(){
        emailTxtResort = findViewById(R.id.emailTxtResort);
    }

    private void emailMessageScreen(){
        Intent intent = new Intent(getApplicationContext(), EmailMessageLayout.class);
        startActivity(intent);
    }
}