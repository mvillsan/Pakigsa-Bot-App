package com.example.pakigsabot.ShareApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pakigsabot.R;

public class Share extends AppCompatActivity {

    ImageView prevBtn;

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

    }

    public void refs(){
        prevBtn = findViewById(R.id.backBtnShare);
    }

    public void onBackPressed(){
        super.onBackPressed();
    }
}