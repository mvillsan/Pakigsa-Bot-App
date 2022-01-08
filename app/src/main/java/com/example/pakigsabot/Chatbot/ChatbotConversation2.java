package com.example.pakigsabot.Chatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pakigsabot.R;

public class ChatbotConversation2 extends AppCompatActivity {

    TextView homeMenuTxtConvo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot_conversation2);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("cC2") != null) {
                Toast.makeText(getApplicationContext(), "data: " + bundle.getString("cC2"),
                        Toast.LENGTH_SHORT).show();
            }
        }

        refs();

        homeMenuTxtConvo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatbotConversation3();
            }
        });
    }

    public void refs(){
        homeMenuTxtConvo2 = findViewById(R.id.homeMenuTxtConvo2);
    }

    private void chatbotConversation3(){
        Intent intent = new Intent(getApplicationContext(), ChatbotConversation3.class);
        startActivity(intent);
    }
}