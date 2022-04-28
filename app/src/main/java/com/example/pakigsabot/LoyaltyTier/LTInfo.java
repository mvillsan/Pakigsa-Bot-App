package com.example.pakigsabot.LoyaltyTier;

//import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pakigsabot.Profile.Profile;
import com.example.pakigsabot.R;
/*import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;*/

public class LTInfo extends AppCompatActivity {

    ImageView backBtnProfile;
    ProgressBar ltProgressBar;
    TextView progressTxt,seeTiersTxt;
    private int currentProgress = 0, rsrvOccur;
  /*  FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String custID,rsrvID;
    StorageReference storageRef;
    DocumentReference documentReference, docRef;*/

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

        //Progress Bar + Text::
        currentProgress = currentProgress + 5;
        ltProgressBar.setProgress(currentProgress);
        ltProgressBar.setMax(10);
        progressTxt.setText("Complete 10 more reservations (" + currentProgress + "/10)");


        /*//Fetching Data from FireStore DB::
        fStore = FirebaseFirestore.getInstance();
        custID = fAuth.getCurrentUser().getUid();
        rsrvID = "rsrv0122";
        storageRef = FirebaseStorage.getInstance().getReference();

        documentReference = fStore.collection("reservations").document(rsrvID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                rsrvOccur = Integer.parseInt(value.getString("reserv_num"));

                //Count total reservation per customer::
                currentProgress += rsrvOccur;

                //Progress Bar + Text::
                ltProgressBar.setProgress(currentProgress);
                if(currentProgress <= 10){
                    ltProgressBar.setMax(10);
                    progressTxt.setText("Complete 10 more reservations (" + currentProgress + "/10)");
                }else{
                    ltProgressBar.setMax(25);
                    progressTxt.setText("Complete 25 more reservations (" + currentProgress + "/25)");
                }
            }
        });

        docRef = fStore.collection("reservations").document(rsrvID);
        docRef.update("cust_id", custID);
*/
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

    private void seeTiers(){
        Intent intent = new Intent(getApplicationContext(), SeeAllTiers.class);
        startActivity(intent);
    }
}