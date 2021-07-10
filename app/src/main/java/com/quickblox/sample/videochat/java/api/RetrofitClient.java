package com.quickblox.sample.videochat.java.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quickblox.sample.videochat.java.constants.ApiConstants;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by poonam on 10-04-2019.
 */

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {

            Gson gson = new GsonBuilder()
                    .setDateFormat(ApiConstants.SERVICE_DATE_FORMAT)
                    .setLenient()
                    .create();

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

//            httpClient.addInterceptor(new Interceptor() {
//                @Override
//                public okhttp3.Response intercept(Chain chain) throws IOException {
//                    Request request = chain.request().newBuilder().addHeader("JWT", "token").build();
//                    return chain.proceed(request);
//                }
//            });
            retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(baseUrl).client(httpClient.build()).build();

        }
        return retrofit;
    }
}
