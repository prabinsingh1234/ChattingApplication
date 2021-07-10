package com.quickblox.sample.videochat.java.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import com.quickblox.sample.videochat.java.R;
import com.quickblox.sample.videochat.java.activity.InterpreterMainActivity;
import com.quickblox.sample.videochat.java.api.ApiRequest;
import com.quickblox.sample.videochat.java.constants.ApiConstants;
import com.quickblox.sample.videochat.java.data.LocalSharePrefData;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragments extends Fragment  {

    private EditText mEtUsername;
    private EditText mEtPassword;

    private Button mBtnLogin;
    private Button mPay;
    private TextView mNewAccount;


    private LocalSharePrefData mLocalSharedPrefData;

    public static OkHttpClient okHttpClient;



    public LoginFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {
        TextView textView,mEtHelp;
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login_fragments, container, false);
        initializeOkHttp();

        initView(view);
        textView = view.findViewById(R.id.textView3);
        textView.setClickable(true);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='http://18.188.72.6/forgetPassword/'> Forgot/Change Password </a>";
        textView.setText(Html.fromHtml(text));
        textView.setVisibility(View.VISIBLE);


        mEtHelp = view.findViewById(R.id.et_help);
        mEtHelp.setClickable(true);
        mEtHelp.setMovementMethod(LinkMovementMethod.getInstance());
        String helpLink = "<a href='https://www.youtube.com/watch?time_continue=3&v=scSH1P7mHVs'>Need Help?</a>";
        mEtHelp.setText(Html.fromHtml(helpLink));
        mEtHelp.setVisibility(View.VISIBLE);



        addClickListener();
        //loadLanguages();

        return view;
    }



    /**function will initialize okhttp **/
    public void initializeOkHttp(){

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cookieJar(new MyCookieJar());

        okHttpClient= builder.build();

    }

    private void initView(View view){

        mEtUsername=(EditText)view.findViewById(R.id.et_login_username);
        mEtPassword=(EditText)view.findViewById(R.id.et_login_password);

        mBtnLogin=(Button)view.findViewById(R.id.btn_login_login);
        mPay=(Button)view.findViewById(R.id.btn_mPay);
        mNewAccount=(TextView)view.findViewById(R.id.tv_login_signup);

        mLocalSharedPrefData=new LocalSharePrefData();
    }


    private void addClickListener(){


        mBtnLogin.setOnClickListener(view -> {

            if(mEtUsername.getText().toString()!="" && mEtPassword.getText().toString()!=null){
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("QuickBLOX.txt", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("QuickBLOX", mEtUsername.getText().toString());
                editor.commit();

                new LoginCheckAsync().execute(mEtUsername.getText().toString(),mEtPassword.getText().toString());

            }else{
                Toast.makeText(getActivity(),"Please enter username and password",Toast.LENGTH_LONG).show();
            }

        });

        mNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // InterpreterMainActivity mainActivity=(InterpreterMainActivity)getActivity();
              //  mainActivity.RAppRTCPhoneFragment();
            }
        });

        mPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  startPayment();

            }
        });
    }



    private class LoginCheckAsync extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {


            String response=null;

            String userName=strings[0];
            String password=strings[1];

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(
                            "username", userName)
                    .addFormDataPart("password", password)
                    .build();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                response= ApiRequest.createRequest(okHttpClient, ApiConstants.BASE_URL,ApiConstants.GET_TOKEN,requestBody);
            }

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            //{"status": 1, "message": "Success"}
            Log.d(TAG, "Token "+ s);

            if(s!=null){

                try {
                    JSONObject jsonObject = new JSONObject(s);

                    String token=jsonObject.getString("token");
                    int status= jsonObject.getInt("status");
                    boolean verified = jsonObject.getBoolean(("verified"));
                    InterpreterMainActivity mainActivity=(InterpreterMainActivity)getActivity();


                    if(status==1 || status==2){
                        /**success login **/
                        token = "token "+token;
                        mLocalSharedPrefData.saveToken(getActivity(),token);
                        mLocalSharedPrefData.ifUserLoggedIn(getActivity(),1);
                        mLocalSharedPrefData.saveUserType(getActivity(),status);
                        mLocalSharedPrefData.saveVerified(getActivity(),verified);
                        mLocalSharedPrefData.setIsUserRegistered(getActivity(),false);
                        mainActivity.successLogin(status);

                        Toast.makeText(getActivity(),"Succesfully logged in ",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getActivity(),"Please create user account",Toast.LENGTH_LONG).show();
                    }

                }catch (Exception ex){
                    Log.d(TAG, "onPostExecute: Wrong user/password");
                    Toast.makeText(getActivity(),"Wrong phone/password",Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }
            }
        }
    }

   /* class LanguageDownload extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... voids) {


            String response=null;





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

                    MainActivity mainActivity=(MainActivity)getActivity();


                    if(status==1){
                        *//**success login **//*
                        //token = "token "+token;
                        mLocalSharedPrefData.setLanguages(getActivity(),s);

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

    private void loadLanguages() {

        new LanguageDownload().execute();



    }
*/
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
}