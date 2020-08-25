package com.workingsafe.safetyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.workingsafe.safetyapp.model.CurrentLocation;
import com.workingsafe.safetyapp.model.Legalcenters;
import com.workingsafe.safetyapp.restapi.RestApi;

import java.math.BigDecimal;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    CardView counselingCard;
    RestApi restApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//counselingMap
        counselingCard = findViewById(R.id.counselingMap);
        restApi = new RestApi();
        CurrentLocation currentLocation = new CurrentLocation(BigDecimal.valueOf(-37.875966),BigDecimal.valueOf(145.049276));
        FetchCentersTask fetchCentersTask = new FetchCentersTask();
        fetchCentersTask.execute(currentLocation);
        counselingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CounselingActivity.class);
                startActivity(intent);
            }
        });
    }
    private class FetchCentersTask extends AsyncTask<CurrentLocation, Void, List<Legalcenters>>
    {
        @Override
        protected List<Legalcenters> doInBackground(CurrentLocation... params)
        {
            return restApi.getNearestLegCent(params[0]);
        }
        @Override
        protected void onPostExecute(List<Legalcenters> legalcenters)
        {
            Log.d("GETTINGDATA","Data: "+legalcenters.get(0).getAddress()+"--"+legalcenters.get(0));
        }
    }
}
