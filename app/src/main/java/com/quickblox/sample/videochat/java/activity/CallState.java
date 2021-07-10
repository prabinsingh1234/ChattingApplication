package com.quickblox.sample.videochat.java.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.quickblox.sample.videochat.java.R;
import com.quickblox.sample.videochat.java.fragments.DialFrag;

public class CallState extends AppCompatActivity {
    private Updateable updateable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_state);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        Fragment fragment = new DialFrag();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_frame, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

    }

    public void initializeUpdate(DialFrag dialFrag) {
    }

    /**interface to change refresh the page **/

    public interface Updateable {
        public void update();
    }

    public void initializeUpdate(Updateable updateable){
        this.updateable=updateable;
    }
}