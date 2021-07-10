package com.quickblox.sample.videochat.java.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;


import com.quickblox.sample.videochat.java.R;
import com.quickblox.sample.videochat.java.data.LocalSharePrefData;
import com.quickblox.sample.videochat.java.fragments.RAppRTCAccountCreatedFragment;
import com.quickblox.sample.videochat.java.fragments.RAppRTCCityFragment;
import com.quickblox.sample.videochat.java.fragments.RAppRTCDOBFragment;
import com.quickblox.sample.videochat.java.fragments.RAppRTCEmailFragment;
import com.quickblox.sample.videochat.java.fragments.RAppRTCGenderFragment;
import com.quickblox.sample.videochat.java.fragments.RAppRTCNameFragment;
import com.quickblox.sample.videochat.java.fragments.RAppRTCOTPFragment;
import com.quickblox.sample.videochat.java.fragments.RAppRTCPhoneFragment;
import com.quickblox.sample.videochat.java.fragments.RAppRTCProfileFragment;
import com.quickblox.sample.videochat.java.fragments.RAppRTCSignLanguage;

import org.json.JSONObject;


import okhttp3.OkHttpClient;


public class MainActivity extends AppCompatActivity {

    int  PERMISSIONS_REQUEST_READ_LOG=1011;

    int PERMISSIONS_REQUEST_READ_CONTACT=1234;

    LocalSharePrefData localSharePrefData;
    //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_main);
       // Checkout.preload(getApplicationContext());
       // permissionCheck();

//getting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
 //       getSupportActionBar().setTitle(null);

//        toolbar.setLogo(R.drawable.back);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


//        getSupportActionBar().setDisplayOptions(androidx.appcompat.app.ActionBar.DISPLAY_SHOW_HOME |
//                    androidx.appcompat.app.ActionBar.DISPLAY_SHOW_TITLE | androidx.appcompat.app.ActionBar.DISPLAY_HOME_AS_UP | androidx.appcompat.app.ActionBar.DISPLAY_USE_LOGO);
//
//            getSupportActionBar().setIcon(R.mipmap.smallest);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//            getSupportActionBar().setDisplayShowTitleEnabled(false);


        try {
            RAppRTCPhoneFragment();

//            localSharePrefData = new LocalSharePrefData();
//            if (localSharePrefData.hasToken(this)){
//                localSharePrefData.setIsUserRegistered(MainActivity.this,false);
//                LoginFragments loginFragments = new LoginFragments();
//                loginFragments.initializeOkHttp();
//                successLogin(localSharePrefData.getUserType(this));
//                return ;
//
//
//            }
//
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
                Manifest.permission.READ_CONTACTS,
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

    public void RAppRTCDOBFragment(){

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.main_container,new RAppRTCDOBFragment()).
                commit();

    }
    public void RAppRTCEmailFragment(){

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.main_container,new RAppRTCEmailFragment()).
                commit();

    }

    public void RAppRTCGenderFragment(){

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.main_container,new RAppRTCGenderFragment()).
                commit();

    }

    public void RAppRTCNameFragment(){

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.main_container,new RAppRTCNameFragment()).
                commit();

    }



    public void RAppRTCOTPFragment(OkHttpClient okHttpClient){
//        RAppRTCOTPFragment otpFragment=new RAppRTCOTPFragment();
//        otpFragment.assignOkhttph(okHttpClient);

        RAppRTCPhoneFragment otpFragment=new RAppRTCPhoneFragment();
        otpFragment.assignOkhttph(okHttpClient);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.main_container,otpFragment).
                commit();

    }

    public void Otp(){

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.main_container,new RAppRTCOTPFragment()).
                commit();

    }

    public void RAppRTCPhoneFragment(){

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.main_container,new RAppRTCPhoneFragment()).
                commit();

    }

    public void RAppRTCProfileFragment(){

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.main_container,new RAppRTCProfileFragment()).
                commit();

    }

    public void RAppRTCAccountCreated(){

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.main_container,new RAppRTCAccountCreatedFragment()).
                commit();

    }

    public void RAppRTCSignLanguageFragment(){

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.main_container,new RAppRTCSignLanguage()).
                commit();

    }

    public void RAppRTCCityFragment(){

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.main_container,new RAppRTCCityFragment()).
                commit();

    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }





}