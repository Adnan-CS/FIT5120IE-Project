package com.workingsafe.safetyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QuestionsActivity extends AppCompatActivity {
    private TextView question, noIndicator;
    private LinearLayout optionsContainer;
    private Button backBtn, nextBtn;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        question = findViewById(R.id.questionQId);
        noIndicator = findViewById(R.id.noIndicatorID);
        optionsContainer = findViewById(R.id.optionsContainerId);
        backBtn = findViewById(R.id.backBtnId);
        nextBtn = findViewById(R.id.nextBtnId);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 0;
                playAnimation(question,0);
            }
        });
    }
    private void playAnimation(View view, int value){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if(value==0 && count<4){
                    playAnimation(optionsContainer.getChildAt(count),0);
                    count++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //Need to change data
                if(value==0){
                    playAnimation(view,1);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}