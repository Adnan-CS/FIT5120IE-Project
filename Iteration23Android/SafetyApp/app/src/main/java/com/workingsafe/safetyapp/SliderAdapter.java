package com.workingsafe.safetyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    public int[] info_slideImages = {
            R.drawable.safewomen,
            R.drawable.legalcounsel_slider,
            R.drawable.motivation_slider,
            R.drawable.video_slider,
            R.drawable.quiz_slider,
            R.drawable.twentyfour_slider,
            R.drawable.sms_slider

    };
    public String[] slider_headings = {"Safe Women", "Legal/Counselling Advice","Survivor Experience"
    ,"Information Page","Quizzes & Games","24/7 Services","SOS"};

    public String[] slider_description = {
            "Facing Sexual Harassment at work?\n" +
                    "Worry Not! Safe Women is here to help Young working women to be safe from sexual abuse\n",
            "Need legal/counselling advice?\n" +
                    "Use your current location to know nearby service and navigate.\n",
            "You think you are alone affected?\n" +
                    "No, you are not, check out survivor experience for other victim stories.\n",
            "Want to know about Sexual Harassment?\n" +
                    "Check out the Information Page to know about sexual harassment, impacts and ways to handle it.\n",
            "Want to test your knowledge on sexual harassment?\n" +
                    "Play quizzes and play a scenario based text game with real time scenarios given as choices.\n",
            "Feeling frightened to travel alone in night?\n" +
                    "24/7 Services comes to your rescue, It provides location of nearby hospital, police station and convenience store.\n",
            "Need to inform your friends in danger?\n" +
                    "Emergency SOS SMS alerts with location will be sent in a span of 5 seconds to your near and dear ones.\n"
    };


    public SliderAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return slider_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View slideView = layoutInflater.inflate(R.layout.info_slider,container,false);
        ImageView sliderImage = slideView.findViewById(R.id.sliderImgView);
        TextView headingTxtView = slideView.findViewById(R.id.sliderHeadingTxtView);
        TextView descTextView = slideView.findViewById(R.id.sliderDescTxtView);
        sliderImage.setImageResource(info_slideImages[position]);
        headingTxtView.setText(slider_headings[position]);
        descTextView.setText(slider_description[position]);
        container.addView(slideView);
        return slideView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
