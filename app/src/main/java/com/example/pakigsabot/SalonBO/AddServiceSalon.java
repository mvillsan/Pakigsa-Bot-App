package com.example.pakigsabot.SalonBO;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.pakigsabot.R;
import com.example.pakigsabot.ServicesBO.ServicesSalon;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddServiceSalon extends AppCompatActivity {
    ImageView salonServPic, salonBackBtn, salonSaveBtn;
    TextInputEditText salonServName, salonServDesc, salonServRate;
    TextInputLayout servNameLayout, servDescLayout, servRateLayout;
    FirebaseAuth fAuth;
    FirebaseFirestore fStoreRef;
    String userId, autoId, txtServName, txtServDesc, txtServRate;
    Uri salonServImgUri;
    StorageReference imageStorageRef;
    StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service_salon);

        //References
        refs();

        fAuth = FirebaseAuth.getInstance();
        fStoreRef = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        imageStorageRef = FirebaseStorage.getInstance().getReference("establishments/salonImages/" + userId);
        autoId = UUID.randomUUID().toString();

        salonServPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addServiceImage();
            }
        });

        salonBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salonServices();
            }
        });

        salonSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uploadTask != null && uploadTask.isInProgress()) {
                    Toast.makeText(AddServiceSalon.this, "Upload in Progress", Toast.LENGTH_SHORT).show();
                } else {
                    saveServiceDetailsToFirestore();


                }
            }
        });
    }

    private void refs() {
        salonServPic = findViewById(R.id.addSalonServicePic);
        salonServName = findViewById(R.id.nameSalonServTxt);
        salonServDesc = findViewById(R.id.descSalonServTxt);
        salonServRate = findViewById(R.id.rateSalonServTxt);

        salonSaveBtn = findViewById(R.id.saveServiceBtn);
        salonBackBtn = findViewById(R.id.backBtnAddSalonService);
        servNameLayout = findViewById(R.id.nameSalonServLayout);
        servDescLayout = findViewById(R.id.descSalonServLayout);
        servRateLayout = findViewById(R.id.rateSalonServLayout);
    }

    private void salonServices() {
        Intent intent = new Intent(getApplicationContext(), ServicesSalon.class);
        startActivity(intent);
    }

    //To get file extension from the image
    private String getFileExtension(Uri uri){
        ContentResolver contentR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentR.getType(uri));
    }

    private void addServiceImage() {
        //uploading image for proc
        salonServPic.setOnClickListener(new View.OnClickListener() {
            @androidx.annotation.RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                boolean pickImg = true;

                if (pickImg) {
                    //check for camera permission
                    if (!checkCameraPermission()) {
                        //request for camera permission
                        reqCameraPermission();
                    } else {
                        pickImage();
                    }
                } else {
                    //check for camera permission
                    if (!checkStoragePermission()) {
                        //request for storage permission
                        reqStoragePermission();
                    } else {
                        pickImage();
                    }
                }
            }
        });

    }

    private void pickImage() {
        // starts the  picker to get image for cropping and then use the image in cropping activity
        CropImage.activity().start(this);
    }

    //checks the permission code to access the device's storage
    @androidx.annotation.RequiresApi(api = Build.VERSION_CODES.M)
    private void reqStoragePermission() {
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    //checks the permission code to access the device's camera
    @androidx.annotation.RequiresApi(api = Build.VERSION_CODES.M)
    private void reqCameraPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }


    private boolean checkStoragePermission() {
        boolean result2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        return result2;
    }

    private boolean checkCameraPermission() {
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean result2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        return result1 && result2;
    }


    //Picking image from camera or gallery
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                salonServImgUri = result.getUri();
                Picasso.get().load(salonServImgUri).into(salonServPic);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

    /*//To get file extension from the image
    public String getFileExtension(Uri uri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();

        // Return file Extension
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }*/


    private void saveServiceDetailsToFirestore() {
        txtServName = salonServName.getText().toString().trim();
        txtServDesc = salonServDesc.getText().toString().trim();
        txtServRate = salonServRate.getText().toString().trim();

        if (salonServImgUri != null) {
            //Validations::
            if (txtServName.isEmpty() || txtServDesc.isEmpty() || txtServRate.isEmpty()) {
                Toast.makeText(AddServiceSalon.this, "Some fields are EMPTY.", Toast.LENGTH_SHORT).show();
            } else {
                if (txtServName.isEmpty()) {
                    servNameLayout.setError("Enter Name of Service");
                } else {
                    Boolean validName = txtServName.matches("[A-Za-z][A-Za-z ]*+");
                    if (!validName) {
                        servNameLayout.setError("Invalid Service Name");
                    } else {
                        servNameLayout.setErrorEnabled(false);
                        servNameLayout.setError("");
                    }
                }
                if (txtServDesc.isEmpty()) {
                    servDescLayout.setError("Enter Description");
                } else {
                    servDescLayout.setErrorEnabled(false);
                    servDescLayout.setError("");
                }
                if (txtServRate.isEmpty()) {
                    servRateLayout.setError("Enter Rate");
                } else {
                    servRateLayout.setErrorEnabled(false);
                    servRateLayout.setError("");
                }


                    StorageReference fileRef = imageStorageRef.child(System.currentTimeMillis() + ".jpg");

                    uploadTask = fileRef.putFile(salonServImgUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Toast.makeText(AddServiceSalon.this, "Upload Successful", Toast.LENGTH_SHORT).show();

                                    //Store dental procedures image and details::
                                    String imgUrl = uri.toString();//get the firebasestorage image url

                                    Map<String, Object> salonServices = new HashMap<>();
                                    salonServices.put("serv_id", autoId);
                                    salonServices.put("serv_name", txtServName);
                                    salonServices.put("serv_desc", txtServDesc);
                                    salonServices.put("serv_rate", txtServRate);
                                    salonServices.put("serv_image", imgUrl);

                                    //To save inside the document of the userID, under the dental-procedures collection
                                    fStoreRef.collection("establishments").document(userId).collection("salon-services").document(autoId).set(salonServices);

                                    salonServices();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddServiceSalon.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            }else{
                Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
            }
        }

    }
