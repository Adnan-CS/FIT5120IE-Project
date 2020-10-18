package com.workingsafe.safetyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizScoreActivity extends AppCompatActivity {
    private TextView quizScore;
    private Button doneButton;
    private TextView quizTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_score);
        quizScore = findViewById(R.id.scoreTextId);
        doneButton = findViewById(R.id.quizDoneBtnId);
        quizTotal = findViewById(R.id.quizTotalId);
        //Set the value received from last activity [QuestionsActivity]
        quizScore.setText(String.valueOf(getIntent().getIntExtra("score",0)));
        quizTotal.setText(String.valueOf("Out of "+getIntent().getIntExtra("total",0)));

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}