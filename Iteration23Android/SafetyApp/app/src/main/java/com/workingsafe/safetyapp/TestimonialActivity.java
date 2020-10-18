package com.workingsafe.safetyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class TestimonialActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TestimonialAdapter testimonialAdapter;
    private ArrayList<String> testimonialList;
    private ArrayList<String> urlList;
    private ArrayList<String> srcAuthor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testimonial);
        recyclerView = findViewById(R.id.testiMonRecViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(TestimonialActivity.this));
        testimonialList = new ArrayList<>();
        urlList = new ArrayList<>();
        srcAuthor = new ArrayList<>();
        initializeArray();
        testimonialAdapter = new TestimonialAdapter(TestimonialActivity.this,testimonialList,urlList,srcAuthor);
        recyclerView.setAdapter(testimonialAdapter);
        getSupportActionBar().setTitle("Survivor Experiences");
        Toast.makeText(TestimonialActivity.this,"To view details please click list item",Toast.LENGTH_LONG).show();

    }
    private void initializeArray(){
        testimonialList.add("How to end the sexual harassment at work");
        testimonialList.add("The power of us");
        testimonialList.add("Advice for people");

        testimonialList.add("Powerful poem on sexual abuse");
        testimonialList.add("Power of speaking out");
        testimonialList.add("Victoria to review law preventing sexual assault survivors speaking publicly without court order");
        testimonialList.add("Survivor stories -Samentha’s Story");
        testimonialList.add("Survivor stories -Lucy’s Story");
        testimonialList.add("My Story: I Was Sexually Harassed at an Informational Interview");


        urlList.add("https://www.ted.com/talks/gretchen_carlson_how_we_can_end_sexual_harassment_at_work?language=en");
        urlList.add("https://www.ted.com/talks/marianne_cooper_the_power_of_us_how_we_stop_sexual_harassment?language=en");
        urlList.add("https://www.fastcompany.com/90389016/i-was-sexually-harassed-at-work-heres-my-advice-for-interns");



        urlList.add("https://www.billboard.com/articles/columns/pop/8095257/halsey-womens-march-speech-poem-a-story-like-mine-video");
        urlList.add("https://www.theguardian.com/commentisfree/2019/sep/24/when-sexual-assault-survivors-speak-out-they-help-change-the-culture-that-enables-it");
        urlList.add("https://www.theguardian.com/australia-news/2020/aug/26/victoria-to-review-law-preventing-sexual-assault-survivors-speaking-publicly-without-court-order");

        urlList.add("https://www.rainn.org/survivor-stories/samentha");
        urlList.add("https://www.rainn.org/survivor-stories/lucy");
        urlList.add("https://www.themuse.com/advice/my-story-i-was-sexually-harassed-at-an-informational-interview");

        srcAuthor.add("Source: TED Talk - Author: Gretchen Carlson");
        srcAuthor.add("Source: TED Talk - Author: Marianne Cooper");
        srcAuthor.add("Source: Fast Company - Author: Mary Rinaldi");
        srcAuthor.add("Source: Billboard - Author: Ashley Lasimone");
        srcAuthor.add("Source: The Guardian - Author: Kristine Ziwica");
        srcAuthor.add("Source: The Guardian - Author: Josh Taylor");
        srcAuthor.add("Source: Rainn - Author: Samentha");
        srcAuthor.add("Source: Rainn - Author: Lucy");
        srcAuthor.add("Source: The Muse - Author: Anonymous");
    }
}