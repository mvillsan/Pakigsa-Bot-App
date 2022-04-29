package com.example.pakigsabot.EmailMessage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pakigsabot.HelpCenter.HelpCenter;
import com.example.pakigsabot.R;

public class EmailMessageLayout extends AppCompatActivity {

    ImageView backBtnEmail;
    EditText emailAddEditTextEM,subjectETEM,bodyEDEM;
    Button sendEmailBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_message_layout);

        //References::
        refs();

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
                        Toast.makeText(EmailMessageLayout.this,"There's no email application that supports this action.", Toast.LENGTH_SHORT).show();
                    }
                    //Clear text fields
                    subjectETEM.setText(null);
                    bodyEDEM.setText(null);
                }else{
                    Toast.makeText(EmailMessageLayout.this,"Please input all the fields! ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backBtnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helpcenter();
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

    private void helpcenter(){
        Intent intent = new Intent(getApplicationContext(), HelpCenter.class);
        startActivity(intent);
    }
}