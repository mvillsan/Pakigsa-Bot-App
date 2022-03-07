package com.example.pakigsabot.FacialRecog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.pakigsabot.R;
import com.example.pakigsabot.Signin;

public class FacialRecogSignIn extends AppCompatActivity {
    Button enableNowBtn, maybeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facial_recog_sign_in);

        //References::
        refs();

        maybeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInScreen();
            }
        });
    }

    public void refs(){
        enableNowBtn = findViewById(R.id.enableNowBtnFRSI);
        maybeBtn = findViewById(R.id.maybeBtnFRSI);
    }

    public void signInScreen(){
        Toast.makeText(FacialRecogSignIn.this, "Sign In Using Email Address", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), Signin.class);
        startActivity(intent);
    }
}