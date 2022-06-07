package com.example.pakigsabot.RestaurantBO;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.pakigsabot.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PlotLocationResto extends AppCompatActivity implements OnMapReadyCallback {
    private Button setLocationBtn, saveEstLocationBtn;
    private GoogleMap pakigsabotMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LatLng latLng;
    double longitude, latitude;
    ImageView backPlotBtn;
    TextView estLocLatitude, estLocLongitude;

    //Firebase
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    DocumentReference docRef;
    String userId, updateLat, updateLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot_location_resto);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        //References
        refs();

        estLocLatitude.setVisibility(View.GONE);
        estLocLongitude.setVisibility(View.GONE);
        saveEstLocationBtn.setVisibility(View.GONE);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.estMapPlotting);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        mapFragment.getMapAsync(this);

     setLocationBtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             //Sets the textviews visibility to visible when the button is tapped
             estLocLatitude.setVisibility(View.VISIBLE);
             estLocLongitude.setVisibility(View.VISIBLE);

             //Sets the text of the textviews to the values based on latitude and longitude of the pinned location
             estLocLatitude.setText(String.valueOf(latLng.latitude));
             estLocLongitude.setText(String.valueOf(latLng.longitude));
             updateLat = estLocLatitude.getText().toString();
             updateLong = estLocLongitude.getText().toString();

             //Check whether there are empty fields
             if(updateLat.isEmpty() || updateLong.isEmpty()){
                 Toast.makeText(PlotLocationResto.this, "Some fields are EMPTY.", Toast.LENGTH_SHORT).show();
             }
             else{
                 saveEstLocationBtn.setVisibility(View.VISIBLE);
             }
         }
     });

     saveEstLocationBtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             saveLatLongToDB();
         }
     });

     backPlotBtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent in = new Intent(getApplicationContext(), InEstLocationOrNot.class);
             startActivity(in);
         }
     });

    }

    private void refs() {
        estLocLatitude = findViewById(R.id.estLocLatitude);
        estLocLongitude = findViewById(R.id.estLocLongitude);
        setLocationBtn = findViewById(R.id.setLocationBtn);
        saveEstLocationBtn = findViewById(R.id.saveEstLocationBtn);
        backPlotBtn = findViewById(R.id.backPlotBtn);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        pakigsabotMap = googleMap;
        pakigsabotMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();

                //Setting the position for the marker
                markerOptions.position(latLng);

                //Setting the title for the marker.
                //This will be displayed on top after tapping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                //Clears previously touched position
                pakigsabotMap.clear();

                //Animating to the touched position
                pakigsabotMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                //Placing a marker on the touched position
                pakigsabotMap.addMarker(markerOptions);
                latitude = markerOptions.getPosition().latitude;
                longitude = markerOptions.getPosition().longitude;
            }
        });
        getMyLocation();
    }

    private void getMyLocation() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        pakigsabotMap.setMyLocationEnabled(true);
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    longitude= latLng.longitude;
                    latitude = latLng.latitude;
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14f);
                    pakigsabotMap.animateCamera(cameraUpdate);
                }
            }
        });

    }

    private void saveLatLongToDB() {
        //Saves the updated latitude and longitude of the establishment location to DB
        docRef = fStore.collection("establishments").document(userId);
        Map<String,Object> edited = new HashMap<>();
        edited.put("est_latitude", updateLat);
        edited.put("est_longitude", updateLong);
        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(PlotLocationResto.this, "Location  successfully set.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), SettingUpEstablishmentRestaurant.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PlotLocationResto.this, "Failed to set location.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}