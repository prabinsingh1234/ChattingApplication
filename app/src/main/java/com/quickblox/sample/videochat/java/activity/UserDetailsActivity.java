package com.quickblox.sample.videochat.java.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.quickblox.sample.videochat.java.R;
import com.quickblox.sample.videochat.java.constants.SystemConstant;


public class UserDetailsActivity extends AppCompatActivity {

    TextView mTvUsername, mTvNumber, mTvPurpose,openingLine;

    private String username, phoneNumber, purpose;

    private Button mBtnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

     //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();

        permissionCheck();

        inflateView();
    }


    public void permissionCheck(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(UserDetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE},100);

        }

    }

    /**initialize views **/
    private void initView() {

        mTvUsername = (TextView) findViewById(R.id.user_detail_name);
        mTvNumber = (TextView) findViewById(R.id.user_detail_number);
        mTvPurpose = (TextView) findViewById(R.id.user_detail_purpose);
        openingLine  = (TextView) findViewById(R.id.user_detail_openingline);
        mBtnCall = (Button) findViewById(R.id.call);


        Intent intent = getIntent();

        if (intent.hasExtra(IntepretorActivity.USER_PHONENUMBER)) {

            Bundle bundle = intent.getExtras();
            username = bundle.getString(IntepretorActivity.USER_NAME);
            phoneNumber = bundle.getString(IntepretorActivity.USER_PHONENUMBER);
            purpose = bundle.getString(IntepretorActivity.USER_PURPOSE);

        }

        mBtnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber



                ));//change the number
                if (ActivityCompat.checkSelfPermission(UserDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.


                    ActivityCompat.requestPermissions(UserDetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE},100);
                    return;
                }
                startActivity(callIntent);
            }
        });

        inflateView();
    }

    private void inflateView(){

        mTvUsername.setText(username);
        mTvNumber.setText(phoneNumber);
        mTvPurpose.setText(purpose);
        openingLine.setText(SystemConstant.openingLine );
    }
}