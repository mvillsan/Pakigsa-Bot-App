package com.example.pakigsabot.RestaurantAndCafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pakigsabot.R;
import com.example.pakigsabot.RestaurantAndCafe.Adapters.RestoEstAdapter;
import com.example.pakigsabot.RestaurantAndCafe.Models.RestoEstModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TopRatedResto extends AppCompatActivity {
    ImageView imgBackBtn;

    // creating variables for our recycler view,
    // array list, adapter, firebase firestore,
    // and our progress bar.
    // initializing our variable for firebase
    // firestore and getting its instance.
    RecyclerView restoCafeRecyclerView;
    ArrayList<RestoEstModel> restoCafeEstArrayList;
    RestoEstAdapter restoCafeAdapter;
    FirebaseFirestore fStore;
    FirebaseUser user;
    String userID;
    ProgressDialog progressDialog;
    SearchView searchView;
    RatingBar ratingBar;
    String ratingValStr;
    TextView ratingDesc;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_rated_resto);

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
        restoCafeEstArrayList = new ArrayList<>();

        //initializing the arraylist where all data is stored
        restoCafeEstArrayList = new ArrayList<RestoEstModel>();

        //initializing the adapter
        restoCafeAdapter = new RestoEstAdapter(TopRatedResto.this, restoCafeEstArrayList);

        restoCafeRecyclerView.setAdapter(restoCafeAdapter);

        //SearchView initialization
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

        //Refresh the recyclerview::
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRestoRating3();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        //RatingBar
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                if (rating == 0) {
                    ratingDesc.setText("0");
                    getRestoRating0();
                } else if (rating == 1) {
                    ratingDesc.setText("1 Star");
                    getRestoRating1();
                } else if (rating == 2) {
                    ratingDesc.setText("2 Stars");
                    getRestoRating2();
                } else if (rating == 3) {
                    ratingDesc.setText("3 Stars");
                    getRestoRating3();
                } else if (rating == 4) {
                    ratingDesc.setText("4 Stars");
                    getRestoRating4();
                } else if (rating == 5) {
                    ratingDesc.setText("5 Stars");
                    getRestoRating5();
                } else {
                    ratingDesc.setText("");
                    getRestoRating0();
                }
                Toast.makeText(TopRatedResto.this, "" + ratingBar.getRating(), Toast.LENGTH_SHORT).show();
            }
        });

        imgBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseEst();
            }
        });
    }

    private void refs(){
        imgBackBtn = findViewById(R.id.imgBackBtn);
        searchView = findViewById(R.id.searchByNameSV);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        ratingBar = findViewById(R.id.ratingBar);
        ratingDesc = findViewById(R.id.ratingDesc);
    }

    private void chooseEst(){
        Intent intent = new Intent(getApplicationContext(), ChooseEstType.class);
        startActivity(intent);
    }

    //Display Restaurant and Cafe List
    private void getRestoRating0(){
        //Progress::
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        fStore.collection("establishments")
                .whereGreaterThanOrEqualTo("est_overallrating", "0")
                .whereLessThan("est_overallrating", "1")
                .orderBy("est_overallrating",Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        restoCafeEstArrayList.clear();

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
                            restoCafeEstArrayList.add(list);
                        }
                        restoCafeAdapter = new RestoEstAdapter(TopRatedResto.this, restoCafeEstArrayList);
                        // setting adapter to our recycler view.
                        restoCafeRecyclerView.setAdapter(restoCafeAdapter);
                        restoCafeAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TopRatedResto.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Display Restaurant and Cafe List
    private void getRestoRating1(){
        //Progress::
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        fStore.collection("establishments")
                .whereGreaterThanOrEqualTo("est_overallrating", "1")
                .whereLessThan("est_overallrating", "2")
                .orderBy("est_overallrating", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        restoCafeEstArrayList.clear();

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
                            restoCafeEstArrayList.add(list);
                        }
                        restoCafeAdapter = new RestoEstAdapter(TopRatedResto.this, restoCafeEstArrayList);
                        // setting adapter to our recycler view.
                        restoCafeRecyclerView.setAdapter(restoCafeAdapter);
                        restoCafeAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TopRatedResto.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Display Restaurant and Cafe List
    private void getRestoRating2(){
        //Progress::
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        fStore.collection("establishments")
                .whereGreaterThanOrEqualTo("est_overallrating", "2")
                .whereLessThan("est_overallrating", "3")
                .orderBy("est_overallrating", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        restoCafeEstArrayList.clear();

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
                            restoCafeEstArrayList.add(list);
                        }
                        restoCafeAdapter = new RestoEstAdapter(TopRatedResto.this, restoCafeEstArrayList);
                        // setting adapter to our recycler view.
                        restoCafeRecyclerView.setAdapter(restoCafeAdapter);
                        restoCafeAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TopRatedResto.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Display Restaurant and Cafe List
    private void getRestoRating3(){
        //Progress::
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        fStore.collection("establishments")
                .whereGreaterThanOrEqualTo("est_overallrating", "3")
                .whereLessThan("est_overallrating", "4")
                .orderBy("est_overallrating", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        restoCafeEstArrayList.clear();

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
                            restoCafeEstArrayList.add(list);
                        }
                        restoCafeAdapter = new RestoEstAdapter(TopRatedResto.this, restoCafeEstArrayList);
                        // setting adapter to our recycler view.
                        restoCafeRecyclerView.setAdapter(restoCafeAdapter);
                        restoCafeAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TopRatedResto.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Display Restaurant and Cafe List
    private void getRestoRating4(){
        //Progress::
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        fStore.collection("establishments")
                .whereGreaterThanOrEqualTo("est_overallrating", "4")
                .whereLessThan("est_overallrating", "5")
                .orderBy("est_overallrating", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        restoCafeEstArrayList.clear();

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
                            restoCafeEstArrayList.add(list);
                        }
                        restoCafeAdapter = new RestoEstAdapter(TopRatedResto.this, restoCafeEstArrayList);
                        // setting adapter to our recycler view.
                        restoCafeRecyclerView.setAdapter(restoCafeAdapter);
                        restoCafeAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TopRatedResto.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Display Restaurant and Cafe List
    private void getRestoRating5(){
        //Progress::
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        fStore.collection("establishments")
                .whereGreaterThanOrEqualTo("est_overallrating", "5")
                .whereLessThan("est_overallrating", "6")
                .orderBy("est_overallrating", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        restoCafeEstArrayList.clear();

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
                            restoCafeEstArrayList.add(list);
                        }
                        restoCafeAdapter = new RestoEstAdapter(TopRatedResto.this, restoCafeEstArrayList);
                        // setting adapter to our recycler view.
                        restoCafeRecyclerView.setAdapter(restoCafeAdapter);
                        restoCafeAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TopRatedResto.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filter(String newText){
        List<RestoEstModel> filteredList = new ArrayList<>();
        for(RestoEstModel item : restoCafeEstArrayList){
            if(item.getEst_Name().toLowerCase().startsWith(newText.toLowerCase())){
                filteredList.add(item);
            }
        }
        restoCafeAdapter.filterList(filteredList);
    }
}