package com.example.pakigsabot.ServicesBO;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pakigsabot.R;
import com.example.pakigsabot.RestaurantBO.InEstLocationOrNot;
import com.example.pakigsabot.RestaurantBO.SettingUpEstablishmentRestaurant;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class InputLocation extends AppCompatActivity {
    EditText latitude, longitude;
    Button setEstLocation;
    ImageView backInputBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    DocumentReference docRef;
    String userId, updateLat, updateLong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_location);

        //References
        refs();

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        setEstLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLatLongToDB();
            }
        });

        backInputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), InEstLocationOrNot.class);
                startActivity(in);
            }
        });
    }


    private void refs() {
        latitude = findViewById(R.id.latitudeInput);
        longitude = findViewById(R.id.longitudeInput);
        setEstLocation = findViewById(R.id.setLocationBtn);
        backInputBtn = findViewById(R.id.backInputBtn);
    }

    private void saveLatLongToDB() {
        updateLat = latitude.getText().toString();
        updateLong = longitude.getText().toString();
        userId = fAuth.getCurrentUser().getUid();

        //Check whether there are empty fields
        if(updateLat.isEmpty() || updateLong.isEmpty()){
            Toast.makeText(InputLocation.this, "Some fields are EMPTY.", Toast.LENGTH_SHORT).show();
        }
        else{
            //Saves the updated latitude and longitude of the establishment location to DB
            docRef = fStore.collection("establishments").document(userId);
            Map<String,Object> edited = new HashMap<>();
            edited.put("est_latitude", updateLat);
            edited.put("est_longitude", updateLong);
            docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(InputLocation.this, "Location  successfully set.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), SettingUpEstablishmentRestaurant.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(InputLocation.this, "Failed to set location.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}