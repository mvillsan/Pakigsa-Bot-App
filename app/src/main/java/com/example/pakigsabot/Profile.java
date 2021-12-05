package com.example.pakigsabot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Profile extends AppCompatActivity {

    ImageView prevBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        /*prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "clicked",
                        Toast.LENGTH_SHORT).show();
                //nBackPressed();
            }
        });*/

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("profile") != null){
                Toast.makeText(getApplicationContext(), "data: " + bundle.getString("profile"),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onBackPressed(){
        super.onBackPressed();
    }

   /* public void refs(){
        prevBtn = findViewById(R.id.backBtnProfile);
    }

    public void homeScreen(){
        Intent in = new Intent(this, HomeFragment.class);
        startActivity(in);
    }*/
}