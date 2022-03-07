package com.example.pakigsabot.FacialRecog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.pakigsabot.NavBar.BottomNavigation;
import com.example.pakigsabot.R;

public class FacialRecogEnable extends AppCompatActivity {
    Button enableNowBtn, maybeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facial_recog_enable);

        //References::
        refs();

        maybeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeScreen();
            }
        });
    }

    public void refs() {
        enableNowBtn = findViewById(R.id.enableNowBtn);
        maybeBtn = findViewById(R.id.maybeBtn);
    }

    public void homeScreen() {
        Toast.makeText(FacialRecogEnable.this, "Welcome to Pakigsa-Bot", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), BottomNavigation.class);
        startActivity(intent);
    }
}