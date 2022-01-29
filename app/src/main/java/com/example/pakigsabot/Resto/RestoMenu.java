package com.example.pakigsabot.Resto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.pakigsabot.R;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class RestoMenu extends AppCompatActivity {

    Button preOrderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resto_menu);

        //References:
        refs();

        preOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restoRDetails();
            }
        });
    }

    public void refs(){ preOrderBtn= findViewById(R.id.preOrderBtn); }

    private void restoRDetails() {
        Intent intent = new Intent(getApplicationContext(), RestoReservationDetails.class);
        startActivity(intent);
    }
}