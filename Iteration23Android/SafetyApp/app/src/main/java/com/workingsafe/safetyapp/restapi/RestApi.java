package com.workingsafe.safetyapp.restapi;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.workingsafe.safetyapp.model.Counsellingcenters;
import com.workingsafe.safetyapp.model.CurrentLocation;
import com.workingsafe.safetyapp.model.Hospital;
import com.workingsafe.safetyapp.model.Legalcenters;
import com.workingsafe.safetyapp.model.NearestLocation;
import com.workingsafe.safetyapp.model.PoliceStation;
import com.workingsafe.safetyapp.model.SevenEleven;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RestApi {
    private static final String BASE_URL = "http://backend-env.eba-gkgw4hqz.us-east-1.elasticbeanstalk.com/api";
    //private static final String BASE_URL = "http://localhost:8080/api";
    //private static final String BASE_URL = "http://10.0.2.2:8080/api";
    private OkHttpClient client=null;
    private static final String TAG = "ADDMEMOIRDATA";


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public RestApi()
    {
        client=new OkHttpClient();
    }
    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }
    public List<Legalcenters> getNearestLegCent(CurrentLocation currentLocation){
        String finalResult = "";
        Gson gson = new Gson();
        String currLocJson = gson.toJson(currentLocation);
        String strResponse="";
        List<Legalcenters> legalcenters = null;
        final String centerMethodPath = "/centers";
        RequestBody body = RequestBody.create(currLocJson, JSON);
        Request request = new Request.Builder().url(BASE_URL + centerMethodPath).post(body).build();
        try {
            Response response= client.newCall(request).execute();
            strResponse= response.body().string();
            legalcenters = stringToArray(strResponse, Legalcenters[].class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
    }
        return legalcenters;
    }

    public List<Counsellingcenters> getNearestCounselling(CurrentLocation currentLocation){
        String finalResult = "";
        List<Counsellingcenters> counsellingcenters=null;
        Gson gson = new Gson();
        String currLocJson = gson.toJson(currentLocation);
        String strResponse="";
        final String centerMethodPath = "/counselling";
        RequestBody body = RequestBody.create(currLocJson, JSON);
        Request request = new Request.Builder().url(BASE_URL + centerMethodPath).post(body).build();
        try {
            Response response= client.newCall(request).execute();
            strResponse= response.body().string();
            counsellingcenters = stringToArray(strResponse, Counsellingcenters[].class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return counsellingcenters;
    }
    public NearestLocation getNearestLocations(CurrentLocation currentLocation) {
        String finalResult = "";
        List<Counsellingcenters> counsellingcenters = null;
        Gson gson = new Gson();
        String currLocJson = gson.toJson(currentLocation);
        String strResponse = "";
        final String centerMethodPath = "/nearestdaynight";
        RequestBody body = RequestBody.create(currLocJson, JSON);
        Request request = new Request.Builder().url(BASE_URL + centerMethodPath).post(body).build();
        try {
            Response response = client.newCall(request).execute();
            strResponse = response.body().string();
            JSONArray jsonArray = new JSONArray(strResponse);
            JSONObject obj1=null;
            JSONObject obj2=null;
            JSONObject obj3=null;
            JSONObject obj4=null;
            try{
                obj1= jsonArray.getJSONObject(0);
                obj2 = jsonArray.getJSONObject(1);
                obj3 = jsonArray.getJSONObject(2);
                obj4 = jsonArray.getJSONObject(20);
            }catch(Exception ex){

            }

            PoliceStation policeStation=null;
            SevenEleven sevenEleven=null;
            Hospital hospital = null;
            Gson gsonBuild = new GsonBuilder().create();

            if(obj1!=null && obj1.getString("locationType").equals("police")){
                policeStation = gsonBuild.fromJson(String.valueOf(obj1),PoliceStation.class);
            }
            else if(obj1!=null && obj1.getString("locationType").equals("hospital")){
                hospital = gsonBuild.fromJson(String.valueOf(obj1),Hospital.class);
            }
            else if(obj1!=null && obj1.getString("locationType").equals("restaurant")){
                sevenEleven = gsonBuild.fromJson(String.valueOf(obj1),SevenEleven.class);
            }

            if(obj2!=null && obj2.getString("locationType").equals("police")){
                policeStation = gsonBuild.fromJson(String.valueOf(obj2),PoliceStation.class);
            }
            else if(obj2!=null && obj2.getString("locationType").equals("hospital")){
                hospital = gsonBuild.fromJson(String.valueOf(obj2),Hospital.class);
            }
            else if(obj2!=null && obj2.getString("locationType").equals("restaurant")){
                sevenEleven = gsonBuild.fromJson(String.valueOf(obj2),SevenEleven.class);
            }


            if(obj3!=null && obj3.getString("locationType").equals("police")){
                policeStation = gsonBuild.fromJson(String.valueOf(obj3),PoliceStation.class);
            }
            else if(obj3!=null && obj3.getString("locationType").equals("hospital")){
                hospital = gsonBuild.fromJson(String.valueOf(obj3),Hospital.class);
            }
            else if(obj3!=null && obj3.getString("locationType").equals("restaurant")){
                sevenEleven = gsonBuild.fromJson(String.valueOf(obj3),SevenEleven.class);
            }

            NearestLocation nearestLocation = new NearestLocation(policeStation, sevenEleven, hospital);
            return nearestLocation;
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
