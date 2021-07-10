package com.quickblox.sample.videochat.java.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.quickblox.sample.videochat.java.R;


public class MakeCall extends AppCompatActivity {
    String s = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_call);



        //getting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

//        toolbar.setLogo(R.drawable.back);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        TextView help = (TextView) findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Heloooooooooooooooooooooooooooooooooooooo");

            }
        });
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                System.out.println("back...............");

            }
        });
        Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(s.equals(""))
                {
                    Toast.makeText(MakeCall.this, "Select any one", Toast.LENGTH_SHORT).show();
                }else if(s.equals("selectcontact"))
                {
                    selectContct();
                }else{
                    next();
                }

                //   System.out.println("button...............");

            }
        });
        RelativeLayout relativeLayout2 = (RelativeLayout) findViewById(R.id.relativelayout2);
        RelativeLayout relativeLayout1 = (RelativeLayout) findViewById(R.id.relativelayout1);
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = "selectcontact";
                relativeLayout1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selectedbackground));
                relativeLayout2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));


            }
        });


        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = "enternumber";
                relativeLayout1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                relativeLayout2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selectedbackground));


            }
        });

//        Button nextButton = (Button) findViewById(R.id.next);
//        nextButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                next();
//            }
//        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.call_history:
                        goToCallHistory();
                        //  item.setIcon(R.drawable.callhistory1);
                        // Toast.makeText(MakeCall.this, "Recents", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.phone_book:
                        selectContct();
                        item.setIcon(R.drawable.contact1);
                        // Toast.makeText(MakeCall.this, "Favorites", Toast.LENGTH_SHORT).show();
                        // next();
                        break;
                    case R.id.profile_setting:
                        item.setIcon(R.drawable.setting1);
                       // startActivity(new Intent(MakeCall.this,ProfileSettingsActivity.class));
                        // Toast.makeText(MakeCall.this, "Nearby", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }

    public void goToCallHistory()
    {
        Intent intent = new Intent(this, History.class);
        startActivity(intent);
    }


    public void selectContct()
    {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void next()
    {
        Intent intent = new Intent(this, ContactDetails.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
