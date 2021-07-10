package com.quickblox.sample.videochat.java.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.quickblox.sample.videochat.java.R;
import com.quickblox.sample.videochat.java.activities.LoginActivity;
import com.quickblox.sample.videochat.java.data.LocalSharePrefData;


public class Permission extends AppCompatActivity {
    int PERMISSION_ALL = 1;
    int  PERMISSIONS_REQUEST_READ_LOG=1011;

    int PERMISSIONS_REQUEST_READ_CONTACT=1234;

    LocalSharePrefData localSharePrefData;
    //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.permission_layout);
        //Checkout.preload(getApplicationContext());
        permissionCheck();



    }

    public void permissionCheck(){

        String[] PERMISSIONS = {
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
        };

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        boolean check = hasPermissions(this, PERMISSIONS);
        System.out.println("hasPermissions"+check);



    }
    public boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }

        Next();
        return true;

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//       // Next();
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_ALL){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED ){

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                       Next();
                    }
                }).start();
            }else{
                Toast.makeText(Permission.this, "Access Denied ! Cannot proceed further ", Toast.LENGTH_SHORT).show();
            }
        }
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

public void Next()
{

    SharedPreferences sharedPreferences = getSharedPreferences("MyLogin.txt", Context.MODE_PRIVATE);
    Boolean loginCheck = sharedPreferences.getBoolean("FirstLogin", false);
    if (loginCheck){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }else{
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }



}
}