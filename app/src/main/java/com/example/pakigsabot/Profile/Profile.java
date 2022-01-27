package com.example.pakigsabot.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pakigsabot.PremiumApp.GoPremiumCA;
import com.example.pakigsabot.R;
import com.example.pakigsabot.Signup;

public class Profile extends AppCompatActivity {

    ImageView prevBtn;
    Button goPremiumBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        refs();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("profile") != null){
                Toast.makeText(getApplicationContext(), "data: " + bundle.getString("profile"),
                        Toast.LENGTH_SHORT).show();
            }
        }

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        goPremiumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goPremium();
            }
        });
    }


    public void onBackPressed(){
        super.onBackPressed();
    }

    public void refs(){
        prevBtn = findViewById(R.id.backBtnProfile);
        goPremiumBtn = findViewById(R.id.goPremiumBtn);
    }

    public void goPremium(){
        Intent intent = new Intent(getApplicationContext(), GoPremiumCA.class);
        startActivity(intent);
    }
}