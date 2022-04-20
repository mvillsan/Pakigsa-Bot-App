package com.example.pakigsabot.PremiumApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pakigsabot.R;

public class UpgradeScreen extends AppCompatActivity {

    TextView subsTerms;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_screen);

        //References::
        refs();

        subsTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subTerms();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previous();
            }
        });

    }

    public void refs(){
        subsTerms = findViewById(R.id.termsTxt);
        backBtn = findViewById(R.id.backBtnProfile);
    }

    //Subscription
    public void subTerms(){
        Intent intent = new Intent(getApplicationContext(), SubscriptionTerms.class);
        startActivity(intent);
    }

    //Back Button
    public void previous(){
        Intent intent = new Intent(getApplicationContext(), GoPremiumCA.class);
        startActivity(intent);
    }
}