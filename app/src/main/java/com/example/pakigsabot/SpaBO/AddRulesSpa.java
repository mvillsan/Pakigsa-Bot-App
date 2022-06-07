package com.example.pakigsabot.SpaBO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pakigsabot.R;
import com.example.pakigsabot.SpaBO.Rules.RulesSpa;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddRulesSpa extends AppCompatActivity {
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
        setContentView(R.layout.activity_add_rules_spa);

        //References
        refs();

        fAuth = FirebaseAuth.getInstance();
        fStoreRef = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        autoId = UUID.randomUUID().toString();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spaRules();
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
        ruleTxt = findViewById(R.id.spaRuleTxt);

        backBtn = findViewById(R.id.backBtnAddSpaRules);
        saveBtn = findViewById(R.id.saveSpaRuleBtn);
        ruleLayout = findViewById(R.id.spaRuleLayout);
    }

    private void spaRules() {
        Intent intent = new Intent(getApplicationContext(), RulesSpa.class);
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

                Toast.makeText(AddRulesSpa.this, "Upload Successful", Toast.LENGTH_SHORT).show();

                //Store promo and deals details
                Map<String, Object> spaRules = new HashMap<>();
                spaRules.put("rule_id", autoId);
                spaRules.put("rule_desc", txtRule);

                //To save inside the document of the userID, under the rules collection
                fStoreRef.collection("establishments").document(userId).collection("spa-rules").document(autoId).set(spaRules);
                spaRules();
    }
}

