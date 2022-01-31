package com.example.pakigsabot.HelpCenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.pakigsabot.R;

public class HelpCenter4 extends AppCompatActivity {

    TextView homeMenuTxt2CC4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center4);

        refs();

        homeMenuTxt2CC4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatbotConversation5();
            }
        });
    }

    public void refs(){
        homeMenuTxt2CC4 = findViewById(R.id.homeMenuTxt2CC4);
    }

    private void chatbotConversation5(){
        Intent intent = new Intent(getApplicationContext(), HelpCenter5.class);
        startActivity(intent);
    }
}