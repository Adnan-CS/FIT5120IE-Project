package com.workingsafe.safetyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

public class InfoActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        getSupportActionBar().setTitle("Information Page");


        VideoView mVideoView = (VideoView)findViewById(R.id.videoView);

        String uriPath = "android.resource://" + getPackageName() + "/" + R.raw.sexual_harassment;

        Uri uri = Uri.parse(uriPath);
        mVideoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        mVideoView.setMediaController(mediaController);
        mediaController.setAnchorView(mVideoView);
        mVideoView.start();
    }
}