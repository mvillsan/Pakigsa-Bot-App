package com.example.pakigsabot.Resorts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pakigsabot.R;

public class ResortReservation extends AppCompatActivity {

    Button cebuWetlandBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resort_reservation);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("resortsReserve") != null){
                Toast.makeText(getApplicationContext(), "data: " + bundle.getString("resortsReserve"),
                        Toast.LENGTH_SHORT).show();
            }
        }

        refs();

        cebuWetlandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cebuWetlandRDetailsCalendar();
            }
        });
    }

    private void refs(){
        cebuWetlandBtn = findViewById(R.id.selectCebuWetland);
    }

    private void cebuWetlandRDetailsCalendar() {
        Intent intent = new Intent(getApplicationContext(), ResortDetailsRCalendar.class);
        startActivity(intent);
    }
}