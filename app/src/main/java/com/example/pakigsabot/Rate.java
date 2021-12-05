package com.example.pakigsabot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class Rate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("rate") != null){
                Toast.makeText(getApplicationContext(), "data: " + bundle.getString("rate"),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}