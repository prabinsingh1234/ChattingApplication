package com.quickblox.sample.videochat.java.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.quickblox.sample.videochat.java.R;
import com.quickblox.sample.videochat.java.adapter.CallHistoryAdapter;
import com.quickblox.sample.videochat.java.models.CallInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CallHistory extends Fragment implements CallHistoryAdapter.ItemClicked{

    private RecyclerView mRecyclerView;
    private CallHistoryAdapter mHistoryAdapter;

    List<CallInfo> list;

    private CallHistoryInformation mCallHistoryInformation;

    @Override
    public void call(int itemPosition) {

        CallInfo callInfo=list.get(itemPosition);

        mCallHistoryInformation.callHistoryInformation(callInfo);


    }

    public interface CallHistoryInformation {

        void callHistoryInformation(CallInfo callInformation);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallHistoryInformation = (CallHistoryInformation) activity;

        }catch (ClassCastException e){

        }

    }

    private int PERMISSIONS_REQUEST_READ_CONTACT=111;
    public CallHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_call_history, container, false);

        intView(view);

        return view;
    }

    private void intView(View view) {

        mRecyclerView=(RecyclerView)view.findViewById(R.id.frag_contact_recyclerview);



        loadAdapter(fetchContact());
    }


    private List<CallInfo> fetchContact() {

        list = new ArrayList<>();


        return list;


    }

    /**load adapter **/
    public void loadAdapter(List<CallInfo> contacts){

        mHistoryAdapter = new CallHistoryAdapter(contacts,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mHistoryAdapter);

    }


}
