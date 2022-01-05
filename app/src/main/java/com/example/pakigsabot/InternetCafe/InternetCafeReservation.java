package com.example.pakigsabot.InternetCafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pakigsabot.R;
import com.example.pakigsabot.SpaSalon.SpaSalonReservationDetails;

public class InternetCafeReservation extends AppCompatActivity {

    Button titusSelectBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_cafe_reservation);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("internetCafeReserve") != null){
                Toast.makeText(getApplicationContext(), "data: " + bundle.getString("internetCafeReserve"),
                        Toast.LENGTH_SHORT).show();
            }
        }

        refs();

        titusSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titusCafeDetailsRCalendar();
            }
        });
    }

    public void refs(){
        titusSelectBtn = findViewById(R.id.titusSelectBtn);
    }

    private void titusCafeDetailsRCalendar(){
        Intent intent = new Intent(getApplicationContext(), InternetCafeDetailsRCalendar.class);
        startActivity(intent);
    }
}