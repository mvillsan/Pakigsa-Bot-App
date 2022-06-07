package com.example.pakigsabot.ServicesBO;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.pakigsabot.R;

public class AddServiceResto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service_resto);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("addResortServices") != null){
                Toast.makeText(getApplicationContext(), "data: " + bundle.getString("addResortServices"),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}