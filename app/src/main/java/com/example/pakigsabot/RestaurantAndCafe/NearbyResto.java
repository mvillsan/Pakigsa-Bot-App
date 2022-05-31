package com.example.pakigsabot.RestaurantAndCafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pakigsabot.R;
import com.example.pakigsabot.RestaurantAndCafe.Adapters.Restaurant.RestoFilterAdapter;
import com.example.pakigsabot.RestaurantAndCafe.Adapters.RestoEstAdapter;
import com.example.pakigsabot.RestaurantAndCafe.Models.Restaurant.EstModelFilter;
import com.example.pakigsabot.RestaurantAndCafe.Models.RestoEstModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NearbyResto extends AppCompatActivity {

    protected LatLng start = null;
    protected LatLng end = null;
    double user1;
    double user2;
    DocumentReference documentReference, docRef, documentRef, docuRef;
    // initializing
    // FusedLocationProviderClient
    // object
    FusedLocationProviderClient mFusedLocationClient;

    // Initializing other items
    // from layout file
    String latitude, longitude;
    int PERMISSION_ID = 44;

    ImageView imgBackBtn;

    // creating variables for our recycler view,
    // array list, adapter, firebase firestore,
    // and our progress bar.
    // initializing our variable for firebase
    // firestore and getting its instance.
    RecyclerView restoCafeRecyclerView;
    ArrayList<RestoEstModel> restoCafeEstArrayList;
    ArrayList<EstModelFilter> filterDistanceArrayList;
    RestoEstAdapter restoCafeAdapter;
    RestoFilterAdapter filterAdapter;
    FirebaseFirestore fStore;
    FirebaseUser user;
    String userID, selectedTxtStr;
    ProgressDialog progressDialog;
    Spinner spinner;
    TextView selectedTxt;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_resto);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // method to get the location
        getLastLocation();

        //References::
        refs();

        //recyclerview initialization
        restoCafeRecyclerView = findViewById(R.id.restoCafeRecyclerView);
        restoCafeRecyclerView.setHasFixedSize(true);
        restoCafeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // adding our array list to our recycler view adapter class.
        fStore=FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        filterDistanceArrayList = new ArrayList<>();

        //initializing the arraylist where all data is stored
        filterDistanceArrayList = new ArrayList<EstModelFilter>();

        //create a list of items for the spinner.
        String[] items = new String[]{"Less than 100M", "100M-199M", "200M-299M", "300M-399M", "400M-499M", "500M-999M", "1KM-2KM"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                selectedTxt = (TextView) spinner.getSelectedView();
                selectedTxtStr = selectedTxt.getText().toString();
                //Filipino Resto Categories:
                if(selectedTxtStr.equalsIgnoreCase("Less than 100M")){
                    getRestoCafeListless100();
                }else if(selectedTxtStr.equalsIgnoreCase("100M-199M")){
                    getRestoCafeList100199();
                }else if(selectedTxtStr.equalsIgnoreCase("200M-299M")){
                    getRestoCafeList200299();
                }else if(selectedTxtStr.equalsIgnoreCase("300M-399M")){
                    getRestoCafeList300399();
                }else if(selectedTxtStr.equalsIgnoreCase("400M-499M")){
                    getRestoCafeList400499();
                }else if(selectedTxtStr.equalsIgnoreCase("500M-999M")) {
                    getRestoCafeList500999();
                }else if(selectedTxtStr.equalsIgnoreCase("1KM-2KM")) {
                    getRestoCafeList1KM2KM();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        //Refresh the recyclerview::
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRestoCafeList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        imgBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseEst();
            }
        });

    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            latitude = location.getLatitude() + "";
                            longitude = location.getLongitude() + "";
                            user1 = location.getLatitude();
                            user2 = location.getLongitude();

                            Log.d("tag", latitude + "latitude");
                            Log.d("tag", longitude + "longitude");
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latitude = "Latitude: " + mLastLocation.getLatitude() + "";
            longitude = "Longitude: " + mLastLocation.getLongitude() + "";
            user1 = mLastLocation.getLatitude();
            user2 = mLastLocation.getLongitude();

            Log.d("tag", latitude + "latitude");
            Log.d("tag", longitude + "longitude");
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }

    //Haversine Formula
    public double distance(double lat1, double lon1, double lat2, double lon2) {
        // Radius of the earth
        int R = 6371;

        double dLat = Math.toRadians(lat2 - lat1); //distance of the latitude
        double dLon = Math.toRadians(lon2 - lon1); //distance of the longitude

        lat1 = Math.toRadians(lat1); //convert to radians
        lat2 = Math.toRadians(lat2);

        //Math pow + Math pow * Math cos * Math cos
        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);

        double c = 2 * Math.asin(Math.sqrt(a));

        return R * c; // return distance
    }

    private SpannableStringBuilder showResult(LatLng point1, LatLng point2) {
        double distance = distance(point1.latitude, point1.longitude, point2.latitude, point2.longitude);

        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.BLACK);
        SpannableStringBuilder ssb = new SpannableStringBuilder();

        int start = 0;
        SpannableString ss = new SpannableString(String.format("%.3f", distance));
        ss.setSpan(boldSpan, start, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(redSpan, start, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(ss);

        return ssb;
    }

    private void refs(){
        imgBackBtn = findViewById(R.id.imgBackBtn);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        spinner = findViewById(R.id.spinner);
        selectedTxt = findViewById(R.id.selectedTxt);
    }

    private void chooseEst(){
        Intent intent = new Intent(getApplicationContext(), ChooseEstType.class);
        startActivity(intent);
    }

    //Display Restaurant and Cafe List
    private void getRestoCafeList(){
        //Progress::
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        fStore.collection("establishments")
                .whereEqualTo("est_Type", "Restaurant")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        filterDistanceArrayList.clear();

                        for(DocumentSnapshot doc : task.getResult()){
                            RestoEstModel list = new RestoEstModel(doc.getString("est_id"),
                                    doc.getString("est_Name"),
                                    doc.getString("est_address"),
                                    doc.getString("est_image"),
                                    doc.getString("est_phoneNum"),
                                    doc.getString("est_latitude"),
                                    doc.getString("est_longitude"),
                                    doc.getString("est_overallrating"),
                                    doc.getString("est_totalratingcount")
                            );
                            start = new LatLng(user1,user2);
                            end = new LatLng(Double.parseDouble(list.getEstLatitude()), Double.parseDouble(list.getEstLongitude()));
                            String distance = showResult(start, end).toString();
                            filterDistanceArrayList.add(new EstModelFilter(list.getEst_id(),list.getEst_Name(),
                                    list.getEst_address(), list.getEst_image(), list.getEst_phoneNum(),
                                    list.getEstLatitude(), list.getEstLongitude(), list.getOverallRating(), list.getRatingCounter(),
                                    distance));

                        }
                        sortByDistance(filterDistanceArrayList);
                        filterAdapter = new RestoFilterAdapter(NearbyResto.this, filterDistanceArrayList);
                        // setting adapter to our recycler view.
                        restoCafeRecyclerView.setAdapter(filterAdapter);
                        filterAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NearbyResto.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Display Restaurant and Cafe List
    private void getRestoCafeListless100(){
        //Progress::
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        fStore.collection("establishments")
                .whereEqualTo("est_Type", "Restaurant")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        filterDistanceArrayList.clear();

                        for(DocumentSnapshot doc : task.getResult()){
                            RestoEstModel list = new RestoEstModel(doc.getString("est_id"),
                                    doc.getString("est_Name"),
                                    doc.getString("est_address"),
                                    doc.getString("est_image"),
                                    doc.getString("est_phoneNum"),
                                    doc.getString("est_latitude"),
                                    doc.getString("est_longitude"),
                                    doc.getString("est_overallrating"),
                                    doc.getString("est_totalratingcount")
                            );
                            start = new LatLng(user1,user2);
                            end = new LatLng(Double.parseDouble(list.getEstLatitude()), Double.parseDouble(list.getEstLongitude()));
                            String distance = showResult(start, end).toString();
                            if(Double.parseDouble(distance) < 0.100){
                                filterDistanceArrayList.add(new EstModelFilter(list.getEst_id(),list.getEst_Name(),
                                        list.getEst_address(), list.getEst_image(), list.getEst_phoneNum(),
                                        list.getEstLatitude(), list.getEstLongitude(), list.getOverallRating(), list.getRatingCounter(),
                                        distance));
                            }
                        }
                        sortByDistance(filterDistanceArrayList);
                        filterAdapter = new RestoFilterAdapter(NearbyResto.this, filterDistanceArrayList);
                        // setting adapter to our recycler view.
                        restoCafeRecyclerView.setAdapter(filterAdapter);
                        filterAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NearbyResto.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Display Restaurant and Cafe List
    private void getRestoCafeList100199(){
        //Progress::
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        fStore.collection("establishments")
                .whereEqualTo("est_Type", "Restaurant")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        filterDistanceArrayList.clear();

                        for(DocumentSnapshot doc : task.getResult()){
                            RestoEstModel list = new RestoEstModel(doc.getString("est_id"),
                                    doc.getString("est_Name"),
                                    doc.getString("est_address"),
                                    doc.getString("est_image"),
                                    doc.getString("est_phoneNum"),
                                    doc.getString("est_latitude"),
                                    doc.getString("est_longitude"),
                                    doc.getString("est_overallrating"),
                                    doc.getString("est_totalratingcount")
                            );
                            start = new LatLng(user1,user2);
                            end = new LatLng(Double.parseDouble(list.getEstLatitude()), Double.parseDouble(list.getEstLongitude()));
                            String distance = showResult(start, end).toString();
                            if(Double.parseDouble(distance) >= 0.100 && Double.parseDouble(distance) < 0.200){
                                filterDistanceArrayList.add(new EstModelFilter(list.getEst_id(),list.getEst_Name(),
                                        list.getEst_address(), list.getEst_image(), list.getEst_phoneNum(),
                                        list.getEstLatitude(), list.getEstLongitude(), list.getOverallRating(), list.getRatingCounter(),
                                        distance));
                            }
                        }
                        sortByDistance(filterDistanceArrayList);
                        filterAdapter = new RestoFilterAdapter(NearbyResto.this, filterDistanceArrayList);
                        // setting adapter to our recycler view.
                        restoCafeRecyclerView.setAdapter(filterAdapter);
                        filterAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NearbyResto.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Display Restaurant and Cafe List
    private void getRestoCafeList200299(){
        //Progress::
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        fStore.collection("establishments")
                .whereEqualTo("est_Type", "Restaurant")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        filterDistanceArrayList.clear();

                        for(DocumentSnapshot doc : task.getResult()){
                            RestoEstModel list = new RestoEstModel(doc.getString("est_id"),
                                    doc.getString("est_Name"),
                                    doc.getString("est_address"),
                                    doc.getString("est_image"),
                                    doc.getString("est_phoneNum"),
                                    doc.getString("est_latitude"),
                                    doc.getString("est_longitude"),
                                    doc.getString("est_overallrating"),
                                    doc.getString("est_totalratingcount")
                            );
                            start = new LatLng(user1,user2);
                            end = new LatLng(Double.parseDouble(list.getEstLatitude()), Double.parseDouble(list.getEstLongitude()));
                            String distance = showResult(start, end).toString();
                            if(Double.parseDouble(distance) >= 0.200 && Double.parseDouble(distance) < 0.300){
                                filterDistanceArrayList.add(new EstModelFilter(list.getEst_id(),list.getEst_Name(),
                                        list.getEst_address(), list.getEst_image(), list.getEst_phoneNum(),
                                        list.getEstLatitude(), list.getEstLongitude(), list.getOverallRating(), list.getRatingCounter(),
                                        distance));
                            }
                        }
                        sortByDistance(filterDistanceArrayList);
                        filterAdapter = new RestoFilterAdapter(NearbyResto.this, filterDistanceArrayList);
                        // setting adapter to our recycler view.
                        restoCafeRecyclerView.setAdapter(filterAdapter);
                        filterAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NearbyResto.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Display Restaurant and Cafe List
    private void getRestoCafeList300399(){
        //Progress::
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        fStore.collection("establishments")
                .whereEqualTo("est_Type", "Restaurant")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        filterDistanceArrayList.clear();

                        for(DocumentSnapshot doc : task.getResult()){
                            RestoEstModel list = new RestoEstModel(doc.getString("est_id"),
                                    doc.getString("est_Name"),
                                    doc.getString("est_address"),
                                    doc.getString("est_image"),
                                    doc.getString("est_phoneNum"),
                                    doc.getString("est_latitude"),
                                    doc.getString("est_longitude"),
                                    doc.getString("est_overallrating"),
                                    doc.getString("est_totalratingcount")
                            );
                            start = new LatLng(user1,user2);
                            end = new LatLng(Double.parseDouble(list.getEstLatitude()), Double.parseDouble(list.getEstLongitude()));
                            String distance = showResult(start, end).toString();
                            if(Double.parseDouble(distance) >= 0.300 && Double.parseDouble(distance) < 0.400){
                                filterDistanceArrayList.add(new EstModelFilter(list.getEst_id(),list.getEst_Name(),
                                        list.getEst_address(), list.getEst_image(), list.getEst_phoneNum(),
                                        list.getEstLatitude(), list.getEstLongitude(), list.getOverallRating(), list.getRatingCounter(),
                                        distance));
                            }
                        }
                        sortByDistance(filterDistanceArrayList);
                        filterAdapter = new RestoFilterAdapter(NearbyResto.this, filterDistanceArrayList);
                        // setting adapter to our recycler view.
                        restoCafeRecyclerView.setAdapter(filterAdapter);
                        filterAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NearbyResto.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Display Restaurant and Cafe List
    private void getRestoCafeList400499(){
        //Progress::
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        fStore.collection("establishments")
                .whereEqualTo("est_Type", "Restaurant")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        filterDistanceArrayList.clear();

                        for(DocumentSnapshot doc : task.getResult()){
                            RestoEstModel list = new RestoEstModel(doc.getString("est_id"),
                                    doc.getString("est_Name"),
                                    doc.getString("est_address"),
                                    doc.getString("est_image"),
                                    doc.getString("est_phoneNum"),
                                    doc.getString("est_latitude"),
                                    doc.getString("est_longitude"),
                                    doc.getString("est_overallrating"),
                                    doc.getString("est_totalratingcount")
                            );
                            start = new LatLng(user1,user2);
                            end = new LatLng(Double.parseDouble(list.getEstLatitude()), Double.parseDouble(list.getEstLongitude()));
                            String distance = showResult(start, end).toString();
                            if(Double.parseDouble(distance) >= 0.400 && Double.parseDouble(distance) < 0.500){
                                filterDistanceArrayList.add(new EstModelFilter(list.getEst_id(),list.getEst_Name(),
                                        list.getEst_address(), list.getEst_image(), list.getEst_phoneNum(),
                                        list.getEstLatitude(), list.getEstLongitude(), list.getOverallRating(), list.getRatingCounter(),
                                        distance));
                            }
                        }
                        sortByDistance(filterDistanceArrayList);
                        filterAdapter = new RestoFilterAdapter(NearbyResto.this, filterDistanceArrayList);
                        // setting adapter to our recycler view.
                        restoCafeRecyclerView.setAdapter(filterAdapter);
                        filterAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NearbyResto.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Display Restaurant and Cafe List
    private void getRestoCafeList500999(){
        //Progress::
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        fStore.collection("establishments")
                .whereEqualTo("est_Type", "Restaurant")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        filterDistanceArrayList.clear();

                        for(DocumentSnapshot doc : task.getResult()){
                            RestoEstModel list = new RestoEstModel(doc.getString("est_id"),
                                    doc.getString("est_Name"),
                                    doc.getString("est_address"),
                                    doc.getString("est_image"),
                                    doc.getString("est_phoneNum"),
                                    doc.getString("est_latitude"),
                                    doc.getString("est_longitude"),
                                    doc.getString("est_overallrating"),
                                    doc.getString("est_totalratingcount")
                            );
                            start = new LatLng(user1,user2);
                            end = new LatLng(Double.parseDouble(list.getEstLatitude()), Double.parseDouble(list.getEstLongitude()));
                            String distance = showResult(start, end).toString();
                            if(Double.parseDouble(distance) >= 0.500 && Double.parseDouble(distance) < 1.0){
                                filterDistanceArrayList.add(new EstModelFilter(list.getEst_id(),list.getEst_Name(),
                                        list.getEst_address(), list.getEst_image(), list.getEst_phoneNum(),
                                        list.getEstLatitude(), list.getEstLongitude(), list.getOverallRating(), list.getRatingCounter(),
                                        distance));
                            }
                        }
                        sortByDistance(filterDistanceArrayList);
                        filterAdapter = new RestoFilterAdapter(NearbyResto.this, filterDistanceArrayList);
                        // setting adapter to our recycler view.
                        restoCafeRecyclerView.setAdapter(filterAdapter);
                        filterAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NearbyResto.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Display Restaurant and Cafe List
    private void getRestoCafeList1KM2KM(){
        //Progress::
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        fStore.collection("establishments")
                .whereEqualTo("est_Type", "Restaurant")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        filterDistanceArrayList.clear();

                        for(DocumentSnapshot doc : task.getResult()){
                            RestoEstModel list = new RestoEstModel(doc.getString("est_id"),
                                    doc.getString("est_Name"),
                                    doc.getString("est_address"),
                                    doc.getString("est_image"),
                                    doc.getString("est_phoneNum"),
                                    doc.getString("est_latitude"),
                                    doc.getString("est_longitude"),
                                    doc.getString("est_overallrating"),
                                    doc.getString("est_totalratingcount")
                            );
                            start = new LatLng(user1,user2);
                            end = new LatLng(Double.parseDouble(list.getEstLatitude()), Double.parseDouble(list.getEstLongitude()));
                            String distance = showResult(start, end).toString();
                            if(Double.parseDouble(distance) >= 1.0 && Double.parseDouble(distance) <= 2.0){
                                filterDistanceArrayList.add(new EstModelFilter(list.getEst_id(),list.getEst_Name(),
                                        list.getEst_address(), list.getEst_image(), list.getEst_phoneNum(),
                                        list.getEstLatitude(), list.getEstLongitude(), list.getOverallRating(), list.getRatingCounter(),
                                        distance));
                            }
                        }
                        sortByDistance(filterDistanceArrayList);
                        filterAdapter = new RestoFilterAdapter(NearbyResto.this, filterDistanceArrayList);
                        // setting adapter to our recycler view.
                        restoCafeRecyclerView.setAdapter(filterAdapter);
                        filterAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NearbyResto.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sortByDistance(List<EstModelFilter> filteredlist) {
        Collections.sort(filteredlist, new Comparator<EstModelFilter>() {
            @Override
            public int compare(EstModelFilter t1, EstModelFilter t2) {
                return Double.compare(Double.parseDouble(t1.getDistance()), Double.parseDouble(t2.getDistance()));
            }
        });

    }
}