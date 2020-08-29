package com.workingsafe.safetyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.workingsafe.safetyapp.model.Counsellingcenters;
import com.workingsafe.safetyapp.model.CurrentLocation;
import com.workingsafe.safetyapp.model.Legalcenters;
import com.workingsafe.safetyapp.restapi.RestApi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CounselingActivity extends AppCompatActivity {
    Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    private String TYPE_DATA = null;

    private SupportMapFragment supportMapFragment;
    private FusedLocationProviderClient client;
    private ArrayList<MarkerOptions> markerOptionsArrayList;
    private RestApi restApi;
    private GoogleMap myMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counseling);
        TYPE_DATA = getIntent().getStringExtra("TYPE");
    }

/*    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CounselingActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                if(location!=null){
                    currentLocation = location;
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.counselling_map);
                    supportMapFragment.getMapAsync(CounselingActivity.this);



                }
            }
        });
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        restApi = new RestApi();
        markerOptionsArrayList = null;
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.counselling_map);
        //Initialize fused location
        client = LocationServices.getFusedLocationProviderClient(this);
        //Asking for Permission checking
        if (ActivityCompat.checkSelfPermission(CounselingActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //When the permission is granted
            getUserCurrentLocation();
        }
        else{
            ActivityCompat.requestPermissions(CounselingActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }
    }

/*    @Override
    protected void onStop() {
        super.onStop();
        restApi = null;
        markerOptionsArrayList = null;
        supportMapFragment = null;
        client = null;
    }*/

    private void getUserCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                if(location!=null){
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());

                            //Creating my current location marker option
                            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("This is my location");
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).snippet("Hello");
                            //Need to zoom towards my current location
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                            //Add the marker (markerOptions) on the map
                            googleMap.addMarker(markerOptions);
                            myMap = googleMap;
                            CurrentLocation currentLocation = new CurrentLocation(BigDecimal.valueOf(location.getLatitude()),BigDecimal.valueOf(location.getLongitude()));
                            if(TYPE_DATA.equals("COUNSELLING")){
                                FetchCounsellingTask fetchCounsellingTask = new FetchCounsellingTask();
                                fetchCounsellingTask.execute(currentLocation);
                            }else{
                                FetchLegalCentrTask fetchLegalCentrTask = new FetchLegalCentrTask();
                                fetchLegalCentrTask.execute(currentLocation);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //Get user current location when permission is granted
                getUserCurrentLocation();
            }
        }
    }


    private class FetchCounsellingTask extends AsyncTask<CurrentLocation, Void, List<Counsellingcenters>>
    {
        @Override
        protected List<Counsellingcenters> doInBackground(CurrentLocation... params)
        {
            return restApi.getNearestCounselling(params[0]);
        }
        @Override
        protected void onPostExecute(List<Counsellingcenters> counsellingcenters)
        {
            markerOptionsArrayList = new ArrayList<>();
            for(Counsellingcenters counsellingcenter: counsellingcenters){
                LatLng latLng = new LatLng(counsellingcenter.getLatitude().doubleValue(),counsellingcenter.getLongitude().doubleValue());
                MarkerOptions newMarker = new MarkerOptions().position(latLng).title(counsellingcenter.getCounselling_name());
                String snippetData = "Address "+ counsellingcenter.getAddress() + "\n" + "Contact: "+counsellingcenter.getContact_details();
                newMarker.snippet(snippetData);
                myMap.addMarker(newMarker);
                markerOptionsArrayList.add(newMarker);
            }
        }
    }
    private class FetchLegalCentrTask extends AsyncTask<CurrentLocation, Void, List<Legalcenters>>
    {
        @Override
        protected List<Legalcenters> doInBackground(CurrentLocation... params)
        {
            return restApi.getNearestLegCent(params[0]);
        }
        @Override
        protected void onPostExecute(List<Legalcenters> legalcentersList)
        {
            markerOptionsArrayList = new ArrayList<>();
            for(Legalcenters legalcenters: legalcentersList){
                LatLng latLng = new LatLng(legalcenters.getLatitude().doubleValue(),legalcenters.getLongitude().doubleValue());
                MarkerOptions newMarker = new MarkerOptions().position(latLng).title(legalcenters.getAddress());
                myMap.addMarker(newMarker);
                markerOptionsArrayList.add(newMarker);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
