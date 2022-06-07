package com.example.pakigsabot.CoworkingSpaceBO;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pakigsabot.CoworkingSpaceBO.CancellationPolicies.CancellationPolicyCoworkingSpace;
import com.example.pakigsabot.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddPolicyCoworkingSpace extends AppCompatActivity {
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
        setContentView(R.layout.activity_add_policy_coworking_space);

        //References
        refs();

        fAuth = FirebaseAuth.getInstance();
        fStoreRef = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        autoId = UUID.randomUUID().toString();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coSpacePolicy();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePolicyDetailsToFirestore();
            }
        });
    }

    public void refs() {
        ruleTxt = findViewById(R.id.coSpacePolicyTxt);

        backBtn = findViewById(R.id.backBtnAddCSPolicy);
        saveBtn = findViewById(R.id.saveCSPolicyBtn);
        ruleLayout = findViewById(R.id.coSpacePolicyLayout);
    }

    private void coSpacePolicy() {
        Intent intent = new Intent(getApplicationContext(), CancellationPolicyCoworkingSpace.class);
        startActivity(intent);
    }

    private void savePolicyDetailsToFirestore() {
        txtRule = ruleTxt.getText().toString().trim();

        //Validations::
        if (txtRule.isEmpty()) {
            ruleLayout.setError("Enter Cancellation Description");
        } else {
            ruleLayout.setErrorEnabled(false);
            ruleLayout.setError("");
        }


        Toast.makeText(AddPolicyCoworkingSpace.this, "Upload Successful", Toast.LENGTH_SHORT).show();

        //Store promo and deals details
        Map<String, Object> coSpacePolicy = new HashMap<>();
        coSpacePolicy.put("rule_id", autoId);
        coSpacePolicy.put("rule_desc", txtRule);

        //To save inside the document of the userID, under the rules collection
        fStoreRef.collection("establishments").document(userId).collection("coworking-space-cancellation-policy").document(autoId).set(coSpacePolicy);
        coSpacePolicy();
    }
}

