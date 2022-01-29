package com.example.pakigsabot.NotificationAlerts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.pakigsabot.R;

public class EstConfirmed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_est_confirmed);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("estConfirmedd") != null){
                Toast.makeText(getApplicationContext(), "data: " + bundle.getString("estConfirmedd"),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}