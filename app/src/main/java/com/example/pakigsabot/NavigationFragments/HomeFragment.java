package com.example.pakigsabot.NavigationFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pakigsabot.DentalClinics.DentalClinicReservation;
import com.example.pakigsabot.Feedback.Feedback;
import com.example.pakigsabot.InternetCafe.InternetCafeReservation;
import com.example.pakigsabot.MainActivity;
import com.example.pakigsabot.NotificationAlerts.EstCancelled;
import com.example.pakigsabot.NotificationAlerts.EstConfirmed;
import com.example.pakigsabot.Profile.Profile;
import com.example.pakigsabot.R;
import com.example.pakigsabot.Resorts.ResortReservation;
import com.example.pakigsabot.Resto.RestaurantReservation;
import com.example.pakigsabot.ShareApp.Share;
import com.example.pakigsabot.SpaSalon.SpaSalonReservation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //References::
        ImageView profileBtn = (ImageView) view.findViewById(R.id.profileImageView);
        ImageButton shareBtn = (ImageButton) view.findViewById(R.id.shareBtn);
        ImageButton signOutBtn = (ImageButton) view.findViewById(R.id.signoutHomeBtn);
        ImageButton resortBtn = (ImageButton) view.findViewById(R.id.resortBtn);
        ImageButton dentalClinicBtn = (ImageButton) view.findViewById(R.id.dentalClinicBtn);
        ImageButton spaSalonBtn = (ImageButton) view.findViewById(R.id.spaSalonBtn);
        ImageButton internetCafeBtn = (ImageButton) view.findViewById(R.id.internetCafeBtn);
        ImageButton restoBtn = (ImageButton) view.findViewById(R.id.restoBtn);
        TextView estConfirmed = (TextView) view.findViewById(R.id.upcomingReservesTxt);
        ImageButton estCancelled = (ImageButton) view.findViewById(R.id.noUpcomingRsrvBtn);

        //Fetching Data from FireStore DB
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        String userID = fAuth.getCurrentUser().getUid();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();


        StorageReference profileRef = storageRef.child("customers/"+userID+"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileBtn);
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileScreen();
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareScreen();
            }
        });

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOutApp();
            }
        });

        resortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resortsReservation();
            }
        });

        dentalClinicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dentalClinicsReservation();
            }
        });

        spaSalonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spaSalonReservation();
            }
        });

        internetCafeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                internetCafeReservation();
            }
        });

        restoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restoReservation();
            }
        });

        estConfirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmedEst();
            }
        });

        estCancelled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelledEst();
            }
        });

        return view;
    }

    public void profileScreen(){
        Intent in = new Intent(getActivity(), Profile.class);
        in.putExtra("profile", "profile");
        startActivity(in);
    }

    public void shareScreen(){
        Intent in = new Intent(getActivity(), Share.class);
        in.putExtra("share", "share");
        startActivity(in);
    }

    public void signOutApp(){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getActivity(),"Signed Out from Pakigsa-Bot", Toast.LENGTH_SHORT).show();
        Intent in = new Intent(getActivity(), MainActivity.class);
        in.putExtra("signin", "signin");
        startActivity(in);
        getActivity().finish();
    }

    public void resortsReservation(){
        Intent in = new Intent(getActivity(), ResortReservation.class);
        in.putExtra("resortsReserve", "resortsReserve");
        startActivity(in);
    }

    public void dentalClinicsReservation(){
        Intent in = new Intent(getActivity(), DentalClinicReservation.class);
        in.putExtra("dentalClinicReserve", "dentalClinicReserve");
        startActivity(in);
    }

    public void spaSalonReservation(){
        Intent in = new Intent(getActivity(), SpaSalonReservation.class);
        in.putExtra("spaSalonReserve", "spaSalonReserve");
        startActivity(in);
    }

    public void internetCafeReservation(){
        Intent in = new Intent(getActivity(), InternetCafeReservation.class);
        in.putExtra("internetCafeReserve", "internetCafeReserve");
        startActivity(in);
    }

    public void restoReservation(){
        Intent in = new Intent(getActivity(), RestaurantReservation.class);
        in.putExtra("restaurantsReserve", "restaurantsReserve");
        startActivity(in);
    }

    public void confirmedEst(){
        Intent in = new Intent(getActivity(), EstConfirmed.class);
        in.putExtra("estConfirmedd", "estConfirmedd");
        startActivity(in);
    }

    public void cancelledEst(){
        Intent in = new Intent(getActivity(), EstCancelled.class);
        in.putExtra("estCancelled", "estCancelled");
        startActivity(in);
    }
}