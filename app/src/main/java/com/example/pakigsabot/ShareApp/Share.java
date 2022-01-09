package com.example.pakigsabot.ShareApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pakigsabot.R;
import com.example.pakigsabot.Resorts.ResortDetailsRCalendar;

public class Share extends AppCompatActivity {

    ImageView prevBtn, shareToFBBtn, shareToInstaBtn,shareToTwitterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        refs();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("share") != null){
                Toast.makeText(getApplicationContext(), "data: " + bundle.getString("share"),
                        Toast.LENGTH_SHORT).show();
            }
        }

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        shareToFBBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharetoFB();
            }
        });

        shareToInstaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharetoInsta();
            }
        });

        shareToTwitterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharetoTwitter();
            }
        });
    }

    public void refs(){
        prevBtn = findViewById(R.id.backBtnShare);
        shareToFBBtn = findViewById(R.id.shareToFBBtn);
        shareToInstaBtn = findViewById(R.id.shareToInstaBtn);
        shareToTwitterBtn = findViewById(R.id.shareToTwitterBtn);
    }

    public void onBackPressed(){
        super.onBackPressed();
    }

    private void sharetoFB(){
        Intent intent = new Intent(getApplicationContext(), ShareAppToFB.class);
        startActivity(intent);
    }

    private void sharetoInsta(){
        Intent intent = new Intent(getApplicationContext(), ShareAppToInsta.class);
        startActivity(intent);
    }

    private void sharetoTwitter(){
        Intent intent = new Intent(getApplicationContext(), ShareAppToTwitter.class);
        startActivity(intent);
    }
}