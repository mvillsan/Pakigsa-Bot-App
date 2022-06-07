package com.example.pakigsabot.EmailMessageBO;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pakigsabot.HelpCenterBO.HelpCenter;
import com.example.pakigsabot.R;

public class EmailMessageLayout extends AppCompatActivity {
    ImageView backBtn;
    Button sendEmailBtn;
    EditText emailAdd, subject, emailMsgBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_message_layout_bo);

        //References
        refs();
        
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Back to Help Center
                Intent in = new Intent(getApplicationContext(), HelpCenter.class);
                startActivity(in);
            }
        });

        sendEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!emailAdd.getText().toString().isEmpty() && !subject.getText().toString().isEmpty()
                        && !emailMsgBody.getText().toString().isEmpty()){

                    //Connecting to GMAIL application
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAdd.getText().toString()});
                    intent.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
                    intent.putExtra(Intent.EXTRA_TEXT, emailMsgBody.getText().toString());

                    intent.setData(Uri.parse("mailto:"));  //ensures that only email application will open

                    if(intent.resolveActivity(getPackageManager()) != null){
                        startActivity(Intent.createChooser(intent, "Send Email"));
                    }else{
                        Toast.makeText(EmailMessageLayout.this,"There's no email application that supports this action.", Toast.LENGTH_SHORT).show();
                    }

                    //Clear text fields
                    subject.setText(null);
                    emailMsgBody.setText(null);

                }else{
                    Toast.makeText(EmailMessageLayout.this,"Please input all the fields! ", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void refs() {
        backBtn = findViewById(R.id.backBtnEmail);
        sendEmailBtn = findViewById(R.id.sendEmailBtn);

        emailAdd = findViewById(R.id.emailAddEditTextEM);
        subject = findViewById(R.id.subjectETEM);
        emailMsgBody = findViewById(R.id.bodyEDEM);

    }
}