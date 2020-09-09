package com.workingsafe.safetyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.workingsafe.safetyapp.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class QuizCtgrsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_ctgrs);
        getSupportActionBar().setTitle("Quiz Category");
        recyclerView = findViewById(R.id.recviewCat);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        List<CategoryModel> categoryModelList = new ArrayList<>();
        categoryModelList.add(new CategoryModel("","Category 1"));
        categoryModelList.add(new CategoryModel("","Category 2"));
        categoryModelList.add(new CategoryModel("","Category 3"));
        categoryModelList.add(new CategoryModel("","Category 4"));
        categoryModelList.add(new CategoryModel("","Category 5"));
        categoryModelList.add(new CategoryModel("","Category 6"));
        categoryModelList.add(new CategoryModel("","Category 7"));
        categoryModelList.add(new CategoryModel("","Category 8"));
        categoryModelList.add(new CategoryModel("","Category 9"));
        categoryModelList.add(new CategoryModel("","Category 10"));
        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryModelList);
        recyclerView.setAdapter(categoryAdapter);
    }
}