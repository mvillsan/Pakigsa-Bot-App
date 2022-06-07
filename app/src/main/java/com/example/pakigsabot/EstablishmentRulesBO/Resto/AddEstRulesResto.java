package com.example.pakigsabot.EstablishmentRulesBO.Resto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pakigsabot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddEstRulesResto extends AppCompatActivity {
    ImageView backBtn, saveBtn;
    EditText estRulesDesc;
    FirebaseAuth fAuth;
    FirebaseFirestore fStoreRef;
    String userId, autoId, txtEstRulesName, txtEstRulesDesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_est_rules_resto);
        //References
        refs();

        fAuth = FirebaseAuth.getInstance();
        fStoreRef = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        autoId = UUID.randomUUID().toString();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               restoRules();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRestoEstRules();
            }
        });
    }

    private void refs() {
        estRulesDesc = findViewById(R.id.ruleTxt);

        backBtn = findViewById(R.id.backBtnAddEstRules);
        saveBtn = findViewById(R.id.saveBtnRule);

    }
    private void restoRules() {
        Intent intent = new Intent(getApplicationContext(), EstRulesResto.class);
        startActivity(intent);
    }

    private void saveRestoEstRules() {
        txtEstRulesDesc = estRulesDesc.getText().toString().trim();


        //Validations::

            if (txtEstRulesDesc.isEmpty()){
                Toast.makeText(AddEstRulesResto.this, "Please input a rule.", Toast.LENGTH_SHORT).show();
            }


                Toast.makeText(AddEstRulesResto.this, "Upload Successful", Toast.LENGTH_SHORT).show();

                //Store promo and deals details
                Map<String, Object> restoEstRules = new HashMap<>();
                restoEstRules.put("resto_ruleId", autoId);
                restoEstRules.put("resto_desc", txtEstRulesDesc);
                restoEstRules.put("estId", userId);

                //To save inside the document of the userID, under the dental-procedures collection
                fStoreRef.collection("establishments").document(userId).collection("resto-est-rules").document(autoId).set(restoEstRules);

                restoRules();

        }
    }
