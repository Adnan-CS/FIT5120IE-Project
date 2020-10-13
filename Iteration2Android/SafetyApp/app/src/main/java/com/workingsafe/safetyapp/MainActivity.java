package com.workingsafe.safetyapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Toast;

import com.workingsafe.safetyapp.model.ContactPerson;
import com.workingsafe.safetyapp.sos.AddContact;
import com.workingsafe.safetyapp.sos.ContactHelper;
import com.workingsafe.safetyapp.sos.ContactListActivity;
import com.workingsafe.safetyapp.sos.TimerActivity;
import com.workingsafe.safetyapp.utility.Utility;
import com.workingsafe.safetyapp.videoquiz.HomeScreen;
import com.workingsafe.safetyapp.videoquiz.MainGameActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    CardView counselingCard;
    CardView legalCentrCardView;
    CardView testimonialCardView;
    CardView quizzesCardView;
    CardView informationCard;
    CardView cardScenarios;
    CardView cardSOSTimer;
    CardView addEmgContact;
    private ContactHelper contactHelper;

    private Intent serviceIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//counselingMap
        counselingCard = findViewById(R.id.counselingMap);
        legalCentrCardView = findViewById(R.id.legalCentCard);
        testimonialCardView = findViewById(R.id.testimonialCard);
        quizzesCardView = findViewById(R.id.quizzesCardVw);
        informationCard = findViewById(R.id.infoPage);
        cardScenarios = findViewById(R.id.quizzesScenarios);
        cardSOSTimer = findViewById(R.id.gridTimeId);
        addEmgContact = findViewById(R.id.gridAddContact);
        contactHelper = new ContactHelper(this);
        informationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,InfoActivity.class);
                startActivity(intent);
            }
        });
        testimonialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TestimonialActivity.class);
                startActivity(intent);
            }
        });
        counselingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.checkNetworkConnection(MainActivity.this) && Utility.LocationEnableRequest(MainActivity.this)) {
                    Intent intent = new Intent(MainActivity.this,CounselingActivity.class);
                    intent.putExtra("TYPE","COUNSELLING");
                    startActivity(intent);
                }

            }
        });
        legalCentrCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utility.checkNetworkConnection(MainActivity.this) && Utility.LocationEnableRequest(MainActivity.this)){
                    Intent intent = new Intent(MainActivity.this,CounselingActivity.class);
                    intent.putExtra("TYPE","LEGAL");
                    startActivity(intent);
                }

            }
        });
        quizzesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomeScreen.class);
                startActivity(intent);
            }
        });
        cardScenarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuestionsActivity.class);
                startActivity(intent);
            }
        });
        cardSOSTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*ArrayList<ContactPerson> contactPersonList = contactHelper.getAllCotacts();
                if(contactPersonList.size()>0){
                    Intent intent = new Intent(MainActivity.this, TimerActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "No emergency contact available", Toast.LENGTH_SHORT).show();
                }*/
                Intent intent = new Intent(MainActivity.this, ContactListActivity.class);
                startActivity(intent);
            }
        });
        addEmgContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContact.class);
                startActivity(intent);
            }
        });
    }
}
