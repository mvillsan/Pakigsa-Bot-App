package com.example.pakigsabot.ShareApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pakigsabot.R;

public class Share extends AppCompatActivity {

    ImageView prevBtn;
    TextView shareApp;

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



        shareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareApp();
            }
        });

           }

    public void refs(){
        prevBtn = findViewById(R.id.backBtnShare);
        shareApp = findViewById(R.id.shareApp);

    }

    public void onBackPressed(){
        super.onBackPressed();
    }

    private void shareApp(){
        Intent in = new Intent(Intent.ACTION_SEND);
        in.setType("text/plain");
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        in.putExtra(Intent.EXTRA_TEXT, "Pakigsa-Bot App" + "https://drive.google.com/drive/folders/1hSs4OOvaINJV3iilXePdP-tPa4QOWDrw?usp=sharing");
        in.putExtra(Intent.EXTRA_SUBJECT, "New App");
        startActivity(Intent.createChooser(in, "Share Pakigsa-Bot"));
    }


}