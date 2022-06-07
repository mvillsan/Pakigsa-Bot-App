package com.example.pakigsabot.InternetCafeBO;

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
import com.example.pakigsabot.ServicesBO.ServicesInternetCafe;
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

public class AddServiceIC extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1000;
    ImageView backBtnAddICServ, addICServPic, uploadICServBtn, saveICServBtn;
    EditText nameTxt, descTxt, rateTxt;
    Uri internetCafeImgUri;
    StorageReference imageFolderRef;
    FirebaseFirestore firestoreRef;
    FirebaseAuth fAuth;
    CollectionReference docRef;
    String userID, autoID, nameTxtStr, descTxtStr, rateTxtStr;
    StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service_ic);

        //References::
        refs();

        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        imageFolderRef = FirebaseStorage.getInstance().getReference("establishments/internetCafeEstImages/"+ userID + "/facilities");
        firestoreRef = FirebaseFirestore.getInstance();
        autoID = UUID.randomUUID().toString();

        backBtnAddICServ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                internetCafeFacilities();
            }
        });

        uploadICServBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChoose();
            }
        });

        saveICServBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uploadTask != null && uploadTask.isInProgress()){
                    Toast.makeText(AddServiceIC.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                }else{
                    uploadImgDetails();
                }
            }
        });
    }

    private void refs(){
        backBtnAddICServ = findViewById(R.id.backBtnAddICServ);
        addICServPic = findViewById(R.id.addICServPic);
        uploadICServBtn = findViewById(R.id.uploadICServBtn);
        saveICServBtn = findViewById(R.id.saveICServDetailsBtn);
        nameTxt = findViewById(R.id.nameTxt);
        descTxt = findViewById(R.id.descTxt);
        rateTxt = findViewById(R.id.rateTxt);
    }

    private void internetCafeFacilities(){
        Intent intent = new Intent(getApplicationContext(), ServicesInternetCafe.class);
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
            internetCafeImgUri = data.getData();

            Picasso.get().load(internetCafeImgUri).into(addICServPic);
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
        descTxtStr = descTxt.getText().toString().trim();
        rateTxtStr = rateTxt.getText().toString().trim();

        //Validations::
        if (nameTxtStr.isEmpty()) {
            Toast.makeText(this, "Enter Name of Facility", Toast.LENGTH_SHORT).show();
        }else if (descTxtStr.isEmpty()) {
            Toast.makeText(this, "Enter Description", Toast.LENGTH_SHORT).show();
        }else if (rateTxtStr.isEmpty()) {
            Toast.makeText(this, "Enter Rate", Toast.LENGTH_SHORT).show();
        }else {
            if (internetCafeImgUri != null) {
                StorageReference fileRef = imageFolderRef.child(System.currentTimeMillis()
                        + "." + getFileExtension(internetCafeImgUri));

                uploadTask = fileRef.putFile(internetCafeImgUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(AddServiceIC.this, "Upload successful", Toast.LENGTH_SHORT).show();

                                //Store Room/Facilities image and details::
                                String imgUrl = uri.toString();//get the firebasestorage image url

                                Map<String, Object> internetCafeServices = new HashMap<>();
                                internetCafeServices.put("serv_id", autoID);
                                internetCafeServices.put("serv_name", nameTxtStr);
                                internetCafeServices.put("serv_desc", descTxtStr);
                                internetCafeServices.put("serv_rate", rateTxtStr);
                                internetCafeServices.put("serv_image", imgUrl);

                                //To save inside the document of the userID, under the resort-rooms-facilities collection
                                firestoreRef.collection("establishments").document(userID).collection("internet-cafe-services").document(autoID).set(internetCafeServices);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddServiceIC.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                //Back to Facilities List
                internetCafeFacilities();
            } else {
                Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
            }
        }
    }
}