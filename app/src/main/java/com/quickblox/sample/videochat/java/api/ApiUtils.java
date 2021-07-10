package com.quickblox.sample.videochat.java.api;


/**
 * Created by poonam on 10-04-2019.
 */

public class ApiUtils {
    private ApiUtils() {
    }

    public static final String BASE_URL = ServiceConstants.BASE_URL;
    //public static final String LOGIN_URL = ServiceConstants.LOGIN_URL;
   // public static final String ADD_PROP_URL = ServiceConstants.ADD_PROP_URL;
   // public static final String ADD_MAINTENANCE_URL = ServiceConstants.ADD_MAINTENANCE_URL;

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }



    /*public static APIService getpropertyAPIService() {

        Gson gson = new GsonBuilder()
                .setDateFormat(SystemConstants.SERVICE_DATE_FORMAT)
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ADD_PROP_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(APIService.class);
    }*/

   /* public static APIService getMaintenanceAPIService(){

        Gson gson = new GsonBuilder()
                .setDateFormat(SystemConstants.SERVICE_DATE_FORMAT)
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(ADD_MAINTENANCE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(APIService.class);
    }*/

  /*  public static APIService getLoginAPIService() {

        Gson gson = new GsonBuilder()
                .setDateFormat(SystemConstants.SERVICE_DATE_FORMAT)
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LOGIN_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(APIService.class);
    }*/
}
