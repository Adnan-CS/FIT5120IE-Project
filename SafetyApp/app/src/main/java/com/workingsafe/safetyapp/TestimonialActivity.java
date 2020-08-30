package com.workingsafe.safetyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class TestimonialActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TestimonialAdapter testimonialAdapter;
    private ArrayList<String> testimonialList;
    private ArrayList<String> urlList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testimonial);
        recyclerView = findViewById(R.id.testiMonRecViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(TestimonialActivity.this));
        testimonialList = new ArrayList<>();
        urlList = new ArrayList<>();
        initializeArray();
        testimonialAdapter = new TestimonialAdapter(TestimonialActivity.this,testimonialList,urlList);
        recyclerView.setAdapter(testimonialAdapter);
    }
    private void initializeArray(){
        testimonialList.add("How to end the sexual harassment at work");
        testimonialList.add("The power of us");
        testimonialList.add("Advice for people");
        testimonialList.add("1993 Sexual Harassment Commercial");
        testimonialList.add("Motivational speech for sexual assualt survivor");
        testimonialList.add("Powerful poem on sexual abuse");
        testimonialList.add("Power of speaking out");
        testimonialList.add("Victoria to review law preventing sexual assault survivors speaking publicly without court order");
        testimonialList.add("Survivor stories -Samentha’s Story");
        testimonialList.add("Survivor stories -Lucy’s Story");
        testimonialList.add("My Story: I Was Sexually Harassed at an Informational Interview");
        testimonialList.add("Lady Gaga's survivor song");

        urlList.add("https://www.ted.com/talks/gretchen_carlson_how_we_can_end_sexual_harassment_at_work?language=en");
        urlList.add("https://www.ted.com/talks/marianne_cooper_the_power_of_us_how_we_stop_sexual_harassment?language=en");
        urlList.add("https://www.fastcompany.com/90389016/i-was-sexually-harassed-at-work-heres-my-advice-for-interns");
        urlList.add("https://www.youtube.com/watch?v=pKhbau8DJj0");

        urlList.add("https://www.youtube.com/watch?v=bClP_x6uDV4");
        urlList.add("https://www.billboard.com/articles/columns/pop/8095257/halsey-womens-march-speech-poem-a-story-like-mine-video");
        urlList.add("https://www.theguardian.com/commentisfree/2019/sep/24/when-sexual-assault-survivors-speak-out-they-help-change-the-culture-that-enables-it");
        urlList.add("https://www.theguardian.com/australia-news/2020/aug/26/victoria-to-review-law-preventing-sexual-assault-survivors-speaking-publicly-without-court-order");

        urlList.add("https://www.rainn.org/survivor-stories/samentha");
        urlList.add("https://www.rainn.org/survivor-stories/lucy");
        urlList.add("https://www.themuse.com/advice/my-story-i-was-sexually-harassed-at-an-informational-interview");
        urlList.add("https://www.youtube.com/watch?v=ZmWBrN7QV6Y");
    }
}