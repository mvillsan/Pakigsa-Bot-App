package com.example.pakigsabot.Resto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.pakigsabot.R;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RestaurantReservation extends AppCompatActivity {

    Button selectHukadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_reservation);

        //References:
        refs();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("restaurantsReserve") != null){
                Toast.makeText(getApplicationContext(), "data: " + bundle.getString("restaurantsReserve"),
                        Toast.LENGTH_SHORT).show();
            }
        }

        selectHukadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { hukadRDetailsCalendar(); }
        });
    }

    public void refs(){
        selectHukadBtn = findViewById(R.id.selectHukadBtn);
    }

    private void hukadRDetailsCalendar() {
        Intent intent = new Intent(getApplicationContext(), RestoDetailsRCalendar.class);
        startActivity(intent);
    }
}