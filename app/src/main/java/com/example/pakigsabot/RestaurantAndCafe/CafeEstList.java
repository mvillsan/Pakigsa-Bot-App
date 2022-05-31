
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
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pakigsabot.R;
import com.example.pakigsabot.RestaurantAndCafe.Adapters.CafeEstAdapter;
import com.example.pakigsabot.RestaurantAndCafe.Models.CafeEstModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CafeEstList extends AppCompatActivity {
    ImageView imgBackBtn;

    // creating variables for our recycler view,
    // array list, adapter, firebase firestore,
    // and our progress bar.
    // initializing our variable for firebase
    // firestore and getting its instance.
    RecyclerView restoCafeRecyclerView;
    ArrayList<CafeEstModel> restoCafeEstArrayList;
    CafeEstAdapter restoCafeAdapter;
    FirebaseFirestore fStore;
    FirebaseUser user;
    String userID;
    ProgressDialog progressDialog;
    SearchView searchView;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_est_list);

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
        restoCafeEstArrayList = new ArrayList<CafeEstModel>();

        //initializing the adapter
        restoCafeAdapter = new CafeEstAdapter(CafeEstList.this, restoCafeEstArrayList);

        restoCafeRecyclerView.setAdapter(restoCafeAdapter);

        // below line is use to get the data from Firebase Firestore.
        getRestoCafeList();

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

    private void refs(){
        imgBackBtn = findViewById(R.id.imgBackBtn);
        searchView = findViewById(R.id.searchByNameSV);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
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
                .whereEqualTo("est_Type", "Cafe")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        restoCafeEstArrayList.clear();

                        for(DocumentSnapshot doc : task.getResult()){
                            CafeEstModel list = new CafeEstModel(doc.getString("est_id"),
                                    doc.getString("est_Name"),
                                    doc.getString("est_address"),
                                    doc.getString("est_image"),
                                    doc.getString("est_phoneNum"));
                            restoCafeEstArrayList.add(list);
                        }
                        restoCafeAdapter = new CafeEstAdapter(CafeEstList.this, restoCafeEstArrayList);
                        // setting adapter to our recycler view.
                        restoCafeRecyclerView.setAdapter(restoCafeAdapter);
                        restoCafeAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CafeEstList.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filter(String newText){
        List<CafeEstModel> filteredList = new ArrayList<>();
        for(CafeEstModel item : restoCafeEstArrayList){
            if(item.getEst_Name().toLowerCase().startsWith(newText.toLowerCase())){
                filteredList.add(item);
            }
        }
        restoCafeAdapter.filterList(filteredList);
    }
}