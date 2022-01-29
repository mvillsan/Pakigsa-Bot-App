package com.example.pakigsabot.Resto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.pakigsabot.R;
import android.content.Intent;
import android.view.View;
import android.widget.Button;


public class RestaurantPreOrder extends AppCompatActivity {

    Button yesBtn, noBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_pre_order);

        //References:
        refs();

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restaurantMenu();
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restaurantReservationDetails();
            }
        });
    }

    public void refs(){
        yesBtn = findViewById(R.id.yesBtn);
        noBtn = findViewById(R.id.noBtn);
    }

    private void restaurantMenu(){
        Intent intent = new Intent(getApplicationContext(), RestoMenu.class);
        startActivity(intent);
    }

    private void restaurantReservationDetails(){
        Intent intent = new Intent(getApplicationContext(), RestoReservationDetails.class);
        startActivity(intent);
    }
}