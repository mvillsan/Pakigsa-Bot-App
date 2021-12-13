package com.example.pakigsabot.Feedback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pakigsabot.FeedbackRecyclerView.CustomAdapter;
import com.example.pakigsabot.FeedbackRecyclerView.Model;
import com.example.pakigsabot.R;
import com.example.pakigsabot.Feedback.Rate;

import java.util.ArrayList;
import java.util.List;

public class Feedback extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Model> myModelList;
    CustomAdapter customAdapter;
    int[] estImagesArray = {R.drawable.asmara_icon_item, R.drawable.westown_icon_item,
            R.drawable.serenity_icon_item};
    EditText editText;
    ImageView prevBtn;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("fb") != null) {
                Toast.makeText(getApplicationContext(), "data: " + bundle.getString("fb"),
                        Toast.LENGTH_SHORT).show();
            }
        }

        //References
        refs();

        displayItems();

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousScreen();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextFB();
            }
        });
    }

    private void displayItems(){
        recyclerView = findViewById(R.id.estRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        myModelList = new ArrayList<>();
        myModelList.add(new Model("Asmara Urban Resort and Lifestyle Village Inc.","1 Paseo Saturnino, Cebu City",estImagesArray[0]));
        myModelList.add(new Model("Cebu Westown Lagoon","MO2 Entertainment Complex", estImagesArray[1]));
        myModelList.add(new Model("Serenity Farm and Resort Busay","Sitio Tiguib, Brgy., Cebu City",estImagesArray[2]));
        customAdapter = new CustomAdapter(this, myModelList);
        recyclerView.setAdapter(customAdapter);
    }

    private void refs(){
        editText = findViewById(R.id.estEditTxtFBS);
        prevBtn = findViewById(R.id.prevBtnFeedBackS);
        next = findViewById(R.id.nextFBBtn);
    }

    private void previousScreen(){
        super.onBackPressed();
    }

    private void nextFB(){
        Intent intent = new Intent(getApplicationContext(), Rate.class);
        startActivity(intent);
    }
}
