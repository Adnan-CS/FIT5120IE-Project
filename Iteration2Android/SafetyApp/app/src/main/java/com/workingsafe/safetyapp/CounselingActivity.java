package com.workingsafe.safetyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.workingsafe.safetyapp.model.Counsellingcenters;
import com.workingsafe.safetyapp.model.CurrentLocation;
import com.workingsafe.safetyapp.restapi.RestApi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;

public class CounselingActivity extends AppCompatActivity implements MapboxMap.OnMapClickListener, OnMapReadyCallback, PermissionsListener {
    private MapView mapView;
    private MapboxMap map;
    private PermissionsManager permissionsManager;
    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String ICON_ID = "ICON_ID";
    private static final String LAYER_ID = "LAYER_ID";

    private FloatingActionButton currentLocationButton;
    private ArrayList<MarkerOptions> markerOptionsArrayList;
    private RestApi restApi;
    private ArrayList<Counsellingcenters> counsellingArrayList;

    /*    Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    private String TYPE_DATA = null;

    private SupportMapFragment supportMapFragment;
    private FusedLocationProviderClient client;

    private GoogleMap myMap;

    private ArrayList<Legalcenters> legalcentersArrayList;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_counseling);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        restApi = new RestApi();

        mapView.getMapAsync(this);
        currentLocationButton = findViewById(R.id.currentLocationBtn);
        currentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map.getLocationComponent().getLastKnownLocation() != null) {
                    map.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                            .target(new LatLng(map.getLocationComponent().getLastKnownLocation().getLatitude(),
                                    map.getLocationComponent().getLastKnownLocation().getLongitude()))
                            .zoom(16)
                            .build()));
                }
            }
        });
//        TYPE_DATA = getIntent().getStringExtra("TYPE");
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

   /* private void getUserCurrentLocation() {
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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //Get user current location when permission is granted
                getUserCurrentLocation();
            }
        }
    }*/

    private class FetchCounsellingTask extends AsyncTask<CurrentLocation, Void, List<Counsellingcenters>> {
        @Override
        protected List<Counsellingcenters> doInBackground(CurrentLocation... params) {
            return restApi.getNearestCounselling(params[0]);
        }

        @Override
        protected void onPostExecute(List<Counsellingcenters> counsellingcenters) {
            markerOptionsArrayList = new ArrayList<>();
            counsellingArrayList = new ArrayList<>();
            List<Feature> symbolLayerIconFeatureList = new ArrayList<>();
            int i = 0;

            for (Counsellingcenters counsellingcenter : counsellingcenters) {
                Feature feature = Feature.fromGeometry(
                        Point.fromLngLat(counsellingcenter.getLongitude().doubleValue(), counsellingcenter.getLatitude().doubleValue()));
                feature.addStringProperty("title",counsellingcenter.getCounselling_name());
                feature.addStringProperty("address",counsellingcenter.getAddress());
                feature.addStringProperty("contact",counsellingcenter.getContact_details());
                feature.addStringProperty("suburb",counsellingcenter.getSuburbortown());
                symbolLayerIconFeatureList.add(feature);
                counsellingArrayList.add(counsellingcenter);
                i++;
            }

            map.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {

                style.addImage(ICON_ID, BitmapFactory.decodeResource(
                        CounselingActivity.this.getResources(), R.drawable.mapbox_marker_icon_default));
                style.addSource(new GeoJsonSource(SOURCE_ID,
                        FeatureCollection.fromFeatures(symbolLayerIconFeatureList)));
                style.addLayer(new SymbolLayer(LAYER_ID, SOURCE_ID)
                        .withProperties(
                                iconImage(ICON_ID),
                                iconAllowOverlap(true),
                                iconIgnorePlacement(true)
                        ));
                }
            });

/*            myMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
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
            });*/
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(CounselingActivity.this, "Please Pinch-to-zoom", Toast.LENGTH_SHORT).show();
                }
            }, 3000);
        }
    }
  /*  private class FetchLegalCentrTask extends AsyncTask<CurrentLocation, Void, List<Legalcenters>>
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

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            // Get an instance of the component
            LocationComponent locationComponent = map.getLocationComponent();

            // Activate with options
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(this, loadedMapStyle).build());

            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);

            //Fetch async task method
            CurrentLocation currentLocation = new CurrentLocation(
                    BigDecimal.valueOf(map.getLocationComponent().getLastKnownLocation().getLatitude()),
                    BigDecimal.valueOf(map.getLocationComponent().getLastKnownLocation().getLatitude()));
            FetchCounsellingTask fetchCounsellingTask = new FetchCounsellingTask();
            fetchCounsellingTask.execute(currentLocation);
        } else {
            permissionsManager = new PermissionsManager((PermissionsListener) this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            map.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    @SuppressWarnings({"MissingPermission"})
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        CounselingActivity.this.map = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS,
                new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                          enableLocationComponent(style);
                    }
                });
        map.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
            @Override
            public boolean onMapClick(@NonNull LatLng point) {
                PointF screenPoint = map.getProjection().toScreenLocation(point);
                List<Feature> features = map.queryRenderedFeatures(screenPoint, LAYER_ID);
                if (!features.isEmpty()) {
                    Feature selectedFeature = features.get(0);
                    selectedFeature.addBooleanProperty("selected", true);
                    String title = selectedFeature.getStringProperty("title");
                    String address = selectedFeature.getStringProperty("address");
                    String contact = selectedFeature.getStringProperty("contact");
                    String suburb = selectedFeature.getStringProperty("suburb");
                    new AlertDialog.Builder(CounselingActivity.this)
                            .setTitle(title)
                            .setMessage("Address: " + address + "\n\n" + "Contact: "+contact+"\n"+"Suburb/Town: "+suburb)
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                    /*Toast.makeText(CounselingActivity.this, "You selected " + title, Toast.LENGTH_SHORT).show();*/

                    // This triggers the update of the feature (Point) on the data source so it updates the SymbolLayer and you can see the feature enabled (bigger in this example)
                    //geoJsonSource.setGeoJson(selectedFeature);
                    return true;
                }
                return false;
            }});
    }
}
