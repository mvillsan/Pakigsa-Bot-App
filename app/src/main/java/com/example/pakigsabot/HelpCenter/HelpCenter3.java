package com.example.pakigsabot.HelpCenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.pakigsabot.R;
public class HelpCenter3 extends AppCompatActivity {

    TextView homeMenuTxt2CC3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center3);

        refs();

        homeMenuTxt2CC3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatbotConversation4();
            }
        });
    }

    public void refs(){
        homeMenuTxt2CC3 = findViewById(R.id.homeMenuTxt2CC3);
    }

    private void chatbotConversation4(){
        Intent intent = new Intent(getApplicationContext(), HelpCenter4.class);
        startActivity(intent);
    }
}