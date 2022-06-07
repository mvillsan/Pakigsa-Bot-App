package com.example.pakigsabot.DentalClinicBO;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.pakigsabot.R;
import com.example.pakigsabot.ServicesBO.ServicesDentalClinic;
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

public class AddServiceDentalClinic extends AppCompatActivity {
    ImageView procPic, backBtn, saveBtn;
    TextInputEditText procName, procDesc, procRate;
    TextInputLayout procNameLayout, procDescLayout, procRateLayout;
    FirebaseAuth fAuth;
    FirebaseFirestore fStoreRef;
    String userId, autoId, txtProcName, txtProcDesc, txtProcRate;
    Uri dentalImageUri;
    StorageReference imageStorageRef;
    StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service_dental_clinic);

        //References
        refs();

        fAuth = FirebaseAuth.getInstance();
        fStoreRef = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        imageStorageRef = FirebaseStorage.getInstance().getReference("establishments/dentalClinicImages/"+ userId);
        autoId = UUID.randomUUID().toString();

        procPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProcedureImage();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dentalClinicServices();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uploadTask != null && uploadTask.isInProgress()){
                    Toast.makeText(AddServiceDentalClinic.this, "Upload in Progress", Toast.LENGTH_SHORT).show();
                }else {
                    saveProcedureDetailsToFirestore();


                }
            }
        });
    }

    private void refs() {
        procPic = findViewById(R.id.procPicture);
        procName = findViewById(R.id.procNameTxt);
        procDesc = findViewById(R.id.procDescTxt);
        procRate = findViewById(R.id.procRateTxt);

        saveBtn = findViewById(R.id.saveProcBtn);
        backBtn = findViewById(R.id.backBtnAddProc);
        procNameLayout = findViewById(R.id.procNameLayout);
        procDescLayout = findViewById(R.id.procDescLayout);
        procRateLayout = findViewById(R.id.procRateLayout);
    }

    private void dentalClinicServices() {
        Intent intent = new Intent(getApplicationContext(), ServicesDentalClinic.class);
        startActivity(intent);
    }

    private void addProcedureImage(){
        //uploading image for proc
        procPic.setOnClickListener(new View.OnClickListener() {
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
                dentalImageUri = result.getUri();
                Picasso.get().load(dentalImageUri).into(procPic);
            }
        else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
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


    private void saveProcedureDetailsToFirestore() {
        txtProcName = procName.getText().toString().trim();
        txtProcDesc = procDesc.getText().toString().trim();
        txtProcRate = procRate.getText().toString().trim();

        if(dentalImageUri != null){
            //Validations::
            if(txtProcName.isEmpty() || txtProcDesc.isEmpty() || txtProcRate.isEmpty()){
                Toast.makeText(AddServiceDentalClinic.this, "Some fields are EMPTY.", Toast.LENGTH_SHORT).show();
            }else{
                if(txtProcName.isEmpty()) {
                    procNameLayout.setError("Enter Name of Procedure");
                } else {
                    Boolean  validName = txtProcName.matches("[A-Za-z][A-Za-z ]*+");
                    if (!validName) {
                        procNameLayout.setError("Invalid Procedure Name");
                    } else {
                        procNameLayout.setErrorEnabled(false);
                        procNameLayout.setError("");
                    }
                }if (txtProcDesc.isEmpty()) {
                    procDescLayout.setError("Enter Description");
                } else {
                    procDescLayout.setErrorEnabled(false);
                    procDescLayout.setError("");
                }if (txtProcRate.isEmpty()) {
                    procRateLayout.setError("Enter Rate");
                } else {
                    procRateLayout.setErrorEnabled(false);
                    procRateLayout.setError("");
                }

                StorageReference fileRef = imageStorageRef.child(System.currentTimeMillis() + ".jpg");

                uploadTask = fileRef.putFile(dentalImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(AddServiceDentalClinic.this, "Upload Successful", Toast.LENGTH_SHORT).show();

                                //Store dental procedures image and details::
                                String imgUrl = uri.toString();//get the firebasestorage image url

                                Map<String,Object> dentalServices = new HashMap<>();
                                dentalServices.put("dentalPRId", autoId);
                                dentalServices.put("dentalPRName", txtProcName);
                                dentalServices.put("dentalPRDesc", txtProcDesc);
                                dentalServices.put("dentalPRRate", txtProcRate);
                                dentalServices.put("dentalPRImgUrl", imgUrl);
                                dentalServices.put("estId", userId);

                                //To save inside the document of the userID, under the dental-procedures collection
                                fStoreRef.collection("establishments").document(userId).collection("dental-procedures").document(autoId).set(dentalServices);

                                dentalClinicServices();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddServiceDentalClinic.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }else{
            Toast.makeText(this, "No Image Selected",Toast.LENGTH_SHORT).show();
        }
    }

}