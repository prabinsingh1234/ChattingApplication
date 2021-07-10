package com.quickblox.sample.videochat.java.fragments;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.quickblox.sample.videochat.java.R;

import java.util.Calendar;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppRTCIndicatorFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppRTCIndicatorFragment1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private VideoView videoview;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public AppRTCIndicatorFragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppRTCIndicatorFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static AppRTCIndicatorFragment1 newInstance(String param1, String param2) {
        AppRTCIndicatorFragment1 fragment = new AppRTCIndicatorFragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_app_rtcindicator_fragment1, container, false);
        Button button2= (Button) view.findViewById(R.id.demo_button);
        playVideo(view);
        button2.setOnClickListener(v -> {
            try {
                ViewPager viewPager = (ViewPager) Objects.requireNonNull(getActivity()).findViewById(
                        R.id.view_pager);
                viewPager.setCurrentItem(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return view;
    }

    private void playVideo(View view) {
        videoview = (VideoView) view.findViewById(R.id.videoView);

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        Uri uri = Uri.parse("android.resource://"+ Objects.requireNonNull(getActivity()).getPackageName()+"/"+R.raw.correct);
        if(timeOfDay >= 0 && timeOfDay < 12){
            uri = Uri.parse("android.resource://"+ Objects.requireNonNull(getActivity()).getPackageName()+"/"+R.raw.morning);
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            uri = Uri.parse("android.resource://"+ Objects.requireNonNull(getActivity()).getPackageName()+"/"+R.raw.afternoon);
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            uri = Uri.parse("android.resource://"+ Objects.requireNonNull(getActivity()).getPackageName()+"/"+R.raw.evening);
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            uri = Uri.parse("android.resource://"+ Objects.requireNonNull(getActivity()).getPackageName()+"/"+R.raw.night);
        }

        videoview.setVideoURI(uri);
        videoview.start();
        videoview.setOnPreparedListener (mp -> mp.setLooping(true));
        videoview.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                //Log.d("video", "setOnErrorListener ");
                return true;
            }
        });
    }

}
