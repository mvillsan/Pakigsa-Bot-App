package com.example.pakigsabot.InternetCafeBO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pakigsabot.InternetCafeBO.Rules.RulesInternetCafe;
import com.example.pakigsabot.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddRulesInternetCafe extends AppCompatActivity {
    ImageView backBtn, saveBtn;
    TextInputEditText ruleTxt;
    TextInputLayout ruleLayout;
    FirebaseAuth fAuth;
    FirebaseFirestore fStoreRef;
    String userId, autoId, txtRule;
    StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rules_internet_cafe);

        //References
        refs();

        fAuth = FirebaseAuth.getInstance();
        fStoreRef = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        autoId = UUID.randomUUID().toString();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iCafeRules();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRuleDetailsToFirestore();
            }
        });
    }

    public void refs() {
        ruleTxt = findViewById(R.id.iCafeRuleTxt);

        backBtn = findViewById(R.id.backBtnAddICRules);
        saveBtn = findViewById(R.id.saveICRuleBtn);
        ruleLayout = findViewById(R.id.iCafeRuleLayout);
    }

    private void iCafeRules() {
        Intent intent = new Intent(getApplicationContext(), RulesInternetCafe.class);
        startActivity(intent);
    }

    private void saveRuleDetailsToFirestore() {
        txtRule = ruleTxt.getText().toString().trim();

        //Validations::
            if (txtRule.isEmpty()) {
                ruleLayout.setError("Enter Rule Description");
            } else {
                ruleLayout.setErrorEnabled(false);
                ruleLayout.setError("");
            }

                Toast.makeText(AddRulesInternetCafe.this, "Upload Successful", Toast.LENGTH_SHORT).show();

                //Store promo and deals details
                Map<String, Object> iCafeRules = new HashMap<>();
                iCafeRules.put("rule_id", autoId);
                iCafeRules.put("rule_desc", txtRule);

                //To save inside the document of the userID, under the rules collection
                fStoreRef.collection("establishments").document(userId).collection("internet-cafe-rules").document(autoId).set(iCafeRules);
                iCafeRules();
    }
}

