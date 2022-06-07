package com.example.pakigsabot.CoworkingSpaceBO;

import static com.airbnb.lottie.L.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pakigsabot.CoworkingSpaceBO.PromosAndDeals.PromosAndDealsCoworking;
import com.example.pakigsabot.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageTask;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddPromosAndDealsCoworking extends AppCompatActivity {
    ImageView backBtn, saveBtn;
    TextInputEditText padName, padDesc;
    TextView padStartDate, padEndDate;
    TextInputLayout padNameLayout, padDescLayout, padEffectiveStartDateLayout, padEffectiveEndDateLayout;
    FirebaseAuth fAuth;
    FirebaseFirestore fStoreRef;
    String userId, autoId, txtPADName, txtPADDesc, txtPADStartDate, txtPADEndDate;
    DatePickerDialog.OnDateSetListener coSpacePADStartDateSetListener, coSpacePADEndDateSetListener;
    StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_promos_and_deals_coworking);
        //References
        refs();

        fAuth = FirebaseAuth.getInstance();
        fStoreRef = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        autoId = UUID.randomUUID().toString();

        //StartDate
        padStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar1 = Calendar.getInstance();
                int year = calendar1.get(Calendar.YEAR);
                int month = calendar1.get(Calendar.MONTH);
                int day = calendar1.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog1 = new DatePickerDialog(
                        AddPromosAndDealsCoworking.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        coSpacePADStartDateSetListener,
                        year,month,day);

                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog1.show();
            }
        });

        coSpacePADStartDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day ){
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yy: "  + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                padStartDate.setText(date);
            }
        };

        //EndDate
        padEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar2 = Calendar.getInstance();
                int year = calendar2.get(Calendar.YEAR);
                int month = calendar2.get(Calendar.MONTH);
                int day = calendar2.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog2 = new DatePickerDialog(
                        AddPromosAndDealsCoworking.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        coSpacePADEndDateSetListener,
                        year,month,day);

                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog2.show();
            }
        });

        coSpacePADEndDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day ){
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yy: "  + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                padEndDate.setText(date);
            }
        };

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coSpacePromosAndDeals();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePADDetailsToFirestore();
            }
        });
    }

    public void refs() {
        padName = findViewById(R.id.coSpacePADNameTxt);
        padDesc = findViewById(R.id.coSpacePADDescTxt);
        padStartDate = findViewById(R.id.coSpacePADESDTxt);
        padEndDate = findViewById(R.id.coSpacePADEEDTxt);

        backBtn = findViewById(R.id.backBtnAddPromoDeals);
        saveBtn = findViewById(R.id.saveCoSpacePADBtn);
        padNameLayout = findViewById(R.id.coSpacePADNameLayout);
        padDescLayout = findViewById(R.id.coSpacePADDescLayout);
        padEffectiveStartDateLayout = findViewById(R.id.coSpacePADESDLayout);
        padEffectiveEndDateLayout = findViewById(R.id.coSpacePADEEDLayout);

    }

    private void coSpacePromosAndDeals() {
        Intent intent = new Intent(getApplicationContext(), PromosAndDealsCoworking.class);
        startActivity(intent);
    }

    private void savePADDetailsToFirestore() {
        txtPADName = padName.getText().toString().trim();
        txtPADDesc = padDesc.getText().toString().trim();
        txtPADStartDate = padStartDate.getText().toString().trim();
        txtPADEndDate = padEndDate.getText().toString().trim();

        //Validations::
        if (txtPADName.isEmpty() || txtPADDesc.isEmpty() || txtPADStartDate.isEmpty() || txtPADEndDate.isEmpty()) {
            Toast.makeText(AddPromosAndDealsCoworking.this, "Some fields are EMPTY.", Toast.LENGTH_SHORT).show();
        } else {
            if (txtPADName.isEmpty()) {
                padNameLayout.setError("Enter Name of Promo or Deals");
            } else {
                Boolean validName = txtPADName.matches("[A-Za-z][A-Za-z ]*+");
                if (!validName) {
                    padNameLayout.setError("Invalid Promo or Deals Name");
                } else {
                    padNameLayout.setErrorEnabled(false);
                    padNameLayout.setError("");
                }
            }
            if (txtPADDesc.isEmpty()) {
                padDescLayout.setError("Enter Description");
            } else {
                padDescLayout.setErrorEnabled(false);
                padDescLayout.setError("");
            }
            if (txtPADStartDate.isEmpty()) {
                padEffectiveStartDateLayout.setError("Enter Rate");
            } else {
                padEffectiveStartDateLayout.setErrorEnabled(false);
                padEffectiveStartDateLayout.setError("");
                if (txtPADEndDate.isEmpty()) {
                    padEffectiveEndDateLayout.setError("Enter Rate");
                } else {
                    padEffectiveEndDateLayout.setErrorEnabled(false);
                    padEffectiveEndDateLayout.setError("");
                }

                Toast.makeText(AddPromosAndDealsCoworking.this, "Upload Successful", Toast.LENGTH_SHORT).show();

                //Store promo and deals details
                Map<String, Object> coSpacePAD = new HashMap<>();
                coSpacePAD.put("coSpacePADId", autoId);
                coSpacePAD.put("coSpacePADName", txtPADName);
                coSpacePAD.put("coSpacePADDesc", txtPADDesc);
                coSpacePAD.put("coSpacePADStartDate", txtPADStartDate);
                coSpacePAD.put("coSpacePADEndDate", txtPADEndDate);

                //To save inside the document of the userID, under the dental-procedures collection
                fStoreRef.collection("establishments").document(userId).collection("coworking-space-promo-and-deals").document(autoId).set(coSpacePAD);

                coSpacePromosAndDeals();
            }
        }
    }

}