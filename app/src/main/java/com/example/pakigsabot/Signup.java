package com.example.pakigsabot;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Signup extends AppCompatActivity {

    ImageView prev;
    TextView signin;
    TextInputLayout firstNameL, lastNameL, phoneNumL, birthdateL, emailAddL, passwordL;
    TextInputEditText firstNameEditTxt, lastNameEditTxt, phoneNumEditTxt, birthdateEditTxt, editTxtEmailAdd, editTxtPass;
    RadioGroup genderRG;
    RadioButton maleRB, femaleRB;
    Button createAccBtnn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

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

       // passwordEditTxt.addTextChangedListener(new ValidationTextWatcher(passwordEditTxt));
       // emailAddEditTxt.addTextChangedListener(new ValidationTextWatcher(emailAddEditTxt));
    }

    public void refs(){
        prev = findViewById(R.id.backBtnSU);
        signin = findViewById(R.id.signinBtnSUS);
        firstNameL = findViewById(R.id.firstNameInputLayout);
        lastNameL = findViewById(R.id.lastNameInputLayout);
        phoneNumL = findViewById(R.id.mobileInputLayout);
        birthdateL = findViewById(R.id.bdayInputLayout);
        emailAddL = findViewById(R.id.emailTxtInputLayout);
        passwordL = findViewById(R.id.passwordTextInputLayout);
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
    public void signUpCustomer(){

        boolean isValid = true;

/*        if(editTxtEmailAdd.getText().toString().isEmpty()){
            emailAddL.setError(getString(R.string.email_req));
            isValid = false;
        } else{
            emailAddL.setEnabled(false);
            emailAddL.setError(null);
        }

        if(editTxtPass.getText().toString().isEmpty()){
            passwordL.setError(getString(R.string.pass_req));
            isValid = false;
        } else{
            passwordL.setEnabled(false);
            passwordL.setError(null);
        }*/

        if(isValid){
            Toast.makeText(Signup.this, R.string.signUp_success, Toast.LENGTH_SHORT).show();
        }
    }

    //Setting FOCUS
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
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