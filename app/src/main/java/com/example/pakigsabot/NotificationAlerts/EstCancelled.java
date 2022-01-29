package com.example.pakigsabot.NotificationAlerts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.pakigsabot.R;

public class EstCancelled extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_est_cancelled);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("estCancelled") != null){
                Toast.makeText(getApplicationContext(), "data: " + bundle.getString("estCancelled"),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}