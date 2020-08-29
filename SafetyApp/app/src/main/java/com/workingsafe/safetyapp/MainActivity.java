package com.workingsafe.safetyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.workingsafe.safetyapp.model.Counsellingcenters;
import com.workingsafe.safetyapp.model.CurrentLocation;
import com.workingsafe.safetyapp.model.Legalcenters;
import com.workingsafe.safetyapp.restapi.RestApi;
import com.workingsafe.safetyapp.service.MyLocationService;
import com.workingsafe.safetyapp.utility.Utility;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    CardView counselingCard;
    CardView legalCentrCardView;

    private Intent serviceIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//counselingMap
        counselingCard = findViewById(R.id.counselingMap);
        legalCentrCardView = findViewById(R.id.legalCentCard);

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
    }



/*    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(serviceIntent);
    }*/
}
