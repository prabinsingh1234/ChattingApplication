package com.quickblox.sample.videochat.java.fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.quickblox.sample.videochat.java.R;
import com.quickblox.sample.videochat.java.activity.ContactDetails;
import com.quickblox.sample.videochat.java.adapter.ContactAdapter;
import com.quickblox.sample.videochat.java.api.ApiRequest;
import com.quickblox.sample.videochat.java.constants.ApiConstants;
import com.quickblox.sample.videochat.java.data.LocalSharePrefData;
import com.quickblox.sample.videochat.java.models.ContactModels;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFrag extends Fragment implements ContactAdapter.ContactAdapterInterface,
        LoaderManager.LoaderCallbacks<List<ContactModels>>, SwipeRefreshLayout.OnRefreshListener {

    int PERMISSIONS_REQUEST_READ_CONTACT=1;

    public static OkHttpClient okHttpClient;
    private RecyclerView mRecyclerView;
    private ContactAdapter mContactAdapter;

    private LocalSharePrefData mLocalSharedPrefData=new LocalSharePrefData();

    private CallInformation mCallInformation;
    List<ContactModels> contacts;

    List<ContactModels> filteredContact;

    private EditText mEditText;

    String[] fieldListProjection = {
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER,
            ContactsContract.Contacts.HAS_PHONE_NUMBER
    };


    ProgressDialog pd ;

    // The column index for the _ID column
    private static final int CONTACT_ID_INDEX = 0;
    // The column index for the CONTACT_KEY column
    private static final int CONTACT_KEY_INDEX = 1;


    // Defines the text expression
    @SuppressLint("InlinedApi")
    private static final String SELECTION =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?" :
                    ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?";
    // Defines a variable for the search string
    private String searchString;
    // Defines the array to hold values that replace the ?
    private String[] selectionArgs = { searchString };
    private String deviceToken="";
    private String token="";


    public ContactsFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_contact, container, false);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cookieJar(new MyCookieJar());

        okHttpClient= builder.build();

        pd  = new ProgressDialog(requireContext());

        mEditText=(EditText)view.findViewById(R.id.editTextSearch);

        FloatingActionButton floatingActionButton = (FloatingActionButton)view.findViewById(R.id.floatb1);
        floatingActionButton.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), ContactDetails.class));
        });

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(Objects.requireNonNull(getActivity()),  new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        String mToken = instanceIdResult.getToken();
                        Log.d("Token1",mToken);
                        //d_PQW8xqRY-in8iI-iNt8O:APA91bHfojQZKfgLPJPWnHiEI3P-aGFt3LMLHjqvTcdqu3hPump8GPBoxCqBjcq1Qi0AcoMu7X4kHuk0zQBJEB8vTzPFzmyQQqL-jSWFFrdlKry-9HzRXK8a29l-rzc821w-fPzEZYvK
                    }
                });

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                getFiltered(editable.toString());
            }
        });

        mRecyclerView=(RecyclerView)view.findViewById(R.id.frag_contact_recyclerview);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {

                        return true;
                    }
                }
                return false;
            }
        });

        isDeviceRegistered();
        return view;
    }

    private void isDeviceRegistered() {
        new DeviceRegisterationCheck().execute();
    }

    public void getFiltered(String start) {
        start = start.toLowerCase();
        filteredContact= new ArrayList<>();

        if(contacts !=null){
            for (ContactModels cm: contacts ){
                if (cm.name.toLowerCase().startsWith(start)){
                    filteredContact.add(cm);

                }
            }

            mContactAdapter.filter(filteredContact);
        }


    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && getActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACT);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {

            getLoaderManager().initLoader(0, null, this);
        }

    }


    /**function will show the list of contacts **/

    public void loadAdapter(List<ContactModels> contacts){

        mContactAdapter = new ContactAdapter(contacts,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mContactAdapter);

    }


    public List<ContactModels> getContacts(Context ctx) {

      String[] PROJECTION = new String[] {
                 ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        List<ContactModels> list = new ArrayList<>();
        ContentResolver contentResolver = ctx.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, PROJECTION, null, null, null);
        if (cursor.getCount() > 0) {

            try {
                final int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                 int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                String name, number;
                while (cursor.moveToNext()) {

                    name = cursor.getString(nameIndex);
                    number = cursor.getString(numberIndex);
                 //   list.add(new ContactModels(name,number));

                }
            } finally {
                cursor.close();
            }
        }
        return list;
    }


    @Override
    public void clickedItem(ContactModels contactModels) {

        if(contacts.size()>0){

            mCallInformation.callInformation(contactModels);

        }

    }


    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mCallInformation = (CallInformation) activity;
        }catch (ClassCastException e){

        }

    }

    @NonNull
    @Override
    public Loader<List<ContactModels>> onCreateLoader(int i, @Nullable Bundle bundle) {

        pd.setMessage("Please wait...");
        pd.show();
        FetchContact asyncTaskLoader = new FetchContact(getActivity());

        asyncTaskLoader.forceLoad();

        return asyncTaskLoader;

    }



    @Override
    public void onLoadFinished(@NonNull Loader<List<ContactModels>> loader, List<ContactModels> contactModels) {

        contacts=contactModels;

        mContactAdapter = new ContactAdapter(contacts,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mContactAdapter);

        pd.dismiss();


    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<ContactModels>> loader) {

    }

    @Override
    public void onRefresh() {

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

                    Log.d("onBackPressedresume","true");

                    getActivity().finish();

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





    /**async task for loader **/
    private static class FetchContact extends AsyncTaskLoader<List<ContactModels>> {
        Bitmap photo ;
        Context context;
        public FetchContact(Context context) {
            super(context);
            this.context = context;
        }

        @Override
        public List<ContactModels> loadInBackground() {

            List<ContactModels> contactModels=new ArrayList<>();


            Cursor phones = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
            while (phones.moveToNext()) {
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String image_uri = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));


//                String UriContactNumber = Uri.encode(phoneNumber);
//                long phoneContactID = new Random().nextInt();
//                Cursor contactLookupCursor = context.getContentResolver().query(Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, UriContactNumber),
//                        new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID}, null, null, null);
//                while (contactLookupCursor.moveToNext()) {
//                    phoneContactID = contactLookupCursor.getLong(contactLookupCursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup._ID));
//                }
//                contactLookupCursor.close();

                //  System.out.println("phoneContactID............"+phoneContactID);



//                try {
//
//                        InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(),
//                                ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, phoneContactID));
//
//                        if (inputStream != null) {
//                            photo = BitmapFactory.decodeStream(inputStream);
//                        }
//
//                    if (inputStream != null) {
//                        inputStream.close();
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                photo = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.contact_pf);
                if (image_uri != null) {

                    try {
                        photo = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(image_uri));

                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                contactModels.add(new ContactModels(phoneNumber, name, photo));
            }
            phones.close();


            return contactModels;
        }

        @Override
        public void deliverResult(List<ContactModels> data) {

            super.deliverResult(data);
        }
    }





    public interface CallInformation {

        void callInformation(ContactModels callInformation);

    }


    @SuppressLint("StaticFieldLeak")
    private class DeviceRegisterationCheck extends AsyncTask<Void,Void,String> {
        @Override
        protected String doInBackground(Void... voids) {

            String response=null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                String deviceToken= getDeviceToken();
                try {
                    String token = new LocalSharePrefData().getToken(Objects.requireNonNull(getActivity()));

                    response= ApiRequest.getRequest(okHttpClient, ApiConstants.BASE_URL,ApiConstants.DEVICE_CHECK+deviceToken,token);
                    Log.d("deviceCheck:",response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return response;


        }



        @Override
        protected void onPostExecute(String s) {
            //{"status": 1, "message": "Success"}
            Log.d(TAG, "IsDeviceregistered " + s);


            if(s!=null){

                try {
                    JSONObject jsonObject = new JSONObject(s);

                    //JSONArray languages=jsonObject.getJSONArray("data");
                    int status= jsonObject.getInt("status");
                    boolean data= jsonObject.getBoolean("data");
                    Log.d(TAG, "status "+ status);

                    //MainActivity mainActivity=(MainActivity)getActivity();


                    if(status==1&& !data){
                        /**success login **/
                        //token = "token "+token;
                        deviceToken=getDeviceToken();
                        registerDeviceToken();
                        mLocalSharedPrefData.setIsDeviceRegistered(Objects.requireNonNull(getActivity()),true);

                        Log.d(TAG, "IsDeviceregistered saved "+ s);

                        //Toast.makeText(getActivity(),"Succesfully logged in ",Toast.LENGTH_LONG).show();
                    }else{

                    }

                }catch (Exception ex){
                    Log.d(TAG, "onPostExecute: Language Loading Failed");
                    Toast.makeText(getActivity(),"Language Loading Failed",Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }
            }
        }



    }

    private void registerDeviceToken() {
        new RegisterDeviceToken().execute();
    }

    private String getDeviceToken() {
        final String[] mToken = new String[1];

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(Objects.requireNonNull(getActivity()),  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                mToken[0] = instanceIdResult.getToken();
                Log.d("Token1", mToken[0]);
                mLocalSharedPrefData.setDeviceToken(Objects.requireNonNull(ContactsFrag.this.getActivity()),mToken[0]);
                //d_PQW8xqRY-in8iI-iNt8O:APA91bHfojQZKfgLPJPWnHiEI3P-aGFt3LMLHjqvTcdqu3hPump8GPBoxCqBjcq1Qi0AcoMu7X4kHuk0zQBJEB8vTzPFzmyQQqL-jSWFFrdlKry-9HzRXK8a29l-rzc821w-fPzEZYvK
            }
        });
        return mToken[0];
    }

    private class RegisterDeviceToken extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... voids) {

            String response = null;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                try {
                    token = new LocalSharePrefData().getToken(Objects.requireNonNull(ContactsFrag.this.getActivity()));
                    deviceToken=mLocalSharedPrefData.getDeviceToken(Objects.requireNonNull(getActivity()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (deviceToken.equals("")){
                    deviceToken=getDeviceToken();
                }

                try {
                    response= ApiRequest.createRequest(okHttpClient, ApiConstants.BASE_URL,ApiConstants.DEVICE_REGISTRATION,deviceToken,token);
                    Log.d("responseDeviceToken:",response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {


            String message=null;
            //  {"status": 1, "message": "Success"}
            if (s != null) {

                try {
                    JSONObject jsonObject = new JSONObject(s);

                    int status = jsonObject.getInt("status");


                    if (status == 1) {
                        Log.d("status1",s);
                        mLocalSharedPrefData.setIsDeviceRegistered(Objects.requireNonNull(getActivity()),true);

                    } if (status==0){
                        String error=jsonObject.getString("error");
                        Log.d("status0",s);

                        mLocalSharedPrefData.setIsDeviceRegistered(Objects.requireNonNull(getActivity()),true);
                    }
                    else {
                        Log.d("statuselse",s);
                    }

                } catch (Exception ex) {
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
}
