package com.workingsafe.safetyapp.nearloc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolClickListener;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.MapboxNavigation;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.workingsafe.safetyapp.CounselingActivity;
import com.workingsafe.safetyapp.R;
import com.workingsafe.safetyapp.model.Counsellingcenters;
import com.workingsafe.safetyapp.model.CurrentLocation;
import com.workingsafe.safetyapp.model.Hospital;
import com.workingsafe.safetyapp.model.Legalcenters;
import com.workingsafe.safetyapp.model.NearestLocation;
import com.workingsafe.safetyapp.model.PoliceStation;
import com.workingsafe.safetyapp.model.SevenEleven;
import com.workingsafe.safetyapp.restapi.RestApi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import info.hoang8f.widget.FButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;

public class NearByLocActivity extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener, MapboxMap.OnMapClickListener {
    private MapView mapView;
    private MapboxMap map;
    private PermissionsManager permissionsManager;
    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String ICON_ID = "ICON_ID";
    private static final String LAYER_ID = "LAYER_ID";
    private LatLng locationOne;
    private LatLng locationTwo;
    private FloatingActionButton currentLocationButton;
    private ArrayList<MarkerOptions> markerOptionsArrayList;
    private RestApi restApi;
    private MapboxNavigation navigation;
    private Point originPoint;
    private FButton navigationButton;
    private DirectionsRoute currentRoute;
    private NavigationMapRoute navigationMapRoute;
    private SymbolManager symbolManager;

    private LatLng locationPolice;
    private LatLng locationHospital;
    private LatLng locationCafe;

    private String TYPE_DATA = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navigation = new MapboxNavigation(this, getString(R.string.mapbox_access_token));
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        setContentView(R.layout.activity_near_by_loc);
        mapView = (MapView) findViewById(R.id.mapViewNearById);
        mapView.onCreate(savedInstanceState);
        restApi = new RestApi();
        mapView.getMapAsync(this);
        currentLocationButton = findViewById(R.id.currentLocNrBtn);
        currentRoute = null;
        currentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map.getLocationComponent().getLastKnownLocation() != null) {
                    map.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                            .target(new LatLng(map.getLocationComponent().getLastKnownLocation().getLatitude(),
                                    map.getLocationComponent().getLastKnownLocation().getLongitude()))
                            //.zoom(13)
                            .build()));
                }
            }
        });
        navigationButton = findViewById(R.id.startNavigationBtnBrBy);
        navigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean simulateRoute = true;
                if (currentRoute != null) {
                    // Create a NavigationLauncherOptions object to package everything together
                    NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                            .directionsRoute(currentRoute)
                            .shouldSimulateRoute(simulateRoute)
                            .build();

                    // Call this method with Context from within an Activity
                    NavigationLauncher.startNavigation(NearByLocActivity.this, options);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        LatLngBounds latLngBounds = new LatLngBounds.Builder()
                .include(locationOne) // Northeast
                .include(locationTwo) // Southwest
                .build();

        map.easeCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 50), 5000);
        return true;
    }

    private class FetchNearestLocationTask extends AsyncTask<CurrentLocation, Void, NearestLocation> {
        @Override
        protected NearestLocation doInBackground(CurrentLocation... params) {
            return restApi.getNearestLocations(params[0]);
        }

        @Override
        protected void onPostExecute(NearestLocation nearestLocation) {
            markerOptionsArrayList = new ArrayList<>();

            List<Feature> symbolLayerIconFeatureList = new ArrayList<>();
            PoliceStation policeStation = null;
            Hospital hospital = null;
            SevenEleven sevenEleven = null;
            policeStation = nearestLocation.getPoliceStation();
            hospital = nearestLocation.getHospital();
            sevenEleven = nearestLocation.getSevenEleven();

            Feature feature1 = Feature.fromGeometry(
                    Point.fromLngLat(policeStation.getLongitude(), policeStation.getLatitude()));
            locationPolice = new LatLng(policeStation.getLatitude(),policeStation.getLongitude());

            Feature feature2 = Feature.fromGeometry(
                    Point.fromLngLat(hospital.getLongitude(), hospital.getLatitude()));
            locationHospital = new LatLng(hospital.getLatitude(),hospital.getLongitude());

            Feature feature3 = Feature.fromGeometry(
                    Point.fromLngLat(sevenEleven.getLongitude(), sevenEleven.getLatitude()));
            locationCafe = new LatLng(sevenEleven.getLatitude(),sevenEleven.getLongitude());


            symbolLayerIconFeatureList.add(feature1);
            symbolLayerIconFeatureList.add(feature2);
            symbolLayerIconFeatureList.add(feature3);

            feature1.addStringProperty("title", "Police Station");
            feature1.addStringProperty("address", policeStation.getStation_address());
            feature1.addStringProperty("contact", policeStation.getPhone());
            feature1.addStringProperty("locationType", "police");
            feature1.addStringProperty("suburb", policeStation.getSuburb() + "\nPostCode: " + policeStation.getPostcode());


            feature2.addStringProperty("title", hospital.getHospital_name());
            feature2.addStringProperty("address", hospital.getAddress());
            feature2.addStringProperty("contact", "");
            feature2.addStringProperty("locationType", "hospital");
            feature2.addStringProperty("suburb", hospital.getSuburb() + "\nPostCode: " + hospital.getPostcode());


            feature3.addStringProperty("title", sevenEleven.getStore_name());
            feature3.addStringProperty("address", sevenEleven.getAddress());
            feature3.addStringProperty("contact", sevenEleven.getPhone());
            feature3.addStringProperty("locationType", "restaurant");
            feature3.addStringProperty("suburb", sevenEleven.getSuburb() + "\nPostCode: " + sevenEleven.getPostcode());

            double minLat = smallest(hospital.getLatitude(), policeStation.getLatitude(), sevenEleven.getLatitude());
            double minLong = smallest(hospital.getLongitude(), policeStation.getLongitude(), sevenEleven.getLongitude());

            double maxLat = largest(hospital.getLatitude(), policeStation.getLatitude(), sevenEleven.getLatitude());
            double maxLong = largest(hospital.getLongitude(), policeStation.getLongitude(), sevenEleven.getLongitude());


            locationOne = new LatLng(minLat, minLong);
            locationTwo = new LatLng(maxLat, maxLong);

            map.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {

                    style.addSource(new GeoJsonSource(SOURCE_ID,
                            FeatureCollection.fromFeatures(symbolLayerIconFeatureList)));

                    SymbolLayer myLayer = new SymbolLayer(LAYER_ID, SOURCE_ID);

/*                    style.addImage(ICON_ID, BitmapFactory.decodeResource(
                            NearByLocActivity.this.getResources(), PropertyFactory.iconImage("locationType-15"));*/
                    style.addLayer(myLayer.withProperties(PropertyFactory.iconImage("{locationType}-15"),iconSize(2.0f),iconAllowOverlap(true),iconIgnorePlacement(false)));
/*                    style.addLayer(new SymbolLayer(LAYER_ID, SOURCE_ID)
                            .withProperties(
                                    PropertyFactory.iconImage(ICON_ID),
                                    iconAllowOverlap(true),
                                    iconIgnorePlacement(false)
                            ));*/

                  /*  symbolManager = new SymbolManager(mapView, map, style);
                    symbolManager.setIconAllowOverlap(true);
                    symbolManager.setIconIgnorePlacement(true);
                    Symbol symbolHospital = symbolManager.create(new SymbolOptions()
                            .withLatLng(locationHospital)
                            .withIconImage("hospital-15")
                            .withIconSize(2.0f));
                    Symbol symbolPolice = symbolManager.create(new SymbolOptions()
                            .withLatLng(locationPolice)
                            .withIconImage("police-15")
                            .withIconSize(2.0f));
                    Symbol symbolCafe = symbolManager.create(new SymbolOptions()
                            .withLatLng(locationCafe)
                            .withIconImage("cafe-15")
                            .withIconSize(2.0f));

                    symbolManager.addClickListener(new OnSymbolClickListener() {
                        @Override
                        public void onAnnotationClick(Symbol symbol) {
                            Toast.makeText(NearByLocActivity.this, "Clicked ...", Toast.LENGTH_SHORT).show();
                            PointF screenPoint = map.getProjection().toScreenLocation(symbol.getLatLng());
                            List<Feature> features = map.queryRenderedFeatures(screenPoint, LAYER_ID);
                            if (!features.isEmpty()) {
                                Feature selectedFeature = features.get(0);
                                selectedFeature.addBooleanProperty("selected", true);
                                String title = selectedFeature.getStringProperty("title");
                                String address = selectedFeature.getStringProperty("address");
                                String contact = selectedFeature.getStringProperty("contact");
                                String suburb = selectedFeature.getStringProperty("suburb");
                                new AlertDialog.Builder(NearByLocActivity.this)
                                        .setTitle(title)
                                        .setMessage("Address: " + address + "\n\n" + "Contact: " + contact + "\n" + "Suburb/Town: " + suburb)
                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .show();
                                Point destination = Point.fromLngLat(symbol.getLatLng().getLongitude(), symbol.getLatLng().getLatitude());
                                getRoute(originPoint, destination);
                            }
                        }
                    });*/
                }
            });
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(NearByLocActivity.this, "Please Pinch-to-zoom", Toast.LENGTH_SHORT).show();
                }
            }, 3000);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        currentRoute = null;
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        currentRoute = null;
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
        currentRoute = null;
        mapView.onDestroy();
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
            locationComponent.setCameraMode(CameraMode.TRACKING_COMPASS);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);

            //Fetch async task method
            CurrentLocation currentLocation = new CurrentLocation(
                    BigDecimal.valueOf(map.getLocationComponent().getLastKnownLocation().getLatitude()),
                    BigDecimal.valueOf(map.getLocationComponent().getLastKnownLocation().getLongitude()));
            originPoint = Point.fromLngLat(map.getLocationComponent().getLastKnownLocation().getLongitude(), map.getLocationComponent().getLastKnownLocation().getLatitude());

            getSupportActionBar().setTitle("Nearest 24/7 Services");
            FetchNearestLocationTask fetchNearestLocationTask = new FetchNearestLocationTask();
            fetchNearestLocationTask.execute(currentLocation);
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
        currentRoute = null;
        mapView.onStart();
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        NearByLocActivity.this.map = mapboxMap;
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
                    new AlertDialog.Builder(NearByLocActivity.this)
                            .setTitle(title)
                            .setMessage("Address: " + address + "\n\n" + "Contact: "+contact+"\n"+"Suburb/Town: "+suburb)
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                    Point destination = Point.fromLngLat(point.getLongitude(),point.getLatitude());
                    getRoute(originPoint,destination);

                    return true;
                }
                return false;
            }});
    }

    private void getRoute(Point origin, Point destination) {
        NavigationRoute.builder(this)
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        currentRoute = response.body().routes().get(0);
                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, map);
                        }
                        navigationMapRoute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                    }
                });
    }

    private double largest(double first, double second, double third) {
        double max = first;
        if (second > max) {
            max = second;
        }
        if (third > max) {
            max = third;
        }
        return max;
    }

    public double smallest(double first, double second, double third) {
        double min = first;
        if (second < min) {
            min = second;
        }
        if (third < min) {
            min = third;
        }
        return min;
    }
}