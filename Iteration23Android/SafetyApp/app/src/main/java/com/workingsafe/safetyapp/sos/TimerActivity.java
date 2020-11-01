package com.workingsafe.safetyapp.sos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.location.Location;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.geocoding.v5.GeocodingCriteria;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;

import com.workingsafe.safetyapp.R;
import com.workingsafe.safetyapp.model.ContactPerson;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import info.hoang8f.widget.FButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ticker.views.com.ticker.widgets.circular.timer.callbacks.CircularViewCallback;
import ticker.views.com.ticker.widgets.circular.timer.view.CircularView;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;

public class TimerActivity extends AppCompatActivity {


    private FButton pauseTimerButton;
    private FButton resumeTimerButton;
    private CircularView circularViewWithTimer;
    private ContactHelper contactHelper;
    private String address;

    LocationManager locationManager;
    String latitude, longitude;
    private static final int REQUEST_LOCATION = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        pauseTimerButton = findViewById(R.id.pauseTimeBtn);
        resumeTimerButton = findViewById(R.id.resumeTimeBtn);
        circularViewWithTimer = findViewById(R.id.circular_view);
        contactHelper = new ContactHelper(this);
        address = null;
        getSupportActionBar().setTitle("Emergency SOS");
        ActivityCompat.requestPermissions( this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        getLastKnownLocation();
        pauseTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circularViewWithTimer.pauseTimer();
            }
        });
        resumeTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circularViewWithTimer.resumeTimer();
            }
        });

        CircularView.OptionsBuilder builderWithTimer =
                new CircularView.OptionsBuilder()
                        .shouldDisplayText(true)
                        .setCounterInSeconds(5)
                        .setCircularViewCallback(new CircularViewCallback() {
                            @Override
                            public void onTimerFinish() {
                                //getLocation();
                                // Will be called if times up of countdown timer
                                FetchDataSendSMSTask fetchDataSendSMSTask = new FetchDataSendSMSTask();
                                fetchDataSendSMSTask.execute();
                            }

                            @Override
                            public void onTimerCancelled() {

                                // Will be called if stopTimer is called
                                Toast.makeText(TimerActivity.this, "CircularCallback: Timer Cancelled ", Toast.LENGTH_SHORT).show();
                            }
                        });

        circularViewWithTimer.setOptions(builderWithTimer);
        circularViewWithTimer.startTimer();
    }
    //Start fetching user current location
    private void getLastKnownLocation(){
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        }
    }
    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                TimerActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                TimerActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                //Call reverse geo coding here
                reverseGeoCoding(latitude,longitude);
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void reverseGeoCoding(String lat, String longit){
        MapboxGeocoding reverseGeocode = MapboxGeocoding.builder()
                .accessToken(getString(R.string.mapbox_access_token))
                .query(Point.fromLngLat(Double.valueOf(longit),Double.valueOf(lat)))
                .geocodingTypes(GeocodingCriteria.TYPE_ADDRESS)
                .build();
        reverseGeocode.enqueueCall(new Callback<GeocodingResponse>() {

            @Override
            public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {

                List<CarmenFeature> results = response.body().features();
                address = null;
                if (results.size() > 0) {
                    String data = results.get(0).toString();
                    //Parsing the address from json array string
                    try{
                        address = data.split("placeName=")[1].split(", Australia,")[0];
                    }catch (Exception e){
                        Log.d("MYTAG", "Not able to fetch address" );
                        address = null;
                    }

                } else {

                }
            }

            @Override
            public void onFailure(Call<GeocodingResponse> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
    //End of fetching user current location
    private class FetchDataSendSMSTask extends AsyncTask<Void, Void, Boolean>
    {
        @Override
        protected Boolean doInBackground(Void... params)
        {

            ArrayList<ContactPerson> contactPersonList = contactHelper.getAllCotacts();
            String newAddress="";
            String message = "";
            if(address!=null){
                newAddress = address;
            }
            if(contactPersonList!=null) {
                try{
                    for(int j=0;j<contactPersonList.size();j++) {
                        if(newAddress.length()>0){
                            message = "Emergency Help\nMessage: "+contactPersonList.get(j).getMessage() + "\nAddress: "+ newAddress;
                        }else{
                            message = contactPersonList.get(j).getMessage();
                        }

                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(contactPersonList.get(j).getNumber(), null, message, null, null);

                    }
                }catch (Exception e){
                    /*Log.d("ExceptionHappens",e.getMessage().toString());*/
                    return false;
                }
            }
            return true;
        }
        @Override
        protected void onPostExecute(Boolean hasMessageSent)
        {
            if(hasMessageSent){
                Toast.makeText(TimerActivity.this, "SMS has been sent", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(TimerActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}