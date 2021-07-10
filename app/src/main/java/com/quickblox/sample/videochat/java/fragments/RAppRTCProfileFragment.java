package com.quickblox.sample.videochat.java.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.quickblox.sample.videochat.java.R;
import com.quickblox.sample.videochat.java.activity.MainActivity;
import com.quickblox.sample.videochat.java.api.APIService;
import com.quickblox.sample.videochat.java.api.ApiRequest;
import com.quickblox.sample.videochat.java.api.RetrofitClient;
import com.quickblox.sample.videochat.java.constants.ApiConstants;
import com.quickblox.sample.videochat.java.constants.SystemConstant;
import com.quickblox.sample.videochat.java.data.LocalSharePrefData;

import org.json.JSONObject;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RAppRTCProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RAppRTCProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RAppRTCProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    final static int GALLERY_REQUEST = 2;
    Bitmap photoFromGallery;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    CircleImageView profileImage;



    private String imagesPath;
    private static String profile_Image_Path, myHostedPath, internalPath, hostedRoot;
    private static File myHostedDirs;
    private String imageName;
    File fileImage;
    File fileFinal;
    ProgressDialog pd ;
    private OnFragmentInteractionListener mListener;
    RetrofitClient retrofit;
    private LocalSharePrefData mLocalSharedPrefData;
    public RAppRTCProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RAppRTCProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RAppRTCProfileFragment newInstance(String param1, String param2) {
        RAppRTCProfileFragment fragment = new RAppRTCProfileFragment();
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
       // return inflater.inflate(R.layout.fragment_rapp_rtcprofile, container, false);


        View view=inflater.inflate(R.layout.fragment_rapp_rtcprofile, container, false);

        pd = new ProgressDialog(requireContext());
        mLocalSharedPrefData=new LocalSharePrefData();

        profileImage = (CircleImageView) view.findViewById(R.id.profile_image);
        profileImage.setOnClickListener(v -> {

            selectImage();
          System.out.println("Hello");

        });

        Button nextStep= (Button) view.findViewById(R.id.demo_button);
        nextStep.setOnClickListener(v -> {
            MainActivity mainActivity=(MainActivity)getActivity();
            new RAppRTCProfileFragment.AsyncTaskRunner().execute();
            // mainActivity.RAppRTCAccountCreated();

        });

        LinearLayout ll = (LinearLayout)view.findViewById(R.id.demo_button1);
        ll.setOnClickListener(v -> {
            MainActivity mainActivity=(MainActivity)getActivity();
            new RAppRTCProfileFragment.AsyncTaskRunner().execute();
                   // mainActivity.RAppRTCAccountCreated();

        });

        TextView skip= (TextView) view.findViewById(R.id.help);
        skip.setOnClickListener(v -> {

            MainActivity mainActivity=(MainActivity)getActivity();
            if (mainActivity != null) {
                new RAppRTCProfileFragment.AsyncTaskRunner().execute();
              //  mainActivity.RAppRTCAccountCreated();
            }
        });

        ImageView back= (ImageView) view.findViewById(R.id.back);
        back.setOnClickListener(v -> {


            MainActivity mainActivity=(MainActivity)getActivity();
            if (mainActivity != null) {
                mainActivity.RAppRTCCityFragment();
                getActivity().getFragmentManager().popBackStack();
                //getActivity().finish();
                //   mainActivity.Otp();
            }


        });

        return view;

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
                        mainActivity.RAppRTCCityFragment();
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


    class AsyncTaskRunner extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {

            pd.setMessage("Creating account, please wait...");
            pd.show();
        }
        @Override
        protected String doInBackground(Void... voids) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.cookieJar(new MyCookieJar());

           // OkHttpClient okHttpClient= builder.build();

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.MINUTES) // connect timeout
                    .writeTimeout(10, TimeUnit.MINUTES) // write timeout
                    .readTimeout(10, TimeUnit.MINUTES) // read timeout
                    .build();


            String response=null;




            //image code
            Bitmap photo = BitmapFactory.decodeResource(getActivity().getResources(),
                    R.drawable.camera);

            BitmapDrawable drawable = (BitmapDrawable) profileImage.getDrawable();

          //  Bitmap converetdImage = getResizedBitmap(drawable.getBitmap(), 500);

            photo = getResizedBitmap(drawable.getBitmap(), 500);


            //create a file to write bitmap data
            File f = new File(requireContext().getCacheDir(), "profile.png");



            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

//Convert bitmap to byte array
          //  Bitmap bitmap = photo;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("phone", SystemConstant.PhoneNumber)
                    .addFormDataPart("name", SystemConstant.NAME)
                    .addFormDataPart("email", SystemConstant.EMAIL)
                    .addFormDataPart("dob", SystemConstant.DOB)
                    .addFormDataPart("gender", SystemConstant.GENDER)
                    .addFormDataPart("sign_language", SystemConstant.SIGNLANGUAGE)
                    .addFormDataPart("type", "U")
                    .addFormDataPart("city", SystemConstant.City)
                    .addFormDataPart("quickblockuserid", "dt"+SystemConstant.PhoneNumber)
                    .addFormDataPart("image", f.getName(), RequestBody.create(MediaType.parse("multipart/form-data"),f))
                    .build();

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {


                response= ApiRequest.createRequest(okHttpClient, ApiConstants.BASE_URL,ApiConstants.CREATE_ACCOUNT,requestBody);


            }

            return  response;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "onPostExecute: "+s);
            pd.dismiss();

            if(s!=null){


                try{
                    JSONObject jsonObject=new JSONObject(s);

                    int status=jsonObject.getInt("status");
                    String token=jsonObject.getString("token");

                    if(status==1){
                      //  Toast.makeText(getActivity(),"User created success",Toast.LENGTH_LONG).show();
                      //  LocalSharePrefData.saveMobileNo(getActivity(),SystemConstant.PhoneNumber);
                      //  Toast.makeText(getActivity(), "mobile no"+SystemConstant.PhoneNumber, Toast.LENGTH_SHORT).show();

                        token = "token "+token;
                        mLocalSharedPrefData.saveToken(getActivity(),token);


                        MainActivity mainActivity=(MainActivity)getActivity();

                         mainActivity.RAppRTCAccountCreated();

                    }
                    else{
                        Toast.makeText(getActivity(),jsonObject.getString("error"),Toast.LENGTH_LONG).show();
                        // Toast.makeText(getActivity(),"User Already Exist",Toast.LENGTH_LONG).show();

                    }

                }catch (Exception ex){

                    Toast.makeText(getActivity(),"Failure creating user"+ex.toString(),Toast.LENGTH_LONG).show();
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
    }


    private void selectImage() {
        final CharSequence[] items = {"Choose from Library", "Cancel"};
        TextView title = new TextView(requireContext());
        title.setText("Add Photo!");
        // title.setTypeface(Typeface.createFromAsset(getAssets(), "font/circularstd_medium.otf"));
        title.setBackgroundColor(Color.parseColor("#0a588f"));
        title.setPadding(10, 15, 15, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(18);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(requireContext(), 0);
        builder.setCustomTitle(title);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Choose from Library")) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                  startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
                }
            }
        });
        builder.show();
    }


    public void sendImage()
    {
        APIService mAPIService = RetrofitClient.getClient(ApiConstants.BASE_URL).create(APIService.class);
        String filePath = Environment.getExternalStorageDirectory().toString() + "/Pictures";
        String fileName = "someFileName.jpg";

        File file1 = new File(filePath,fileName);
       // File file1 = "";// initialize file here

                MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file1.getName(), RequestBody.create(MediaType.parse("image/*"), file1));

        mAPIService.uploadAttachment(filePart);



    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {
            if (data != null) {
                try {
                    photoFromGallery = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), data.getData());
                    profileImage.setImageBitmap(photoFromGallery);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }



}
