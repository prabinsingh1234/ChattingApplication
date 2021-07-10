package com.quickblox.sample.videochat.java.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
 * {@link RAppRTCDOBFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RAppRTCDOBFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RAppRTCDOBFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private VideoView videoview;
    private DatePickerDialog picker;
    private LocalSharePrefData mLocalSharedPrefData;
    private EditText editDob,d1,m1,m2,y1,y2,y3,y4;

    public RAppRTCDOBFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RAppRTCDOBFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RAppRTCDOBFragment newInstance(String param1, String param2) {
        RAppRTCDOBFragment fragment = new RAppRTCDOBFragment();
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
        View view=inflater.inflate(R.layout.fragment_rapp_rtcdob, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        playVideo(view);
        mLocalSharedPrefData=new LocalSharePrefData();

        editDob=(EditText) view.findViewById(R.id.editText);
        d1=(EditText) view.findViewById(R.id.d);

        m1=(EditText) view.findViewById(R.id.m1);
        m2=(EditText) view.findViewById(R.id.m2);

        y1=(EditText) view.findViewById(R.id.y1);
        y2=(EditText) view.findViewById(R.id.y2);
        y3=(EditText) view.findViewById(R.id.y3);
        y4=(EditText) view.findViewById(R.id.y4);
        LinearLayout ll2 = (LinearLayout)view.findViewById(R.id.ll2);
        LinearLayout ll3 = (LinearLayout)view.findViewById(R.id.demo_button1);

        editDob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    videoview.setVisibility(View.GONE);

                    ll2.setVisibility(View.VISIBLE);
                    ll3.setVisibility(View.GONE);
                } else {
                    //  Toast.makeText(getApplicationContext(), "Lost the focus", Toast.LENGTH_LONG).show();
                }
            }
        });
        d1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    videoview.setVisibility(View.GONE);

                    ll2.setVisibility(View.VISIBLE);
                    ll3.setVisibility(View.GONE);
                } else {
                    //  Toast.makeText(getApplicationContext(), "Lost the focus", Toast.LENGTH_LONG).show();
                }
            }
        });
        m1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    videoview.setVisibility(View.GONE);

                    ll2.setVisibility(View.VISIBLE);
                    ll3.setVisibility(View.GONE);
                } else {
                    //  Toast.makeText(getApplicationContext(), "Lost the focus", Toast.LENGTH_LONG).show();
                }
            }
        });
        editDob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    videoview.setVisibility(View.GONE);

                    ll2.setVisibility(View.VISIBLE);
                    ll3.setVisibility(View.GONE);
                } else {
                    //  Toast.makeText(getApplicationContext(), "Lost the focus", Toast.LENGTH_LONG).show();
                }
            }
        });
        m2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    videoview.setVisibility(View.GONE);

                    ll2.setVisibility(View.VISIBLE);
                    ll3.setVisibility(View.GONE);
                } else {
                    //  Toast.makeText(getApplicationContext(), "Lost the focus", Toast.LENGTH_LONG).show();
                }
            }
        });
        y1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    videoview.setVisibility(View.GONE);

                    ll2.setVisibility(View.VISIBLE);
                    ll3.setVisibility(View.GONE);
                } else {
                    //  Toast.makeText(getApplicationContext(), "Lost the focus", Toast.LENGTH_LONG).show();
                }
            }
        });
        y2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    videoview.setVisibility(View.GONE);

                    ll2.setVisibility(View.VISIBLE);
                    ll3.setVisibility(View.GONE);
                } else {
                    //  Toast.makeText(getApplicationContext(), "Lost the focus", Toast.LENGTH_LONG).show();
                }
            }
        });
        y3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    videoview.setVisibility(View.GONE);

                    ll2.setVisibility(View.VISIBLE);
                    ll3.setVisibility(View.GONE);
                } else {
                    //  Toast.makeText(getApplicationContext(), "Lost the focus", Toast.LENGTH_LONG).show();
                }
            }
        });
        y4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    videoview.setVisibility(View.GONE);

                    ll2.setVisibility(View.VISIBLE);
                    ll3.setVisibility(View.GONE);
                } else {
                    //  Toast.makeText(getApplicationContext(), "Lost the focus", Toast.LENGTH_LONG).show();
                }
            }
        });


               editDob.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(editDob.getText().toString().length()==1)     //size as per your requirement
                {
                    d1.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
                videoview.setVisibility(View.GONE);

                ll2.setVisibility(View.VISIBLE);
                ll3.setVisibility(View.GONE);
            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        d1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(d1.getText().toString().length()==1)     //size as per your requirement
                {
                    m1.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
                videoview.setVisibility(View.GONE);

                ll2.setVisibility(View.VISIBLE);
                ll3.setVisibility(View.GONE);

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        m1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(m1.getText().toString().length()==1)     //size as per your requirement
                {
                    m2.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
                videoview.setVisibility(View.GONE);

                ll2.setVisibility(View.VISIBLE);
                ll3.setVisibility(View.GONE);

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        m2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(m2.getText().toString().length()==1)     //size as per your requirement
                {
                    y1.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
                videoview.setVisibility(View.GONE);

                ll2.setVisibility(View.VISIBLE);
                ll3.setVisibility(View.GONE);

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        y1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(y1.getText().toString().length()==1)     //size as per your requirement
                {
                    y2.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
                videoview.setVisibility(View.GONE);

                ll2.setVisibility(View.VISIBLE);
                ll3.setVisibility(View.GONE);

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        y2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(y2.getText().toString().length()==1)     //size as per your requirement
                {
                    y3.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
                videoview.setVisibility(View.GONE);

                ll2.setVisibility(View.VISIBLE);
                ll3.setVisibility(View.GONE);

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        y3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(y3.getText().toString().length()==1)     //size as per your requirement
                {
                    y4.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
                videoview.setVisibility(View.GONE);

                ll2.setVisibility(View.VISIBLE);
                ll3.setVisibility(View.GONE);

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        y4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {

            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
                videoview.setVisibility(View.GONE);

                ll2.setVisibility(View.VISIBLE);
                ll3.setVisibility(View.GONE);

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        Button nextStep= (Button) view.findViewById(R.id.demo_button);
        nextStep.setOnClickListener(v -> {

            saveDataToLocal();
            if ( editDob.length()!=0 && d1.length()!=0 && m1.length()!=0 && m2.length()!=0 && y1.length()!=0
            && y2.length()!=0 && y3.length()!=0 && y4.length()!=0) {
                MainActivity mainActivity=(MainActivity)getActivity();
                if (mainActivity != null) {
                    String a = editDob.getText().toString();
                    String b = d1.getText().toString();

                    String c = m1.getText().toString();
                    String d = m2.getText().toString();

                    String e = y1.getText().toString();
                    String f = y2.getText().toString();
                    String g = y3.getText().toString();
                    String h = y4.getText().toString();

                    SystemConstant.DOB = e+f+g+h+"-"+c+d+"-"+a+b;
                   // mainActivity.RAppRTCProfileFragment();
                    mainActivity.RAppRTCGenderFragment();
                }

            } else {
                Toast.makeText(getActivity(),"Enter Your DOB",Toast.LENGTH_LONG).show();
            }

        });

        Button demo_button11= (Button) view.findViewById(R.id.demo_button11);
        demo_button11.setOnClickListener(v -> {
            saveDataToLocal();
            if ( editDob.length()!=0 && d1.length()!=0 && m1.length()!=0 && m2.length()!=0 && y1.length()!=0
                    && y2.length()!=0 && y3.length()!=0 && y4.length()!=0) {
                MainActivity mainActivity=(MainActivity)getActivity();
                if (mainActivity != null) {
                    String a = editDob.getText().toString();
                    String b = d1.getText().toString();

                    String c = m1.getText().toString();
                    String d = m2.getText().toString();

                    String e = y1.getText().toString();
                    String f = y2.getText().toString();
                    String g = y3.getText().toString();
                    String h = y4.getText().toString();

                    SystemConstant.DOB = e+f+g+h+"-"+c+d+"-"+a+b;
                    // mainActivity.RAppRTCProfileFragment();
                    mainActivity.RAppRTCGenderFragment();
                }

            } else {
                Toast.makeText(getActivity(),"Enter Your DOB",Toast.LENGTH_LONG).show();
            }

        });
        ll2.setOnClickListener(v -> {
            saveDataToLocal();
            if ( editDob.length()!=0 && d1.length()!=0 && m1.length()!=0 && m2.length()!=0 && y1.length()!=0
                    && y2.length()!=0 && y3.length()!=0 && y4.length()!=0) {
                MainActivity mainActivity=(MainActivity)getActivity();
                if (mainActivity != null) {
                    String a = editDob.getText().toString();
                    String b = d1.getText().toString();

                    String c = m1.getText().toString();
                    String d = m2.getText().toString();

                    String e = y1.getText().toString();
                    String f = y2.getText().toString();
                    String g = y3.getText().toString();
                    String h = y4.getText().toString();

                    SystemConstant.DOB = e+f+g+h+"-"+c+d+"-"+a+b;
                    // mainActivity.RAppRTCProfileFragment();
                    mainActivity.RAppRTCGenderFragment();
                }

            } else {
                Toast.makeText(getActivity(),"Enter Your DOB",Toast.LENGTH_LONG).show();
            }

        });
        ll3.setOnClickListener(v -> {
            saveDataToLocal();
            if ( editDob.length()!=0 && d1.length()!=0 && m1.length()!=0 && m2.length()!=0 && y1.length()!=0
                    && y2.length()!=0 && y3.length()!=0 && y4.length()!=0) {
                MainActivity mainActivity=(MainActivity)getActivity();
                if (mainActivity != null) {
                    String a = editDob.getText().toString();
                    String b = d1.getText().toString();

                    String c = m1.getText().toString();
                    String d = m2.getText().toString();

                    String e = y1.getText().toString();
                    String f = y2.getText().toString();
                    String g = y3.getText().toString();
                    String h = y4.getText().toString();

                    SystemConstant.DOB = e+f+g+h+"-"+c+d+"-"+a+b;
                    // mainActivity.RAppRTCProfileFragment();
                    mainActivity.RAppRTCGenderFragment();
                }

            } else {
                Toast.makeText(getActivity(),"Enter Your DOB",Toast.LENGTH_LONG).show();
            }

        });


        ImageView back= (ImageView) view.findViewById(R.id.back);
        back.setOnClickListener(v -> {


            MainActivity mainActivity=(MainActivity)getActivity();
            if (mainActivity != null) {
                mainActivity.RAppRTCEmailFragment();
                getActivity().getFragmentManager().popBackStack();
                //   mainActivity.Otp();
            }


        });

        return view;
    }

    private void saveDataToLocal() {
        try {
            if (editDob.getText()!=null || !editDob.getText().toString().equals("")) {
                mLocalSharedPrefData.saveSignUpData(Objects.requireNonNull(getActivity()),editDob.getText().toString(),"userdob");
            } else {
                Toast.makeText(getActivity(),"Enter Valid DOB",Toast.LENGTH_LONG).show();
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

    private void playVideo(View view) {
        videoview = (VideoView) view.findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://"+ Objects.requireNonNull(getActivity()).getPackageName()+"/"+R.raw.birthday);
        videoview.setVideoURI(uri);
        videoview.start();
        videoview.setOnPreparedListener (mp -> mp.setLooping(true));
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
                        mainActivity.RAppRTCEmailFragment();
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
