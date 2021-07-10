package com.quickblox.sample.videochat.java.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import com.quickblox.sample.videochat.java.Database.DbHandler;
import com.quickblox.sample.videochat.java.R;
import com.quickblox.sample.videochat.java.activity.SelectPurpose;
import com.quickblox.sample.videochat.java.adapter.ContactHistoryAdapter;
import com.quickblox.sample.videochat.java.constants.SystemConstant;
import com.quickblox.sample.videochat.java.data.LocalSharePrefData;
import com.quickblox.sample.videochat.java.models.ContactModels;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class CallHistoryFragment extends Fragment implements ContactHistoryAdapter.ContactAdapterInterface,
        LoaderManager.LoaderCallbacks<List<ContactModels>>, SwipeRefreshLayout.OnRefreshListener {

    int PERMISSIONS_REQUEST_READ_CONTACT=1;

    private RecyclerView mRecyclerView;
    private ContactHistoryAdapter mContactAdapter;

    private LocalSharePrefData mLocalSharedPrefData=new LocalSharePrefData();

    private CallInformation mCallInformation;
    List<ContactModels> contacts;

    List<ContactModels> filteredContact;

    public CallHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_call_history, container, false);



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

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);

    }


    @Override
    public void clickedItem(ContactModels contactModels) {

        if(contacts.size()>0){

          //  mCallInformation.callInformation(contactModels);
            DbHandler dbHandler = new DbHandler(requireContext());
            System.out.println("Checking.........."+dbHandler.getPh(contactModels.name));


            DateFormat dateFormat = new SimpleDateFormat("dd:MM HH:mm");
            Date date = new Date();
            String dateformatted = dateFormat.format(date);

            byte[] dbbyte = dbHandler.getImage(dbHandler.getPh(contactModels.name));
           // Bitmap dbimage = getImage(dbbyte);
          //  byte[]abc = getBytes(dbimage);
            if(!(contactModels.name).equals("Username")){
                dbHandler.insertUserDetails(contactModels.name,dbHandler.getPh(contactModels.name),dateformatted,dbbyte);
            }

            SystemConstant.callingPhno = dbHandler.getPh(contactModels.name);
            SystemConstant.CallingName  = contactModels.name;

            next();

        }

    }
    public void next()
    {
        Intent intent = new Intent(requireContext(), SelectPurpose.class);
        startActivity(intent);
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

        FetchContact asyncTaskLoader = new FetchContact(getActivity());

        asyncTaskLoader.forceLoad();

        return asyncTaskLoader;
    }



    @Override
    public void onLoadFinished(@NonNull Loader<List<ContactModels>> loader, List<ContactModels> contactModels) {

        contacts=contactModels;

        mContactAdapter = new ContactHistoryAdapter(contacts,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mContactAdapter);


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
        Context context;
        Bitmap photo;
        public FetchContact(Context context) {
            super(context);
            this.context = context;
            photo = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.contact_pf);
        }

        @Override
        public List<ContactModels> loadInBackground() {

            List<ContactModels> contactModels=new ArrayList<>();


            DbHandler dbHandler = new DbHandler(context);

            ArrayList<HashMap<String, String>> userList = dbHandler.GetUsers();


            for(Map<String, String> map : userList)
            {
                String name = map.get("name");
                String time = map.get("time");

                System.out.println(name);
                System.out.println(time);
                System.out.println(map.get("phone"));
                byte[] dbbyte = dbHandler.getImage(map.get("phone"));
                Bitmap dbimage = getImage(dbbyte);
              //  System.out.println(dbimage);


                if(!(map.get("name")).equals("Username")) {
                    contactModels.add(new ContactModels("Last call at " + time, name, dbimage));
                }else{

                }
            }




            return contactModels;
        }

        @Override
        public void deliverResult(List<ContactModels> data) {

            super.deliverResult(data);
        }
    }
    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public interface CallInformation {

        void callInformation(ContactModels callInformation);

    }

}
