package com.quickblox.sample.videochat.java.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.quickblox.sample.videochat.java.R;
import com.quickblox.sample.videochat.java.constants.SystemConstant;


public class SelectPurpose extends AppCompatActivity {

    LinearLayout family,frnd,delivery,employment,medical,police,govmnt,others;

    String purpose = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_purpose);

        //getting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

//        toolbar.setLogo(R.drawable.back);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        family = (LinearLayout) findViewById(R.id.fmly);
        frnd = (LinearLayout) findViewById(R.id.frnd);
        delivery = (LinearLayout) findViewById(R.id.dlvry);
        employment = (LinearLayout) findViewById(R.id.emp);
        medical = (LinearLayout) findViewById(R.id.mdcal);
        police = (LinearLayout) findViewById(R.id.police);
        govmnt = (LinearLayout) findViewById(R.id.govtsrvc);
        others = (LinearLayout) findViewById(R.id.others);

        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                purpose = "family";
                family.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selectedbackground));
                frnd.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                delivery.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                employment.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                medical.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                police.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                govmnt.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                others.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));


            }
        });
        frnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                purpose = "friends";
                frnd.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selectedbackground));
                family.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                delivery.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                employment.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                medical.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                police.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                govmnt.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                others.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));


            }
        });
        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                purpose = "delivery";
                frnd.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                family.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                delivery.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selectedbackground));
                employment.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                medical.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                police.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                govmnt.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                others.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));


            }
        });
        employment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                purpose = "employment";
                frnd.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                family.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                delivery.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                employment.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selectedbackground));
                medical.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                police.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                govmnt.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                others.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));


            }
        });
        medical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                purpose = "medical";
                frnd.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                family.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                delivery.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                employment.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                medical.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selectedbackground));
                police.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                govmnt.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                others.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));


            }
        });
        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                purpose = "police";
                frnd.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                family.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                delivery.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                employment.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                medical.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                police.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selectedbackground));
                govmnt.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                others.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));


            }
        });
        govmnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                purpose = "goverment";
                frnd.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                family.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                delivery.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                employment.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                medical.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                police.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                govmnt.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selectedbackground));
                others.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));


            }
        });
        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                purpose = "others";
                frnd.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                family.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                delivery.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                employment.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                medical.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                police.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                govmnt.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
                others.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selectedbackground));


            }
        });




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
                if(purpose.equals("others"))
                {
                    next();
                } else if(purpose.equals(""))
                {
                    Toast.makeText(SelectPurpose.this,"Select purpose",Toast.LENGTH_LONG).show();
                }else{
                    SystemConstant.callingPurpose = purpose;
                    nextToCall();
                }

                System.out.println("button...............");

            }
        });
//        RelativeLayout relativeLayout2 = (RelativeLayout) findViewById(R.id.relativelayout2);
//        RelativeLayout relativeLayout1 = (RelativeLayout) findViewById(R.id.relativelayout1);
//        relativeLayout1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                relativeLayout1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selectedbackground));
//                relativeLayout2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
//
//
//            }
//        });
//
//
//        relativeLayout2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                relativeLayout1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_border));
//                relativeLayout2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selectedbackground));
//
//
//            }
//        });

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

                        item.setIcon(R.drawable.contact1);
                        Intent intent = new Intent(SelectPurpose.this, HomeActivity.class);
                        startActivity(intent);
                        // Toast.makeText(MakeCall.this, "Favorites", Toast.LENGTH_SHORT).show();
                        // next();
                        break;
                    case R.id.profile_setting:
                        item.setIcon(R.drawable.setting1);
                       // startActivity(new Intent(SelectPurpose.this,ProfileSettingsActivity.class));
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
    public void next()
    {
        Intent intent = new Intent(this, EnterPurpose.class);
        startActivity(intent);
    }
    public void nextToCall()
    {
        Intent intent = new Intent(this, CallState.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
