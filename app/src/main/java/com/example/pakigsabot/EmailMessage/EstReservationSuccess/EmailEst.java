package com.example.pakigsabot.EmailMessage.EstReservationSuccess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pakigsabot.R;
import com.example.pakigsabot.Reservations.ViewReservations;
import com.example.pakigsabot.Resorts.ResortReservation;

public class EmailEst extends AppCompatActivity {

    //Initialization of variables::
    String estID, estEmailAddress, estName;
    ImageView backBtnEmail;
    EditText emailAddEditTextEM,subjectETEM,bodyEDEM;
    Button sendEmailBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_est);


        //References::
        refs();

        //Getting data from recyclerview::
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            estID = extra.getString("EstablishmentID");
            estName = extra.getString("EstablishmentName");
            estEmailAddress = extra.getString("EstablishmentEmail");
        }

        //Setting fetched data to edittext
        emailAddEditTextEM.setText(estEmailAddress);
        subjectETEM.setText(estName + " Reservation Concern");

        sendEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!emailAddEditTextEM.getText().toString().isEmpty() && !subjectETEM.getText().toString().isEmpty()
                        && !bodyEDEM.getText().toString().isEmpty()){
                    //Connecting to GMAIL application
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddEditTextEM.getText().toString()});
                    intent.putExtra(Intent.EXTRA_SUBJECT, subjectETEM.getText().toString());
                    intent.putExtra(Intent.EXTRA_TEXT, bodyEDEM.getText().toString());
                    intent.setData(Uri.parse("mailto:"));
                    if(intent.resolveActivity(getPackageManager()) != null){
                        startActivity(Intent.createChooser(intent, "Send Email"));
                    }else{
                        Toast.makeText(EmailEst.this,"There's no email application that supports this action.", Toast.LENGTH_SHORT).show();
                    }
                    //Clear text fields
                    subjectETEM.setText(null);
                    bodyEDEM.setText(null);
                }else{
                    Toast.makeText(EmailEst.this,"Please input all the fields! ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backBtnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resortEstList();
            }
        });
    }

    private void refs(){
        emailAddEditTextEM = findViewById(R.id.emailAddEditTextEM);
        subjectETEM = findViewById(R.id.subjectETEM);
        bodyEDEM = findViewById(R.id.bodyEDEM);
        sendEmailBtn = findViewById(R.id.sendEmailBtn);
        backBtnEmail = findViewById(R.id.backBtnEmail);
    }

    private void resortEstList(){
        Intent intent = new Intent(getApplicationContext(), ResortReservation.class);
        startActivity(intent);
    }
}