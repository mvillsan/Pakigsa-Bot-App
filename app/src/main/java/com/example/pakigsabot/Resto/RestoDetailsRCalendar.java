package com.example.pakigsabot.Resto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.pakigsabot.R;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class RestoDetailsRCalendar extends AppCompatActivity {

    Button reserveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resto_details_rcalendar);

        //References:
        refs();

        reserveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTime();
            }
        });
    }

    public void refs(){ reserveBtn = findViewById(R.id.reserveBtnResto); }

    private void selectTime(){
        Intent intent = new Intent(getApplicationContext(), Resto_CheckIn.class);
        startActivity(intent);
    }
}