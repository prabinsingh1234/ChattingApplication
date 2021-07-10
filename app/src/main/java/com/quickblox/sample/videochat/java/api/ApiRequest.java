package com.quickblox.sample.videochat.java.api;

import android.os.Build;

import androidx.annotation.RequiresApi;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ApiRequest {


    /** function create request **/
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String createRequest(OkHttpClient okHttpClient, String BASE_URL, String SUB_PART, RequestBody requestBody){

        OkHttpClient client = okHttpClient;


        Request request = new Request.Builder()
                .url(BASE_URL+SUB_PART)
                .post(requestBody)
                .build();


        try{

           Response response = client.newCall(request).execute();
           ResponseBody body = response.body();

           return body.string();


        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String createRequest(OkHttpClient okHttpClient, String BASE_URL, String SUB_PART, RequestBody requestBody,String token){

        OkHttpClient client = okHttpClient;


        Request request = new Request.Builder()
                .url(BASE_URL+SUB_PART)
                .addHeader("Authorization",token)
                .post(requestBody)
                .build();

//        Request request = new Request.Builder()
//                .url(BASE_URL+SUB_PART)
//                .post(requestBody)
//                .build();

        try{

            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();

            return body.string();


        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }


    /**function used for getting response **/
    public static String getRequest(OkHttpClient okHttpClient, String BASE_URL, String SUB_PART) throws Exception {


        Request request = new Request.Builder()
                    .url(BASE_URL+SUB_PART)
                    .build();



        Response response = okHttpClient.newCall(request).execute();


        return response.body().string();

        }

    public static String getRequest(OkHttpClient okHttpClient, String BASE_URL, String SUB_PART,String token) throws Exception {


        Request request = new Request.Builder()
                .url(BASE_URL+SUB_PART)
                .addHeader("Authorization",token)
                .build();



        Response response = okHttpClient.newCall(request).execute();


        return response.body().string();

    }

    public static String createRequest(OkHttpClient okHttpClient, String baseUrl,String SUB_PART, String deviceregistration,String token) {

        OkHttpClient client = okHttpClient;

        RequestBody requestBody = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url(baseUrl+SUB_PART+deviceregistration)
                .addHeader("Authorization",token)

                .build();

        try{

            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();

            return body.string();


        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}