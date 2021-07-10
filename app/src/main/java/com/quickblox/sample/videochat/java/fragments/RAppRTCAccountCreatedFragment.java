package com.quickblox.sample.videochat.java.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;


import com.quickblox.sample.videochat.java.R;
import com.quickblox.sample.videochat.java.activities.LoginActivity;
import com.quickblox.sample.videochat.java.activity.MainActivity;
import com.quickblox.sample.videochat.java.activity.MakeCall;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RAppRTCProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RAppRTCProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RAppRTCAccountCreatedFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VideoView videoview;
    private OnFragmentInteractionListener mListener;

    public RAppRTCAccountCreatedFragment() {
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
        View view=inflater.inflate(R.layout.account_created_fragment, container, false);
        playVideo(view);
        Button nextStep= (Button) view.findViewById(R.id.demo_button);
        nextStep.setOnClickListener(v -> {
            next();
            getActivity().finish();

        });
        LinearLayout ll = (LinearLayout)view.findViewById(R.id.demo_button1);
        ll.setOnClickListener(v -> {
            next();
            getActivity().finish();

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

    private void playVideo(View view) {
        videoview = (VideoView) view.findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://"+ Objects.requireNonNull(getActivity()).getPackageName()+"/"+R.raw.correct);
        videoview.setVideoURI(uri);
        videoview.start();
        videoview.setOnPreparedListener (mp -> mp.setLooping(true));
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
                        mainActivity.RAppRTCProfileFragment();
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

    public void next()
    {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyLogin.txt", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("FirstLogin", true);
        editor.commit();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }
}
