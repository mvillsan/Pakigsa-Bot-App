package com.example.pakigsabot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class ServicesFavorites extends AppCompatActivity {

    ImageView estFav, servicesFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_favorites);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("servFav") != null){
                Toast.makeText(getApplicationContext(), "data: " + bundle.getString("servFav"),
                        Toast.LENGTH_SHORT).show();
            }
        }

        refs();

        estFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        servicesFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                services();
            }
        });
    }

    public void refs(){
        estFav = findViewById(R.id.estFavImageViewBack);
        servicesFav = findViewById(R.id.servicesFavImageViewCurrent);
    }

    public void onBackPressed(){
        super.onBackPressed();
    }

    public void services(){
         Intent intent = new Intent(getApplicationContext(), ServicesFavorites.class);
            startActivity(intent);
    }
}