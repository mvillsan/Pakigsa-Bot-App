/*
package com.example.pakigsabot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pakigsabot.recycleview.AdapterRV;
import com.example.pakigsabot.recycleview.ItemRV;

import java.util.ArrayList;

public class Feedback extends AppCompatActivity {

    private ArrayList<ItemRV> mExampleList;

    private RecyclerView mRecyclerView;
    private AdapterRV mAdapterRV;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("fb") != null){
                Toast.makeText(getApplicationContext(), "data: " + bundle.getString("fb"),
                        Toast.LENGTH_SHORT).show();
            }
        }

        createExampleList();
        buildRecyclerView();

        EditText editText = findViewById(R.id.estEditTxtFB);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }
    private void filter(String text) {
        ArrayList<ItemRV> filteredList = new ArrayList<>();

        for (ItemRV item : mExampleList) {
            if (item.getText1().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        mAdapter.filterList(filteredList);
    }

    private void createExampleList() {
        mExampleList = new ArrayList<>();
        mExampleList.add(new ItemRV(R.drawable.back_arrow, "One", "Line 2"));
        mExampleList.add(new ItemRV(R.drawable.back_arrow, "Two", "Line 2"));
        mExampleList.add(new ItemRV(R.drawable.back_arrow, "Three", "Line 2"));
        mExampleList.add(new ItemRV(R.drawable.back_arrow, "Four", "Line 2"));
        mExampleList.add(new ItemRV(R.drawable.back_arrow, "Five", "Line 2"));
        mExampleList.add(new ItemRV(R.drawable.back_arrow, "Six", "Line 2"));
        mExampleList.add(new ItemRV(R.drawable.back_arrow, "Seven", "Line 2"));
        mExampleList.add(new ItemRV(R.drawable.back_arrow, "Eight", "Line 2"));
        mExampleList.add(new ItemRV(R.drawable.back_arrow, "Nine", "Line 2"));
    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.estRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new AdapterRV(mExampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

}*/
