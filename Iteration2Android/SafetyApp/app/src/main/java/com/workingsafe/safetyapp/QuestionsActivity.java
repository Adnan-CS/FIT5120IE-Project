package com.workingsafe.safetyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.workingsafe.safetyapp.model.QuestionModel;

import java.util.ArrayList;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity {
    private TextView question, noIndicator;
    private LinearLayout optionsContainer;
    private Button backBtn, nextBtn;
    private int count = 0;
    private List<QuestionModel> questionModelList;
    private int position = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        question = findViewById(R.id.questionQId);
        noIndicator = findViewById(R.id.noIndicatorID);
        optionsContainer = findViewById(R.id.optionsContainerId);
        backBtn = findViewById(R.id.backBtnId);
        nextBtn = findViewById(R.id.nextBtnId);
        questionModelList = new ArrayList<>();
        questionModelList.add(new QuestionModel("Question 1","Option A","Option B","Option C","Option D","Option A"));
        questionModelList.add(new QuestionModel("Question 2","Option A","Option B","Option C","Option D","Option A"));
        questionModelList.add(new QuestionModel("Question 3","Option A","Option B","Option C","Option D","Option A"));
        questionModelList.add(new QuestionModel("Question 4","Option A","Option B","Option C","Option D","Option A"));
        questionModelList.add(new QuestionModel("Question 5","Option A","Option B","Option C","Option D","Option A"));
        questionModelList.add(new QuestionModel("Question 6","Option A","Option B","Option C","Option D","Option A"));
        questionModelList.add(new QuestionModel("Question 7","Option A","Option B","Option C","Option D","Option A"));
        questionModelList.add(new QuestionModel("Question 8","Option A","Option B","Option C","Option D","Option A"));
        questionModelList.add(new QuestionModel("Question 9","Option A","Option B","Option C","Option D","Option A"));
        questionModelList.add(new QuestionModel("Question 10","Option A","Option B","Option C","Option D","Option A"));


        for(int i=0;i<4;i++){
            optionsContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswers((Button) v);
                }
            });
        }

        playAnimation(question,0,questionModelList.get(position).getQuestion());
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextBtn.setEnabled(false);
                nextBtn.setAlpha(0.7f);
                optionEnable(true);
                position++;
                if(position == questionModelList.size()){
                    //Send the user to score activity
                    Intent intent = new Intent(QuestionsActivity.this,QuizScoreActivity.class);
                    intent.putExtra("score",score);
                    intent.putExtra("total",questionModelList.size());
                    startActivity(intent);
                    finish();
                    return;
                }
                count = 0;
                playAnimation(question,0,questionModelList.get(position).getQuestion());
            }
        });
    }
    private void playAnimation(View view, int value,String data){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

                if(value==0 && count<4){
                    String option="";
                    if(count==0){
                        option = questionModelList.get(position).getQuestionA();
                    }
                    else if(count==1){
                        option = questionModelList.get(position).getQuestionB();
                    }
                    else if(count==2){
                        option = questionModelList.get(position).getQuestionC();
                    }
                    else if(count==3){
                        option = questionModelList.get(position).getQuestionD();
                    }
                    playAnimation(optionsContainer.getChildAt(count),0,option);
                    count++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //Need to change data
                if(value==0){
                    try{
                        ((TextView)view).setText(data);
                        noIndicator.setText(position+1+"/"+questionModelList.size());
                    }catch (ClassCastException ex){
                        ((Button)view).setText(data);
                    }
                    view.setTag(data);
                    playAnimation(view,1,data);
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
    private void checkAnswers(Button selectedOption){
        optionEnable(false);
        nextBtn.setEnabled(true);
        nextBtn.setAlpha(1);
        if(selectedOption.getText().toString().equals(questionModelList.get(position).getQuestionAns())){
            //Correct answer
            score++;
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
        }else{
            //incorrect answer
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
            Button correctOpt = (Button) optionsContainer.findViewWithTag(questionModelList.get(position).getQuestionAns());
            correctOpt.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
        }
    }
    //Disable other unselected option here
    private void optionEnable(boolean enableOption){
        for(int i=0;i<4;i++){
            optionsContainer.getChildAt(i).setEnabled(enableOption);
            if(enableOption){
                optionsContainer.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#989898")));
            }
        }
    }
}