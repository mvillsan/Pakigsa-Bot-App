package com.example.pakigsabot.ReservationHistoryBO;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.pakigsabot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class LoadReservationHistory extends AppCompatActivity {
    ProgressBar progressBar;
    String userID,est;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String[] estList = new String[]{
            "Restaurant", "Cafe", "Resort", "Dental Clinic", "Eye Clinic", "Spa and Salon", "Internet Cafe", "Coworking Space"
    };
    DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_reservation_history);

        //References::
        progressBar = findViewById(R.id.progressBar);

        //Specific establishment's reservation history to be displayed::
        loadEstReservationHistory();
    }

    private void loadEstReservationHistory(){
        //Fetching Data from FireStore DB
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        docRef = fStore.collection("establishments").document(userID);
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                est = value.getString("est_Type");
                if(est.equals(estList[0])){
                    restoEst();
                }else if(est.equals(estList[1])){
                    //cafeEst();
                }else if(est.equals(estList[2])){
                    resortEst();
                }else if(est.equals(estList[3])){
                    dentalEst();
                }else if(est.equals(estList[4])){
                    eyeEst();
                }else if(est.equals(estList[5])){
                    //spaSalonEst();
                }
            }
        });
    }
    private void restoEst(){
        Intent in = new Intent(getApplicationContext(), ViewReservationHistoryRestaurant.class);
        startActivity(in);
    }

    private void resortEst(){
        Intent in = new Intent(getApplicationContext(), ViewReservationHistoryResort.class);
        startActivity(in);
    }

    private void dentalEst(){
        Intent in = new Intent(getApplicationContext(), ViewReservationHistoryDentalClinic.class);
        startActivity(in);
    }

    private void eyeEst(){
        Intent in = new Intent(getApplicationContext(), ViewReservationHistoryEyeClinic.class);
        startActivity(in);
    }
}