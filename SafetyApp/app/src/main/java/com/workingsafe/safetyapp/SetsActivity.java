package com.workingsafe.safetyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

public class SetsActivity extends AppCompatActivity {

    private GridView setsGridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        setsGridView = findViewById(R.id.setsIdGridVw);
        GridAdapter gridAdapter = new GridAdapter(16);
        setsGridView.setAdapter(gridAdapter);
    }
}