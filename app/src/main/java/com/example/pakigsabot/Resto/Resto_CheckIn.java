package com.example.pakigsabot.Resto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.pakigsabot.R;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class Resto_CheckIn extends AppCompatActivity {

    Button am1011Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resto_check_in);

        //References:
        refs();

        am1011Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restaurantPreOrder();
            }
        });
    }

    public void refs(){
        am1011Btn = findViewById(R.id.am1011Btn);
    }

    private void restaurantPreOrder(){
        Intent intent = new Intent(getApplicationContext(), RestaurantPreOrder.class);
        startActivity(intent);
    }
}