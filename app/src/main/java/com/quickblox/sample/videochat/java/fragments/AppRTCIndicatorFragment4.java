package com.quickblox.sample.videochat.java.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.quickblox.sample.videochat.java.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppRTCIndicatorFragment4#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppRTCIndicatorFragment4 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ViewPager viewPager;
    private VideoView videoview;


    public AppRTCIndicatorFragment4() {
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
    public static AppRTCIndicatorFragment4 newInstance(String param1, String param2) {
        AppRTCIndicatorFragment4 fragment = new AppRTCIndicatorFragment4();
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
        View view=inflater.inflate(R.layout.fragment_app_rtcindicator_fragment4, container, false);
        playVideo(view);

        Button button2= (Button) view.findViewById(R.id.button2);
        viewPager = (ViewPager) Objects.requireNonNull(getActivity()).findViewById(
                R.id.view_pager);
        button2.setOnClickListener(v -> {
            try {

                viewPager.setCurrentItem(4);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Button button= (Button) view.findViewById(R.id.button);
        button.setOnClickListener(v -> {

            viewPager.setCurrentItem(2);

        });
        return view;
    }
    private void playVideo(View view) {
        try {
            videoview = (VideoView) view.findViewById(R.id.videoView);
            Uri uri = Uri.parse("android.resource://"+ Objects.requireNonNull(getActivity()).getPackageName()+"/"+R.raw.slide3);
            videoview.setVideoURI(uri);
            videoview.start();
            videoview.setOnPreparedListener (mp -> mp.setLooping(true));
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        if (videoview!=null) {
            videoview.start();
            Log.e("Frontales","resumevdo");

        }

        Log.e("Frontales","resume");

        goToLastFragment();
    }

    private void goToLastFragment() {
        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    // handle back button's click listener
                    Log.d("onBackPressedresume","true");
                    viewPager.setCurrentItem(2);

                    return true;

                }
                Log.d("onBackPressedresume","false");

                return false;
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("Frontales","Pause");

    }

}
