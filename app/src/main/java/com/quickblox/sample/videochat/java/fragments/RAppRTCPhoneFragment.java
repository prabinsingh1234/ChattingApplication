package com.quickblox.sample.videochat.java.fragments;

import android.app.ProgressDialog;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;

import com.quickblox.sample.videochat.java.R;
import com.quickblox.sample.videochat.java.activity.MainActivity;
import com.quickblox.sample.videochat.java.adapter.SpinnerCustomAdapter;
import com.quickblox.sample.videochat.java.api.APIService;
import com.quickblox.sample.videochat.java.api.JsonUtils;
import com.quickblox.sample.videochat.java.api.RetrofitClient;
import com.quickblox.sample.videochat.java.constants.ApiConstants;
import com.quickblox.sample.videochat.java.constants.SystemConstant;
import com.quickblox.sample.videochat.java.data.LocalSharePrefData;
import com.quickblox.sample.videochat.java.data.Otp;

import org.json.JSONException;
import org.json.JSONObject;


import java.lang.reflect.Field;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*import com.google.android.gms.common.util.JsonUtils;*/

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RAppRTCPhoneFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RAppRTCPhoneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RAppRTCPhoneFragment extends Fragment {
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
    private TextView textView14;
    OkHttpClient okHttpClient;
    ProgressDialog pd ;
    public RAppRTCPhoneFragment() {
        // Required empty public constructor
    }
    public void assignOkhttph(OkHttpClient okHttpClient){
        this.okHttpClient=okHttpClient;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RAppRTCPhoneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RAppRTCPhoneFragment newInstance(String param1, String param2) {
        RAppRTCPhoneFragment fragment = new RAppRTCPhoneFragment();
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
        View view=inflater.inflate(R.layout.fragment_app_rtcphone, container, false);

        pd = new ProgressDialog(requireContext());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        videoview = (VideoView) view.findViewById(R.id.videoView);
        playVideo(view);
        mLocalSharedPrefData=new LocalSharePrefData();

        Spinner spin = (Spinner) view.findViewById(R.id.simpleSpinner);
      //  spin.setOnItemSelectedListener(this);
        int flags[] = {R.mipmap.in,R.mipmap.ad,R.mipmap.ad,R.mipmap.ae, R.mipmap.af, R.mipmap.ag, R.mipmap.ai, R.mipmap.al, R.mipmap.am
                , R.mipmap.an, R.mipmap.ao, R.mipmap.aq, R.mipmap.ar, R.mipmap.as, R.mipmap.at, R.mipmap.au, R.mipmap.aw
                , R.mipmap.ax, R.mipmap.az, R.mipmap.ba, R.mipmap.bb, R.mipmap.bd, R.mipmap.be, R.mipmap.bf, R.mipmap.bg
                , R.mipmap.bh, R.mipmap.bi, R.mipmap.bj, R.mipmap.bl, R.mipmap.bm, R.mipmap.bn, R.mipmap.bo, R.mipmap.bq, R.mipmap.br
                , R.mipmap.bs, R.mipmap.bt};
        SpinnerCustomAdapter customAdapter=new SpinnerCustomAdapter(requireContext(),flags);
        spin.setAdapter(customAdapter);

      //  Spinner spinner = (Spinner) findViewById(R.id.spinner);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spin);

            // Set popupWindow height to 500px
            popupWindow.setHeight(320);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        LinearLayout ll2 = (LinearLayout)view.findViewById(R.id.ll2);
        LinearLayout ll3 = (LinearLayout)view.findViewById(R.id.demo_button1);
        editDob=(EditText) view.findViewById(R.id.phno);
//        textView14=(TextView) view.findViewById(R.id.textView14);
//        textView14.setOnClickListener(v -> {
//            MainActivity mainActivity=(MainActivity)getActivity();
//            if (mainActivity != null) {
//                mainActivity.loginFragment();
//            }
//        });
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

        Button nextStep= (Button) view.findViewById(R.id.demo_button);
        nextStep.setOnClickListener(v -> {
            saveDataToLocal();
            if (editDob.getText()!=null) {
                if ( editDob.getText().length()==10) {

                    getOOtp();

                } else {
                            Toast.makeText(getActivity(),getString(R.string.enter_valid_phone),Toast.LENGTH_LONG).show();
                        }
            } else {
                Toast.makeText(getActivity(),getString(R.string.enter_valid_phone),Toast.LENGTH_LONG).show();

            }

        });
        Button demo_button11= (Button) view.findViewById(R.id.demo_button11);
        demo_button11.setOnClickListener(v -> {
            saveDataToLocal();
            if (editDob.getText()!=null) {
                if ( editDob.getText().length()==10) {

                    getOOtp();

                } else {
                    Toast.makeText(getActivity(),getString(R.string.enter_valid_phone),Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getActivity(),getString(R.string.enter_valid_phone),Toast.LENGTH_LONG).show();

            }

        });

        ll3.setOnClickListener(v -> {
            saveDataToLocal();
            if (editDob.getText()!=null) {
                if ( editDob.getText().length()==10) {

//                    MainActivity mainActivity=(MainActivity)getActivity();
//                    if (mainActivity != null) {
//                        mainActivity.RAppRTCNameFragment();
//                    }

                    getOOtp();

                } else {
                    Toast.makeText(getActivity(),getString(R.string.enter_valid_phone),Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getActivity(),getString(R.string.enter_valid_phone),Toast.LENGTH_LONG).show();

            }

        });
        ll2.setOnClickListener(v -> {
            saveDataToLocal();
            if (editDob.getText()!=null) {
                if ( editDob.getText().length()==10) {

                    getOOtp();

                } else {
                    Toast.makeText(getActivity(),getString(R.string.enter_valid_phone),Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getActivity(),getString(R.string.enter_valid_phone),Toast.LENGTH_LONG).show();

            }

        });

        ImageView back= (ImageView) view.findViewById(R.id.back);
        back.setOnClickListener(v -> {


                    MainActivity mainActivity=(MainActivity)getActivity();
                    if (mainActivity != null) {
                        getActivity().finish();
                       // getActivity().getFragmentManager().popBackStack();
                       //mainActivity.RAppRTCNameFragment();
                        //   mainActivity.Otp();
                    }


        });

        return view;
    }

    private void saveDataToLocal() {
        try {
            if (editDob.getText()!=null) {
                if ( editDob.getText().length()==10) {
                    mLocalSharedPrefData.saveSignUpData(Objects.requireNonNull(getActivity()),editDob.getText().toString(),"userphonenumber");
                } else {
                    Toast.makeText(getActivity(),getString(R.string.enter_valid_phone),Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getActivity(),getString(R.string.enter_valid_phone),Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playVideo(View view) {

        Uri uri = Uri.parse("android.resource://"+ Objects.requireNonNull(getActivity()).getPackageName()+"/"+R.raw.number);
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
        /*try {
            if (context instanceof OnFragmentInteractionListener) {
                mListener = (OnFragmentInteractionListener) context;
            } else {
                throw new RuntimeException(context.toString()
                        + " must implement OnFragmentInteractionListener");
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
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
                        getActivity().finish();

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

    public void getOOtp()
    {

        APIService mAPIService = RetrofitClient.getClient(ApiConstants.BASE_URL).create(APIService.class);

        Otp otp = new Otp();
        String cc = (editDob.getText().toString().trim());
        SystemConstant.PhoneNumber = cc;
        try{
        } catch(NumberFormatException ex){ // handle your exception
        ex.printStackTrace();
        }
        System.out.println(cc);

       otp.setPhone_number(cc);
        JSONObject postData = new JSONObject();
        try {
            postData.put("phone_number", cc);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(cc);

        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),postData.toString());
        pd.setMessage("Please wait...");
        pd.show();

        mAPIService.getOtp(requestBody).enqueue(new Callback<Otp>() {
            @Override
            public void onResponse(Call<Otp> call, Response<Otp> response) {
                pd.dismiss();
                Log.d("debug", "responsecode==>" + response.code());
                int status = 0;
                String myJsonStringSchema = JsonUtils.getJsonString(response.body());
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(myJsonStringSchema);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    status = jsonObject.getInt("status");
                    System.out.println("status..................."+status);
                    String token=jsonObject.getString("token");
                    System.out.println("token..................."+token);
                    token = "token "+token;
                    mLocalSharedPrefData.saveToken(getActivity(),token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }catch (Exception e) {
                    e.printStackTrace();
                }

                if(status == 2)
                {
                    SystemConstant.checkStatus = 2;
                }else
                {
                    SystemConstant.checkStatus = 0;
                }

                if (response.code() == 200) {
                    MainActivity mainActivity=(MainActivity)getActivity();
                    if (mainActivity != null) {
                        mainActivity.Otp();
                    }


                }if (response.code() == 401) {


                }if (response.code() == 400) {

                }
            }

            @Override
            public void onFailure(Call<Otp> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(getActivity(),"connection to server failed",Toast.LENGTH_LONG).show();
                Log.d("debug", "connection to server failed==" + t.getMessage());

            }
        });
    }


    }




