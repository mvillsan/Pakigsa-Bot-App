package com.example.pakigsabot.Feedback;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.pakigsabot.R;

public class Rate extends AppCompatActivity {

    TextView txtFB;
    RatingBar rbStars;
    ImageView prevBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        //References
        refs();

        rbStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean user) {
                if(rating == 0){
                    txtFB.setText("Very Disastisfied");
                }else if(rating == 1){
                    txtFB.setText("Disastisfied");
                }else if(rating == 2 || rating == 3){
                    txtFB.setText("Fine");
                }else if(rating == 4){
                    txtFB.setText("Satisfied");
                }else if(rating == 5){
                    txtFB.setText("Very Satisfied");
                }
            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousScreen();
            }
        });
    }

    private void refs(){
        txtFB = findViewById(R.id.resultTxtFB);
        rbStars = findViewById(R.id.ratingBar);
        prevBtn = findViewById(R.id.prevBtnRateS);
    }

    private void previousScreen(){
        Intent intent = new Intent(getApplicationContext(), Feedback.class);
        startActivity(intent);
    }
}