package com.workingsafe.clientapp.restapi;

import com.google.gson.Gson;
import com.workingsafe.clientapp.model.Registration;

import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RestApi {
    private static final String BASE_URL = "http://10.0.2.2:4004/api";
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
    public String addCredentials(Registration registration){
        String finalResult = "";
        Registration signUpModel1 = registration;
        Gson gson = new Gson();
        String signUpJson = gson.toJson(registration);
        String strResponse="";
        final String credMethodPath = "/users";
        RequestBody body = RequestBody.create(signUpJson, JSON);
        Request request = new Request.Builder().url(BASE_URL + credMethodPath).post(body).build();
        try {
            Response response= client.newCall(request).execute();
            strResponse= response.body().string();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return strResponse;
    }
}
