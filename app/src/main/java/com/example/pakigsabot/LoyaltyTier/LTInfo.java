package com.example.pakigsabot.LoyaltyTier;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pakigsabot.Profile.Profile;
import com.example.pakigsabot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class LTInfo extends AppCompatActivity {

    //Initialization of variables
    ImageView backBtnProfile;
    ProgressBar ltProgressBar;
    TextView progressTxt,seeTiersTxt;
    private int currentProgress;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    String custID;
    DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ltinfo);

        //References::
        refs();

        backBtnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prevScreen();
            }
        });

        //Setting Customer's Number of Reservations
        setNumReservation();

        seeTiersTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seeTiers();
            }
        });
    }

    private void refs(){
        backBtnProfile = findViewById(R.id.backBtnProfile);
        ltProgressBar = findViewById(R.id.rsrvNumProgressBar);
        progressTxt = findViewById(R.id.progressTxt);
        seeTiersTxt = findViewById(R.id.seeTiersTxt);
    }

    private void prevScreen(){
        Intent intent = new Intent(getApplicationContext(), Profile.class);
        startActivity(intent);
    }

    private void setNumReservation(){
        //Fetching Data from FireStore DB::
        custID = fAuth.getCurrentUser().getUid();
        docRef = fStore.collection("customers").document(custID);
            docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    currentProgress = value.getLong("cust_numOfReservations").intValue();

                    //Progress Bar + Text::
                    ltProgressBar.setProgress(currentProgress);
                    //No Tier Level
                    if (currentProgress >= 0 && currentProgress <= 9) {
                        ltProgressBar.setMax(10);
                        progressTxt.setText("Complete 10 more reservations (" + currentProgress + "/10)");
                        //Silver Level
                    }else if (currentProgress >= 10 && currentProgress <= 24) {
                        Intent intent = new Intent(getApplicationContext(), LTInfoSilver.class);
                        startActivity(intent);
                        //Gold Level
                    }else if (currentProgress >= 25 && currentProgress <= 44){
                        Intent intent = new Intent(getApplicationContext(), LTInfoGold.class);
                        startActivity(intent);
                        //Platinum Level
                    }else if (currentProgress >= 45){
                        Intent intent = new Intent(getApplicationContext(), LTInfoPlatinum.class);
                        startActivity(intent);
                    }
                }
            });
    }

    private void seeTiers(){
        Intent intent = new Intent(getApplicationContext(), SeeAllTiers.class);
        startActivity(intent);
    }
}