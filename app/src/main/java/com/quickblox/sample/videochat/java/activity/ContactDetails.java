package com.quickblox.sample.videochat.java.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.quickblox.sample.videochat.java.Database.DbHandler;
import com.quickblox.sample.videochat.java.R;
import com.quickblox.sample.videochat.java.adapter.SpinnerCustomAdapter;
import com.quickblox.sample.videochat.java.constants.SystemConstant;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ContactDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText phno,name;
    LinearLayout ll1,ll2,ll3;
    int selectionCurrent = 0,selectionCurrent1 =0;
    String ph,uname,language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_details);

        //getting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

         ll1 = (LinearLayout) findViewById(R.id.llphno);
         ll3 = (LinearLayout) findViewById(R.id.llphno1);
         ll2 = (LinearLayout) findViewById(R.id.llname);

//        toolbar.setLogo(R.drawable.back);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = (Spinner) findViewById(R.id.simpleSpinner);
        //  spin.setOnItemSelectedListener(this);
        int flags[] = {R.mipmap.in,R.mipmap.ad,R.mipmap.ae, R.mipmap.af, R.mipmap.ag, R.mipmap.ai, R.mipmap.al, R.mipmap.am
                , R.mipmap.an, R.mipmap.ao, R.mipmap.aq, R.mipmap.ar, R.mipmap.as, R.mipmap.at, R.mipmap.au, R.mipmap.aw
                , R.mipmap.ax, R.mipmap.az, R.mipmap.ba, R.mipmap.bb, R.mipmap.bd, R.mipmap.be, R.mipmap.bf, R.mipmap.bg
                , R.mipmap.bh, R.mipmap.bi, R.mipmap.bj, R.mipmap.bl, R.mipmap.bm, R.mipmap.bn, R.mipmap.bo, R.mipmap.bq, R.mipmap.br
                , R.mipmap.bs, R.mipmap.bt};
        SpinnerCustomAdapter customAdapter=new SpinnerCustomAdapter(this,flags);
        spin.setAdapter(customAdapter);

        //  Spinner spinner = (Spinner) findViewById(R.id.spinner);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spin);

            // Set popupWindow height to 500px
            popupWindow.setHeight(320);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        selectionCurrent1 = spin.getSelectedItemPosition();

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (selectionCurrent1 != position){
                    // Your code here

                }
                selectionCurrent1= position;
                System.out.println( selectionCurrent1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner spinner = (Spinner)findViewById(R.id.languagespinner);
        String text = spinner.getSelectedItem().toString();
        System.out.println( text);

        selectionCurrent = spinner.getSelectedItemPosition();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (selectionCurrent != position){
                    // Your code here

                }
                selectionCurrent= position;
                System.out.println( selectionCurrent);

                language = (String) parentView.getItemAtPosition(position);
                // Notify the selected item text
             //   Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        phno =(EditText) findViewById(R.id.phno);

        ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phno.requestFocus();
                ll1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onclick_selectcontact_background));
                ll2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selectcontact_background));

                System.out.println("Heloooooooooooooooooooooooooooooooooooooo");

            }
        });

        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phno.requestFocus();
                ll1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onclick_selectcontact_background));
                ll2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selectcontact_background));

                System.out.println("Heloooooooooooooooooooooooooooooooooooooo");

            }
        });

        phno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phno.requestFocus();
                ll1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onclick_selectcontact_background));
                ll2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selectcontact_background));

            }
        });


        name =(EditText) findViewById(R.id.name);

        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.requestFocus();
                ll2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onclick_selectcontact_background));
                ll1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selectcontact_background));

            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.requestFocus();
                ll2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onclick_selectcontact_background));
                ll1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selectcontact_background));

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
                if (phno.getText()!=null && name.getText() != null) {
                    if ( phno.getText().length()==10 && name.getText().length()>0) {
                        if(language.equals("Select")){
                            Toast.makeText(ContactDetails.this, "Select language", Toast.LENGTH_SHORT).show();
                        }else{
                            // getOOtp();
                            SystemConstant.callingPhno = phno.getText().toString();
                            SystemConstant.CallingName  = name.getText().toString();
                            if(language.equals("Kannada"))
                            {
                                SystemConstant.callingLanguage  = "7";
                            } else if(language.equals("English"))
                            {
                                SystemConstant.callingLanguage  = "8";
                            }else if(language.equals("Hindi"))
                            {
                                SystemConstant.callingLanguage  = "9";
                            }else if(language.equals("Tamil"))
                            {
                                SystemConstant.callingLanguage  = "10";
                            }


                            Bitmap photo = BitmapFactory.decodeResource(getResources(), R.drawable.contact_pf);
                            byte[]abc = getBytes(photo);
                            DateFormat dateFormat = new SimpleDateFormat("dd:MM HH:mm");
                            Date date = new Date();
                            String dateformatted = dateFormat.format(date);

                            DbHandler dbHandler = new DbHandler(ContactDetails.this);
                            if(!(name.getText().toString()).equals("Username")){
                                dbHandler.insertUserDetails(name.getText().toString(),phno.getText().toString(),dateformatted,abc);
                            }

                            next();
                        }


                    } else {
                      //  Toast.makeText(this,"",Toast.LENGTH_LONG).show();
                        Toast.makeText(ContactDetails.this, "Enter valid Phone number and Name", Toast.LENGTH_SHORT).show();
                    }
                } else {
                   Toast.makeText(ContactDetails.this,getString(R.string.enter_valid_phone),Toast.LENGTH_LONG).show();

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
                        Intent intent = new Intent(ContactDetails.this, HomeActivity.class);
                        startActivity(intent);
                        // Toast.makeText(MakeCall.this, "Favorites", Toast.LENGTH_SHORT).show();
                        // next();
                        break;
                    case R.id.profile_setting:
                        item.setIcon(R.drawable.setting1);
                       // startActivity(new Intent(ContactDetails.this,ProfileSettingsActivity.class));
                        // Toast.makeText(MakeCall.this, "Nearby", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }
    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public void goToCallHistory()
    {
        Intent intent = new Intent(this, History.class);
        startActivity(intent);
    }
    public void next()
    {
       Intent intent = new Intent(this, SelectPurpose.class);
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
