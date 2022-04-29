package com.example.pakigsabot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pakigsabot.NavBar.BottomNavigation;
import com.example.pakigsabot.SignUpRequirements.AgreementScreen;
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

public class Signin extends AppCompatActivity {

    TextView signup, forgotPassTxtLink;
    TextInputEditText emailAddEditTxt,passEditTxt;
    TextInputLayout emailTxtInputL, passTxtInputL;
    Button signInBtn;
    ImageView backBtnSignIn;
    ProgressBar progressBarSI;
    FirebaseAuth fAuth2;
    String cust_id;
    FirebaseFirestore fStore;

    public static String passwordAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        //References::
        refs();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agreementScreen();
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInCustomer();
            }
        });

        backBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                welcomeScreen();
            }
        });

        //Reset Password Method
        forgotPassTxtLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Do you want to Reset Password ?");
                passwordResetDialog.setMessage("Enter your Email Address to Receive the Reset Password Link");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Extract the email add and send the reset password link

                        String emailAdd = resetMail.getText().toString();
                        fAuth2.sendPasswordResetEmail(emailAdd).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Signin.this, "Reset Link Sent to your Email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Signin.this, "ERROR! Reset Link is NOT SENT " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //close the dialog
                    }
                });

                passwordResetDialog.create().show();
            }
        });

        //Validations
        passEditTxt.addTextChangedListener(new ValidationTextWatcher(passEditTxt));
        emailAddEditTxt.addTextChangedListener(new ValidationTextWatcher(emailAddEditTxt));

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("signin") != null){
                Toast.makeText(getApplicationContext(), "data: " + bundle.getString("signin"),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void refs(){
        signup = findViewById(R.id.signUpTxtView);
        signInBtn = findViewById(R.id.signInBtnn);
        emailAddEditTxt = findViewById(R.id.emailAddEditTxtSI);
        passEditTxt = findViewById(R.id.passwordEditTxtSI);
        emailTxtInputL = findViewById(R.id.emailTxtInputLayout);
        passTxtInputL = findViewById(R.id.passwordTextInputLayout);
        fAuth2 = FirebaseAuth.getInstance();
        progressBarSI = findViewById(R.id.progressBarSignIn);
        forgotPassTxtLink = findViewById(R.id.forgotPassTxtView);
        fStore = FirebaseFirestore.getInstance();
        backBtnSignIn = findViewById(R.id.backBtnSignIn);
    }

    public void welcomeScreen(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void agreementScreen(){
        Intent intent = new Intent(getApplicationContext(), AgreementScreen.class);
        startActivity(intent);
    }

    //Validations for Signing In on the Application
    public boolean signInCustomer(){

        //Variables::
        String email = emailAddEditTxt.getText().toString().trim();
        String pass = passEditTxt.getText().toString().trim();
        boolean isValid = true;

        if(emailAddEditTxt.getText().toString().isEmpty()){
            emailTxtInputL.setError(getString(R.string.email_req));
            isValid = false;
        } else{
            String emailId = emailAddEditTxt.getText().toString();
            boolean validEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches();
            if (!validEmail) {
                emailTxtInputL.setError("Invalid Email Address, ex: abc@example.com");
                requestFocus(emailAddEditTxt);
                return false;
            } else {
                emailTxtInputL.setErrorEnabled(false);
                emailTxtInputL.setError("");
            }
        }

        if(passEditTxt.getText().toString().isEmpty()){
            passTxtInputL.setError(getString(R.string.pass_req));
            isValid = false;
        } else{
            if(passEditTxt.getText().toString().length() < 8) {
                passTxtInputL.setError(getString(R.string.pass_min));
                requestFocus(passEditTxt);
                return false;
            }else{
                passTxtInputL.setError("");
            }
        }

        passEditTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if(actionId == EditorInfo.IME_ACTION_SEND || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    passwordAuth = passEditTxt.getText().toString();
                    handled = true;
                }else{
                    Toast.makeText(Signin.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                    passEditTxt.requestFocus();
                }return handled;
            }
        });

        if(isValid){
            progressBarSI.setVisibility(View.VISIBLE);

            //Authenticate User::
            fAuth2.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        cust_id = fAuth2.getCurrentUser().getUid();

                        //Save data to database
                        DocumentReference docRef = fStore.collection("customers").document(cust_id);
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("cust_password", pass);
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Signin.this, "Welcome to Pakigsa-Bot", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), BottomNavigation.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Signin.this, "No Changes has been made", Toast.LENGTH_SHORT).show();
                                progressBarSI.setVisibility(View.GONE);
                            }
                        });
                        //Clear fields
                        passEditTxt.setText(null);
                        emailAddEditTxt.setText(null);
                    }else{
                        passEditTxt.setText(null);
                        Toast.makeText(Signin.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressBarSI.setVisibility(View.GONE);
                    }
                }
            });
        }
        else{
            Toast.makeText(Signin.this, "Please Input All Fields", Toast.LENGTH_SHORT).show();
            progressBarSI.setVisibility(View.GONE);
        }
        return true;
    }

    //Setting FOCUS
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    //Email address Validations
    private boolean validateEmail() {
        if (emailAddEditTxt.getText().toString().trim().isEmpty()) {
            emailTxtInputL.setError(getString(R.string.email_req));
        } else {
            String emailId = emailAddEditTxt.getText().toString();
            Boolean  isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches();
            if (!isValid) {
                emailTxtInputL.setError("Invalid Email Address, ex: abc@example.com");
                requestFocus(emailAddEditTxt);
                return false;
            } else {
                emailTxtInputL.setErrorEnabled(false);
                emailTxtInputL.setError("");
            }
        }
        return true;
    }

    //Password Validations
    private boolean validatePassword() {
        if (passEditTxt.getText().toString().trim().isEmpty()) {
            passTxtInputL.setError(getString(R.string.pass_req));
            requestFocus(passEditTxt);
            return false;
        }else if(passEditTxt.getText().toString().length() < 8){
            passTxtInputL.setError(getString(R.string.pass_min));
            requestFocus(passEditTxt);
            return false;
        }
        else {
            passTxtInputL.setErrorEnabled(false);
            passTxtInputL.setError("");
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
                case R.id.emailAddEditTxtSI:
                    validateEmail();
                    break;
                case R.id.passwordEditTxtSI:
                    validatePassword();
                    break;
            }
        }
    }
}