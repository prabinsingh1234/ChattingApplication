package com.quickblox.sample.videochat.java.fragments;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.quickblox.sample.videochat.java.Database.DbHandler;
import com.quickblox.sample.videochat.java.R;
import com.quickblox.sample.videochat.java.activities.CallActivity;
import com.quickblox.sample.videochat.java.activity.HomeActivity;
import com.quickblox.sample.videochat.java.api.APIService;
import com.quickblox.sample.videochat.java.api.ApiRequest;
import com.quickblox.sample.videochat.java.api.JsonUtils;
import com.quickblox.sample.videochat.java.api.RetrofitClient;
import com.quickblox.sample.videochat.java.constants.ApiConstants;
import com.quickblox.sample.videochat.java.constants.SystemConstant;
import com.quickblox.sample.videochat.java.data.LocalSharePrefData;
import com.quickblox.sample.videochat.java.data.Otp;
import com.skyfishjy.library.RippleBackground;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.SystemClock.sleep;
import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class DialFrag extends Fragment implements HomeActivity.Updateable, AdapterView.OnItemSelectedListener {

    private EditText mEtUsername;
    private EditText mEtPhoneNumber;
    private EditText mEtFurtherDetails;

    private Button mBtnCall;
    private TextView mTvConnect;
    private TextView mInterpID;

    private String name;
    private String number;
    private String description;
    private String subject="not mentioned";

    private int INTEPRETOR_ID=-1;


    private static boolean commandLineRun = false;
    private String keyprefRoomServerUrl;

    private SharedPreferences sharedPref;

    private String keyprefResolution;
    private String keyprefFps;
    private String keyprefVideoBitrateType;

    private static final int CONNECTION_REQUEST = 1;

    public static String USER_ACTIVITY="useractivity";

    private String keyprefVideoBitrateValue;

    private String keyprefAudioBitrateType;
    private String keyprefAudioBitrateValue;
    private String keyprefAudioCodec;
    private String keyprefHwCodecAcceleration;
    private String keyprefCaptureToTexture;
    private String keyprefNoAudioProcessingPipeline;
    private String keyprefAecDump;
    private String keyprefOpenSLES;
    private String keyprefDisableBuiltInAec;
    private String keyprefDisableBuiltInAgc;
    private String keyprefDisableBuiltInNs;
    private String keyprefEnableLevelControl;
    private String keyprefDisplayHud;
    private String keyprefTracing;
    private String keyprefRoom;
    private String keyprefRoomList;
    private ArrayList<String> roomList;
    private ArrayAdapter<String> adapter;
    private String keyprefEnableDataChannel;
    private String keyprefOrdered;
    private String keyprefMaxRetransmitTimeMs;
    private String keyprefMaxRetransmits;
    private String keyprefDataProtocol;
    private String keyprefNegotiated;
    private String keyprefDataId;
    private Button mDisconnect;
    private String token;
    private LocalSharePrefData mLocalSharedPrefData;
    HashMap<Integer,String> hm;
    private String langID="0";
    private Spinner spinnerlang;
    int count = 0;
    ImageView interpreterstatus,bckgrnd;
    TextView status1,status2,status3;
    CircleImageView profile,foundDevice,foundDevice1,foundDevice2,foundDevice3,foundDevice4,foundDevice5,profile1;
    RelativeLayout r1,r2,r3;
    RippleBackground rippleBackground1,rippleBackground2;
    Bitmap dbimage;
  //  int Seconds, Minutes, MilliSeconds ;
 //   long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    public DialFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // View view= inflater.inflate(R.layout.fragment_dial, container, false);
        View view= inflater.inflate(R.layout.call_status, container, false);

        //New code added by RituRaj

        interpreterstatus = (ImageView) view.findViewById(R.id.interpreterstatus);
        foundDevice=(CircleImageView) view.findViewById(R.id.foundDevice);
        foundDevice1=(CircleImageView) view.findViewById(R.id.foundDevice1);
        foundDevice2=(CircleImageView) view.findViewById(R.id.foundDevice2);
        foundDevice3=(CircleImageView) view.findViewById(R.id.foundDevice3);
        foundDevice4=(CircleImageView) view.findViewById(R.id.foundDevice4);
        foundDevice5=(CircleImageView) view.findViewById(R.id.foundDevice5);
        profile=(CircleImageView) view.findViewById(R.id.profile);
        profile1=(CircleImageView) view.findViewById(R.id.centerImage1);

        status1 = (TextView) view.findViewById(R.id.title1);
        status2 = (TextView) view.findViewById(R.id.subtitle);
        status3 = (TextView) view.findViewById(R.id.subtitle1);

        TextView name = (TextView) view.findViewById(R.id.name);
        TextView purpose = (TextView) view.findViewById(R.id.purpose);
        name.setText(SystemConstant.CallingName);
        purpose.setText(SystemConstant.callingPurpose);
        ImageView disconnect=(ImageView)  view.findViewById(R.id.icon3);
        disconnect.setOnClickListener(v -> {
            getActivity().finish();
        });
        handler=new Handler();
        //starting timer
      //  StartTime = SystemClock.uptimeMillis();
      //  handler.postDelayed(runnable, 0);

        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                status3.setText("00: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
             //   mTextField.setText("done!");
            }

        }.start();


        TextView help = (TextView) view.findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Heloooooooooooooooooooooooooooooooooooooo");

            }
        });
        ImageView back = (ImageView) view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();

            }
        });

        final RippleBackground rippleBackground=(RippleBackground) view.findViewById(R.id.content);
        rippleBackground.startRippleAnimation();

        DbHandler dbHandler = new DbHandler(requireContext());


        byte[] dbbyte = dbHandler.getImage(SystemConstant.callingPhno);
        dbimage = getImage(dbbyte);
        profile.setImageBitmap(dbimage);

        final Handler handler=new Handler();



         r1 = (RelativeLayout) view.findViewById(R.id.ll1);
         r2 = (RelativeLayout) view.findViewById(R.id.ll2);
         r3 = (RelativeLayout) view.findViewById(R.id.ll3);
         rippleBackground1=(RippleBackground) view.findViewById(R.id.content2);
         rippleBackground2=(RippleBackground) view.findViewById(R.id.content1);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               // getOOtp();
               new Connect().execute();
              //  next();

                // goToPriceFeedBackActivity();
               // connectToRoom("11234", false, false, false, 0);

            }
        },3000);


        //End of new code........................




        try {
            sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
            mLocalSharedPrefData=new LocalSharePrefData();


//
//            keyprefResolution = getString(R.string.pref_resolution_key);
//            keyprefResolution = getString(R.string.pref_resolution_key);
//            keyprefFps = getString(R.string.pref_fps_key);
//            keyprefVideoBitrateType = getString(R.string.pref_maxvideobitrate_key);
//            keyprefVideoBitrateValue = getString(R.string.pref_maxvideobitratevalue_key);
//            keyprefHwCodecAcceleration = getString(R.string.pref_hwcodec_key);
//            keyprefCaptureToTexture = getString(R.string.pref_capturetotexture_key);
//            keyprefAudioBitrateType = getString(R.string.pref_startaudiobitrate_key);
//            keyprefAudioBitrateValue = getString(R.string.pref_startaudiobitratevalue_key);
//            keyprefAudioCodec = getString(R.string.pref_audiocodec_key);
//            keyprefNoAudioProcessingPipeline = getString(R.string.pref_noaudioprocessing_key);
//            keyprefAecDump = getString(R.string.pref_aecdump_key);
//            keyprefOpenSLES = getString(R.string.pref_opensles_key);
//            keyprefDisableBuiltInAec = getString(R.string.pref_disable_built_in_aec_key);
//            keyprefDisableBuiltInAgc = getString(R.string.pref_disable_built_in_agc_key);
//            keyprefDisableBuiltInNs = getString(R.string.pref_disable_built_in_ns_key);
//            keyprefEnableLevelControl = getString(R.string.pref_enable_level_control_key);
//            keyprefDisplayHud = getString(R.string.pref_displayhud_key);
//            keyprefTracing = getString(R.string.pref_tracing_key);
//            keyprefRoomServerUrl = getString(R.string.pref_room_server_url_key);
//            keyprefRoom = getString(R.string.pref_room_key);
//            keyprefRoomList = getString(R.string.pref_room_list_key);
//            keyprefEnableDataChannel = getString(R.string.pref_enable_datachannel_key);
//            keyprefOrdered = getString(R.string.pref_ordered_key);
//            keyprefMaxRetransmitTimeMs = getString(R.string.pref_max_retransmit_time_ms_key);
//            keyprefMaxRetransmits = getString(R.string.pref_max_retransmits_key);
//            keyprefDataProtocol = getString(R.string.pref_data_protocol_key);
//            keyprefNegotiated = getString(R.string.pref_negotiated_key);
//            keyprefDataId = getString(R.string.pref_data_id_key);

        }catch (Exception ex){
            ex.printStackTrace();
        }
       hm=new HashMap<Integer,String>();

       // initializeView(view);
      //  loadLanguages();
      //  addListener();
      //  selectLanguage();

        //communicator();

        return view;

    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    private void foundDevice(int noOfInterpreter){


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        ArrayList<Animator> animatorList=new ArrayList<Animator>();
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(foundDevice, "ScaleX", 0f, 1.2f, 1f);
        animatorList.add(scaleXAnimator);
        ObjectAnimator scaleXAnimator1 = ObjectAnimator.ofFloat(foundDevice1, "ScaleX", 0f, 1.2f, 1f);
        animatorList.add(scaleXAnimator1);
        ObjectAnimator scaleXAnimator2 = ObjectAnimator.ofFloat(foundDevice2, "ScaleX", 0f, 1.2f, 1f);
        animatorList.add(scaleXAnimator2);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(foundDevice3, "ScaleY", 0f, 1.2f, 1f);
        animatorList.add(scaleYAnimator);
        ObjectAnimator scaleYAnimator1 = ObjectAnimator.ofFloat(foundDevice4, "ScaleY", 0f, 1.2f, 1f);
        animatorList.add(scaleYAnimator1);
        ObjectAnimator scaleYAnimator2 = ObjectAnimator.ofFloat(foundDevice5, "ScaleY", 0f, 1.2f, 1f);
        animatorList.add(scaleYAnimator2);
        animatorSet.playTogether(animatorList);
        if(noOfInterpreter == 1){
            foundDevice.setVisibility(View.VISIBLE);
        }  if(noOfInterpreter == 2){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    foundDevice.setVisibility(View.VISIBLE);
                    foundDevice1.setVisibility(View.VISIBLE);
                }
            },100);
        }  if(noOfInterpreter == 3){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    foundDevice.setVisibility(View.VISIBLE);
                    foundDevice1.setVisibility(View.VISIBLE);
                    foundDevice2.setVisibility(View.VISIBLE);
                }
            },200);
        }  if(noOfInterpreter == 4){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    foundDevice.setVisibility(View.VISIBLE);
                    foundDevice1.setVisibility(View.VISIBLE);
                    foundDevice2.setVisibility(View.VISIBLE);
                    foundDevice3.setVisibility(View.VISIBLE);
                }
            },300);
        }  if(noOfInterpreter == 5){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    foundDevice.setVisibility(View.VISIBLE);
                    foundDevice1.setVisibility(View.VISIBLE);
                    foundDevice2.setVisibility(View.VISIBLE);
                    foundDevice3.setVisibility(View.VISIBLE);
                    foundDevice4.setVisibility(View.VISIBLE);
                }
            },400);
        }  if(noOfInterpreter == 6){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    foundDevice.setVisibility(View.VISIBLE);
                    foundDevice1.setVisibility(View.VISIBLE);
                    foundDevice2.setVisibility(View.VISIBLE);
                    foundDevice3.setVisibility(View.VISIBLE);
                    foundDevice5.setVisibility(View.VISIBLE);
                }
            },500);
        }
        if(noOfInterpreter > 6){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    foundDevice.setVisibility(View.VISIBLE);
                    foundDevice1.setVisibility(View.VISIBLE);
                    foundDevice2.setVisibility(View.VISIBLE);
                    foundDevice3.setVisibility(View.VISIBLE);
                    foundDevice5.setVisibility(View.VISIBLE);
                    foundDevice5.setVisibility(View.VISIBLE);
                }
            },500);
        }

        // foundDevice1.setVisibility(View.VISIBLE);
        animatorSet.start();
    }














    @Override
    public void onPause() {
       // this.mTvConnect.setText("");
        super.onPause();
        getActivity().finish();
    }


    private void connectToRoom(String roomId, boolean commandLineRun, boolean loopback,
                               boolean useValuesFromIntent, int runTimeMs) {

//        this.commandLineRun = commandLineRun;
//
//        // roomId is random for loopback.
//        if (loopback) {
//            Random random = new Random();
//            final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_";
//            String id = "";
//            for (int i=0;i<20;i++){
//                id+= AB.charAt(random.nextInt(AB.length()));
//            }
//            roomId = id;
//        }
//
//        String roomUrl = sharedPref.getString(
//                keyprefRoomServerUrl, getString(R.string.pref_room_server_url_default));
//
//        // Video call enabled flag.
//        boolean videoCallEnabled = sharedPrefGetBoolean(R.string.pref_videocall_key,
//                CallActivity.EXTRA_VIDEO_CALL, R.string.pref_videocall_default, useValuesFromIntent);
//
//        // Use screencapture option.
//        boolean useScreencapture = sharedPrefGetBoolean(R.string.pref_screencapture_key,
//                CallActivity.EXTRA_SCREENCAPTURE, R.string.pref_screencapture_default, useValuesFromIntent);
//
//        // Use Camera2 option.
//        boolean useCamera2 = sharedPrefGetBoolean(R.string.pref_camera2_key, CallActivity.EXTRA_CAMERA2,
//                R.string.pref_camera2_default, useValuesFromIntent);
//
//        // Get default codecs.
//        String videoCodec = sharedPrefGetString(R.string.pref_videocodec_key,
//                CallActivity.EXTRA_VIDEOCODEC, R.string.pref_videocodec_default, useValuesFromIntent);
//        String audioCodec = sharedPrefGetString(R.string.pref_audiocodec_key,
//                CallActivity.EXTRA_AUDIOCODEC, R.string.pref_audiocodec_default, useValuesFromIntent);
//
//        // Check HW codec flag.
//        boolean hwCodec = sharedPrefGetBoolean(R.string.pref_hwcodec_key,
//                CallActivity.EXTRA_HWCODEC_ENABLED, R.string.pref_hwcodec_default, useValuesFromIntent);
//
//        // Check Capture to texture.
//        boolean captureToTexture = sharedPrefGetBoolean(R.string.pref_capturetotexture_key,
//                CallActivity.EXTRA_CAPTURETOTEXTURE_ENABLED, R.string.pref_capturetotexture_default,
//                useValuesFromIntent);
//
//        // Check Disable Audio Processing flag.
//        boolean noAudioProcessing = sharedPrefGetBoolean(R.string.pref_noaudioprocessing_key,
//                CallActivity.EXTRA_NOAUDIOPROCESSING_ENABLED, R.string.pref_noaudioprocessing_default,
//                useValuesFromIntent);
//
//        // Check Disable Audio Processing flag.
//        boolean aecDump = sharedPrefGetBoolean(R.string.pref_aecdump_key,
//                CallActivity.EXTRA_AECDUMP_ENABLED, R.string.pref_aecdump_default, useValuesFromIntent);
//
//        // Check OpenSL ES enabled flag.
//        boolean useOpenSLES = sharedPrefGetBoolean(R.string.pref_opensles_key,
//                CallActivity.EXTRA_OPENSLES_ENABLED, R.string.pref_opensles_default, useValuesFromIntent);
//
//        // Check Disable built-in AEC flag.
//        boolean disableBuiltInAEC = sharedPrefGetBoolean(R.string.pref_disable_built_in_aec_key,
//                CallActivity.EXTRA_DISABLE_BUILT_IN_AEC, R.string.pref_disable_built_in_aec_default,
//                useValuesFromIntent);
//
//        // Check Disable built-in AGC flag.
//        boolean disableBuiltInAGC = sharedPrefGetBoolean(R.string.pref_disable_built_in_agc_key,
//                CallActivity.EXTRA_DISABLE_BUILT_IN_AGC, R.string.pref_disable_built_in_agc_default,
//                useValuesFromIntent);
//
//        // Check Disable built-in NS flag.
//        boolean disableBuiltInNS = sharedPrefGetBoolean(R.string.pref_disable_built_in_ns_key,
//                CallActivity.EXTRA_DISABLE_BUILT_IN_NS, R.string.pref_disable_built_in_ns_default,
//                useValuesFromIntent);
//
//        // Check Enable level control.
//        boolean enableLevelControl = sharedPrefGetBoolean(R.string.pref_enable_level_control_key,
//                CallActivity.EXTRA_ENABLE_LEVEL_CONTROL, R.string.pref_enable_level_control_key,
//                useValuesFromIntent);
//
//        // Get video resolution from settings.
//        int videoWidth = 0;
//        int videoHeight = 0;
//        if (useValuesFromIntent) {
//            videoWidth = getActivity().getIntent().getIntExtra(CallActivity.EXTRA_VIDEO_WIDTH, 0);
//            videoHeight =getActivity(). getIntent().getIntExtra(CallActivity.EXTRA_VIDEO_HEIGHT, 0);
//        }
//        if (videoWidth == 0 && videoHeight == 0) {
//            String resolution =
//                    sharedPref.getString(keyprefResolution, getString(R.string.pref_resolution_default));
//            String[] dimensions = resolution.split("[ x]+");
//            if (dimensions.length == 2) {
//                try {
//                    videoWidth = Integer.parseInt(dimensions[0]);
//                    videoHeight = Integer.parseInt(dimensions[1]);
//                } catch (NumberFormatException e) {
//                    videoWidth = 0;
//                    videoHeight = 0;
//                    //  Log.e(TAG, "Wrong video resolution setting: " + resolution);
//                }
//            }
//        }
//
//
//
//
//        // Get camera fps from settings.
//        int cameraFps = 0;
//        if (useValuesFromIntent) {
//            cameraFps = getActivity().getIntent().getIntExtra(CallActivity.EXTRA_VIDEO_FPS, 0);
//        }
//        if (cameraFps == 0) {
//            String fps = sharedPref.getString(keyprefFps, getString(R.string.pref_fps_default));
//            String[] fpsValues = fps.split("[ x]+");
//            if (fpsValues.length == 2) {
//                try {
//                    cameraFps = Integer.parseInt(fpsValues[0]);
//                } catch (NumberFormatException e) {
//                    cameraFps = 0;
//                    //  Log.e(TAG, "Wrong camera fps setting: " + fps);
//                }
//            }
//        }
//
//        // Check capture quality slider flag.
//        boolean captureQualitySlider = sharedPrefGetBoolean(R.string.pref_capturequalityslider_key,
//                CallActivity.EXTRA_VIDEO_CAPTUREQUALITYSLIDER_ENABLED,
//                R.string.pref_capturequalityslider_default, useValuesFromIntent);
//
//        // Get video and audio start bitrate.
//        int videoStartBitrate = 0;
//        if (useValuesFromIntent) {
//            videoStartBitrate = getActivity().getIntent().getIntExtra(CallActivity.EXTRA_VIDEO_BITRATE, 0);
//        }
//        if (videoStartBitrate == 0) {
//            String bitrateTypeDefault = getString(R.string.pref_maxvideobitrate_default);
//            String bitrateType = sharedPref.getString(keyprefVideoBitrateType, bitrateTypeDefault);
//            if (!bitrateType.equals(bitrateTypeDefault)) {
//                String bitrateValue = sharedPref.getString(
//                        keyprefVideoBitrateValue, getString(R.string.pref_maxvideobitratevalue_default));
//                videoStartBitrate = Integer.parseInt(bitrateValue);
//            }
//        }
//
//        int audioStartBitrate = 0;
//        if (useValuesFromIntent) {
//            audioStartBitrate = getActivity().getIntent().getIntExtra(CallActivity.EXTRA_AUDIO_BITRATE, 0);
//        }
//        if (audioStartBitrate == 0) {
//            String bitrateTypeDefault = getString(R.string.pref_startaudiobitrate_default);
//            String bitrateType = sharedPref.getString(keyprefAudioBitrateType, bitrateTypeDefault);
//            if (!bitrateType.equals(bitrateTypeDefault)) {
//                String bitrateValue = sharedPref.getString(
//                        keyprefAudioBitrateValue, getString(R.string.pref_startaudiobitratevalue_default));
//                audioStartBitrate = Integer.parseInt(bitrateValue);
//            }
//        }
//
//        // Check statistics display option.
//        boolean displayHud = sharedPrefGetBoolean(R.string.pref_displayhud_key,
//                CallActivity.EXTRA_DISPLAY_HUD, R.string.pref_displayhud_default, useValuesFromIntent);
//
//        boolean tracing = sharedPrefGetBoolean(R.string.pref_tracing_key, CallActivity.EXTRA_TRACING,
//                R.string.pref_tracing_default, useValuesFromIntent);
//
//        // Get datachannel options
//        boolean dataChannelEnabled = sharedPrefGetBoolean(R.string.pref_enable_datachannel_key,
//                CallActivity.EXTRA_DATA_CHANNEL_ENABLED, R.string.pref_enable_datachannel_default,
//                useValuesFromIntent);
//        boolean ordered = sharedPrefGetBoolean(R.string.pref_ordered_key, CallActivity.EXTRA_ORDERED,
//                R.string.pref_ordered_default, useValuesFromIntent);
//        boolean negotiated = sharedPrefGetBoolean(R.string.pref_negotiated_key,
//                CallActivity.EXTRA_NEGOTIATED, R.string.pref_negotiated_default, useValuesFromIntent);
//        int maxRetrMs = sharedPrefGetInteger(R.string.pref_max_retransmit_time_ms_key,
//                CallActivity.EXTRA_MAX_RETRANSMITS_MS, R.string.pref_max_retransmit_time_ms_default,
//                useValuesFromIntent);
//        int maxRetr =
//                sharedPrefGetInteger(R.string.pref_max_retransmits_key, CallActivity.EXTRA_MAX_RETRANSMITS,
//                        R.string.pref_max_retransmits_default, useValuesFromIntent);
//        int id = sharedPrefGetInteger(R.string.pref_data_id_key, CallActivity.EXTRA_ID,
//                R.string.pref_data_id_default, useValuesFromIntent);
//        String protocol = sharedPrefGetString(R.string.pref_data_protocol_key,
//                CallActivity.EXTRA_PROTOCOL, R.string.pref_data_protocol_default, useValuesFromIntent);
//
//        // Start AppRTCMobile activity.
//        // Log.d(TAG, "Connecting to room " + roomId + " at URL " + roomUrl);
//        if (validateUrl(roomUrl)) {
//            Uri uri = Uri.parse(roomUrl);
//            Intent intent = new Intent(getActivity(), CallActivity.class);
//            intent.setData(uri);
//            intent.putExtra(CallActivity.EXTRA_ROOMID, roomId);
//            intent.putExtra(CallActivity.EXTRA_LOOPBACK, loopback);
//            intent.putExtra(CallActivity.EXTRA_VIDEO_CALL, videoCallEnabled);
//            intent.putExtra(CallActivity.EXTRA_SCREENCAPTURE, useScreencapture);
//            intent.putExtra(CallActivity.EXTRA_CAMERA2, useCamera2);
//            intent.putExtra(CallActivity.EXTRA_VIDEO_WIDTH, videoWidth);
//            intent.putExtra(CallActivity.EXTRA_VIDEO_HEIGHT, videoHeight);
//            intent.putExtra(CallActivity.EXTRA_VIDEO_FPS, cameraFps);
//            intent.putExtra(CallActivity.EXTRA_VIDEO_CAPTUREQUALITYSLIDER_ENABLED, captureQualitySlider);
//            intent.putExtra(CallActivity.EXTRA_VIDEO_BITRATE, videoStartBitrate);
//            intent.putExtra(CallActivity.EXTRA_VIDEOCODEC, videoCodec);
//            intent.putExtra(CallActivity.EXTRA_HWCODEC_ENABLED, hwCodec);
//            intent.putExtra(CallActivity.EXTRA_CAPTURETOTEXTURE_ENABLED, captureToTexture);
//            intent.putExtra(CallActivity.EXTRA_NOAUDIOPROCESSING_ENABLED, noAudioProcessing);
//            intent.putExtra(CallActivity.EXTRA_AECDUMP_ENABLED, aecDump);
//            intent.putExtra(CallActivity.EXTRA_OPENSLES_ENABLED, useOpenSLES);
//            intent.putExtra(CallActivity.EXTRA_DISABLE_BUILT_IN_AEC, disableBuiltInAEC);
//            intent.putExtra(CallActivity.EXTRA_DISABLE_BUILT_IN_AGC, disableBuiltInAGC);
//            intent.putExtra(CallActivity.EXTRA_DISABLE_BUILT_IN_NS, disableBuiltInNS);
//            intent.putExtra(CallActivity.EXTRA_ENABLE_LEVEL_CONTROL, enableLevelControl);
//            intent.putExtra(CallActivity.EXTRA_AUDIO_BITRATE, audioStartBitrate);
//            intent.putExtra(CallActivity.EXTRA_AUDIOCODEC, audioCodec);
//            intent.putExtra(CallActivity.EXTRA_DISPLAY_HUD, displayHud);
//            intent.putExtra(CallActivity.EXTRA_TRACING, tracing);
//            intent.putExtra(CallActivity.EXTRA_CMDLINE, commandLineRun);
//            intent.putExtra(CallActivity.EXTRA_RUNTIME, runTimeMs);
//            intent.putExtra(CallActivity.INTEPRETOR_ID,INTEPRETOR_ID);
//
//            intent.putExtra(CallActivity.EXTRA_DATA_CHANNEL_ENABLED, dataChannelEnabled);
//            intent.putExtra(CallActivity.ACTIVITY,DialFrag.USER_ACTIVITY);
//
//            if (dataChannelEnabled) {
//                intent.putExtra(CallActivity.EXTRA_ORDERED, ordered);
//                intent.putExtra(CallActivity.EXTRA_MAX_RETRANSMITS_MS, maxRetrMs);
//                intent.putExtra(CallActivity.EXTRA_MAX_RETRANSMITS, maxRetr);
//                intent.putExtra(CallActivity.EXTRA_PROTOCOL, protocol);
//                intent.putExtra(CallActivity.EXTRA_NEGOTIATED, negotiated);
//                intent.putExtra(CallActivity.EXTRA_ID, id);
//            }
//
//            if (useValuesFromIntent) {
//                if (getActivity().getIntent().hasExtra(CallActivity.EXTRA_VIDEO_FILE_AS_CAMERA)) {
//                    String videoFileAsCamera =
//                            getActivity().getIntent().getStringExtra(CallActivity.EXTRA_VIDEO_FILE_AS_CAMERA);
//                    intent.putExtra(CallActivity.EXTRA_VIDEO_FILE_AS_CAMERA, videoFileAsCamera);
//                }
//
//                if (getActivity().getIntent().hasExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE)) {
//                    String saveRemoteVideoToFile =
//                            getActivity().getIntent().getStringExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE);
//                    intent.putExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE, saveRemoteVideoToFile);
//                }
//
//                if (getActivity().getIntent().hasExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_WIDTH)) {
//                    int videoOutWidth =
//                            getActivity().getIntent().getIntExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_WIDTH, 0);
//                    intent.putExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_WIDTH, videoOutWidth);
//                }
//
//                if (getActivity().getIntent().hasExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_HEIGHT)) {
//                    int videoOutHeight =
//                            getActivity().getIntent().getIntExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_HEIGHT, 0);
//                    intent.putExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_HEIGHT, videoOutHeight);
//                }
//            }
//
//            startActivityForResult(intent, CONNECTION_REQUEST);
//        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode ==CONNECTION_REQUEST){


        }
    }


    private boolean sharedPrefGetBoolean(
            int attributeId, String intentName, int defaultId, boolean useFromIntent) {
        boolean defaultValue = Boolean.valueOf(getString(defaultId));
        if (useFromIntent) {
            return getActivity().getIntent().getBooleanExtra(intentName, defaultValue);
        } else {
            String attributeName = getString(attributeId);
            return sharedPref.getBoolean(attributeName, defaultValue);
        }
    }

    private String sharedPrefGetString(
            int attributeId, String intentName, int defaultId, boolean useFromIntent) {
        String defaultValue = getString(defaultId);
        if (useFromIntent) {
            String value = getActivity().getIntent().getStringExtra(intentName);
            if (value != null) {
                return value;
            }
            return defaultValue;
        } else {
            String attributeName = getString(attributeId);
            return sharedPref.getString(attributeName, defaultValue);
        }
    }

    private int sharedPrefGetInteger(
            int attributeId, String intentName, int defaultId, boolean useFromIntent) {
        String defaultString = getString(defaultId);
        int defaultValue = Integer.parseInt(defaultString);
        if (useFromIntent) {
            return Objects.requireNonNull(getActivity()).getIntent().getIntExtra(intentName, defaultValue);
        } else {
            String attributeName = getString(attributeId);
            String value="";
            value = sharedPref.getString(attributeName, defaultString);
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                //    Log.e(TAG, "Wrong setting for: " + attributeName + ":" + value);
                return defaultValue;
            }
        }
    }

    @Override
    public void update() {

        mEtUsername.setText("");
        mEtPhoneNumber.setText("");
        mEtFurtherDetails.setText("");
        mTvConnect.setText("");

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        String s = String.valueOf(position);
        langID = hm.get(position);
        Toast.makeText(getActivity(), langID, Toast.LENGTH_LONG).show();
        Log.d("langID", Objects.requireNonNull(hm.get(position)));

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




    private class Connect extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.cookieJar(new MyCookieJar());

            OkHttpClient okHttpClient= builder.build();

            String response=null;

           // String response=null;

            Log.d("langIDSending", langID);

            RequestBody requestBody;
//            if (langID.equals("0")) {
//
//                requestBody = new MultipartBody.Builder()
//                        .setType(MultipartBody.FORM)
//                        .addFormDataPart("number",number)
//                        .addFormDataPart("name",name)
//                        .addFormDataPart("purpose",description)
//                        .build();
//            } else {
//                requestBody = new MultipartBody.Builder()
//                        .setType(MultipartBody.FORM)
//                        .addFormDataPart("number",number)
//                        .addFormDataPart("name",name)
//                        .addFormDataPart("purpose",description)
//                        .addFormDataPart("langID",langID)
//                        .build();
//
//            }
            //  .addFormDataPart("number", SystemConstant.callingPhno)
            String mobileNo =mLocalSharedPrefData.getSignUpData(getActivity(),"userphonenumber");
            requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("number", SystemConstant.callingPhno)
                    .addFormDataPart("name",SystemConstant.CallingName)
                    .addFormDataPart("purpose",SystemConstant.callingPurpose)
                    .addFormDataPart("language",SystemConstant.callingLanguage)
                    .addFormDataPart("callernumber",mobileNo)
                    .build();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                try {
                    String token = new LocalSharePrefData().getToken(DialFrag.this.getActivity());
                    Log.d(TAG, "doInBackground: "+token);

                    response= ApiRequest.createRequest(okHttpClient,ApiConstants.BASE_URL, ApiConstants.START_CALL,requestBody,token);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            }

            return  response;

        }


        @Override
        protected void onPostExecute(String s) {
            //  {"status": 0, "error": "Could not find interpretor"}

            
            if(s!=null){
                Log.d(TAG, "onPostExecute: "+s);

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int status=jsonObject.getInt("status");


                    if(status==1) {

                        INTEPRETOR_ID = jsonObject.getInt("data");
                        int noOfInterpreter = getInterpreterCount();

                        System.out.println("noOfInterpreter..."+noOfInterpreter);






                       // Toast.makeText(getActivity(),"Found Interpreter waiting for accepting ",Toast.LENGTH_LONG).show();
                      //  mBtnCall.setVisibility(View.INVISIBLE);
                       // mDisconnect.setVisibility(View.VISIBLE);


                     //   status3.setText("01:00 min");

                    }else{
                        profile.setImageResource(R.mipmap.icon);
                        status1.setText("Couldn't find interpreter...");
                        status2.setText("Couldn't find interpreter");
                       // Toast.makeText(getActivity(),"Couldn't find interpreter",Toast.LENGTH_LONG).show();

                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }

        }

    }

    private class GetStatusOfInterprator extends  AsyncTask<Void,Void,String> {


        @Override
        protected String doInBackground(Void... voids) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.cookieJar(new MyCookieJar());

            OkHttpClient okHttpClient= builder.build();

            String response=null;
            try {
                String token = new LocalSharePrefData().getToken(Objects.requireNonNull(DialFrag.this.getActivity()));

                return ApiRequest.getRequest(okHttpClient,ApiConstants.BASE_URL, ApiConstants.GET_STATUS+INTEPRETOR_ID,token);

            } catch (Exception ex) {

                ex.printStackTrace();
                return null;
            }
        }



        @Override
        protected void onPostExecute(String s) {

            if(s!=null) {

                try {
                    JSONObject jsonObject = new JSONObject(s);

                    int status=jsonObject.getInt("status");




                    if(status==1){


                      //  {"status": 1, "data": {"number": "98184 47953", "purpose": "test", "name": "Manjil", "room_number": "21102111"}}

                        JSONObject jsonObjectData=jsonObject.getJSONObject("data");

                        String roomNumber=jsonObjectData.getString("room_number");

                        /**checking if room number is not null **/
                        //System.out.println(roo);
                        Log.d("test"+"roomnumber", roomNumber);
                        Log.d("test"+"interpretor id",""+INTEPRETOR_ID);

                        String message=jsonObjectData.getString("status");
                        System.out.println("Status....................."+message);
                       // mTvConnect.setText(message);


                        if(message.equals("INTERPRETOR_RECEIVED")){
                            //new code
                            foundDevice.setVisibility(View.INVISIBLE);
                            foundDevice1.setVisibility(View.INVISIBLE);
                            foundDevice2.setVisibility(View.INVISIBLE);
                            foundDevice3.setVisibility(View.INVISIBLE);
                            foundDevice4.setVisibility(View.INVISIBLE);
                            foundDevice5.setVisibility(View.INVISIBLE);

                            r1.setVisibility(View.GONE);
                            r2.setVisibility(View.GONE);
                            r3.setVisibility(View.VISIBLE);
                            rippleBackground1.startRippleAnimation();
                            interpreterstatus.setImageResource(R.drawable.correct);
                            status1.setText("Interpreter connected");
                            status2.setText("Waiting for  your contact to accept");
                            //   status3.setText("00:30 min");


                            //end new code
                        }else if(message.equals("WAITING_FOR_INTERPRETOR_TO_RECEIVE"))
                        {
                            foundDevice.setVisibility(View.INVISIBLE);
                            foundDevice1.setVisibility(View.INVISIBLE);
                            foundDevice2.setVisibility(View.INVISIBLE);
                            foundDevice3.setVisibility(View.INVISIBLE);
                            foundDevice4.setVisibility(View.INVISIBLE);
                            foundDevice5.setVisibility(View.INVISIBLE);

                            profile.setImageResource(R.drawable.layer_1);
                            status1.setText("Interpreters received");
                            status2.setText("Waiting for interpreter to accept");

                        }else if(message.equals("INTERPRETOR_ENDED"))
                        {
                            foundDevice.setVisibility(View.INVISIBLE);
                            foundDevice1.setVisibility(View.INVISIBLE);
                            foundDevice2.setVisibility(View.INVISIBLE);
                            foundDevice3.setVisibility(View.INVISIBLE);
                            foundDevice4.setVisibility(View.INVISIBLE);
                            foundDevice5.setVisibility(View.INVISIBLE);

                            profile.setImageResource(R.drawable.layer_1);
                            status1.setText("Interpreter Ended...");
                            status2.setText("Interpreter ended the call");
                           // sleep(5000);
                          //  Toast.makeText(getActivity(),"Interpreter ended the call",Toast.LENGTH_LONG).show();
                            getActivity().finish();
                        }

                        Log.i("Dial Farg",jsonObject.toString());

                       // mInterpID.setText(INTEPRETOR_ID);


                        if(!roomNumber.equals("null")){

                            r1.setVisibility(View.VISIBLE);
                            r2.setVisibility(View.GONE);
                            r3.setVisibility(View.GONE);

                            rippleBackground2.startRippleAnimation();
                            profile1.setImageBitmap(dbimage);
                            interpreterstatus.setImageResource(R.drawable.correct);
                            status1.setText("Contact connected");
                            status2.setText("Waiting for  your contact to accept");
                           // status3.setText("00:00 min");
                            sleep(1000);
                          //  handler.removeCallbacks(runnable);
                            Toast.makeText(getActivity(),"Connected"+INTEPRETOR_ID+"ROOM ID"+roomNumber,Toast.LENGTH_LONG).show();
                          //  connectToRoom(jsonObjectData.getString("room_number"), false, false, false, 0);
                           // next();

                        }else{

                            try {


                            }catch (Exception ex){
                                ex.printStackTrace();
                            }
                            new GetStatusOfInterprator().execute();

                        }
                    }else{

                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }else{

            }


        }
    }


    private class GetNameOfInterprator extends  AsyncTask<Void,Void,String> {


        @Override
        protected String doInBackground(Void... voids) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.cookieJar(new MyCookieJar());

            OkHttpClient okHttpClient= builder.build();

            String response=null;
            try {
                String token = new LocalSharePrefData().getToken(Objects.requireNonNull(DialFrag.this.getActivity()));

                return ApiRequest.getRequest(okHttpClient,ApiConstants.BASE_URL, ApiConstants.interpretername+INTEPRETOR_ID,token);

            } catch (Exception ex) {

                ex.printStackTrace();
                return null;
            }
        }



        @Override
        protected void onPostExecute(String s) {

            if(s!=null) {

                try {
                    JSONObject jsonObject = new JSONObject(s);

                    int status=jsonObject.getInt("status");


                  // JSONObject jsonObjectData=jsonObject.getJSONObject("data");
                    System.out.println("status interpretor....................."+status);

                    String message=jsonObject.optString("data");
                    System.out.println("interpretor....................."+message);
                    // mTvConnect.setText(message);

                    Toast.makeText(getActivity(),"interpretor..."+message,Toast.LENGTH_LONG).show();

                    if(status==1){


                        //  {"status": 1, "data": {"number": "98184 47953", "purpose": "test", "name": "Manjil", "room_number": "21102111"}}

//                        JSONObject jsonObjectData=jsonObject.getJSONObject("data");
//
//
//                        String message=jsonObjectData.getString("interpretor");
//                        System.out.println("interpretor....................."+message);
//                        // mTvConnect.setText(message);
//
//                        Toast.makeText(getActivity(),"interpretor..."+message,Toast.LENGTH_LONG).show();


                        Log.i("Dial Farg",jsonObject.toString());

                        // mInterpID.setText(INTEPRETOR_ID);



                    }else{

                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }else{

            }


        }
    }
//    public Runnable runnable = new Runnable() {
//
//        public void run() {
//
//            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
//
//            UpdateTime = TimeBuff + MillisecondTime;
//
//            Seconds = (int) (UpdateTime / 1000);
//
//            Minutes = Seconds / 60;
//
//            Seconds = Seconds % 60;
//
//            MilliSeconds = (int) (UpdateTime % 1000);
//
//            status3.setText("00:"+ Minutes + ":"
//                    + String.format("%02d", Seconds));
//
//            handler.postDelayed(this, 0);
//        }
//
//    };







    private class LanguageDownload extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... voids) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.cookieJar(new MyCookieJar());

            OkHttpClient okHttpClient= builder.build();

            String response=null;


         //   String response=null;





            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                try {
                    response= ApiRequest.getRequest(okHttpClient, ApiConstants.BASE_URL,ApiConstants.GET_LANGUAGE);
                    Log.d("getLang:",response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            //{"status": 1, "message": "Success"}
            Log.d(TAG, "Languages "+ s);

            if(s!=null){

                try {
                    JSONObject jsonObject = new JSONObject(s);

                    //JSONArray languages=jsonObject.getJSONArray("data");
                    int status= jsonObject.getInt("status");

                    //MainActivity mainActivity=(MainActivity)getActivity();


                    if(status==1){
                        /**success login **/
                        //token = "token "+token;
                        mLocalSharedPrefData.setLanguages(Objects.requireNonNull(getActivity()),s);

                        Log.d(TAG, "Languages saved "+ s);

                        //Toast.makeText(getActivity(),"Succesfully logged in ",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getActivity(),"Languages Not Saved",Toast.LENGTH_LONG).show();
                    }

                }catch (Exception ex){
                    Log.d(TAG, "onPostExecute: Language Loading Failed");
                    Toast.makeText(getActivity(),"Language Loading Failed",Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }
            }
        }
    }






    class MyCookieJar implements CookieJar {

        private List<Cookie> cookies;



        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            if (cookies != null)
                return cookies;
            return new ArrayList<Cookie>();

        }

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            this.cookies =  cookies;
        }
    }

    public int getInterpreterCount()
    {

        APIService mAPIService = RetrofitClient.getClient(ApiConstants.BASE_URL).create(APIService.class);

        Otp otp = new Otp();
        //String cc = (editDob.getText().toString().trim());

        JSONObject postData = new JSONObject();
//        try {
//          //  postData.put("phone_number", cc);
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(cc);

        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),postData.toString());


        mAPIService.getInterpreters().enqueue(new Callback<Otp>() {
            @Override
            public void onResponse(Call<Otp> call, Response<Otp> response) {

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
                    count =jsonObject.getInt("count");
                    System.out.println("count..................."+count);

                    if (count == 1){
                        foundDevice(count);
                        profile.setImageBitmap(dbimage);
                        status1.setText("1 interpreters available...");
                        status2.setText("Waiting for intrpreter to receive");
                        status3.setText("01:30 min");
                    }else  if (count == 2){
                        foundDevice(count);
                        profile.setImageBitmap(dbimage);
                        status1.setText("2 interpreters available...");
                        status2.setText("Waiting for intrpreter to receive");
                        status3.setText("01:30 min");
                    }else  if (count == 3){
                        foundDevice(count);
                        profile.setImageBitmap(dbimage);
                        status1.setText("3 interpreters available...");
                        status2.setText("Waiting for intrpreter to receive");
                        status3.setText("01:30 min");
                    }else  if (count == 4){
                        foundDevice(count);
                        profile.setImageBitmap(dbimage);
                        status1.setText("4 interpreters available...");
                        status2.setText("Waiting for intrpreter to receive");
                        status3.setText("01:30 min");
                    }else  if (count == 5){
                        foundDevice(count);
                        profile.setImageBitmap(dbimage);
                        status1.setText("5 interpreters available...");
                        status2.setText("Waiting for intrpreter to receive");
                        status3.setText("01:30 min");
                    }else  if (count == 6){
                        foundDevice(count);
                        profile.setImageBitmap(dbimage);
                        status1.setText("6 interpreters available...");
                        status2.setText("Waiting for intrpreter to receive");
                        status3.setText("01:30 min");
                    }else  if (count >6){
                        foundDevice(count);
                        profile.setImageBitmap(dbimage);
                        status1.setText("6+ interpreters available...");
                        status2.setText("Waiting for intrpreter to receive");
                        status3.setText("01:30 min");
                    }
                    new GetNameOfInterprator().execute();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new GetStatusOfInterprator().execute();
                        }
                    },30000);






                } catch (JSONException e) {
                    e.printStackTrace();
                }catch (Exception e) {
                    e.printStackTrace();
                }



                if (response.code() == 200) {



                }if (response.code() == 401) {


                }if (response.code() == 400) {

                }
            }

            @Override
            public void onFailure(Call<Otp> call, Throwable t) {

                Toast.makeText(getActivity(),"connection to server failed",Toast.LENGTH_LONG).show();
                Log.d("debug", "connection to server failed==" + t.getMessage());

            }
        });
        return count;
    }
    public void next()
    {
        Intent intent = new Intent(getActivity(), CallActivity.class);
        startActivity(intent);
    }

}



