package com.example.pakigsabot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.example.pakigsabot.Profile.Profile;
import com.example.pakigsabot.Reservations.ViewReservations;
import com.example.pakigsabot.SignUpRequirements.AgreementScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Signin extends AppCompatActivity {

    //Initialization of variables::
    TextView signup, forgotPassTxtLink;
    TextInputEditText emailAddEditTxt,passEditTxt;
    TextInputLayout emailTxtInputL, passTxtInputL;
    Button signInBtn;
    ImageView backBtnSignIn;
    ProgressBar progressBarSI;
    FirebaseAuth fAuth2;
    String cust_id, dateToday, expDate, status;
    FirebaseFirestore fStore;
    FirebaseUser user;
    ProgressDialog progressDialog;
    SearchView searchView;
    List<String> listDates, listCancellationDates, listConfirmDates;

    public static String passwordAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        //References::
        refs();

        //Reminder Notification::
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Alert","Alert", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

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
                        user = fAuth2.getInstance().getCurrentUser();

                        //Email Verification::
                        if(user.isEmailVerified()){
                            //Save data to database
                            DocumentReference docRef = fStore.collection("customers").document(cust_id);
                            Map<String,Object> edited = new HashMap<>();
                            edited.put("cust_password", pass);
                            docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    //Check if user's account is Free or Premium:
                                    DocumentReference documentReference = fStore.collection("customers").document(cust_id);
                                    documentReference.addSnapshotListener(Signin.this, new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                                            status = documentSnapshot.getString("cust_status");

                                            if(status.equalsIgnoreCase("Free")){
                                                try{
                                                    //Notification Alert
                                                    notificationAlert();

                                                    //Redirected to Home Page::
                                                    Toast.makeText(Signin.this, "Welcome to Pakigsa-Bot", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getApplicationContext(), BottomNavigation.class));
                                                    finish();

                                                    //Clear fields
                                                    passEditTxt.setText(null);
                                                    emailAddEditTxt.setText(null);
                                                }catch (Exception e){
                                                    Toast.makeText(Signin.this, "Double Tap to Sign in", Toast.LENGTH_SHORT).show();
                                                    progressBarSI.setVisibility(View.GONE);
                                                }
                                            }else{
                                                //Check if subscription date has expired
                                                checkDateExpiration();
                                            }
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Signin.this, "Error! Incorrect credentials", Toast.LENGTH_SHORT).show();
                                    progressBarSI.setVisibility(View.GONE);
                                }
                            });
                        }else{
                            user.sendEmailVerification();
                            Toast.makeText(Signin.this, "Check your email to verify your account!", Toast.LENGTH_SHORT).show();
                            progressBarSI.setVisibility(View.GONE);
                        }
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

    private void checkDateExpiration(){
        //Getting the date today.
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        dateToday = df.format(date);

        DocumentReference documentReference = fStore.collection("premium-subscriptions").document(cust_id);
        documentReference.addSnapshotListener(Signin.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                expDate = documentSnapshot.getString("subs_expDate");
            }
        });

        try {
            //Notification Alert
            notificationAlert();

            Date date1, date2;
            SimpleDateFormat dates = new SimpleDateFormat("MM/dd/yyyy");
            date1 = dates.parse(dateToday);
            date2 = dates.parse(expDate);
            long difference = date1.getTime() - date2.getTime();
            if (difference <= 0) {
                Toast.makeText(Signin.this, "Premium Account", Toast.LENGTH_SHORT).show();
            } else {
                //Update Account Status DB::
                updateAccountStatusDB();
                AlertDialog.Builder alert = new AlertDialog.Builder(Signin.this)
                        .setTitle("Subscription Renewal Alert!")
                        .setMessage("Premium Subscription has Expired")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(Signin.this, "Go to Profile to Subscribe Premium", Toast.LENGTH_SHORT).show();
                            }
                        });
                alert.show();
            }
            //Redirected to Home Page::
            Toast.makeText(Signin.this, "Welcome to Pakigsa-Bot", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), BottomNavigation.class));
            finish();
            //Clear fields
            passEditTxt.setText(null);
            emailAddEditTxt.setText(null);
        } catch (Exception exception) {
            Toast.makeText(Signin.this, "Double Tap to Sign in", Toast.LENGTH_SHORT).show();
            progressBarSI.setVisibility(View.GONE);
        }
    }

    private void updateAccountStatusDB(){
        DocumentReference documentRef = fStore.collection("customers").document(cust_id);
        Map<String,Object> edited = new HashMap<>();
        edited.put("cust_status", "Free");
        documentRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Signin.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void notificationAlert(){
        //List of Check-in Dates::
        fStore.collection("reservations")
                .whereEqualTo("reserv_cust_ID", cust_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listDates = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                listDates.add(document.getString("reserv_dateIn"));
                            }
                            Log.d("TAG", listDates.toString());
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

        //List of Confirmation Dates::
        fStore.collection("reservations")
                .whereEqualTo("reserv_cust_ID", cust_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listConfirmDates = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                listConfirmDates.add(document.getString("reserv_confirmedDate"));
                            }
                            Log.d("TAG", listConfirmDates.toString());
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

        //List of Cancellation Dates::
        fStore.collection("cancelled-reservations")
                .whereEqualTo("cancelReserv_cust_ID", cust_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listCancellationDates = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                listCancellationDates.add(document.getString("cancelReserv_cancelledDate"));
                            }
                            Log.d("TAG", listCancellationDates.toString());
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

        //Getting the date today.
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        dateToday = df.format(date);

        //Getting date tomorrow
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        Date tomorrowDate = c.getTime();
        SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
        String tomorrowDateStr = df2.format(tomorrowDate);

        if(listDates.contains(tomorrowDateStr) && listConfirmDates.contains(dateToday) && listCancellationDates.contains(dateToday)) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(Signin.this, "Alert");
            builder.setContentTitle("Confirmed & Cancelled Reservation Alert!");
            builder.setContentText("You have a Reservation Tomorrrow - " + tomorrowDateStr);
            builder.setSmallIcon(R.mipmap.icnew_launchercircle);
            builder.setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Signin.this);
            managerCompat.notify(1, builder.build());
        }else if(listDates.contains(tomorrowDateStr) && listConfirmDates.contains(dateToday)){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(Signin.this, "Alert");
            builder.setContentTitle("Confirmed Reservation Alert!");
            builder.setContentText("You have a Reservation Tomorrrow - " + tomorrowDateStr);
            builder.setSmallIcon(R.mipmap.icnew_launchercircle);
            builder.setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Signin.this);
            managerCompat.notify(1, builder.build());
        }else if(listDates.contains(tomorrowDateStr) && listCancellationDates.contains(dateToday)){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(Signin.this, "Alert");
            builder.setContentTitle("Cancelled Reservation Alert!");
            builder.setContentText("Reservation Tomorrrow - " + tomorrowDateStr);
            builder.setSmallIcon(R.mipmap.icnew_launchercircle);
            builder.setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Signin.this);
            managerCompat.notify(1, builder.build());
        }else if(listConfirmDates.contains(dateToday) && listCancellationDates.contains(dateToday)){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(Signin.this, "Alert");
            builder.setContentTitle("Confirmed & Cancelled Reservation Alert!");
            builder.setContentText(">>Check Confirmed and Cancelled Reservations");
            builder.setSmallIcon(R.mipmap.icnew_launchercircle);
            builder.setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Signin.this);
            managerCompat.notify(1, builder.build());
        }else if(listDates.contains(tomorrowDateStr)){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(Signin.this, "Alert");
            builder.setContentTitle("Notification Alert!");
            builder.setContentText("\nYou have a Reservation Tomorrrow - " + tomorrowDateStr);
            builder.setSmallIcon(R.mipmap.icnew_launchercircle);
            builder.setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Signin.this);
            managerCompat.notify(1, builder.build());
        }else if(listConfirmDates.contains(dateToday)){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(Signin.this, "Alert");
            builder.setContentTitle("Confirmed Reservation Alert!");
            builder.setContentText("A Reservation has been CONFIRMED!");
            builder.setSmallIcon(R.mipmap.icnew_launchercircle);
            builder.setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Signin.this);
            managerCompat.notify(1, builder.build());
        }else if(listCancellationDates.contains(dateToday)) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(Signin.this, "Alert");
            builder.setContentTitle("Cancelled Reservation Alert!");
            builder.setContentText("A Reservation has been CANCELLED!");
            builder.setSmallIcon(R.mipmap.icnew_launchercircle);
            builder.setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Signin.this);
            managerCompat.notify(1, builder.build());
        }
    }
}