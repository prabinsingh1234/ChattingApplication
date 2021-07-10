package com.quickblox.sample.videochat.java.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.quickblox.sample.videochat.java.R;
import com.quickblox.sample.videochat.java.constants.SystemConstant;


public class EnterPurpose extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText purpose;
    int selectionCurrent = 0,selectionCurrent1 =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_purpose);

        //getting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

//        toolbar.setLogo(R.drawable.back);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);



        purpose =(EditText) findViewById(R.id.name);


        purpose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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


                System.out.println("button...............");
                if (purpose.getText()!=null) {
                    if ( purpose.getText().length()> 0) {
                        SystemConstant.callingPurpose = purpose.getText().toString();
                        next();
                        // getOOtp();

                    } else {
                        Toast.makeText(EnterPurpose.this,"Enter purpose",Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(EnterPurpose.this,"Enter purpose",Toast.LENGTH_LONG).show();

                }



            }
        });
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
                        Intent intent = new Intent(EnterPurpose.this, HomeActivity.class);
                        startActivity(intent);
                        // Toast.makeText(MakeCall.this, "Favorites", Toast.LENGTH_SHORT).show();
                        // next();
                        break;
                    case R.id.profile_setting:
                        item.setIcon(R.drawable.setting1);
                      //  startActivity(new Intent(EnterPurpose.this,ProfileSettingsActivity.class));
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
       Intent intent = new Intent(this, CallState.class);
        startActivity(intent);
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        //  Toast.makeText(getApplicationContext(), countryNames[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
