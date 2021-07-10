package com.quickblox.sample.videochat.java.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.quickblox.sample.videochat.java.models.UserInformation;

import static android.content.Context.MODE_PRIVATE;

public class LocalSharePrefData {

    private static final String USERREGISTERED = "userregistered";
    private String MY_PREFS_NAME="InkudooApp";
    private String USERNAME="username";
    private String PASSWORD="password";
    private String userLoggedIn="userLoggedIn";
    private String USER_TYPE="usertype";
    private String TOKEN = "token";
    private String DATA = "data";
    private String DEVICETOKEN = "devicetoken";
    private String LANGUAGES = "languages";
    private String DEVICEREGISTERED="deviceregistered";

    private String VERIFIED = "verified";


    /**saves userinformation **/
    public void saveUserCredentials(Context context, UserInformation userInformation){

        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(USERNAME, userInformation.getUsername());
        editor.putString(PASSWORD, userInformation.getPassword());
        editor.apply();

    }

    public void saveUserType(Context context,int userType){

        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt(USER_TYPE, userType);
        editor.apply();

    }

    public void saveToken(Context context, String token){
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(TOKEN, token);
        editor.apply();

    }

    public void setDeviceToken(Context context, String token){
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(DEVICETOKEN, token);
        editor.apply();

    }



    public String getDeviceToken(Context context){
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String token= prefs.getString(DEVICETOKEN,"");
        return token;

    }



    public void setIsDeviceRegistered(Context context, Boolean IsDeviceRegistered){
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(DEVICEREGISTERED, IsDeviceRegistered);
        editor.apply();

    }

    public Boolean getIsDeviceRegistered(Context context){
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        return prefs.getBoolean(DEVICEREGISTERED,false);

    }

    public void saveSignUpData(Context context, String data, String key){
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(key, data);
        editor.apply();

    }


    public String getSignUpData(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        return prefs.getString(key,"");

    }

    public void setIsUserRegistered(Context context, Boolean isUserRegistered){
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(USERREGISTERED, isUserRegistered);
        editor.apply();

    }

    public Boolean getIsUserRegistered(Context context){
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        return prefs.getBoolean(USERREGISTERED,true);
    }

    public void setLanguages(Context context, String languages){
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(LANGUAGES, languages);
        editor.apply();

    }

    public String getLanguages(Context context){
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        return prefs.getString(LANGUAGES,"");

    }

    public boolean hasToken(Context context){
        return  !getToken(context).equals("");

    }

    public void removeToken(Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.remove(TOKEN);
        editor.apply();
    }

    public String getToken(Context context){
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String token= prefs.getString(TOKEN,"");
        return token;

    }

    public void saveVerified(Context context,boolean verified){
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(VERIFIED, verified);
        editor.apply();

    }

    public boolean isVerified(Context context){
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        return  prefs.getBoolean(VERIFIED,false);

    }


    public int getUserType(Context context){

        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        int userType= prefs.getInt( USER_TYPE, 0);

        return  userType;
    }

    /** function saves the status of user if logged in **/
    public void ifUserLoggedIn(Context context,int status){

        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt(USERNAME, status);
        editor.apply();

    }
    /** function returns user logged information  **/
    public int getIfUserLoggedIn(Context context){
        UserInformation userInformation=new UserInformation();

        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        int userLoggedIn = prefs.getInt( USERNAME, 0);

        return  userLoggedIn;
    }

    /**fetches user information **/
    public UserInformation getUserCredentials(Context context){

        UserInformation userInformation=new UserInformation();

        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String userName = prefs.getString( USERNAME, null);
        String userPassword=prefs.getString(PASSWORD,null);

        if(userName!=null && userPassword !=null){
            userInformation.setUsername(userName);
            userInformation.setPassword(userPassword);

            return userInformation;
        }else
            return null;
    }

    public void setNotification(Context context, String data){
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(DATA, data);
        editor.apply();

    }

    public String getNotification(Context context){
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String data= prefs.getString(DATA,"");
        return data;

    }
}
