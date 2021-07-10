package com.quickblox.sample.videochat.java.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;


import com.quickblox.sample.videochat.java.R;
import com.quickblox.sample.videochat.java.activity.MainActivity;
import com.quickblox.sample.videochat.java.constants.SystemConstant;
import com.quickblox.sample.videochat.java.data.LocalSharePrefData;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RAppRTCGenderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RAppRTCGenderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RAppRTCGenderFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private LocalSharePrefData mLocalSharedPrefData;
    private String gender="";
    private VideoView videoview;

    public RAppRTCGenderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RAppRTCGenderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RAppRTCGenderFragment newInstance(String param1, String param2) {
        RAppRTCGenderFragment fragment = new RAppRTCGenderFragment();
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
        View view=inflater.inflate(R.layout.fragment_rapp_rtcgender, container, false);
        playVideo(view);
        mLocalSharedPrefData=new LocalSharePrefData();


        Button nextStep= (Button) view.findViewById(R.id.demo_button);
        Button button3= (Button) view.findViewById(R.id.button3);
        Button button4= (Button) view.findViewById(R.id.button4);
        Button button5= (Button) view.findViewById(R.id.button5);

        LinearLayout ll1= (LinearLayout) view.findViewById(R.id.button31);
        LinearLayout ll2= (LinearLayout) view.findViewById(R.id.button32);
        LinearLayout ll3= (LinearLayout) view.findViewById(R.id.button33);


        ll1.setOnClickListener(v -> {
            gender="Male";
            ll1.setBackgroundResource(R.drawable.button_select);
            button3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.feather_icon_check_circle, 0);
            button4.setCompoundDrawablesWithIntrinsicBounds(0, 0,0, 0);
            button5.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            ll2.setBackgroundResource(R.drawable.editextbackground);
            ll3.setBackgroundResource(R.drawable.editextbackground);
            button3.setTextColor(Color.parseColor("#2358a3"));
            button4.setTextColor(Color.parseColor("#82818b"));
            button5.setTextColor(Color.parseColor("#82818b"));
        });

        ll2.setOnClickListener(v -> {
            gender="Female";
            ll2.setBackgroundResource(R.drawable.button_select);
            button3.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            button4.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.feather_icon_check_circle, 0);
            button5.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            ll1.setBackgroundResource(R.drawable.editextbackground);
            ll3.setBackgroundResource(R.drawable.editextbackground);
            button3.setTextColor(Color.parseColor("#82818b"));
            button4.setTextColor(Color.parseColor("#2358a3"));
            button5.setTextColor(Color.parseColor("#82818b"));

        });

        ll3.setOnClickListener(v -> {
            gender="Other";
            ll3.setBackgroundResource(R.drawable.button_select);
            button3.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            button4.setCompoundDrawablesWithIntrinsicBounds(0, 0,0, 0);
            button5.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.feather_icon_check_circle, 0);
            ll1.setBackgroundResource(R.drawable.editextbackground);
            ll2.setBackgroundResource(R.drawable.editextbackground);
            button3.setTextColor(Color.parseColor("#82818b"));
            button4.setTextColor(Color.parseColor("#82818b"));
            button5.setTextColor(Color.parseColor("#2358a3"));

        });


        button3.setOnClickListener(v -> {
            gender="Male";
            ll1.setBackgroundResource(R.drawable.button_select);
            button3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.feather_icon_check_circle, 0);
            button4.setCompoundDrawablesWithIntrinsicBounds(0, 0,0, 0);
            button5.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            ll2.setBackgroundResource(R.drawable.editextbackground);
            ll3.setBackgroundResource(R.drawable.editextbackground);
            button3.setTextColor(Color.parseColor("#2358a3"));
            button4.setTextColor(Color.parseColor("#82818b"));
            button5.setTextColor(Color.parseColor("#82818b"));
        });

        button4.setOnClickListener(v -> {
            gender="Female";
            ll2.setBackgroundResource(R.drawable.button_select);
            button3.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            button4.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.feather_icon_check_circle, 0);
            button5.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            ll1.setBackgroundResource(R.drawable.editextbackground);
            ll3.setBackgroundResource(R.drawable.editextbackground);
            button3.setTextColor(Color.parseColor("#82818b"));
            button4.setTextColor(Color.parseColor("#2358a3"));
            button5.setTextColor(Color.parseColor("#82818b"));

        });

        button5.setOnClickListener(v -> {
            gender="Other";
            ll3.setBackgroundResource(R.drawable.button_select);
            button3.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            button4.setCompoundDrawablesWithIntrinsicBounds(0, 0,0, 0);
            button5.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.feather_icon_check_circle, 0);
            ll1.setBackgroundResource(R.drawable.editextbackground);
            ll2.setBackgroundResource(R.drawable.editextbackground);
            button3.setTextColor(Color.parseColor("#82818b"));
            button4.setTextColor(Color.parseColor("#82818b"));
            button5.setTextColor(Color.parseColor("#2358a3"));

        });

        nextStep.setOnClickListener(v -> {
            saveDataToLocal();

            if ( gender.length()!=0) {
                MainActivity mainActivity=(MainActivity)getActivity();
                if (mainActivity != null) {
                    mainActivity.RAppRTCSignLanguageFragment();
                    SystemConstant.GENDER = gender;
                }
            } else {
                Toast.makeText(getActivity(),"Select Gender",Toast.LENGTH_LONG).show();
            }
        });

        LinearLayout ll = (LinearLayout)view.findViewById(R.id.demo_button1);
        ll.setOnClickListener(v -> {
            saveDataToLocal();

                if ( gender.length()!=0) {
                    MainActivity mainActivity=(MainActivity)getActivity();
                    if (mainActivity != null) {
                        mainActivity.RAppRTCSignLanguageFragment();
                        SystemConstant.GENDER = gender;
                    }
                } else {
                    Toast.makeText(getActivity(),"Select Gender",Toast.LENGTH_LONG).show();
                }
        });


        TextView skip= (TextView) view.findViewById(R.id.help);
        skip.setOnClickListener(v -> {

                MainActivity mainActivity=(MainActivity)getActivity();
                if (mainActivity != null) {
                    mainActivity.RAppRTCSignLanguageFragment();
                }
        });


        ImageView back= (ImageView) view.findViewById(R.id.back);
        back.setOnClickListener(v -> {


            MainActivity mainActivity=(MainActivity)getActivity();
            if (mainActivity != null) {
                mainActivity.RAppRTCDOBFragment();
                getActivity().getFragmentManager().popBackStack();
                //   mainActivity.Otp();
            }


        });

        return view;
    }
    private void playVideo(View view) {
        videoview = (VideoView) view.findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://"+ Objects.requireNonNull(getActivity()).getPackageName()+"/"+R.raw.gender);
        videoview.setVideoURI(uri);
        videoview.start();
        videoview.setOnPreparedListener (mp -> mp.setLooping(true));
    }

    private void saveDataToLocal() {
        try {
            if (!gender.equals("")) {
                mLocalSharedPrefData.saveSignUpData(Objects.requireNonNull(getActivity()),gender,"usergender");
            } else {
                Toast.makeText(getActivity(),"Select Valid Gender",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
                    MainActivity mainActivity=(MainActivity)getActivity();
                    if (mainActivity != null) {
                        mainActivity.RAppRTCDOBFragment();
                        getActivity().getFragmentManager().popBackStack();

                    }

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
