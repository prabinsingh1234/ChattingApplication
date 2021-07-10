package com.quickblox.sample.videochat.java.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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
 * {@link RAppRTCNameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RAppRTCNameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RAppRTCNameFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private LocalSharePrefData mLocalSharedPrefData;
    private EditText editDob;
    private VideoView videoview;

    public RAppRTCNameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RAppRTCNameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RAppRTCNameFragment newInstance(String param1, String param2) {
        RAppRTCNameFragment fragment = new RAppRTCNameFragment();
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
        View view=inflater.inflate(R.layout.fragment_app_rtcname, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        playVideo(view);
        mLocalSharedPrefData=new LocalSharePrefData();

        editDob=(EditText) view.findViewById(R.id.editText);
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

//        editDob.addTextChangedListener(new TextWatcher() {
//
//            public void onTextChanged(CharSequence s, int start,int before, int count)
//            {
//                // videoview.setVisibility(View.GONE);
//                // TODO Auto-generated method stub
//                if(editDob.getText().toString().length()==10)     //size as per your requirement
//                {
//
//                }
//            }
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//                videoview.setVisibility(View.GONE);
//
//                ll2.setVisibility(View.VISIBLE);
//                ll3.setVisibility(View.GONE);
//                // TODO Auto-generated method stub
//
//
//            }
//
//            public void afterTextChanged(Editable s) {
//                // TODO Auto-generated method stub
//                //  videoview.setVisibility(View.VISIBLE);
//            }
//
//        });

        Button nextStep= (Button) view.findViewById(R.id.demo_button);
        nextStep.setOnClickListener(v -> {
            saveDataToLocal();
            if (editDob.getText()!=null) {
                if ( editDob.getText().length()!=0) {
                    MainActivity mainActivity=(MainActivity)getActivity();
                    if (mainActivity != null) {
                        SystemConstant.NAME =  editDob.getText().toString();
                        mainActivity.RAppRTCEmailFragment();
                    }

                } else {
                    Toast.makeText(getActivity(),"Enter Valid Name",Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getActivity(),"Enter Valid Name",Toast.LENGTH_LONG).show();

            }
        });

        Button demo_button11= (Button) view.findViewById(R.id.demo_button11);
        demo_button11.setOnClickListener(v -> {
            saveDataToLocal();
            if (editDob.getText()!=null) {
                if ( editDob.getText().length()!=0) {
                    MainActivity mainActivity=(MainActivity)getActivity();
                    if (mainActivity != null) {
                        SystemConstant.NAME =  editDob.getText().toString();
                        mainActivity.RAppRTCEmailFragment();
                    }

                } else {
                    Toast.makeText(getActivity(),"Enter Valid Name",Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getActivity(),"Enter Valid Name",Toast.LENGTH_LONG).show();

            }
        });
        ll2.setOnClickListener(v -> {
            saveDataToLocal();
            if (editDob.getText()!=null) {
                if ( editDob.getText().length()!=0) {
                    MainActivity mainActivity=(MainActivity)getActivity();
                    if (mainActivity != null) {
                        SystemConstant.NAME =  editDob.getText().toString();
                        mainActivity.RAppRTCEmailFragment();
                    }

                } else {
                    Toast.makeText(getActivity(),"Enter Valid Name",Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getActivity(),"Enter Valid Name",Toast.LENGTH_LONG).show();

            }
        });

        ll3.setOnClickListener(v -> {
            saveDataToLocal();
            if (editDob.getText()!=null) {
                if ( editDob.getText().length()!=0) {
                    MainActivity mainActivity=(MainActivity)getActivity();
                    if (mainActivity != null) {
                        SystemConstant.NAME =  editDob.getText().toString();
                        mainActivity.RAppRTCEmailFragment();
                    }

                } else {
                    Toast.makeText(getActivity(),"Enter Valid Name",Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getActivity(),"Enter Valid Name",Toast.LENGTH_LONG).show();

            }
        });

        ImageView back= (ImageView) view.findViewById(R.id.back);
        back.setOnClickListener(v -> {


            MainActivity mainActivity=(MainActivity)getActivity();
            if (mainActivity != null) {
                mainActivity.RAppRTCPhoneFragment();
                getActivity().getFragmentManager().popBackStack();
                //   mainActivity.Otp();
            }


        });

        return view;
    }

    private void saveDataToLocal() {
        try {
            if (editDob.getText()!=null) {
                if ( editDob.getText().length()!=0) {
                mLocalSharedPrefData.saveSignUpData(Objects.requireNonNull(getActivity()),editDob.getText().toString(),"username");
            } else {
                Toast.makeText(getActivity(),"Enter Valid Name",Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getActivity(),"Enter Valid Name",Toast.LENGTH_LONG).show();

        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playVideo(View view) {
        videoview = (VideoView) view.findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://"+ Objects.requireNonNull(getActivity()).getPackageName()+"/"+R.raw.name);
        videoview.setVideoURI(uri);
        videoview.start();
        videoview.setOnPreparedListener (mp -> mp.setLooping(true));
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
                        mainActivity.RAppRTCPhoneFragment();
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
