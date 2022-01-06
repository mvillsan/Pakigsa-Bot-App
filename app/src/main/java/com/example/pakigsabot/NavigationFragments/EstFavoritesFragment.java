package com.example.pakigsabot.NavigationFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pakigsabot.R;
import com.example.pakigsabot.Favorites.ServicesFavorites;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EstFavoritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstFavoritesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EstFavoritesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EstFavoritesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EstFavoritesFragment newInstance(String param1, String param2) {
        EstFavoritesFragment fragment = new EstFavoritesFragment();
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
        View view = inflater.inflate(R.layout.fragment_estfavorites, container, false);

        //References:
        ImageView estFav = (ImageView) view.findViewById(R.id.estFavImageView);
        ImageView servFav = (ImageView) view.findViewById(R.id.servicesFavImageView);

        estFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                estFavScreen();
            }
        });

        servFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                services();
            }
        });

        return view;
    }

    public void estFavScreen(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(EstFavoritesFragment.this).attach(EstFavoritesFragment.this).commit();
    }

    public void services(){
         Intent intent = new Intent(getActivity(), ServicesFavorites.class);
         intent.putExtra("servFav", "servFav");
         startActivity(intent);
    }
}