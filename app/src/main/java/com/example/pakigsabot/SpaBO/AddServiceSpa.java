package com.example.pakigsabot.SpaBO;

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
import com.example.pakigsabot.ServicesBO.ServicesSpa;
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

public class AddServiceSpa extends AppCompatActivity {
    ImageView spaServPic, spaBackBtn, spaSaveBtn;
    TextInputEditText spaServName, spaServDesc, spaServRate;
    TextInputLayout servNameLayout, servDescLayout, servRateLayout;
    FirebaseAuth fAuth;
    FirebaseFirestore fStoreRef;
    String userId, autoId, txtServName, txtServDesc, txtServRate;
    Uri spaServImgUri;
    StorageReference imageStorageRef;
    StorageTask uploadTask;
//    AutoCompleteTextView spaServStat;


    /*private static final String[] EST_STAT = new String[]{
            "Available", "Unavailable"
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service_spa);

        //References
        refs();

        fAuth = FirebaseAuth.getInstance();
        fStoreRef = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        imageStorageRef = FirebaseStorage.getInstance().getReference("establishments/spaImages/"+ userId);
        autoId = UUID.randomUUID().toString();

        spaServPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addServiceImage();
            }
        });

        spaBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spaServices();
            }
        });

        spaSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uploadTask != null && uploadTask.isInProgress()){
                    Toast.makeText(AddServiceSpa.this, "Upload in Progress", Toast.LENGTH_SHORT).show();
                }else {
                    saveServiceDetailsToFirestore();


                }
            }
        });

        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,EST_STAT);
        spaServStat.setAdapter(adapter);
        spaServStat.setThreshold(1);*/
    }

    private void refs() {
            spaServPic = findViewById(R.id.addSpaServPic);
            spaServName = findViewById(R.id.spaServNameTxt);
            spaServDesc = findViewById(R.id.spaServDescTxt);
            spaServRate = findViewById(R.id.spaServRateTxt);

            spaSaveBtn = findViewById(R.id.saveSpaServBtn);
            spaBackBtn = findViewById(R.id.backBtnAddSpaServ);
            servNameLayout = findViewById(R.id.spaServNameLayout);
            servDescLayout = findViewById(R.id.spaServDescLayout);
            servRateLayout = findViewById(R.id.spaServRateLayout);
    }

    private void spaServices() {
        Intent intent = new Intent(getApplicationContext(), ServicesSpa.class);
        startActivity(intent);
    }

    //To get file extension from the image
    private String getFileExtension(Uri uri){
        ContentResolver contentR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentR.getType(uri));
    }



    private void addServiceImage(){
        //uploading image for proc
        spaServPic.setOnClickListener(new View.OnClickListener() {
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
                spaServImgUri = result.getUri();
                Picasso.get().load(spaServImgUri).into(spaServPic);
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


    private void saveServiceDetailsToFirestore() {
        txtServName = spaServName.getText().toString().trim();
        txtServDesc = spaServDesc.getText().toString().trim();
        txtServRate = spaServRate.getText().toString().trim();

        if(spaServImgUri != null){
            //Validations::
            if(txtServName.isEmpty() || txtServDesc.isEmpty() || txtServRate.isEmpty()){
                Toast.makeText(AddServiceSpa.this, "Some fields are EMPTY.", Toast.LENGTH_SHORT).show();
            }else{
                if(txtServName.isEmpty()) {
                    servNameLayout.setError("Enter Name of Service");
                } else {
                    Boolean  validName = txtServName.matches("[A-Za-z][A-Za-z ]*+");
                    if (!validName) {
                        servNameLayout.setError("Invalid Procedure Name");
                    } else {
                        servNameLayout.setErrorEnabled(false);
                        servNameLayout.setError("");
                    }
                }if (txtServDesc.isEmpty()) {
                    servDescLayout.setError("Enter Description");
                } else {
                    servDescLayout.setErrorEnabled(false);
                    servDescLayout.setError("");
                }if (txtServRate.isEmpty()) {
                    servRateLayout.setError("Enter Rate");
                } else {
                    servRateLayout.setErrorEnabled(false);
                    servRateLayout.setError("");
                }

                StorageReference fileRef = imageStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(spaServImgUri));

                uploadTask = fileRef.putFile(spaServImgUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(AddServiceSpa.this, "Upload Successful", Toast.LENGTH_SHORT).show();

                                //Store dental procedures image and details::
                                String imgUrl = uri.toString();//get the firebasestorage image url

                                Map<String,Object> spaServices = new HashMap<>();
                                spaServices.put("serv_id", autoId);
                                spaServices.put("serv_name", txtServName);
                                spaServices.put("serv_desc", txtServDesc);
                                spaServices.put("serv_rate", txtServRate);
                                spaServices.put("serv_image", imgUrl);

                                //To save inside the document of the userID, under the dental-procedures collection
                                fStoreRef.collection("establishments").document(userId).collection("spa-services").document(autoId).set(spaServices);

                                spaServices();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddServiceSpa.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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