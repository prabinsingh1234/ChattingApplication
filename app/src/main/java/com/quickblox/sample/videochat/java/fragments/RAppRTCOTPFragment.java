package com.quickblox.sample.videochat.java.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;

import com.quickblox.sample.videochat.java.R;
import com.quickblox.sample.videochat.java.activities.LoginActivity;
import com.quickblox.sample.videochat.java.activity.MainActivity;
import com.quickblox.sample.videochat.java.activity.MakeCall;
import com.quickblox.sample.videochat.java.api.APIService;
import com.quickblox.sample.videochat.java.api.JsonUtils;
import com.quickblox.sample.videochat.java.api.RetrofitClient;
import com.quickblox.sample.videochat.java.constants.ApiConstants;
import com.quickblox.sample.videochat.java.constants.SystemConstant;
import com.quickblox.sample.videochat.java.data.LocalSharePrefData;
import com.quickblox.sample.videochat.java.data.Otp;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RAppRTCOTPFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RAppRTCOTPFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RAppRTCOTPFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VideoView videoview;
    private OnFragmentInteractionListener mListener;
    private EditText editOtp1,editOtp2,editOtp3,editOtp4;
    private LocalSharePrefData mLocalSharedPrefData;
   // private TextView btnResend;
    private OkHttpClient okHttpClient;
    ProgressDialog pd ;
    public RAppRTCOTPFragment() {
        // Required empty public constructor
    }

    public void assignOkhttph(OkHttpClient okHttpClient){
        this.okHttpClient=okHttpClient;
    }

    @Override
    public void onStart() {


        super.onStart();

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RAppRTCOTPFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RAppRTCOTPFragment newInstance(String param1, String param2) {
        RAppRTCOTPFragment fragment = new RAppRTCOTPFragment();
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
        View view=inflater.inflate(R.layout.fragment_rapp_rtcot, container, false);

        pd = new ProgressDialog(requireContext());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mLocalSharedPrefData=new LocalSharePrefData();

        editOtp1 =(EditText) view.findViewById(R.id.o1);
        editOtp2 =(EditText) view.findViewById(R.id.o2);
        editOtp3 =(EditText) view.findViewById(R.id.o3);
        editOtp4 =(EditText) view.findViewById(R.id.o4);
        LinearLayout ll2 = (LinearLayout)view.findViewById(R.id.ll2);
        LinearLayout ll3 = (LinearLayout)view.findViewById(R.id.demo_button1);

        editOtp1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        editOtp2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        editOtp3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        editOtp4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        editOtp1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(editOtp1.getText().toString().length()==1)     //size as per your requirement
                {
                    editOtp2.requestFocus();
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

        editOtp2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(editOtp2.getText().toString().length()==1)     //size as per your requirement
                {
                    editOtp3.requestFocus();
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
        editOtp3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(editOtp3.getText().toString().length()==1)     //size as per your requirement
                {
                    editOtp4.requestFocus();
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
        editOtp4.addTextChangedListener(new TextWatcher() {

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


        playVideo(view);
        Button nextStep= (Button) view.findViewById(R.id.demo_button);
        nextStep.setOnClickListener(v -> {

            if (editOtp1.getText()!=null && editOtp2.getText()!=null && editOtp3.getText()!=null && editOtp4.getText()!=null) {

                checkOOTp();

                //    new CheckOtpAsync().execute();


            } else {
                Toast.makeText(getActivity(),"Enter Valid OTP",Toast.LENGTH_LONG).show();

            }
        });
        Button demo_button11= (Button) view.findViewById(R.id.demo_button11);
        demo_button11.setOnClickListener(v -> {
            if (editOtp1.getText()!=null && editOtp2.getText()!=null && editOtp3.getText()!=null && editOtp4.getText()!=null) {

                checkOOTp();

                //    new CheckOtpAsync().execute();


            } else {
                Toast.makeText(getActivity(),"Enter Valid OTP",Toast.LENGTH_LONG).show();

            }

        });
        ll2.setOnClickListener(v -> {
            if (editOtp1.getText()!=null && editOtp2.getText()!=null && editOtp3.getText()!=null && editOtp4.getText()!=null) {

                checkOOTp();

                //    new CheckOtpAsync().execute();


            } else {
                Toast.makeText(getActivity(),"Enter Valid OTP",Toast.LENGTH_LONG).show();

            }

        });
        ll3.setOnClickListener(v -> {
            if (editOtp1.getText()!=null && editOtp2.getText()!=null && editOtp3.getText()!=null && editOtp4.getText()!=null) {

                checkOOTp();

                //    new CheckOtpAsync().execute();


            } else {
                Toast.makeText(getActivity(),"Enter Valid OTP",Toast.LENGTH_LONG).show();

            }

        });
        return view;
    }
    private void playVideo(View view) {
        videoview = (VideoView) view.findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://"+ Objects.requireNonNull(getActivity()).getPackageName()+"/"+R.raw.otp);
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

    public void checkOOTp()
    {

        APIService mAPIService = RetrofitClient.getClient(ApiConstants.BASE_URL).create(APIService.class);

        Otp otp = new Otp();

        String a = editOtp1.getText().toString();
        String b = editOtp2.getText().toString();
        String c = editOtp3.getText().toString();
        String d = editOtp4.getText().toString();

        String otp1 = a+b+c+d;

        otp.setOtp(otp1);
        JSONObject postData = new JSONObject();
        try {
            postData.put("otp", otp1);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(otp1);


        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),postData.toString());
        pd.setMessage("Please wait...");
        pd.show();
        mAPIService.checkOTP(requestBody).enqueue(new Callback<Otp>() {
            @Override
            public void onResponse(Call<Otp> call, Response<Otp> response) {
                pd.dismiss();
                Log.d("debug", "responsecode==>" + response.code());
                   String myJsonStringSchema = JsonUtils.getJsonString(response.body());
                   Log.d("debug", "myJsonStringSchema==>" + myJsonStringSchema);
                int status = 0;

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(myJsonStringSchema);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    status = jsonObject.getInt("status");
                    System.out.println("status..................."+status);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (response.code() == 200) {
                    MainActivity mainActivity=(MainActivity)getActivity();
                    if (mainActivity != null) {


                        if(status == 1 && SystemConstant.checkStatus == 2){
                            next();
                        }else if (status ==1 && SystemConstant.checkStatus == 0)
                        {
                            mainActivity.RAppRTCNameFragment();

                        }
                        else{
                            Toast.makeText(getActivity(),"Entered Wrong OTP",Toast.LENGTH_LONG).show();
                        }
                    }


                }if (response.code() == 401) {


                }if (response.code() == 400) {

                }
            }

            @Override
            public void onFailure(Call<Otp> call, Throwable t) {
                pd.dismiss();
                Log.d("debug", "connection to server failed==" + t.getMessage());
                Toast.makeText(getActivity(),"connection to server failed",Toast.LENGTH_LONG).show();
            }
        });
    }



    public void next()
    {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyLogin.txt", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("FirstLogin", true);
        editor.commit();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

}
