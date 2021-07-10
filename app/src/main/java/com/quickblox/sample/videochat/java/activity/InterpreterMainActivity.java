package com.quickblox.sample.videochat.java.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.quickblox.sample.videochat.java.R;
import com.quickblox.sample.videochat.java.activities.InterpreterQuickBloxLogin;
import com.quickblox.sample.videochat.java.api.ApiRequest;
import com.quickblox.sample.videochat.java.constants.ApiConstants;
import com.quickblox.sample.videochat.java.data.LocalSharePrefData;
import com.quickblox.sample.videochat.java.fragments.LoginFragments;

import org.json.JSONObject;

import okhttp3.OkHttpClient;

import static com.quickblox.sample.videochat.java.fragments.ContactsFrag.okHttpClient;

public class InterpreterMainActivity  extends AppCompatActivity {

    int  PERMISSIONS_REQUEST_READ_LOG=1011;

    int PERMISSIONS_REQUEST_READ_CONTACT=1234;

    LocalSharePrefData localSharePrefData;
    //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        permissionCheck();



//        getSupportActionBar().setDisplayOptions(androidx.appcompat.app.ActionBar.DISPLAY_SHOW_HOME |
//                androidx.appcompat.app.ActionBar.DISPLAY_SHOW_TITLE | androidx.appcompat.app.ActionBar.DISPLAY_HOME_AS_UP | androidx.appcompat.app.ActionBar.DISPLAY_USE_LOGO);
//
//        getSupportActionBar().setIcon(R.mipmap.smallest);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);


        try {
            localSharePrefData = new LocalSharePrefData();
            if (localSharePrefData.hasToken(this)){
                localSharePrefData.setIsUserRegistered(InterpreterMainActivity.this,false);
                LoginFragments loginFragments = new LoginFragments();
                loginFragments.initializeOkHttp();
                successLogin(localSharePrefData.getUserType(this));
                return ;


            }
            loginFragment();

//            if (localSharePrefData.getIsUserRegistered(MainActivity.this)) {
//                RAppRTCPhoneFragment();
//            } else {
//                loginFragment();
//            }


        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public void permissionCheck(){
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.READ_CONTACTS,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
        };

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }


    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**function replace the fragment with login fragment **/
    public void loginFragment(){

        localSharePrefData=new LocalSharePrefData();

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.main_container,new LoginFragments()).
                commit();




    }



    public void successLogin(int status){

        try {



            if(status==1) {
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            }

            else if(status==2){

                Intent intent = new Intent(this, InterpreterQuickBloxLogin.class);
                startActivity(intent);

            }

            else if(status==0){
                loginFragment();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
      finishAffinity();
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }

}
