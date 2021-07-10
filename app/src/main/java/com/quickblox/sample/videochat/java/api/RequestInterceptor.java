package com.quickblox.sample.videochat.java.api;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestInterceptor implements Interceptor {
    final ConnectivityManager connectivityManager;

    public RequestInterceptor() {
        connectivityManager = null;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        if (isConnected()) {
            throw new OfflineException ("no internet");
        }

        Request.Builder r = chain.request().newBuilder();
        return chain.proceed(r.build());
    }
    protected boolean isConnected() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    class OfflineException extends  IOException{

        public OfflineException(String no_internet) {
        }
    }

           
}
