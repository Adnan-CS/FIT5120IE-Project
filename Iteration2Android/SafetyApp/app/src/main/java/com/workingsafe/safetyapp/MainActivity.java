package com.workingsafe.safetyapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Toast;
import com.workingsafe.safetyapp.utility.Utility;

public class MainActivity extends AppCompatActivity {
    CardView counselingCard;
    CardView legalCentrCardView;
    CardView testimonialCardView;
    CardView quizzesCardView;


    private Intent serviceIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//counselingMap
        counselingCard = findViewById(R.id.counselingMap);
        legalCentrCardView = findViewById(R.id.legalCentCard);
        testimonialCardView = findViewById(R.id.testimonialCard);
        quizzesCardView = findViewById(R.id.quizzesCardVw);

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
                Intent intent = new Intent(MainActivity.this,QuizCtgrsActivity.class);
                startActivity(intent);
            }
        });
    }
}
