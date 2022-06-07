package com.example.pakigsabot.FeedbackBO;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pakigsabot.R;
import com.example.pakigsabot.ReservationHistoryBO.ViewReservationHistoryRestaurant;
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

public class ViewFeedbackResto extends AppCompatActivity {
    //Initialization of variables::
    ImageView imgBackBtn;

    // creating variables for our recycler view,
    // array list, adapter, firebase firestore,
    // and our progress bar.
    // initializing our variable for firebase
    // firestore and getting its instance.
    RecyclerView feedbackRecyclerView;
    ArrayList<FeedbackModel> feedbackArrayList;
    FeedbackAdapter feedbackAdapter;
    FirebaseFirestore fStore;
    FirebaseUser user;
    String userID;
    ProgressDialog progressDialog;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback_resto);

        //References
        refs();


        //recyclerview initialization
        feedbackRecyclerView = findViewById(R.id.feedbackRecyclerView);
        feedbackRecyclerView.setHasFixedSize(true);
        feedbackRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // adding our array list to our recycler view adapter class.
        fStore=FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        feedbackArrayList = new ArrayList<>();

        //initializing the arraylist where all data is stored
        feedbackArrayList = new ArrayList<FeedbackModel>();

        //initializing the adapter
        feedbackAdapter = new FeedbackAdapter(ViewFeedbackResto.this, feedbackArrayList);

        feedbackRecyclerView.setAdapter(feedbackAdapter);

        // below line is used to get the data from Firebase Firestore.
        getFeedbackList();

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

        //Back to Reservation History
        imgBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reservationHistory();
            }
        });
    }

    private void refs(){
        imgBackBtn = findViewById(R.id.imgBackBtn);
        searchView = findViewById(R.id.searchByDateSV);
    }

    private void getFeedbackList(){
        //Progress::
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        fStore.collection("feedback")
                .whereEqualTo("feedbackEstID", userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        feedbackArrayList.clear();

                        for(DocumentSnapshot doc : task.getResult()){
                            FeedbackModel list = new FeedbackModel(doc.getString("feedbackID"),
                                    doc.getString("feedbackEstID"),
                                    doc.getString("feedbackCustID"),
                                    doc.getString("feedbackDate"),
                                    doc.getString("feedbackReview"),
                                    doc.getString("feedbackRating"),
                                    doc.getString("feedbackDesc"));
                            feedbackArrayList.add(list);
                        }
                        feedbackAdapter = new FeedbackAdapter(ViewFeedbackResto.this,feedbackArrayList);
                        // setting adapter to our recycler view.
                        feedbackRecyclerView.setAdapter(feedbackAdapter);
                        feedbackAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ViewFeedbackResto.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void reservationHistory(){
        Intent intent = new Intent(getApplicationContext(), ViewReservationHistoryRestaurant.class);
        startActivity(intent);
    }

    private void filter(String newText){
        List<FeedbackModel> filteredList = new ArrayList<>();
        for(FeedbackModel item : feedbackArrayList){
            if(item.getFeedbackDate().toLowerCase().startsWith(newText.toLowerCase())){
                filteredList.add(item);
            }
        }
        feedbackAdapter.filterList(filteredList);
    }
}