package com.example.pakigsabot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("profile") != null){
                Toast.makeText(getApplicationContext(), "data: " + bundle.getString("profile"),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}