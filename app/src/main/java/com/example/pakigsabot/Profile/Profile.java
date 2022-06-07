package com.example.pakigsabot.Profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.pakigsabot.LoyaltyTier.LTInfo;
import com.example.pakigsabot.NavigationFragments.EstFavoritesFragment;
import com.example.pakigsabot.NavigationFragments.HelpCenterFragment;
import com.example.pakigsabot.NavigationFragments.HomeFragment;
import com.example.pakigsabot.NavigationFragments.NearbyFragment;
import com.example.pakigsabot.NavigationFragments.ReservationsFragment;
import com.example.pakigsabot.PremiumApp.GoPremiumCA;
import com.example.pakigsabot.PremiumApp.PremiumPrivileges;
import com.example.pakigsabot.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

    //Initialize Variables
    MeowBottomNavigation bottomNavigation;
    TextView genderProfileET,BDateTxtView, statusType, tierTxt;
    ImageView prevBtn, profileImageView, uploadImg, saveProfBtn;
    Button changePassBtn,goPremiumBtn, premiumAccount;
    EditText firstNameProfileET,lastNameProfileET,emailAddProfileET,editTextPhone;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    StorageReference storageRef;
    StorageReference profileRef;
    DocumentReference documentReference;
    DocumentReference docRef;
    DocumentReference docuRef;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //References:
        refs();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("profile") != null){
                Toast.makeText(getApplicationContext(), "data: " + bundle.getString("profile"),
                        Toast.LENGTH_SHORT).show();
            }
        }

        //Fetching Data from FireStore DB
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        userID = fAuth.getCurrentUser().getUid();
        storageRef = FirebaseStorage.getInstance().getReference();

        profileRef = storageRef.child("customers/profile_pictures/"+userID+"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImageView);
            }
        });

        documentReference = fStore.collection("customers").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                firstNameProfileET.setText(value.getString("cust_fname"));
                lastNameProfileET.setText(value.getString("cust_lname"));
                emailAddProfileET.setText(value.getString("cust_email"));
                editTextPhone.setText(value.getString("cust_phoneNum"));
                BDateTxtView.setText(value.getString("cust_birthDate"));
                genderProfileET.setText(value.getString("cust_gender"));
                statusType.setText(value.getString("cust_status"));

                //Check Account Status::
                if(statusType.getText().toString().equalsIgnoreCase("Free")){
                    goPremiumBtn.setVisibility(View.VISIBLE);
                    premiumAccount.setVisibility(View.GONE);
                }else{
                    goPremiumBtn.setVisibility(View.GONE);
                    premiumAccount.setVisibility(View.VISIBLE);
                }
            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragments();
            }
        });

        //Upload Profile Image::
        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadProfImg();
            }
        });

        //Saving Updated Profile Details::
        saveProfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProfileUpdatedDetails();
            }
        });

        //Change Password::
        changePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePass();
            }
        });

        //Go Premium::
        goPremiumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goPremium();
            }
        });

        //Loyalty Tier::
        tierTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loyalty();
            }
        });

        //Premium Account::
        premiumAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                premiumPrivileges();
            }
        });
    }

    public void refs(){
        prevBtn = findViewById(R.id.backBtnProfile);
        goPremiumBtn = findViewById(R.id.goPremiumBtn);
        statusType = findViewById(R.id.statusType);
        firstNameProfileET = findViewById(R.id.firstNameProfileET);
        lastNameProfileET = findViewById(R.id.lastNameProfileET);
        emailAddProfileET = findViewById(R.id.emailAddProfileET);
        editTextPhone = findViewById(R.id.editTextPhone);
        genderProfileET = findViewById(R.id.genderProfileET);
        BDateTxtView = findViewById(R.id.BDateTxtView);
        profileImageView = findViewById(R.id.profileImageView);
        uploadImg = findViewById(R.id.uploadImg);
        saveProfBtn = findViewById(R.id.saveProfBtn);
        changePassBtn = findViewById(R.id.changePassBtn);
        tierTxt = findViewById(R.id.tierTxt);
        premiumAccount = findViewById(R.id.premiumAccount);
        goPremiumBtn.setVisibility(View.GONE);
        premiumAccount.setVisibility(View.GONE);
    }

    public void uploadProfImg(){
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(openGalleryIntent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                //profileImageView.setImageURI(imageUri);
                uploadImageToFirebase(imageUri);
            }
        }
    }

    //Upload Image to Firebase Storage::
    private void uploadImageToFirebase(Uri imageUri) {
        final StorageReference fileRef = storageRef.child("customers/profile_pictures/"+userID+"profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImageView);

                        //Save the image to firestore database
                        String imgUrl = String.valueOf(uri);
                        docuRef = fStore.collection("customers").document(userID);
                        Map<String,Object> customer = new HashMap<>();
                        customer.put("cust_image",imgUrl);
                        docuRef.update(customer).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("PROFILE-IMG SAVED TO DB","SUCCESS");
                            }
                        });
                    }
                });
                Toast.makeText(Profile.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Profile.this, "Failed to Upload Image", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Save Updated Profile Details to Firebase
    private void saveProfileUpdatedDetails(){
        String firstNameP = firstNameProfileET.getText().toString();
        String lastNameP = lastNameProfileET.getText().toString();
        String emailAddP = emailAddProfileET.getText().toString();
        String mobilePhoneP = editTextPhone.getText().toString();

        //Check whether there are empty fields ::
        if(firstNameP.isEmpty() || lastNameP.isEmpty() || emailAddP.isEmpty() || mobilePhoneP.isEmpty()){
            Toast.makeText(Profile.this, "Some fields are EMPTY.", Toast.LENGTH_SHORT).show();
        }else{
            Boolean  isValidEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddP).matches();
            Boolean  isValidFName = firstNameP.matches("[A-Za-z][A-Za-z ]*+");
            Boolean  isValidLName = lastNameP.matches("[A-Za-z][A-Za-z ]*+");
            Boolean  validPhone = mobilePhoneP.matches("^(?:\\d{2}-\\d{3}-\\d{3}-\\d{3}|\\d{11})$");

            if(!isValidEmail) {//Validate valid Email Address
                Toast.makeText(Profile.this, "Invalid Email Address, ex: abc@example.com", Toast.LENGTH_SHORT).show();
            }else if (!isValidFName) {//Validate valid First name
                Toast.makeText(Profile.this, "Invalid First Name, ex: John Anthony", Toast.LENGTH_SHORT).show();
            }else if (!isValidLName) {//Validate valid Last name
                Toast.makeText(Profile.this, "Invalid Last Name, ex: Doe", Toast.LENGTH_SHORT).show();
            }else if (!validPhone) {//Validate valid Phone Number
                Toast.makeText(Profile.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
            }else{//save the updates on the firebase db
                user.updateEmail(emailAddP).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        docRef = fStore.collection("customers").document(userID);
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("cust_email",emailAddP);
                        edited.put("cust_fname",firstNameP);
                        edited.put("cust_lname",lastNameP);
                        edited.put("cust_phoneNum",mobilePhoneP);
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Profile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),Profile.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Profile.this, "No Changes has been made", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Profile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    //Change Password::
    private void changePass(){
        Intent intent = new Intent(getApplicationContext(), ChangePass.class);
        startActivity(intent);
    }

    //Premium::
    public void goPremium(){
        Intent intent = new Intent(getApplicationContext(), GoPremiumCA.class);
        startActivity(intent);
    }

    //Loyalty Tier::
    public void loyalty(){
        Intent intent = new Intent(getApplicationContext(), LTInfo.class);
        startActivity(intent);
    }

    //Previous Pages::
    private void fragments(){
        setContentView(R.layout.activity_bottom_navigation);
        //Bottom Nav
        //Assign variable
        bottomNavigation = findViewById(R.id.bottom_nav_bo);

        //Add menu item
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_nearby));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_reserve));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_favorites));
        bottomNavigation.add(new MeowBottomNavigation.Model(5,R.drawable.ic_help_cust));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                //Initialize fragment
                Fragment fragment = null;

                //Check condition
                switch (item.getId()){
                    case 1: //When id is 1, initialize nearby fragment
                        fragment = new NearbyFragment();
                        break;

                    case 2: //When id is 2, initialize reservations fragment
                        fragment = new ReservationsFragment();
                        break;

                    case 3: //When id is 3, initialize home fragment
                        fragment = new HomeFragment();
                        break;

                    case 4: //When id is 4, initialize favorites fragment
                        fragment = new EstFavoritesFragment();
                        break;

                    case 5: //When id is 5, initialize help center fragment
                        fragment = new HelpCenterFragment();
                        break;
                }
                //Load fragment
                loadFragment(fragment);
            }
        });

        /*//Set notification count
        bottomNavigation.setCount(3,"10");*/
        //Set home fragment initially selected
        bottomNavigation.show(3,true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        //Replace fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,fragment)
                .commit();
    }

    private void premiumPrivileges(){
        Intent intent = new Intent(getApplicationContext(), PremiumPrivileges.class);
        startActivity(intent);
    }
}