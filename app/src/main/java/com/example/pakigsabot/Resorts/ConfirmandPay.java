package com.example.pakigsabot.Resorts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.example.pakigsabot.R;

public class ConfirmandPay extends AppCompatActivity {

    Button confirmandPayBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmand_pay);


        refs();

        confirmandPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                success();
            }
        });
    }

    public void refs(){
        confirmandPayBtn = findViewById(R.id.confirmandPayBtnResortCD);
    }

    private void success(){
        Intent intent = new Intent(getApplicationContext(), ResortReservationSuccess.class);
        startActivity(intent);
    }
}