package com.example.pakigsabot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class Share extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("share") != null){
                Toast.makeText(getApplicationContext(), "data: " + bundle.getString("share"),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}