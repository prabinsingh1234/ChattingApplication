package com.quickblox.sample.videochat.java.activity;

import android.Manifest;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.quickblox.sample.videochat.java.Database.DbHandler;
import com.quickblox.sample.videochat.java.R;
import com.quickblox.sample.videochat.java.adapter.HomePageAdapter;
import com.quickblox.sample.videochat.java.constants.SystemConstant;
import com.quickblox.sample.videochat.java.data.LocalSharePrefData;
import com.quickblox.sample.videochat.java.fragments.CallHistory;
import com.quickblox.sample.videochat.java.fragments.ContactsFrag;
import com.quickblox.sample.videochat.java.models.CallInfo;
import com.quickblox.sample.videochat.java.models.ContactModels;


import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements CallHistory.CallHistoryInformation, ContactsFrag.CallInformation{


//
//    @BindView(R.id.tool_bar)
//    Toolbar toolbar;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private CallInfo mCallInfo;
    private ContactModels mCallModules;

    int PERMISSIONS_REQUEST_READ_LOG=121;
    int PERMISSIONS_REQUEST_READ_CONTACT=111;

    private Updateable updateable;

    private LocalSharePrefData mLocalSharedPref;
    private String token;
    private TabLayout.Tab tabContact;
    private TabLayout.Tab tabDial;
    private TabLayout.Tab tabSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //getting the toolbar
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle(null);
//
////        toolbar.setLogo(R.drawable.back);
//
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        permissionCheck();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.phone_book).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.call_history:
                         goToCallHistory();
                        //  item.setIcon(R.drawable.callhistory1);
                        // Toast.makeText(MakeCall.this, "Recents", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.phone_book:

                        item.setIcon(R.drawable.contact1);
                       // Intent intent = new Intent(Home.this, HomeActivity.class);
                      //  startActivity(intent);
                        // Toast.makeText(MakeCall.this, "Favorites", Toast.LENGTH_SHORT).show();
                        // next();
                        break;
                    case R.id.profile_setting:
                        item.setIcon(R.drawable.setting1);
                       // startActivity(new Intent(HomeActivity.this,ProfileSettingsActivity.class));
                        // Toast.makeText(MakeCall.this, "Nearby", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        try {
            ButterKnife.bind(this);


        }catch (Exception ex){
            ex.printStackTrace();
        }
        try {
            String[] PERMISSIONS = {
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO
            };

            if(hasPermissions(this, PERMISSIONS)){
                initView();
            }



        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void goToCallHistory()
    {
        Intent intent = new Intent(this, History.class);
        startActivity(intent);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        initView();
    }

    public void initView(){

       // setSupportActionBar(toolbar);

        try {

            getSupportActionBar().setDisplayOptions(androidx.appcompat.app.ActionBar.DISPLAY_SHOW_HOME |
                    androidx.appcompat.app.ActionBar.DISPLAY_SHOW_TITLE | androidx.appcompat.app.ActionBar.DISPLAY_HOME_AS_UP | androidx.appcompat.app.ActionBar.DISPLAY_USE_LOGO);

            getSupportActionBar().setIcon(R.mipmap.smallest);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);




        }catch (Exception ex){
            ex.printStackTrace();
        }

        ActionBar actionBar = getActionBar();
        tabContact=tabLayout.newTab();
      //  tabContact.setIcon(R.drawable.feather_icon_clock);
        tabLayout.addTab(tabContact);
//        tabDial=tabLayout.newTab().setText(R.string.dial);
        tabDial=tabLayout.newTab().setText("");

//        tabDial.setIcon(R.drawable.men_2);
//        tabLayout.addTab(tabDial);
//        tabLayout.addTab(tabLayout.newTab().setText("Call History"));
        //tabSettings=tabLayout.newTab().setText(R.string.notification);
//        tabSettings=tabLayout.newTab().setText("");
//        tabSettings.setIcon(R.drawable.interface_5_1);
//        tabLayout.addTab(tabSettings);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        HomePageAdapter tabsAdapter = new HomePageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabsAdapter);

        mLocalSharedPref=new LocalSharePrefData();

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        String menuFragmentIntent = getIntent().getStringExtra("menuFragment");

        if (menuFragmentIntent != null) {

            // Here we can decide what do to -- perhaps load other parameters from the intent extras such as IDs, etc
            if (menuFragmentIntent.equals("notificationFragment")) {
                viewPager.setCurrentItem(2);
            }

        }
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

                if(tab.getPosition()==0){

                    tab.setIcon(R.drawable.feather_icon_clock);
                }
                if(tab.getPosition()==1){
                    tab.setIcon(R.drawable.men_1);
                }
                if(tab.getPosition()!=1){
                    updateable.update();
                }

                if(tab.getPosition()==2){

                    tab.setIcon(R.drawable.interface_6_1);
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){

                    tab.setIcon(R.drawable.interface_4_1);
                }
                if(tab.getPosition()==1){
                    tab.setIcon(R.drawable.men_2);
                }
                if(tab.getPosition()!=1){
                    updateable.update();
                }

                if(tab.getPosition()==2){

                    tab.setIcon(R.drawable.interface_5_1);
                }

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        permissionCheck();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mLocalSharedPref.ifUserLoggedIn(HomeActivity.this,0);
        mLocalSharedPref.removeToken(HomeActivity.this);
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);


        return false;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {


        mLocalSharedPref.ifUserLoggedIn(HomeActivity.this,0);

        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);


        return false;

    }


    private OnTabSenderData mAboutDataListener;

    @Override
    public void callHistoryInformation(CallInfo callInformation) {

        this.mCallInfo=callInformation;

        try {

            viewPager.setCurrentItem(1);

            mAboutDataListener.onDataReceived(null, mCallInfo);

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
        finish();
    }


    @Override
    public void callInformation(ContactModels callInformation) {

        this.mCallModules=callInformation;


        Bitmap image = mCallModules.photo;
        byte[]abc = getBytes(image);
        DateFormat dateFormat = new SimpleDateFormat("dd:MM HH:mm");
        Date date = new Date();
        String dateformatted = dateFormat.format(date);

        DbHandler dbHandler = new DbHandler(this);
        if(!(mCallModules.name).equals("Username")){
            dbHandler.insertUserDetails(mCallModules.name,mCallModules.mobileNumber,dateformatted,abc);
        }

        SystemConstant.callingPhno =mCallModules.mobileNumber;
        SystemConstant.CallingName  = mCallModules.name;

        next();

//        viewPager.setCurrentItem(1);
//        mAboutDataListener.onDataReceived(callInformation,mCallInfo);

    }

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public interface OnTabSenderData {

        void onDataReceived(ContactModels model, CallInfo callInfo);

    }


    public void setAboutDataListener(OnTabSenderData listener) {
        this.mAboutDataListener = listener;
    }



    /**interface to change refresh the page **/

    public interface Updateable {
        public void update();
    }

    public void initializeUpdate(Updateable updateable){
        this.updateable=updateable;
    }
    public void next()
    {
        Intent intent = new Intent(this, SelectPurpose.class);
        startActivity(intent);
    }
}