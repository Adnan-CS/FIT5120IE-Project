package com.workingsafe.safetyapp.restapi;

import android.util.Log;

import com.google.gson.Gson;
import com.workingsafe.safetyapp.model.Counsellingcenters;
import com.workingsafe.safetyapp.model.CurrentLocation;
import com.workingsafe.safetyapp.model.Legalcenters;

import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RestApi {
    private static final String BASE_URL = "http://192.168.1.104:4004/api";
    //private static final String BASE_URL = "http://10.0.2.2:4004/api";
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
}
