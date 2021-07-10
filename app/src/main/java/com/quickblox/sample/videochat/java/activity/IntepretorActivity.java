package com.quickblox.sample.videochat.java.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.quickblox.sample.videochat.java.App;
import com.quickblox.sample.videochat.java.R;
import com.quickblox.sample.videochat.java.activities.CallActivity;
import com.quickblox.sample.videochat.java.api.ApiRequest;
import com.quickblox.sample.videochat.java.constants.ApiConstants;
import com.quickblox.sample.videochat.java.constants.SystemConstant;
import com.quickblox.sample.videochat.java.data.LocalSharePrefData;
import com.quickblox.sample.videochat.java.fragments.LoginFragments;
import com.quickblox.sample.videochat.java.models.AcceptCallBean;
import com.quickblox.sample.videochat.java.utils.CollectionsUtils;
import com.quickblox.sample.videochat.java.utils.Consts;
import com.quickblox.sample.videochat.java.utils.RingtonePlayer;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class IntepretorActivity extends AppCompatActivity {

    boolean makeCall = false;
    boolean manageIntepeterStatus = false;

    private TextView mTextHaveCall;
    private Button mButtonAcceptCall;
    private Button mButtonStartListening;
    private Button mButtonStopListening;
    private Button mButtonStartCall;
    private Button userunavailable;
    private TextView mTvInterp,tvOpenDialer;

    private Button mButtonUserDetails;

    private int IntepretorId = 12;
    private boolean canMakeCall = false;

    public static String roomID;

    private static boolean commandLineRun = false;
    private String keyprefRoomServerUrl;

    private SharedPreferences sharedPref;

    private String keyprefResolution;
    private String keyprefFps;
    private String keyprefVideoBitrateType;

    private static final int CONNECTION_REQUEST = 1;


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


    /**
     * variables
     **/
    public static final String USER_NAME = "username";
    public static final String USER_PHONENUMBER = "phonenumber";
    public static final String USER_PURPOSE = "purpose";
    private boolean haveCall = false;
    public static String INTEPRETOR_ACTIVITY = "intepretoractivity";

    private LocalSharePrefData mLocalSharedPref;

    private AcceptCallBean acceptCallBean = new AcceptCallBean();
    private Button mStopCall;
    private boolean listening = false;
    private boolean stopAceept = false;
    private Context mContext;
    private RingtonePlayer ringtonePlayer;
//    private String phoneNumberOfDeaf = "";

    @Override
    protected void onStart() {
        super.onStart();

        recordAudioPermission();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intepretor);
        ringtonePlayer = new RingtonePlayer(this, R.raw.swinging);
        mContext=getApplicationContext();
        SystemConstant.home = false;
        mLocalSharedPref = new LocalSharePrefData();

        try {
            sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        initView();

        roomID = generateRoomId();

        permissionCheck();

        addClickListener();


//        if(SystemConstant.startlsn == true)
//        {
//            new StartListening().execute();
//        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout, menu);

        return true;
    }

    public String generateRoomId() {
        /**

         String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
         StringBuilder salt = new StringBuilder();
         Random rnd = new Random();
         while (salt.length() < 18) { // length of the random string.
         int index = (int) (rnd.nextFloat() * SALTCHARS.length());
         salt.append(SALTCHARS.charAt(index));
         }
         String saltStr = salt.toString();
         **/

        Random random = new Random();
        final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_";
        String id = "";
        for (int i=0;i<20;i++){
            id+= AB.charAt(random.nextInt(AB.length()));
        }


        return id;



    }

    private void initView() {

        mButtonAcceptCall = (Button) findViewById(R.id.btn_intereprator_startvideocall);
        mButtonStartCall = (Button) findViewById(R.id.btn_intereprator_startcall);
        mTextHaveCall = (TextView) findViewById(R.id.tv_intepretaor_value);
        mButtonStopListening = (Button) findViewById(R.id.btn_intereprator_stopListening);
        userunavailable = (Button) findViewById(R.id.userunavailable);
        mButtonStartListening = (Button) findViewById(R.id.btn_intereprator_startListening);
        mButtonUserDetails = (Button) findViewById(R.id.btn_intereprator_userDetails);
        mStopCall = (Button) findViewById(R.id.btn_intereprator_endcall);
        mTvInterp = (TextView) findViewById(R.id.tv_intepretpr);
        tvOpenDialer = (TextView) findViewById(R.id.tvOpenDialer);


    }

    public void permissionCheck() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(IntepretorActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 100);

        }

    }


    public void addClickListener() {

        mButtonAcceptCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (canMakeCall) {
                    new VideoCalling().execute();
                } else {
                    Toast.makeText(IntepretorActivity.this, "Please start listening first", Toast.LENGTH_LONG).show();
                }


            }
        });

        userunavailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (canMakeCall) {
                    new UserNotAvailable().execute();
                } else {
                    Toast.makeText(IntepretorActivity.this, "Please start listening first", Toast.LENGTH_LONG).show();
                }


            }
        });

        tvOpenDialer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntepretorActivity.this,DialerActivity2.class);
                if(!App.phoneNumberOfDeaf.isEmpty()){
                    intent.putExtra("phoneNumber",App.phoneNumberOfDeaf);
                }
               startActivity(intent);
            }
        });

        mButtonStopListening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(IntepretorActivity.this, "Stopped Listening", Toast.LENGTH_LONG).show();
                mButtonStartCall.setVisibility(View.INVISIBLE);
                mStopCall.setVisibility(View.INVISIBLE);
                tvOpenDialer.setVisibility(View.GONE);
                mButtonUserDetails.setVisibility(View.INVISIBLE);
                new StopListening().execute();

            }
        });

        mButtonStartCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(IntepretorActivity.this, "Accepting call ....", Toast.LENGTH_LONG).show();
                mButtonStopListening.setVisibility(View.INVISIBLE);
                mButtonStartListening.setVisibility(View.INVISIBLE);
                mStopCall.setVisibility(View.VISIBLE);
                new AcceptCall().execute();
            }
        });

        mButtonStartListening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new StartListening().execute();
                mButtonStartCall.setVisibility(View.INVISIBLE);
                mButtonUserDetails.setVisibility(View.INVISIBLE);


            }
        });

        mStopCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new StopCall().execute();
                mButtonStartListening.setVisibility(View.VISIBLE);
                mButtonStopListening.setVisibility(View.VISIBLE);
                mButtonAcceptCall.setVisibility(View.INVISIBLE);
                mButtonStartCall.setVisibility(View.INVISIBLE);
                mButtonUserDetails.setVisibility(View.INVISIBLE);
                mStopCall.setVisibility(View.INVISIBLE);
                tvOpenDialer.setVisibility(View.GONE);
                mTextHaveCall.setText("No");
                canMakeCall= false;


            }
        });


        mButtonUserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(IntepretorActivity.this, UserDetailsActivity.class);

                intent.putExtra(USER_NAME, acceptCallBean.getName());
                intent.putExtra(USER_PHONENUMBER, acceptCallBean.getNumber());
                intent.putExtra(USER_PURPOSE, acceptCallBean.getPurpose());

                startActivity(intent);


            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        mLocalSharedPref.ifUserLoggedIn(IntepretorActivity.this,0);
        new StopListening().execute();
        mLocalSharedPref.removeToken(IntepretorActivity.this);

        SystemConstant.firstLogin = false;

        Intent intent=new Intent(this,InterpreterMainActivity.class);
        startActivity(intent);




        return false;
    }

    public void checkIfHaveCall() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                mTextHaveCall.setText("No");
                mButtonAcceptCall.setVisibility(View.INVISIBLE);

                new HaveCall().execute();


                //Do something after 100ms
            }
        }, 1 * 1000);


    }


    private boolean sharedPrefGetBoolean(
            int attributeId, String intentName, int defaultId, boolean useFromIntent) {
        boolean defaultValue = Boolean.valueOf(getString(defaultId));
        if (useFromIntent) {
            return getIntent().getBooleanExtra(intentName, defaultValue);
        } else {
            String attributeName = getString(attributeId);
            return sharedPref.getBoolean(attributeName, defaultValue);
        }
    }

    private String sharedPrefGetString(int attributeId, String intentName, int defaultId, boolean useFromIntent) {
        String defaultValue = getString(defaultId);
        if (useFromIntent) {
            String value = getIntent().getStringExtra(intentName);
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
            return getIntent().getIntExtra(intentName, defaultValue);
        } else {
            String attributeName = getString(attributeId);
            String value = sharedPref.getString(attributeName, defaultString);
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                //    Log.e(TAG, "Wrong setting for: " + attributeName + ":" + value);
                return defaultValue;
            }
        }
    }


    /**
     * permission to record audio
     **/
    private void recordAudioPermission() {


        if (ContextCompat.checkSelfPermission(IntepretorActivity.this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(IntepretorActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,}, 121);
        }

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
//            videoWidth = getIntent().getIntExtra(CallActivity.EXTRA_VIDEO_WIDTH, 0);
//            videoHeight = getIntent().getIntExtra(CallActivity.EXTRA_VIDEO_HEIGHT, 0);
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
//        // Get camera fps from settings.
//        int cameraFps = 0;
//        if (useValuesFromIntent) {
//            cameraFps = getIntent().getIntExtra(CallActivity.EXTRA_VIDEO_FPS, 0);
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
//            videoStartBitrate = getIntent().getIntExtra(CallActivity.EXTRA_VIDEO_BITRATE, 0);
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
//            audioStartBitrate = getIntent().getIntExtra(CallActivity.EXTRA_AUDIO_BITRATE, 0);
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
//            Intent intent = new Intent(this, CallActivity.class);
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
//            intent.putExtra(CallActivity.ACTIVITY, INTEPRETOR_ACTIVITY);
//            intent.putExtra(CallActivity.INTEPRETOR_ID, IntepretorId);
//
//            intent.putExtra(CallActivity.EXTRA_DATA_CHANNEL_ENABLED, dataChannelEnabled);
//
//            if (dataChannelEnabled) {
//                intent.putExtra(CallActivity.EXTRA_ORDERED, ordered);
//                intent.putExtra(CallActivity.EXTRA_MAX_RETRANSMITS_MS, maxRetrMs);
//                intent.putExtra(CallActivity.EXTRA_MAX_RETRANSMITS, maxRetr);
//                intent.putExtra(CallActivity.EXTRA_PROTOCOL, protocol);
//                intent.putExtra(CallActivity.EXTRA_NEGOTIATED, negotiated);
//                intent.putExtra(CallActivity.EXTRA_ID, id);
//
//            }
//
//            if (useValuesFromIntent) {
//                if (getIntent().hasExtra(CallActivity.EXTRA_VIDEO_FILE_AS_CAMERA)) {
//                    String videoFileAsCamera =
//                            getIntent().getStringExtra(CallActivity.EXTRA_VIDEO_FILE_AS_CAMERA);
//                    intent.putExtra(CallActivity.EXTRA_VIDEO_FILE_AS_CAMERA, videoFileAsCamera);
//                }
//
//                if (getIntent().hasExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE)) {
//                    String saveRemoteVideoToFile =
//                            getIntent().getStringExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE);
//                    intent.putExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE, saveRemoteVideoToFile);
//                }
//
//                if (getIntent().hasExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_WIDTH)) {
//                    int videoOutWidth =
//                            getIntent().getIntExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_WIDTH, 0);
//                    intent.putExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_WIDTH, videoOutWidth);
//                }
//
//                if (getIntent().hasExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_HEIGHT)) {
//                    int videoOutHeight =
//                            getIntent().getIntExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_HEIGHT, 0);
//                    intent.putExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_HEIGHT, videoOutHeight);
//                }
//            }
//
//            startActivityForResult(intent, CONNECTION_REQUEST);
 //       }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        roomID = generateRoomId();

        makeCall = false;
        canMakeCall = false;

        Toast.makeText(IntepretorActivity.this, "To receive a new call .\nPress \"START LISTENING\"!", Toast.LENGTH_LONG).show();

        mTextHaveCall.setText("No");

        mButtonAcceptCall.setVisibility(View.INVISIBLE);
        mButtonStartCall.setVisibility(View.INVISIBLE);
        mButtonUserDetails.setVisibility(View.INVISIBLE);
        mButtonStartListening.setVisibility(View.VISIBLE);
        mButtonStopListening.setVisibility(View.VISIBLE);
        mStopCall.setVisibility(View.INVISIBLE);

        new StopListening().execute();


    }

//    private boolean validateUrl(String url) {
//        if (URLUtil.isHttpsUrl(url) || URLUtil.isHttpUrl(url)) {
//            return true;
//        }
//
//        new AlertDialog.Builder(this)
//                .setTitle(getText(R.string.invalid_url_title))
//                .setMessage(getString(R.string.invalid_url_text, url))
//                .setCancelable(false)
//                .setNeutralButton(R.string.ok,
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        })
//                .create()
//                .show();
//        return false;
//    }

    private class StartListening extends AsyncTask<String, Void, String> {


        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            try {
                SystemConstant.startlsn = true;
                if( SystemConstant.dateStart.equals("")) {
                    Date d = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    String formattedDate = sdf.format(d);
                    SystemConstant.dateStart = formattedDate;
                    System.out.println("date...." + SystemConstant.dateStart);
                }




                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.MINUTES) // connect timeout
                        .writeTimeout(10, TimeUnit.MINUTES) // write timeout
                        .readTimeout(10, TimeUnit.MINUTES) // read timeout
                        .build();

                RequestBody body = RequestBody.create(null, new byte[]{});
                String token = new LocalSharePrefData().getToken(IntepretorActivity.this);
                return ApiRequest.createRequest(okHttpClient, ApiConstants.BASE_URL, ApiConstants.START_LISTENING, body,token);
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;

            }
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "onPostExecute: "+s);
            //status:1,message:'Success'
            if (s != null) {

                try {

                    JSONObject jsoObject = new JSONObject(s);
                    int status = jsoObject.getInt("status");


                    if (status == 1) {
                        listening=true;
                        canMakeCall=false;
                        Toast.makeText(IntepretorActivity.this, "Started listening", Toast.LENGTH_LONG).show();
                        checkIfHaveCall();

                    }
                } catch (Exception ex) {

                    ex.printStackTrace();
                }

            }
        }
    }


    /**
     * async task for getting otp
     **/
    private class GetOtpAsync extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {


            try {
                String token = new LocalSharePrefData().getToken(IntepretorActivity.this);

                return ApiRequest.getRequest(LoginFragments.okHttpClient, ApiConstants.BASE_URL, ApiConstants.GET_OTP,token);

            } catch (Exception ex) {

                ex.printStackTrace();
                return null;
            }


        }

        @Override
        protected void onPostExecute(String s) {
            //{"status": 1, "message": "sms sent to phone"}

            if (s != null) {

                try {
                    JSONObject jsonObject = new JSONObject(s);

                    int status = jsonObject.getInt("status");

                    if (status == 1) {

                    } else {

                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    private class HaveCall extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {

            try {
                String token = new LocalSharePrefData().getToken(IntepretorActivity.this);
                return ApiRequest.getRequest(LoginFragments.okHttpClient, ApiConstants.BASE_URL, ApiConstants.HAVE_CALL,token);
            } catch (Exception ex) {
                new HaveCall().execute();
                ex.printStackTrace();
                return null;

            }

        }

        @Override
        protected void onPostExecute(String s) {

            if (s != null) {

                try {

                    JSONObject jsonObject = new JSONObject(s);

                    int status = jsonObject.getInt("status");

                    if (status == 1) {
                        stopAceept = true;
                        IntepretorId = jsonObject.getInt("data");

                        mTvInterp.setText("" + IntepretorId);
                       // Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                       // final Ringtone ringtone = RingtoneManager.getRingtone(mContext,uri);
                       // ringtone.play();

//                        final Handler handler = new Handler();
//                        long timeout = 7000;
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (ringtone.isPlaying())
//                                    ringtone.stop();
//                            }
//                        }, timeout);
                        makeCall = true;
                        canMakeCall = true;

//                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
//                        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), notification);
//                        mp.start();
                        final Handler handler = new Handler();
                        long timeout = 100;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
//                                if (mp.isPlaying())
//                                    mp.stop();
                               new AcceptCall().execute();
                            }
                        }, timeout);


                        Toast.makeText(IntepretorActivity.this, "You have a call ..!!", Toast.LENGTH_LONG).show();

                        mButtonStartCall.setVisibility(View.INVISIBLE);
                        mTextHaveCall.setText("Yes");


                    } else {
                        Toast.makeText(IntepretorActivity.this, "Error occured please try again", Toast.LENGTH_LONG).show();
                        mButtonAcceptCall.setVisibility(View.INVISIBLE);
                        mButtonStartCall.setVisibility(View.INVISIBLE);

                        canMakeCall = false;
                        mTextHaveCall.setText("No");
                        makeCall = false;
                    }
                } catch (Exception ex) {

                    if (listening) {
                        mTextHaveCall.setText("No");
                    }
                    Log.d(TAG, "onPostExecute: "+canMakeCall);

                    if (canMakeCall == false && listening) {

                        new HaveCall().execute();
                    }
                }

            }
            //  status:1,message:'Success',data: Boolean
        }
    }


    //connectToRoom(, true, loopback, useValuesFromIntent, runTimeMs);

    /**
     * async task for stop listening
     **/


    private class VideoCalling extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {

            try {
                roomID = generateRoomId();

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("room_no", roomID)
                        .build();
                String token = new LocalSharePrefData().getToken(IntepretorActivity.this);
                return ApiRequest.createRequest(LoginFragments.okHttpClient, ApiConstants.BASE_URL, ApiConstants.VIDEO_CALLING_ROOM + IntepretorId, requestBody,token);
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;

            }
        }

        @Override
        protected void onPostExecute(String s) {

            //status:1,message:'Success'
            try {

                if (s != null) {
                    JSONObject jsonObject = new JSONObject(s);
                    int status = jsonObject.getInt("status");

                  //  Toast.makeText(IntepretorActivity.this, "Videocall Room id" + roomID + " Intepretor id" + IntepretorId, Toast.LENGTH_LONG).show();

                    if (status == 1) {
                        SystemConstant.videoCall = true;

                        //connectToRoom(roomID, false, false, false, 0);

                        Intent intent = new Intent(IntepretorActivity.this, com.quickblox.sample.videochat.java.activities.CallActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(IntepretorActivity.this, "Couldn't connect", Toast.LENGTH_LONG).show();
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }
    private class UserNotAvailable extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {

            try {
                roomID = generateRoomId();

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("contact_unavailable", "True")
                        .build();
                String token = new LocalSharePrefData().getToken(IntepretorActivity.this);
                return ApiRequest.createRequest(LoginFragments.okHttpClient, ApiConstants.BASE_URL, ApiConstants.Contact_UNAVAILABLE + IntepretorId, requestBody,token);
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;

            }
        }

        @Override
        protected void onPostExecute(String s) {

            //status:1,message:'Success'
            try {

                if (s != null) {
                    JSONObject jsonObject = new JSONObject(s);
                    int status = jsonObject.getInt("status");

                    //  Toast.makeText(IntepretorActivity.this, "Videocall Room id" + roomID + " Intepretor id" + IntepretorId, Toast.LENGTH_LONG).show();

                    if (status == 1) {
                        Toast.makeText(IntepretorActivity.this, "Message Sent Sucessfuly", Toast.LENGTH_LONG).show();


                        mButtonStartListening.setVisibility(View.VISIBLE);
                        mButtonStopListening.setVisibility(View.VISIBLE);
                        mButtonAcceptCall.setVisibility(View.INVISIBLE);
                        mButtonStartCall.setVisibility(View.INVISIBLE);
                        mButtonUserDetails.setVisibility(View.INVISIBLE);
                        mStopCall.setVisibility(View.INVISIBLE);

                        userunavailable.setVisibility(View.INVISIBLE);
                        new StartListening().execute();

                    } else {
                        Toast.makeText(IntepretorActivity.this, "Couldn't connect", Toast.LENGTH_LONG).show();
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    private class StopListening extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {

            try {

                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                String formattedDate = sdf.format(d);
                SystemConstant.dateStop = formattedDate;
                System.out.println("stop date"+ SystemConstant.dateStop);

                //HH converts hour in 24 hours format (0-23), day calculation
                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                Date d1 = null;
                Date d2 = null;


                d1 = format.parse(SystemConstant.dateStart);
                d2 = format.parse(SystemConstant.dateStop);

                //in milliseconds
                long diff = d2.getTime() - d1.getTime();

                long diffSeconds = diff / 1000 % 60;
                long diffMinutes = diff / (60 * 1000) % 60;
                long diffHours = diff / (60 * 60 * 1000) % 24;
                long diffDays = diff / (24 * 60 * 60 * 1000);

                System.out.print(diffDays + " days, ");
                System.out.print(diffHours + " hours, ");
                System.out.print(diffMinutes + " minutes, ");
                System.out.print(diffSeconds + " seconds.");

                SystemConstant.dateStart = "";
                SystemConstant.dateStop = "";



                Date date = new Date();
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate1 = sdf1.format(date);
                String senddate = formattedDate1;

                System.out.println("senddate date"+ senddate);

                RequestBody requestBody;
                String token = new LocalSharePrefData().getToken(IntepretorActivity.this);

                requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("date", senddate)
                        .addFormDataPart("hours", String.valueOf(diffHours))
                        .addFormDataPart("minutes", String.valueOf(diffMinutes))
                        .addFormDataPart("seconds", String.valueOf(diffSeconds))
                        .build();


                SystemConstant.startlsn = false;


              //  RequestBody requestBody = RequestBody.create(null, new byte[0]);
              //  String token = new LocalSharePrefData().getToken(IntepretorActivity.this);

                return ApiRequest.createRequest(LoginFragments.okHttpClient, ApiConstants.BASE_URL, ApiConstants.STOP_LISTENING, requestBody,token);
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;

            }
        }

        @Override
        protected void onPostExecute(String s) {


            listening=false;
            mTextHaveCall.setText("");
            if (s==null){
                s= "empty string";
            }

            Log.i("error", s);
        }
    }


    private class AcceptCall extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            try {
                String token = new LocalSharePrefData().getToken(IntepretorActivity.this);

                return ApiRequest.getRequest(LoginFragments.okHttpClient, ApiConstants.BASE_URL, ApiConstants.ACCEPT_CALL + IntepretorId,token);
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;

            }
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "onPostExecute: "+s);

            if (s != null) {

                try {

                    JSONObject jsonObject = new JSONObject(s);

                    int status = jsonObject.getInt("status");

                    if (status == 1) {

                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");


                        SystemConstant.quickbloxId = jsonObject1.getString("caller_number");
                        acceptCallBean.setNumber(jsonObject1.getString("number"));
                        acceptCallBean.setName(jsonObject1.getString("name"));
                        acceptCallBean.setPurpose(jsonObject1.getString("purpose"));
                        //  acceptCallBean.setPurpose(jsonObject1.getString("purpose"));
                        acceptCallBean.setStatus(status);

                        SystemConstant.openingLine = jsonObject1.getString("opening_line");
                        // Toast.makeText(IntepretorActivity.this, jsonObject1.getString("callernumber"),Toast.LENGTH_LONG).show();

                        mButtonUserDetails.setVisibility(View.VISIBLE);
                        mButtonAcceptCall.setVisibility(View.VISIBLE);
                        userunavailable.setVisibility(View.VISIBLE);

                        mButtonStartCall.setVisibility(View.INVISIBLE);
                        mButtonStartListening.setVisibility(View.INVISIBLE);
                        mButtonStopListening.setVisibility(View.INVISIBLE);


                        try {


//                             ringtonePlayer = new RingtonePlayer(IntepretorActivity.this);

//                            ringtonePlayer.play(true);

                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), notification);
                            mp.start();

                            String phoneNumber = jsonObject1.getString("number");

                            AlertDialog.Builder builder = new AlertDialog.Builder(IntepretorActivity.this);
                            ViewGroup viewGroup = findViewById(android.R.id.content);
                            View dialogView = LayoutInflater.from(IntepretorActivity.this).inflate(R.layout.customview, viewGroup, false);
                            builder.setView(dialogView);
                            AlertDialog alertDialog = builder.create();
                            TextView name = (TextView) dialogView.findViewById(R.id.name);
                            TextView numb = (TextView) dialogView.findViewById(R.id.number);
                            TextView purpose = (TextView) dialogView.findViewById(R.id.purpose);
                            TextView opnen = (TextView) dialogView.findViewById(R.id.openigline);
                            try {
                                name.setText("Name: " + jsonObject1.getString("name"));
                                numb.setText("Ph no: " + jsonObject1.getString("number"));
                                App.phoneNumberOfDeaf = jsonObject1.getString("number");
                                tvOpenDialer.setVisibility(View.VISIBLE);
                                purpose.setText("Purpose: " + jsonObject1.getString("purpose"));
                                opnen.setText("Opening Line: " + jsonObject1.getString("opening_line"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            alertDialog.show();
                            manageIntepeterStatus = true;

                            final Handler handler = new Handler();
                            long timeout = 10000;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
//                                    ringtonePlayer.stop();
                                    if (mp.isPlaying())
                                        mp.stop();
                                    SystemConstant.videoCall = true;
                                    alertDialog.dismiss();

                                   /* Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    callIntent.setData(Uri.parse("tel:" + phoneNumber));*/

                                    //change the number
                                    if (ActivityCompat.checkSelfPermission(IntepretorActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        // TODO: Consider calling
                                        //    ActivityCompat#requestPermissions
                                        // here to request the missing permissions, and then overriding
                                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                        //                                          int[] grantResults)
                                        // to handle the case where the user grants the permission. See the documentation
                                        // for ActivityCompat#requestPermissions for more details.


                                        ActivityCompat.requestPermissions(IntepretorActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 100);
                                        return;
                                    }

                                   /* startActivity(callIntent);*/

                                    new VideoCalling().execute();

                                }
                            }, timeout);
                        }catch (Exception e)
                        {

                        }

                        } else{

                            Toast.makeText(IntepretorActivity.this, "call expired", Toast.LENGTH_LONG).show();
                            mButtonUserDetails.setVisibility(View.INVISIBLE);
                            mButtonStopListening.setVisibility(View.VISIBLE);
                            mButtonStartListening.setVisibility(View.VISIBLE);
                            mStopCall.setVisibility(View.INVISIBLE);
                            tvOpenDialer.setVisibility(View.GONE);
                            mButtonStartCall.setVisibility(View.INVISIBLE);
                            mTextHaveCall.setText("");

                        }

                } catch (Exception ex) {

                }
            }
            //{"status": 1, "data": {"number": "9947670277", "purpose": "gdff", "name": "Rahim"}}


            //http://18.188.72.6/acceptCall/18
            ///{"status": 1, "data": {"number": "01234", "purpose": "test", "name": "Ravi"}}


        }
    }


    @Override
    protected void onPause() {
        super.onPause();

        if( manageIntepeterStatus != true)
        {
            new StopListening().execute();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if( manageIntepeterStatus != true) {
            new StartListening().execute();
        }
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if( manageIntepeterStatus != true)
        {
            new StopListening().execute();
        }

     //   new StopListening().execute();

    }


    private class StopCall extends AsyncTask<String, Void, String> {


        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {

            try {
                RequestBody requestBody = RequestBody.create(null, new byte[0]);
                String token = new LocalSharePrefData().getToken(IntepretorActivity.this);
                return ApiRequest.createRequest(LoginFragments.okHttpClient, ApiConstants.BASE_URL, ApiConstants.END_CALL + IntepretorId, requestBody,token);
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;

            }
        }

        @Override
        protected void onPostExecute(String s) {

            //status:1,message:'Success'
            try {
                Log.d(TAG, "onPostExecute: " + s);

                if (s != null) {
                    JSONObject jsonObject = new JSONObject(s);
                    Log.d(TAG, "onPostExecute: " + jsonObject.toString());
                    Toast.makeText(IntepretorActivity.this, jsonObject.toString(), Toast.LENGTH_LONG).show();
                    int status = jsonObject.getInt("status");

                    Toast.makeText(IntepretorActivity.this, "Stopped call", Toast.LENGTH_LONG).show();

                    if (status == 1) {

                        Toast.makeText(IntepretorActivity.this, "Success", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(IntepretorActivity.this, "Failure", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onPostExecute: " + jsonObject.toString());

                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }
}