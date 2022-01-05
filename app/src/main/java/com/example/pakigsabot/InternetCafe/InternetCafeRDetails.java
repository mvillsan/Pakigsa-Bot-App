package com.example.pakigsabot.InternetCafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pakigsabot.R;
import com.example.pakigsabot.SpaSalon.SpaSalonConfirmationDetails;

public class InternetCafeRDetails extends AppCompatActivity {

    Button submitBtnRDetailsIC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_cafe_rdetails);

        refs();

        submitBtnRDetailsIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                internetCafeConfirmationDetails();
            }
        });
    }

    public void refs(){
        submitBtnRDetailsIC = findViewById(R.id.submitBtnRDetailsIC);
    }

    private void internetCafeConfirmationDetails(){
        Intent intent = new Intent(getApplicationContext(), InternetCafeCDetails.class);
        startActivity(intent);
    }
}