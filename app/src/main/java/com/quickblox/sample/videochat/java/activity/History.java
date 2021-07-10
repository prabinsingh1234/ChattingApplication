package com.quickblox.sample.videochat.java.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.quickblox.sample.videochat.java.R;
import com.quickblox.sample.videochat.java.fragments.CallHistoryFragment;


public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        Fragment fragment = new CallHistoryFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_frame, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatb1);
        floatingActionButton.setOnClickListener(v -> {
            startActivity(new Intent(History.this,ContactDetails.class));
        });
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.call_history:

                        break;
                    case R.id.phone_book:
                        selectContct();
                        item.setIcon(R.drawable.contact1);
                        // Toast.makeText(MakeCall.this, "Favorites", Toast.LENGTH_SHORT).show();
                        // next();
                        break;
                    case R.id.profile_setting:
                        item.setIcon(R.drawable.setting1);
                        goToSetting();
                        break;
                }
                return true;
            }
        });

    }
    public void goToSetting()
    {
//        Intent intent = new Intent(this, ProfileSettingsActivity.class);
//        startActivity(intent);
    }


    public void selectContct()
    {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }





}
