package com.workingsafe.safetyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.workingsafe.safetyapp.model.QuestionModel;
import com.workingsafe.safetyapp.model.Scenario;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.widget.FButton;

public class QuestionsActivity extends AppCompatActivity {
    private LinearLayout optionsContainer;
    private ImageView nextBtn;
    private int count = 0;
    private int position = 0;
    private int score = 0;
    private ArrayList<Scenario> scenarioImagesResource;
    private ArrayList<String> questionOneOptions;
    private FButton opt1;
    private FButton opt2;
    private FButton opt3;
    private FButton opt4;
    private boolean isButtonSelected;
    private String previousSelectedOption;
    private boolean gameOver;
    private int noOfOptions = 0;
    private LinearLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        optionsContainer = findViewById(R.id.optionsContainerId);
        nextBtn = findViewById(R.id.nextBtnId);
        rootLayout = findViewById(R.id.rootLayoutId);
        opt1 = findViewById(R.id.buttonOpt1);
        opt2 = findViewById(R.id.buttonOpt2);
        opt3 = findViewById(R.id.buttonOpt3);
        opt4 = findViewById(R.id.buttonOpt4);
        isButtonSelected = false;
        previousSelectedOption=null;
        gameOver = false;
        getSupportActionBar().setTitle("User Scenarios");

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        scenarioImagesResource = new ArrayList<>();
        scenarioImagesResource.add(new Scenario(R.drawable.primary_1,false,"scenarioOne"));
        scenarioImagesResource.add(new Scenario(R.drawable.primary_2,false,"scenarioOne"));
        scenarioImagesResource.add(new Scenario(R.drawable.primary_3,false,"scenarioOne"));
        scenarioImagesResource.add(new Scenario(R.drawable.primary_4,false,"scenarioOne"));

        scenarioImagesResource.add(new Scenario(R.drawable.question1_5,true,"scenarioOne"));

        questionOneOptions = new ArrayList<>();
        questionOneOptions.add("Continue the chat casually");
        questionOneOptions.add("End Conversation abruptly and leave");
        questionOneOptions.add("Change the topic");
        questionOneOptions.add("Feel angry and question him why he asked it");



        initializeOptionOne(4);
        //scenarioImages.setImage(ImageSource.resource(scenarioImagesResource.get(count).getImageId()));
        rootLayout.setBackgroundResource(scenarioImagesResource.get(count).getImageId());

        for(int i=0;i<4;i++){
            optionsContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkScenarioOne((Button)v);
                }
            });
        }

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;

                if(isButtonSelected && previousSelectedOption.toLowerCase().equals("continue the chat casually")){
                    previousSelectedOption = null;
                    isButtonSelected = false;
                    addLateWorkScenario();
                }else if(isButtonSelected && previousSelectedOption.toLowerCase().equals("decide to report to the hr tomorrow")){
                    previousSelectedOption = null;
                    isButtonSelected = false;
                    goingToHR();
                }else if(isButtonSelected && previousSelectedOption.toLowerCase().equals("You think Eric did it. You are shy and afraid to lose your job, so you decide to hide".toLowerCase())){
                    previousSelectedOption = null;
                    isButtonSelected = false;
                    afraidDecidedToHide();
                }
                else if(isButtonSelected && previousSelectedOption.toLowerCase().equals("continue to endure for your job")){
                    previousSelectedOption = null;
                    isButtonSelected = false;
                    followHome();
                }
                else if(isButtonSelected && previousSelectedOption.toLowerCase().equals("hit him hard with the bag")){
                    previousSelectedOption = null;
                    isButtonSelected = false;
                    hospitalExpense();
                }
                else if(isButtonSelected && previousSelectedOption.toLowerCase().equals("You screamed in fright and ran to the crowded place".toLowerCase())){
                    previousSelectedOption = null;
                    isButtonSelected = false;
                    hearCryEricFired();
                }
                else if(isButtonSelected && previousSelectedOption.toLowerCase().equals("You share this text message with your friend Amy and ask her for her opinion".toLowerCase())){
                    previousSelectedOption = null;
                    isButtonSelected = false;
                    angryTextMessage();
                }
                else if(isButtonSelected && previousSelectedOption.toLowerCase().equals("You feel that it doesn't matter, and you delete the text message at hand".toLowerCase())){
                    previousSelectedOption = null;
                    isButtonSelected = false;
                    deleteMsgAssaulted();
                }
                else if(isButtonSelected && previousSelectedOption.toLowerCase().equals("Resign".toLowerCase())){
                    previousSelectedOption = null;
                    isButtonSelected = false;
                    realizeThenDecide();
                }//Seek help from a psychologist
                else if(isButtonSelected && previousSelectedOption.toLowerCase().equals("Seek help from a psychologist".toLowerCase())){
                    previousSelectedOption = null;
                    isButtonSelected = false;
                    counsellingHelp();
                }
                else if(isButtonSelected && previousSelectedOption.toLowerCase().equals("Refuse to acknowledge your physical condition".toLowerCase())){
                    previousSelectedOption = null;
                    isButtonSelected = false;
                    refuseAcknowledge();
                }
                else if(isButtonSelected && previousSelectedOption.toLowerCase().equals("Find a legal agency for help".toLowerCase())){
                    previousSelectedOption = null;
                    isButtonSelected = false;
                    legalAgencyFrHelp();
                }
                else if(isButtonSelected && previousSelectedOption.toLowerCase().equals("Ask for help on the safe Women app".toLowerCase())){
                    previousSelectedOption = null;
                    isButtonSelected = false;
                    askForHelpApp();
                }//End Conversation abruptly and leave
                else if(isButtonSelected && previousSelectedOption.toLowerCase().equals("End Conversation abruptly and leave".toLowerCase())){
                    previousSelectedOption = null;
                    isButtonSelected = false;
                    endConversation();
                }
                else if(isButtonSelected && previousSelectedOption.toLowerCase().equals("Change the topic".toLowerCase())){
                    previousSelectedOption = null;
                    isButtonSelected = false;
                    changeThetopic();
                }
                else if(isButtonSelected && previousSelectedOption.toLowerCase().equals("Feel angry and question him why he asked it".toLowerCase())){
                    previousSelectedOption = null;
                    isButtonSelected = false;
                    feelAngry();
                }
                if(!scenarioImagesResource.get(count).isQuestion()){
                    rootLayout.setBackgroundResource(scenarioImagesResource.get(count).getImageId());
                    //scenarioImages.setImage(ImageSource.resource(scenarioImagesResource.get(count).getImageId()));
                }
                else if(gameOver && scenarioImagesResource.get(count).isQuestion()){
                    rootLayout.setBackgroundResource(scenarioImagesResource.get(count).getImageId());
                    //scenarioImages.setImage(ImageSource.resource(scenarioImagesResource.get(count).getImageId()));
                    nextBtn.setEnabled(false);
                    gameOverFinishActivity();
                }
                else{
                    rootLayout.setBackgroundResource(scenarioImagesResource.get(count).getImageId());
                    //scenarioImages.setImage(ImageSource.resource(scenarioImagesResource.get(count).getImageId()));
                    optionsContainer.setVisibility(View.VISIBLE);
                    changeEnableStatus(false);
                }
               /* optionsContainer.setVisibility(View.VISIBLE);*/
            }
        });
    }
    private void feelAngry(){
        optionsContainer.setVisibility(View.GONE);
        scenarioImagesResource.add(new Scenario(R.drawable.feelangry_1,false,"scenarioSixteen"));
        scenarioImagesResource.add(new Scenario(R.drawable.feelangry_2,false,"scenarioSixteen"));
        scenarioImagesResource.add(new Scenario(R.drawable.feelangry_3,false,"scenarioSixteen"));
        scenarioImagesResource.add(new Scenario(R.drawable.gameover,false,"scenarioSixteen"));
        scenarioImagesResource.add(new Scenario(R.drawable.info_summary,true,"scenarioFive"));
        gameOver = true;
        questionOneOptions.clear();
    }
    private void changeThetopic(){
        optionsContainer.setVisibility(View.GONE);
        scenarioImagesResource.add(new Scenario(R.drawable.changetopic_1,false,"scenarioFifteen"));
        scenarioImagesResource.add(new Scenario(R.drawable.changetopic_2,false,"scenarioFifteen"));
        scenarioImagesResource.add(new Scenario(R.drawable.changetopic_3,false,"scenarioFifteen"));
        scenarioImagesResource.add(new Scenario(R.drawable.happyending,false,"scenarioFifteen"));
        scenarioImagesResource.add(new Scenario(R.drawable.info_summary,true,"scenarioFive"));
        gameOver = true;
        questionOneOptions.clear();
    }
    private void endConversation(){
        optionsContainer.setVisibility(View.GONE);
        scenarioImagesResource.add(new Scenario(R.drawable.endconversation,false,"scenarioFourteen"));
        scenarioImagesResource.add(new Scenario(R.drawable.gameover,false,"scenarioFourteen"));
        scenarioImagesResource.add(new Scenario(R.drawable.info_summary,true,"scenarioFive"));
        gameOver = true;
        questionOneOptions.clear();
    }
    private void refuseAcknowledge(){
        optionsContainer.setVisibility(View.GONE);
        scenarioImagesResource.add(new Scenario(R.drawable.refuseack,false,"scenarioEleven"));
        scenarioImagesResource.add(new Scenario(R.drawable.gameover,false,"scenarioEleven"));
        scenarioImagesResource.add(new Scenario(R.drawable.info_summary,true,"scenarioFive"));
        gameOver = true;
        questionOneOptions.clear();
    }
    private void askForHelpApp(){
        optionsContainer.setVisibility(View.GONE);
        scenarioImagesResource.add(new Scenario(R.drawable.learnapp,false,"scenarioThirteen"));
        scenarioImagesResource.add(new Scenario(R.drawable.psychologist,false,"scenarioThirteen"));
        scenarioImagesResource.add(new Scenario(R.drawable.happyending,false,"scenarioThirteen"));
        scenarioImagesResource.add(new Scenario(R.drawable.info_summary,true,"scenarioFive"));
        gameOver = true;
        questionOneOptions.clear();
    }
    private void legalAgencyFrHelp(){
        optionsContainer.setVisibility(View.GONE);
        scenarioImagesResource.add(new Scenario(R.drawable.foundagency,false,"scenarioTwelve"));
        scenarioImagesResource.add(new Scenario(R.drawable.lawsuit,false,"scenarioTwelve"));
        scenarioImagesResource.add(new Scenario(R.drawable.graduallyforgot,false,"scenarioTwelve"));
        scenarioImagesResource.add(new Scenario(R.drawable.blindlytolerate,false,"scenarioTwelve"));
        scenarioImagesResource.add(new Scenario(R.drawable.happyending,false,"scenarioTwelve"));
        scenarioImagesResource.add(new Scenario(R.drawable.info_summary,true,"scenarioFive"));
        gameOver = true;
        questionOneOptions.clear();
    }
    private void counsellingHelp(){
        optionsContainer.setVisibility(View.GONE);
        scenarioImagesResource.add(new Scenario(R.drawable.foundcounselling,false,"scenarioTen"));
        scenarioImagesResource.add(new Scenario(R.drawable.happyending,false,"scenarioTen"));
        scenarioImagesResource.add(new Scenario(R.drawable.info_summary,true,"scenarioFive"));
        gameOver = true;
        questionOneOptions.clear();
    }
    private void realizeThenDecide(){
        optionsContainer.setVisibility(View.GONE);
        scenarioImagesResource.add(new Scenario(R.drawable.resignillness,false,"scenarioNine"));
        scenarioImagesResource.add(new Scenario(R.drawable.resignrealize,true,"scenarioNine"));
        questionOneOptions.clear();
        addrealizeDecOptions();
    }
    private void angryTextMessage(){
        optionsContainer.setVisibility(View.GONE);
        scenarioImagesResource.add(new Scenario(R.drawable.friendangry,false,"scenarioEight"));
        scenarioImagesResource.add(new Scenario(R.drawable.weekoff,false,"scenarioEight"));
        scenarioImagesResource.add(new Scenario(R.drawable.glassboy,false,"scenarioEight"));
        scenarioImagesResource.add(new Scenario(R.drawable.ericdare,false,"scenarioEight"));
        scenarioImagesResource.add(new Scenario(R.drawable.happyending,false,"scenarioEight"));
        scenarioImagesResource.add(new Scenario(R.drawable.info_summary,true,"scenarioFive"));
        gameOver = true;
        questionOneOptions.clear();
    }
    private void hearCryEricFired(){
        optionsContainer.setVisibility(View.GONE);
        scenarioImagesResource.add(new Scenario(R.drawable.hearcry,false,"scenarioSeven"));
        scenarioImagesResource.add(new Scenario(R.drawable.ericfired,false,"scenarioSeven"));
        scenarioImagesResource.add(new Scenario(R.drawable.happyending,false,"scenarioSeven"));
        scenarioImagesResource.add(new Scenario(R.drawable.info_summary,true,"scenarioFive"));
        gameOver = true;
        questionOneOptions.clear();
    }
    private void hospitalExpense(){
        optionsContainer.setVisibility(View.GONE);
        scenarioImagesResource.add(new Scenario(R.drawable.hospital,false,"scenarioSix"));
        scenarioImagesResource.add(new Scenario(R.drawable.medicalexpense,false,"scenarioSix"));
        scenarioImagesResource.add(new Scenario(R.drawable.gameover,false,"scenarioSix"));
        scenarioImagesResource.add(new Scenario(R.drawable.info_summary,true,"scenarioFive"));
        gameOver = true;
        questionOneOptions.clear();
    }
    private void followHome(){
        optionsContainer.setVisibility(View.GONE);
        scenarioImagesResource.add(new Scenario(R.drawable.qfive,false,"scenarioFive"));
        scenarioImagesResource.add(new Scenario(R.drawable.gameover,false,"scenarioFive"));
        scenarioImagesResource.add(new Scenario(R.drawable.info_summary,true,"scenarioFive"));
        gameOver = true;
        questionOneOptions.clear();
    }
    private void afraidDecidedToHide(){
        optionsContainer.setVisibility(View.GONE);
        scenarioImagesResource.add(new Scenario(R.drawable.qfour_1,false,"scenarioFour"));
        scenarioImagesResource.add(new Scenario(R.drawable.qfour_2,true,"scenarioFour"));
        questionOneOptions.clear();
        addAfraidHideOptions();
    }
    private void deleteMsgAssaulted(){
        optionsContainer.setVisibility(View.GONE);
        scenarioImagesResource.add(new Scenario(R.drawable.deletemessage,false,"scenarioEight"));
        scenarioImagesResource.add(new Scenario(R.drawable.assaulted,true,"scenarioEight"));
        questionOneOptions.clear();
        addDelMsgOptions();
    }
    private void addrealizeDecOptions(){
        questionOneOptions.add("Seek help from a psychologist");
        questionOneOptions.add("Refuse to acknowledge your physical condition");
        initializeOptionOne(2);
        previousSelectedOption = null;
    }
    private void addDelMsgOptions(){
        questionOneOptions.add("Resign");
        questionOneOptions.add("Find a legal agency for help");
        questionOneOptions.add("Ask for help on the safe Women app");
        initializeOptionOne(3);
        previousSelectedOption = null;
    }
    private void addAfraidHideOptions(){
        questionOneOptions.add("Continue to endure for your job");
        questionOneOptions.add("Hit him hard with the bag");
        questionOneOptions.add("You screamed in fright and ran to the crowded place");
        /*questionOneOptions.add("Choose any from above :)");*/
        initializeOptionOne(3);
        previousSelectedOption = null;
    }
    private void gameOverFinishActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 4000);
    }
    private void addLateWorkScenario(){
        optionsContainer.setVisibility(View.GONE);
        scenarioImagesResource.add(new Scenario(R.drawable.qoneopoutput_1,false,"scenarioTwo"));
        scenarioImagesResource.add(new Scenario(R.drawable.qtwoabranch,false,"scenarioTwo"));
        scenarioImagesResource.add(new Scenario(R.drawable.qtwoabranch_2,false,"scenarioTwo"));
        scenarioImagesResource.add(new Scenario(R.drawable.qtwoabranch_3,false,"scenarioTwo"));
        scenarioImagesResource.add(new Scenario(R.drawable.qtwoabranch_4,false,"scenarioTwo"));
        scenarioImagesResource.add(new Scenario(R.drawable.question2,true,"scenarioTwo"));
        questionOneOptions.clear();
        addLateWorkerOptions();
    }
    private void goingToHR(){
        optionsContainer.setVisibility(View.GONE);
        scenarioImagesResource.add(new Scenario(R.drawable.qthree_1,false,"scenarioThree"));
        scenarioImagesResource.add(new Scenario(R.drawable.qthree_2,false,"scenarioThree"));
        scenarioImagesResource.add(new Scenario(R.drawable.qthree_3,false,"scenarioThree"));
        scenarioImagesResource.add(new Scenario(R.drawable.qthree_4,false,"scenarioThree"));
        scenarioImagesResource.add(new Scenario(R.drawable.gameover,false,"scenarioThree"));
        scenarioImagesResource.add(new Scenario(R.drawable.info_summary,true,"scenarioThree"));
        gameOver = true;
        questionOneOptions.clear();
    }
    private void addLateWorkerOptions(){
        questionOneOptions.add("Decide to report to the HR tomorrow");
        questionOneOptions.add("You think Eric did it. You are shy and afraid to lose your job, so you decide to hide");
        questionOneOptions.add("You share this text message with your friend Amy and ask her for her opinion");
        questionOneOptions.add("You feel that it doesn't matter, and you delete the text message at hand");
        initializeOptionOne(4);
        previousSelectedOption = null;
    }
    private void changeEnableStatus(boolean status){
        nextBtn.setEnabled(status);
    }
    private void checkScenarioOne(Button selectedOption){
        for(int i=0;i<4;i++){
            if(selectedOption.getText().toString().toLowerCase().equals(questionOneOptions.get(i).toLowerCase())){
                changeEnableStatus(true);
                isButtonSelected = true;
                previousSelectedOption = questionOneOptions.get(i);
                Toast.makeText(this,questionOneOptions.get(i),Toast.LENGTH_SHORT).show();
                break;
            }
        }

    }
    private void initializeOptionOne(int noOfOptions){
        for(int i=0;i<noOfOptions;i++){
            ((Button)optionsContainer.getChildAt(i)).setText(questionOneOptions.get(i));
        }
        if(noOfOptions==3){
            Button button = findViewById(R.id.buttonOpt4);
            button.setVisibility(View.INVISIBLE);
        }
        if(noOfOptions==2){
            Button button1 = findViewById(R.id.buttonOpt4);
            Button button2 = findViewById(R.id.buttonOpt3);
            button1.setVisibility(View.INVISIBLE);
            button2.setVisibility(View.INVISIBLE);
        }
    }
}