package com.example.pakigsabot.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pakigsabot.PremiumApp.GoPremiumCA;
import com.example.pakigsabot.R;
import com.example.pakigsabot.Signup;

public class Profile extends AppCompatActivity {

    ImageView prevBtn;
    Button goPremiumBtn;
    AutoCompleteTextView statusType;

    private static final String[] STATUS_TYPE = new String[]{
            "Active", "Do not Disturb", "Snooze", "Offline"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //References:
        refs();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("profile") != null){
                Toast.makeText(getApplicationContext(), "data: " + bundle.getString("profile"),
                        Toast.LENGTH_SHORT).show();
            }
        }

        //Status Array
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,STATUS_TYPE);
        statusType.setAdapter(adapter);
        statusType.setThreshold(1);

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
        statusType = findViewById(R.id.statusType);
    }

    public void goPremium(){
        Intent intent = new Intent(getApplicationContext(), GoPremiumCA.class);
        startActivity(intent);
    }

}