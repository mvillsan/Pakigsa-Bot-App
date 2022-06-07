package com.example.pakigsabot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button customer, businessOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refs();

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customerMain();
            }
        });

        businessOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                businessOwnerMain();
            }
        });

    }

    public void refs(){
        customer = findViewById(R.id.customerBtn);
        businessOwner = findViewById(R.id.businessOwnerBtn);
    }

    public void customerMain(){
        Intent intent = new Intent(getApplicationContext(), MainActivityCustomer.class);
        startActivity(intent);
    }

    public void businessOwnerMain(){
        Intent intent = new Intent(getApplicationContext(), MainActivityBO.class);
        startActivity(intent);
    }
}