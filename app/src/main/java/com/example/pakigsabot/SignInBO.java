package com.example.pakigsabot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
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

import com.example.pakigsabot.MonthlySubscriptionBO.PayMonthlySub;
import com.example.pakigsabot.NavBarBO.BottomNavigationBO;
import com.example.pakigsabot.SignUpRequirementBO.AgreementScreen;
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

public class SignInBO extends AppCompatActivity {

    ImageView prev;
    TextView signup, forgotPassTxtView;
    TextInputEditText emailAddEditTxt,passEditTxt;
    TextInputLayout emailTxtInputL, passTxtInputL;
    Button signInBtn;
    ProgressBar progressSI;
    FirebaseAuth fAuth2;
    String est_id, expDate, dateToday,status;
    FirebaseFirestore fStore;
    FirebaseUser user;

    public static String passwordAuth;
    List<String> listDateInDates, listCancellationDates, listTransactionDates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_bo);

        //References::
        refs();

        //Reminder Notification::
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Alert","Alert", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                welcomeScreen();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agreementScreen();
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInBusinessStaff();
            }
        });

        forgotPassTxtView.setOnClickListener(new View.OnClickListener() {
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
                                Toast.makeText(SignInBO.this, "Reset Link Sent to your Email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignInBO.this, "ERROR! Reset Link is NOT SENT " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
        prev = findViewById(R.id.backBtnSignIn);
        signup = findViewById(R.id.signUpTxtView);
        signInBtn = findViewById(R.id.signInBtnn);
        emailAddEditTxt = findViewById(R.id.emailAddEditTxtSI);
        passEditTxt = findViewById(R.id.passwordEditTxtSI);
        emailTxtInputL = findViewById(R.id.emailTxtInputLayout);
        passTxtInputL = findViewById(R.id.passwordTextInputLayout);
        progressSI = findViewById(R.id.progressBarSignIn);
        forgotPassTxtView = findViewById(R.id.forgotPassTxtView);
        fAuth2 = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }

    public void welcomeScreen(){
        Intent intent = new Intent(getApplicationContext(), MainActivityBO.class);
        startActivity(intent);
    }

    public void agreementScreen(){
        Intent intent = new Intent(getApplicationContext(), AgreementScreen.class);
        startActivity(intent);
    }

    //Validations for Signing In on the Application
    public boolean signInBusinessStaff(){

        //Variables::
        boolean isValid = true;
        String email = emailAddEditTxt.getText().toString();
        String pass = passEditTxt.getText().toString();

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
                    Toast.makeText(SignInBO.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                    passEditTxt.requestFocus();
                }return handled;
            }
        });

        if(isValid){
            progressSI.setVisibility(View.VISIBLE);

            //Authenticate User::
            fAuth2.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        est_id = fAuth2.getCurrentUser().getUid();
                        user = fAuth2.getInstance().getCurrentUser();

                        /*//Email Verification::
                        if(user.isEmailVerified()){*/
                        DocumentReference docRef = fStore.collection("establishments").document(est_id);
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("est_password", pass);
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                //Check if user's account is Free or Premium:
                                DocumentReference documentReference = fStore.collection("establishments").document(est_id);
                                documentReference.addSnapshotListener(SignInBO.this, new EventListener<DocumentSnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                                        status = documentSnapshot.getString("est_status");

                                        if(status.equalsIgnoreCase("Free")){
                                            //Notification Alert
                                            notificationAlert();

                                            AlertDialog.Builder alert = new AlertDialog.Builder(SignInBO.this)
                                                    .setTitle("Subscription Renewal Alert!")
                                                    .setMessage("Monthly Subscription has Expired")
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            Toast.makeText(SignInBO.this, "Pay Monthly Subscription Fee ", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(getApplicationContext(), PayMonthlySub.class);
                                                            startActivity(intent);
                                                            progressSI.setVisibility(View.GONE);
                                                        }
                                                    });
                                            alert.show();
                                        }else{
                                            //Check if monthly subscription has expired::
                                            checkExpDate();
                                        }
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignInBO.this, "Error! Incorrect credentials", Toast.LENGTH_SHORT).show();
                                progressSI.setVisibility(View.GONE);
                            }
                        });
                        /*}else{
                            user.sendEmailVerification();
                            Toast.makeText(SignIn.this, "Check your email to verify your account!", Toast.LENGTH_SHORT).show();
                            progressSI.setVisibility(View.GONE);
                        }*/
                    }else{
                        passEditTxt.setText(null);
                        Toast.makeText(SignInBO.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressSI.setVisibility(View.GONE);
                    }
                }
            });
        } else{
            Toast.makeText(SignInBO.this, "Please Input All Fields", Toast.LENGTH_SHORT).show();
            progressSI.setVisibility(View.GONE);
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

    private void checkExpDate(){
        //Check if subscription date has expired
        //Getting the date today.
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        dateToday = df.format(date);

        DocumentReference documentReference = fStore.collection("est-subscriptions").document(est_id);
        documentReference.addSnapshotListener(SignInBO.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                expDate = documentSnapshot.getString("subs_expDate");
            }
        });


        try {
            notificationAlert();

            Date date1, date2;
            SimpleDateFormat dates = new SimpleDateFormat("MM/dd/yyyy");
            date1 = dates.parse(dateToday);
            date2 = dates.parse(expDate);
            long difference = date1.getTime() - date2.getTime();
            if (difference <= 0) {
                Toast.makeText(SignInBO.this, "Welcome to Pakigsa-Bot", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), BottomNavigationBO.class));
                finish();
                //Clear fields
                emailAddEditTxt.setText(null);
                passEditTxt.setText(null);
            } else {
                //Update Account Status DB::
                updateAccountStatusDB();
                AlertDialog.Builder alert = new AlertDialog.Builder(SignInBO.this)
                        .setTitle("Subscription Renewal Alert!")
                        .setMessage("Monthly Subscription has Expired")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(SignInBO.this, "Pay Monthly Subscription Fee ", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), PayMonthlySub.class);
                                startActivity(intent);
                                progressSI.setVisibility(View.GONE);
                            }
                        });
                alert.show();
            }
        } catch (Exception exception) {
            Toast.makeText(SignInBO.this, "Tap Sign In Again", Toast.LENGTH_SHORT).show();
            progressSI.setVisibility(View.GONE);
        }
    }

    private void updateAccountStatusDB(){
        DocumentReference docuRef = fStore.collection("establishments").document(est_id);
        Map<String,Object> edited = new HashMap<>();
        edited.put("est_status", "Free");
        docuRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignInBO.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void notificationAlert(){
        //List of Check-in Dates::
        fStore.collection("reservations")
                .whereEqualTo("reserv_est_ID", est_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listDateInDates = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                listDateInDates.add(document.getString("reserv_dateIn"));
                            }
                            Log.d("TAG", listDateInDates.toString());
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

        //List of Transaction Dates::
        fStore.collection("reservations")
                .whereEqualTo("reserv_est_ID", est_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listTransactionDates= new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                listTransactionDates.add(document.getString("reserv_transactionDate"));
                            }
                            Log.d("TAG", listTransactionDates.toString());
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

        //List of Cancellation Dates::
        fStore.collection("cancelled-reservations")
                .whereEqualTo("cancelReserv_est_ID", est_id)
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

            if(listTransactionDates.contains(dateToday)){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(SignInBO.this, "Alert");
            builder.setContentTitle("Pending Reservation Alert!");
            builder.setContentText("A Reservation has been REQUESTED!");
            builder.setSmallIcon(R.mipmap.icnew_launchercircle);
            builder.setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(SignInBO.this);
            managerCompat.notify(1, builder.build());
            }else if(listCancellationDates.contains(dateToday)){
                NotificationCompat.Builder builder = new NotificationCompat.Builder(SignInBO.this, "Alert");
                builder.setContentTitle("Cancelled Reservation Alert!");
                builder.setContentText("A reservation has been CANCELLED - " + tomorrowDateStr);
                builder.setSmallIcon(R.mipmap.icnew_launchercircle);
                builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(SignInBO.this);
                managerCompat.notify(1, builder.build());
            } else if(listDateInDates.contains(tomorrowDateStr)){
                NotificationCompat.Builder builder = new NotificationCompat.Builder(SignInBO.this, "Alert");
                builder.setContentTitle("Notification Alert!");
                builder.setContentText("\nYou have a Reservation Tomorrrow - " + tomorrowDateStr);
                builder.setSmallIcon(R.mipmap.icnew_launchercircle);
                builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(SignInBO.this);
                managerCompat.notify(1, builder.build());
            }else if(listTransactionDates.contains(dateToday) && listCancellationDates.contains(dateToday)){
                NotificationCompat.Builder builder = new NotificationCompat.Builder(SignInBO.this, "Alert");
                builder.setContentTitle("Pending & Cancelled Reservation Alert!");
                builder.setContentText(">>Check Reservations");
                builder.setSmallIcon(R.mipmap.icnew_launchercircle);
                builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(SignInBO.this);
                managerCompat.notify(1, builder.build());
            }
            else if(listTransactionDates.contains(dateToday) && listDateInDates.contains(tomorrowDateStr)){
                NotificationCompat.Builder builder = new NotificationCompat.Builder(SignInBO.this, "Alert");
                builder.setContentTitle("Pending Reservation Alert!");
                builder.setContentText("Reservation Tomorrow - " + tomorrowDateStr);
                builder.setSmallIcon(R.mipmap.icnew_launchercircle);
                builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(SignInBO.this);
                managerCompat.notify(1, builder.build());
            }
            else if(listCancellationDates.contains(dateToday) && listDateInDates.contains(tomorrowDateStr)){
                NotificationCompat.Builder builder = new NotificationCompat.Builder(SignInBO.this, "Alert");
                builder.setContentTitle("Cancelled Reservation Alert!");
                builder.setContentText("Reservation Tomorrow - " + tomorrowDateStr);
                builder.setSmallIcon(R.mipmap.icnew_launchercircle);
                builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(SignInBO.this);
                managerCompat.notify(1, builder.build());
            }
            else if(listTransactionDates.contains(dateToday) && listCancellationDates.contains(dateToday) && listDateInDates.contains(tomorrowDateStr)){
                NotificationCompat.Builder builder = new NotificationCompat.Builder(SignInBO.this, "Alert");
                builder.setContentTitle("Pending and Cancelled Reservation Alert!");
                builder.setContentText("Reservation Tomorrow - " + tomorrowDateStr);
                builder.setSmallIcon(R.mipmap.icnew_launchercircle);
                builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(SignInBO.this);
                managerCompat.notify(1, builder.build());
            }
    }
}
