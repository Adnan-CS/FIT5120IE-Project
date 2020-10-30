package com.workingsafe.safetyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import info.hoang8f.widget.FButton;

public class SlidingHome extends AppCompatActivity {

    private ViewPager infoSliderViewPager;
    private LinearLayout navigateBtnLinLayout;
    private SliderAdapter infoSliderAdapter;
    private TextView[] slidingDots;
    private FButton startSlidingBtn;
    private int currentSliderPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_home);
        infoSliderViewPager = findViewById(R.id.sliderViewPager);
        navigateBtnLinLayout = findViewById(R.id.navigateBtnLayout);
        infoSliderAdapter = new SliderAdapter(this);
        infoSliderViewPager.setAdapter(infoSliderAdapter);
        addSliderDotIndicator(0);
        startSlidingBtn = findViewById(R.id.startSliderIndx);
        startSlidingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SlidingHome.this,MainActivity.class);
                startActivity(intent);
            }
        });
        infoSliderViewPager.addOnPageChangeListener(sliderViewListener);
    }
    private void addSliderDotIndicator(int position){
        slidingDots = new TextView[infoSliderAdapter.getCount()];
        navigateBtnLinLayout.removeAllViews();
        for(int i=0;i<slidingDots.length;i++){
            slidingDots[i] = new TextView(this);
            slidingDots[i].setText(Html.fromHtml("&#8226;"));
            slidingDots[i].setTextSize(40);
            slidingDots[i].setTextColor(getResources().getColor(R.color.sliderHeading));
            navigateBtnLinLayout.addView(slidingDots[i]);
        }
        if(slidingDots.length>0){
            slidingDots[position].setTextColor(getResources().getColor(R.color.white));
        }

    }
    ViewPager.OnPageChangeListener sliderViewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addSliderDotIndicator(position);
            currentSliderPage = position;
            if(position==(slidingDots.length-1)){
                startSlidingBtn.setEnabled(true);
                startSlidingBtn.setVisibility(View.VISIBLE);
                startSlidingBtn.setText("Next");
            }else{
                startSlidingBtn.setEnabled(false);
                startSlidingBtn.setVisibility(View.INVISIBLE);
                startSlidingBtn.setText("Next");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}