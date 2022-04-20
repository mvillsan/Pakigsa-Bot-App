package com.example.pakigsabot;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pakigsabot.NavBar.BottomNavigation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    ImageView prev;
    TextView signin;
    TextInputLayout firstNameL, lastNameL, phoneNumL, birthdateL, emailAddL, passwordL;
    TextInputEditText firstNameEditTxt, lastNameEditTxt, phoneNumEditTxt, birthdateEditTxt, editTxtEmailAdd, editTxtPass;
    RadioGroup genderRG;
    RadioButton maleRB, femaleRB;
    Button createAccBtnn;
    ProgressBar progressBarSU;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String cust_id;
    String genderLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //References::
        refs();

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                welcomeScreen();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInScreen();
            }
        });

        createAccBtnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpCustomer();
            }
        });

        //Validations
        firstNameEditTxt.addTextChangedListener(new ValidationTextWatcher(firstNameEditTxt));
        lastNameEditTxt.addTextChangedListener(new ValidationTextWatcher(lastNameEditTxt));
        phoneNumEditTxt.addTextChangedListener(new ValidationTextWatcher(phoneNumEditTxt));
        birthdateEditTxt.addTextChangedListener(new ValidationTextWatcher(birthdateEditTxt));
        editTxtEmailAdd.addTextChangedListener(new ValidationTextWatcher(editTxtEmailAdd));
        editTxtPass.addTextChangedListener(new ValidationTextWatcher(editTxtPass));
    }

    public void refs(){
        prev = findViewById(R.id.backBtnSU);
        signin = findViewById(R.id.signinBtnSUS);
        firstNameL = findViewById(R.id.firstNameInputLayout);
        lastNameL = findViewById(R.id.lastNameInputLayout);
        phoneNumL = findViewById(R.id.mobileInputLayout);
        birthdateL = findViewById(R.id.bdayInputLayout);
        emailAddL = findViewById(R.id.emailAddInputLayoutSU);
        passwordL = findViewById(R.id.passInputLayoutSU);
        firstNameEditTxt = findViewById(R.id.firstNameEditTxt);
        lastNameEditTxt = findViewById(R.id.lastNameEditTxt);
        phoneNumEditTxt = findViewById(R.id.mobileEditTxt);
        birthdateEditTxt = findViewById(R.id.bdayEditText);
        editTxtEmailAdd = findViewById(R.id.emailAddEditTextSU);
        editTxtPass = findViewById(R.id.passEditTextSU);
        genderRG = findViewById(R.id.genderRadioGroup);
        maleRB = findViewById(R.id.maleRadioBtn);
        femaleRB = findViewById(R.id.femaleRadioBtn);
        createAccBtnn = findViewById(R.id.createAccBtn);
        progressBarSU = findViewById(R.id.progressBarSignUp);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }

    public void welcomeScreen(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void signInScreen(){
        Intent intent = new Intent(getApplicationContext(), Signin.class);
        startActivity(intent);
    }


    //Validations for Signing Up on the Application
    public boolean signUpCustomer(){
        //Variables::
        boolean isValid = true;
        String email = editTxtEmailAdd.getText().toString().trim();
        String pass = editTxtPass.getText().toString().trim();
        String fName = firstNameEditTxt.getText().toString();
        String lName = lastNameEditTxt.getText().toString();
        String phoneNum = phoneNumEditTxt.getText().toString();
        String bday = birthdateEditTxt.getText().toString();

        //First Name Validation
        if (firstNameEditTxt.getText().toString().trim().isEmpty()) {
            firstNameL.setError(getString(R.string.fName_req));
        } else {
            String firstName = firstNameEditTxt.getText().toString();
            Boolean  validFName = firstName.matches("[A-Za-z][A-Za-z ]*+");
            if (!validFName) {
                firstNameL.setError("Invalid First Name, ex: John Anthony");
                requestFocus(firstNameEditTxt);
                return false;
            } else {
                firstNameL.setErrorEnabled(false);
                firstNameL.setError("");
            }
        }

        //Last Name Validation
        if (lastNameEditTxt.getText().toString().trim().isEmpty()) {
            lastNameL.setError(getString(R.string.lName_req));
        } else {
            String lastName = lastNameEditTxt.getText().toString();
            Boolean  validLName = lastName.matches("[A-Za-z][A-Za-z ]*+");
            if (!validLName) {
                lastNameL.setError("Invalid Last Name, ex: Doe");
                requestFocus(lastNameEditTxt);
                return false;
            } else {
                lastNameL.setErrorEnabled(false);
                lastNameL.setError("");
            }
        }

        //Phone Number Validation
        if (phoneNumEditTxt.getText().toString().trim().isEmpty()) {
            phoneNumL.setError(getString(R.string.phoneNum_req));
        } else {
            String phone = phoneNumEditTxt.getText().toString();
            Boolean  validPhone = Patterns.PHONE.matcher(phone).matches();
            if (!validPhone) {
                phoneNumL.setError("Invalid Phone Number");
                requestFocus(phoneNumEditTxt);
                return false;
            } else {
                phoneNumL.setErrorEnabled(false);
                phoneNumL.setError("");
            }
        }

        //Birthdate Validation
        if (birthdateEditTxt.getText().toString().trim().isEmpty()) {
            birthdateL.setError(getString(R.string.bDay_req));
        } else {
            String bdate = birthdateEditTxt.getText().toString();
            Boolean validBdate = bdate.matches("^(?:0[1-9]|[12]\\d|3[01])([\\/.-])(?:0[1-9]|1[012])\\1(?:19|20)\\d\\d$");
            if (!validBdate) {
                birthdateL.setError("Invalid Birthdate: example: DD/MM/YYYY");
                requestFocus(birthdateEditTxt);
                return false;
            } else {
                birthdateL.setErrorEnabled(false);
                birthdateL.setError("");
            }
        }

        //Email Address Validation
        if(editTxtEmailAdd.getText().toString().isEmpty()){
            emailAddL.setError(getString(R.string.email_req));
            isValid = false;
        } else{
            String emailId = editTxtEmailAdd.getText().toString();
            boolean validEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches();
            if (!validEmail) {
                emailAddL.setError("Invalid Email Address, ex: abc@example.com");
                requestFocus(emailAddL);
                return false;
            } else {
                emailAddL.setErrorEnabled(false);
                emailAddL.setError("");
            }
        }

        //Password Validation
        if(editTxtPass.getText().toString().isEmpty()){
            passwordL.setError(getString(R.string.pass_req));
            isValid = false;
        } else{
            if(editTxtPass.getText().toString().length() < 8) {
                passwordL.setError(getString(R.string.pass_min));
                requestFocus(editTxtPass);
                return false;
            }else{
                passwordL.setEnabled(false);
                passwordL.setError("");
            }
        }

        if(isValid){
            progressBarSU.setVisibility(View.VISIBLE);

            //Get value of RadioButton
            if(genderRG.getCheckedRadioButtonId() == -1){
                Toast.makeText(Signup.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            }else{
                if(maleRB.isChecked()){
                    genderLabel = maleRB.getText().toString();
                }else{
                    genderLabel = femaleRB.getText().toString();
                }
            }

            //Register the user in Firebase::
            fAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Signup.this, "Account has been Created Successfully", Toast.LENGTH_SHORT).show();
                        cust_id = fAuth.getCurrentUser().getUid();
                        DocumentReference docRef = fStore.collection("customers").document(cust_id);
                        Map<String,Object> customer = new HashMap<>();
                        customer.put("cust_id",cust_id);
                        customer.put("cust_fname", fName);
                        customer.put("cust_lname", lName);
                        customer.put("cust_phoneNum", phoneNum);
                        customer.put("cust_gender", genderLabel);
                        customer.put("cust_birthDate", bday);
                        customer.put("cust_email", email);
                        customer.put("cust_password", pass);
                        customer.put("cust_status", "Free");
                        docRef.set(customer).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("SignUp", "onSignUpSuccess: Data is Stored for " + cust_id);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("SignUp", "onSignUpFailure: " + e.toString());
                            }
                        });
                        Toast.makeText(Signup.this, R.string.signUp_success, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), BottomNavigation.class));
                        firstNameEditTxt.setText(null);
                        lastNameEditTxt.setText(null);
                        phoneNumEditTxt.setText(null);
                        birthdateEditTxt.setText(null);
                        editTxtEmailAdd.setText(null);
                        editTxtPass.setText(null);
                    }else{
                        Toast.makeText(Signup.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressBarSU.setVisibility(View.GONE);
                    }
                }
            });
        }

        //User is Log-in already::
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), Signup.class));
            finish();
        }

        return true;
    }

    //Setting FOCUS
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    //First Name Validations
    private boolean validateFirstNameSU() {
        if (firstNameEditTxt.getText().toString().trim().isEmpty()) {
            firstNameL.setError(getString(R.string.fName_req));
        } else {
            String firstName = firstNameEditTxt.getText().toString();
            Boolean  isValid = firstName.matches("[A-Za-z][A-Za-z ]*+");
            if (!isValid) {
                firstNameL.setError("Invalid First Name, ex: John Anthony");
                requestFocus(firstNameEditTxt);
                return false;
            } else {
                firstNameL.setErrorEnabled(false);
                firstNameL.setError("");
            }
        }
        return true;
    }

    //Last Name Validations
    private boolean validateLastNameSU(){
        if (lastNameEditTxt.getText().toString().trim().isEmpty()) {
            lastNameL.setError(getString(R.string.lName_req));
        } else {
            String lastName = lastNameEditTxt.getText().toString();
            Boolean  validLName = lastName.matches("[A-Za-z][A-Za-z ]*+");
            if (!validLName) {
                lastNameL.setError("Invalid Last Name, ex: Doe");
                requestFocus(lastNameEditTxt);
                return false;
            } else {
                lastNameL.setErrorEnabled(false);
                lastNameL.setError("");
            }
        }
        return true;
    }

    //Phone Num Validations
    private boolean validatePhoneNumSU(){
        if (phoneNumEditTxt.getText().toString().trim().isEmpty()) {
            phoneNumL.setError(getString(R.string.phoneNum_req));
        } else {
            String phone = phoneNumEditTxt.getText().toString();
            Boolean  validPhone = phone.matches("^(?:\\d{2}-\\d{3}-\\d{3}-\\d{3}|\\d{11})$");
            if (!validPhone) {
                phoneNumL.setError("Invalid Phone Number");
                requestFocus(phoneNumEditTxt);
                return false;
            } else {
                phoneNumL.setErrorEnabled(false);
                phoneNumL.setError("");
            }
        }
        return true;
    }

    //Birthdate Validations
    private boolean validateBirthdateSU(){
        if (birthdateEditTxt.getText().toString().trim().isEmpty()) {
            birthdateL.setError(getString(R.string.bDay_req));
        } else {
            String bdate = birthdateEditTxt.getText().toString();
            Boolean validBdate = bdate.matches("^(?:0[1-9]|[12]\\d|3[01])([\\/.-])(?:0[1-9]|1[012])\\1(?:19|20)\\d\\d$");
            if (!validBdate) {
                birthdateL.setError("Invalid Birthdate: example: DD/MM/YYYY");
                requestFocus(birthdateEditTxt);
                return false;
            } else {
                birthdateL.setErrorEnabled(false);
                birthdateL.setError("");
            }
        }
        return true;
    }

    //Email address Validations
    private boolean validateEmailSU() {
        if (editTxtEmailAdd.getText().toString().trim().isEmpty()) {
            emailAddL.setError(getString(R.string.email_req));
        } else {
            String emailId = editTxtEmailAdd.getText().toString();
            Boolean  isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches();
            if (!isValid) {
                emailAddL.setError("Invalid Email Address, ex: abc@example.com");
                requestFocus(editTxtEmailAdd);
                return false;
            } else {
                emailAddL.setErrorEnabled(false);
                emailAddL.setError("");
            }
        }
        return true;
    }

    //Password Validations
    private boolean validatePasswordSU() {
        if (editTxtPass.getText().toString().trim().isEmpty()) {
            passwordL.setError(getString(R.string.pass_req));
            requestFocus(editTxtPass);
            return false;
        }else if(editTxtPass.getText().toString().length() < 8){
            passwordL.setError(getString(R.string.pass_min));
            requestFocus(editTxtPass);
            return false;
        }
        else {
            passwordL.setErrorEnabled(false);
            passwordL.setError("");
        }
        return true;
    }

    //ValidationTextWatcher
    private class ValidationTextWatcher implements TextWatcher {
        private View view;
        private ValidationTextWatcher(View view) {
            this.view = view;
        }
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.firstNameEditTxt:
                    validateFirstNameSU();
                    break;
                case R.id.lastNameEditTxt:
                    validateLastNameSU();
                    break;
                case R.id.mobileEditTxt:
                    validatePhoneNumSU();
                    break;
                case R.id.bdayEditText:
                    validateBirthdateSU();
                    break;
                case R.id.emailAddEditTextSU:
                    validateEmailSU();
                    break;
                case R.id.passEditTextSU:
                    validatePasswordSU();
                    break;
            }
        }
    }
}