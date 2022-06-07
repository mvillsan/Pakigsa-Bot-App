package com.example.pakigsabot.CoworkingSpaceBO;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pakigsabot.R;
import com.example.pakigsabot.ServicesBO.ServicesCoworkingSpace;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddFacilityCS extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1000;
    ImageView backBtnAddCSFac, addCSFacPic, uploadCSFacBtn, saveCSFacBtn;
    EditText nameTxt, capTxt, descTxt, rateTxt;
    Uri coworkSpaceImgUri;
    StorageReference imageFolderRef;
    FirebaseFirestore firestoreRef;
    FirebaseAuth fAuth;
    CollectionReference docRef;
    String userID, autoID, nameTxtStr, capTxtStr, descTxtStr, rateTxtStr;
    StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_facility_cs);

        //References::
        refs();

        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        imageFolderRef = FirebaseStorage.getInstance().getReference("establishments/coworkingSpaceEstImages/"+ userID + "/facilities");
        firestoreRef = FirebaseFirestore.getInstance();
        autoID = UUID.randomUUID().toString();

        backBtnAddCSFac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coworkingSpaceFacilities();
            }
        });

        uploadCSFacBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChoose();
            }
        });

        saveCSFacBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uploadTask != null && uploadTask.isInProgress()){
                    Toast.makeText(AddFacilityCS.this, "Upload in Progress", Toast.LENGTH_SHORT).show();
                }else{
                    uploadImgDetails();
                }
            }
        });
    }

    private void refs(){
        backBtnAddCSFac = findViewById(R.id.backBtnAddCSFacility);
        addCSFacPic = findViewById(R.id.addCSFacilityPic);
        uploadCSFacBtn = findViewById(R.id.uploadCSFacilityBtn);
        saveCSFacBtn = findViewById(R.id.saveCSFacilityDetailsBtn);
        nameTxt = findViewById(R.id.nameTxt);
        capTxt = findViewById(R.id.maxCapTxt);
        descTxt = findViewById(R.id.descTxt);
        rateTxt = findViewById(R.id.rateTxt);
    }

    private void coworkingSpaceFacilities(){
        Intent intent = new Intent(getApplicationContext(), ServicesCoworkingSpace.class);
        startActivity(intent);
    }


    private void openFileChoose() {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(openGalleryIntent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            coworkSpaceImgUri = data.getData();

            Picasso.get().load(coworkSpaceImgUri).into(addCSFacPic);
        }
    }

    //To get file extension from the image
    private String getFileExtension(Uri uri){
        ContentResolver contentR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentR.getType(uri));
    }

    private void uploadImgDetails(){
        nameTxtStr = nameTxt.getText().toString().trim();
        capTxtStr = capTxt.getText().toString().trim();
        descTxtStr = descTxt.getText().toString().trim();
        rateTxtStr = rateTxt.getText().toString().trim();

        //Validations::
        if (nameTxtStr.isEmpty()) {
            Toast.makeText(this, "Enter Name of Facility", Toast.LENGTH_SHORT).show();
        }else if (capTxtStr.isEmpty()) {
            Toast.makeText(this, "Enter Capacity", Toast.LENGTH_SHORT).show();
        }else if (descTxtStr.isEmpty()) {
            Toast.makeText(this, "Enter Description", Toast.LENGTH_SHORT).show();
        }else if (rateTxtStr.isEmpty()) {
            Toast.makeText(this, "Enter Rate", Toast.LENGTH_SHORT).show();
        }else {
            if (coworkSpaceImgUri != null) {
                StorageReference fileRef = imageFolderRef.child(System.currentTimeMillis()
                        + "." + getFileExtension(coworkSpaceImgUri));

                uploadTask = fileRef.putFile(coworkSpaceImgUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(AddFacilityCS.this, "Upload Successful", Toast.LENGTH_SHORT).show();

                                //Store Room/Facilities image and details::
                                String imgUrl = uri.toString();//get the firebasestorage image url

                                Map<String, Object> coSpaceFacilities = new HashMap<>();
                                coSpaceFacilities.put("fac_id", autoID);
                                coSpaceFacilities.put("fac_name", nameTxtStr);
                                coSpaceFacilities.put("fac_cap", capTxtStr);
                                coSpaceFacilities.put("fac_desc", descTxtStr);
                                coSpaceFacilities.put("fac_rate", rateTxtStr);
                                coSpaceFacilities.put("fac_image", imgUrl);

                                //To save inside the document of the userID, under the resort-rooms-facilities collection
                                firestoreRef.collection("establishments").document(userID).collection("coworking-space-facilities").document(autoID).set(coSpaceFacilities);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddFacilityCS.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                //Back to Facilities List
                coworkingSpaceFacilities();
            } else {
                Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
            }
        }
    }
}