package com.workingsafe.safetyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.workingsafe.safetyapp.model.Counsellingcenters;
import com.workingsafe.safetyapp.model.CurrentLocation;
import com.workingsafe.safetyapp.model.Legalcenters;
import com.workingsafe.safetyapp.restapi.RestApi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CounselingActivity extends AppCompatActivity {
    Location currentLocation;
    /*private FusedLocationProviderClient fusedLocationProviderClient;*/
    private static final int REQUEST_CODE = 101;
    private String TYPE_DATA = null;

/*    private SupportMapFragment supportMapFragment;
    private FusedLocationProviderClient client;
    private ArrayList<MarkerOptions> markerOptionsArrayList;*/
    private RestApi restApi;
    /*private GoogleMap myMap;*/
    private ArrayList<Counsellingcenters> counsellingArrayList;
    private ArrayList<Legalcenters> legalcentersArrayList;

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*TYPE_DATA = getIntent().getStringExtra("TYPE");*/
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_counseling);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                        // Map is set up and the style has loaded. Now you can add data or make other map adjustments


                    }
                });

            }
        });
    }

   @Override
    protected void onResume() {
        super.onResume();
       mapView.onResume();
/*        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
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
        }*/
    }

    /*private void getUserCurrentLocation() {
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
                            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("My location");
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                            //Need to zoom towards my current location
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                            //Add the marker (markerOptions) on the map
                            googleMap.addMarker(markerOptions);
                            myMap = googleMap;
                            //Added UI Current location purpose icon
                            myMap.setMyLocationEnabled(true);
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
    }*/

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //Get user current location when permission is granted
                getUserCurrentLocation();
            }
        }
    }*/


   /* private class FetchCounsellingTask extends AsyncTask<CurrentLocation, Void, List<Counsellingcenters>>
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
            counsellingArrayList = new ArrayList<>();
            int i = 0;
            for(Counsellingcenters counsellingcenter: counsellingcenters){
                LatLng latLng = new LatLng(counsellingcenter.getLatitude().doubleValue(),counsellingcenter.getLongitude().doubleValue());
                MarkerOptions newMarker = new MarkerOptions().position(latLng).title(String.valueOf(i)).snippet(counsellingcenter.getCounselling_name());
                counsellingArrayList.add(counsellingcenter);
                myMap.addMarker(newMarker);
                markerOptionsArrayList.add(newMarker);
                i++;
            }
            myMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Counsellingcenters getCounselling = null;
                    try{
                        getCounselling = counsellingArrayList.get(Integer.valueOf(marker.getTitle()));
                    }catch (Exception e){
                        return false;
                    }
                    new AlertDialog.Builder(CounselingActivity.this)
                            .setTitle(getCounselling.getCounselling_name())
                            .setMessage("Address: " + getCounselling.getAddress() + "\n\n" + "Contact: "+getCounselling.getContact_details()+"\n"+"Suburb/Town: "+getCounselling.getSuburbortown())
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                    return false;
                }
            });
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(CounselingActivity.this, "Please Pinch-to-zoom", Toast.LENGTH_SHORT).show();
                }
            }, 3000);
        }
    }*/
   /* private class FetchLegalCentrTask extends AsyncTask<CurrentLocation, Void, List<Legalcenters>>
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
            legalcentersArrayList = new ArrayList<>();
            int i = 0;
            for(Legalcenters legalcenters: legalcentersList){
                LatLng latLng = new LatLng(legalcenters.getLatitude().doubleValue(),legalcenters.getLongitude().doubleValue());
                MarkerOptions newMarker = new MarkerOptions().position(latLng).title(String.valueOf(i)).snippet(legalcenters.getCenter_name());
                myMap.addMarker(newMarker);
                markerOptionsArrayList.add(newMarker);
                legalcentersArrayList.add(legalcenters);
                i++;
            }
            myMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Legalcenters getLegalCentre =null;
                    try{
                        getLegalCentre = legalcentersArrayList.get(Integer.valueOf(marker.getTitle()));
                    }catch (Exception e){
                        return false;
                    }
                    new AlertDialog.Builder(CounselingActivity.this)
                            .setTitle(getLegalCentre.getCenter_name())
                            .setMessage("Address: " + getLegalCentre.getAddress() + "\n\n" + "Contact: "+getLegalCentre.getContact_details()+"\n"+"Suburb/Town: "+getLegalCentre.getSuburbortown())
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                    return false;
                }
            });
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(CounselingActivity.this, "Please Pinch-to-zoom", Toast.LENGTH_SHORT).show();
                }
            }, 4000);
        }
    }*/

/*    @Override
    protected void onDestroy() {
        super.onDestroy();
        legalcentersArrayList = null;
        counsellingArrayList = null;
    }*/
    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
