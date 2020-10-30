package com.workingsafe.safetyapp.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.Toast;

public class Utility {
    public static boolean LocationEnableRequest(final Context context) {
        LocationManager locationManager = (LocationManager)
                context.getSystemService(Context. LOCATION_SERVICE ) ;
        boolean gpsEnabled = false;
        boolean networkEnabled = false;
        try {
            gpsEnabled = locationManager.isProviderEnabled(LocationManager. GPS_PROVIDER ) ;
        } catch (Exception e) {
            e.printStackTrace() ;
        }
        try {
            networkEnabled = locationManager.isProviderEnabled(LocationManager. NETWORK_PROVIDER ) ;
        } catch (Exception e) {
            e.printStackTrace() ;
        }
        if (!gpsEnabled && !networkEnabled) {
            Toast.makeText(context.getApplicationContext(),"Please enable your GPS connection.",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    public static boolean checkNetworkConnection(Context context){
        ConnectivityManager connectivityManager =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null &&
                networkInfo.isConnectedOrConnecting();

        if(!isConnected)
            Toast.makeText(context.getApplicationContext(),"Please enable your internet connection.",Toast.LENGTH_LONG).show();
        return isConnected;
    }
}
