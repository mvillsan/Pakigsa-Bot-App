package com.example.pakigsabot.NotificationAlertsBO;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.pakigsabot.R;


public class CustomerCancelled extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_cancelled);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("alertCC") != null){
                Toast.makeText(getApplicationContext(), "data: " + bundle.getString("alertCC"),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}