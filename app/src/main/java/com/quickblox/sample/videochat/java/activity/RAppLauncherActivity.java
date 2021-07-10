package com.quickblox.sample.videochat.java.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.quickblox.sample.videochat.java.R;
import com.quickblox.sample.videochat.java.data.LocalSharePrefData;
import com.quickblox.sample.videochat.java.fragments.AppRTCIndicatorFragment1;
import com.quickblox.sample.videochat.java.fragments.AppRTCIndicatorFragment2;
import com.quickblox.sample.videochat.java.fragments.AppRTCIndicatorFragment3;
import com.quickblox.sample.videochat.java.fragments.AppRTCIndicatorFragment4;
import com.quickblox.sample.videochat.java.fragments.AppRTCIndicatorFragment5;
import com.quickblox.sample.videochat.java.fragments.AppRTCIndicatorFragment6;
import com.quickblox.sample.videochat.java.util.IOnBackPressed;

public class RAppLauncherActivity extends AppCompatActivity {
    ViewPager viewPager;
    private LocalSharePrefData mLocalSharedPrefData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapp_launcher);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        viewPager=(ViewPager)findViewById(R.id.view_pager);
        mLocalSharedPrefData=new LocalSharePrefData();

        loadAdapter();

    }

    private void loadAdapter() {
        if (mLocalSharedPrefData.getIsUserRegistered(RAppLauncherActivity.this)){
            viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        }

    }
    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {

            switch (pos) {

                case 0:
                    return AppRTCIndicatorFragment1.newInstance(getString(R.string.title_indicator_one),getString(R.string.title_indicator_onep2));
                case 1:
                    return AppRTCIndicatorFragment2.newInstance(getString(R.string.title_indicator_two),getString(R.string.title_indicator_twop2));
                case 2:
                    return AppRTCIndicatorFragment3.newInstance(getString(R.string.title_indicator_three),getString(R.string.title_indicator_threep2));
                case 3:
                    return AppRTCIndicatorFragment4.newInstance(getString(R.string.title_indicator_four),getString(R.string.title_indicator_fourp2));
                case 4:
                    return AppRTCIndicatorFragment5.newInstance(getString(R.string.title_indicator_five),getString(R.string.title_indicator_fivep2));
                case 5:
                    return AppRTCIndicatorFragment6.newInstance(getString(R.string.title_indicator_six),getString(R.string.title_indicator_six2));
                default:
                    return AppRTCIndicatorFragment1.newInstance(getString(R.string.title_indicator_default),getString(R.string.title_indicator_defaultp2));
            }
        }

        @Override
        public int getCount() {
            return 6;
        }
    }

    @Override
    public void onBackPressed() {



        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
        if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {

        }
    }
}
