package com.quickblox.sample.videochat.java.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.quickblox.sample.videochat.java.fragments.CallHistory;
import com.quickblox.sample.videochat.java.fragments.ContactsFrag;


public class HomePageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public HomePageAdapter(FragmentManager fm, int NoofTabs){
        super(fm);
        this.mNumOfTabs = NoofTabs;
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                ContactsFrag home = new ContactsFrag();
                //DialogFragment home=new DialogFragment();
                return home;
            case 1:
                //ContactsFrag dialFrag = new ContactsFrag();
               // DialFrag dialFrag=new DialFrag();
              //  return  dialFrag;

            case 2:
               // DialFrag about=new DialFrag();
              //  NotificationListFragment notificationListFragment = new NotificationListFragment();
               // return notificationListFragment;

            case 3:
                // DialFrag about=new DialFrag();
                CallHistory about = new CallHistory();
                return about;
            default:
                return null;
        }
    }
}