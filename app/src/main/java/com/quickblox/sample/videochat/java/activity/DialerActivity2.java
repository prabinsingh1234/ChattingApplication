package com.quickblox.sample.videochat.java.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.quickblox.sample.videochat.java.R;
import com.quickblox.sample.videochat.java.utils.ToastUtils;
import com.quickblox.sample.videochat.java.utils.UiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialerActivity2 extends AppCompatActivity {
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.tv5)
    TextView tv5;
    @BindView(R.id.tv6)
    TextView tv6;
    @BindView(R.id.tv7)
    TextView tv7;
    @BindView(R.id.tv8)
    TextView tv8;
    @BindView(R.id.tv9)
    TextView tv9;
    @BindView(R.id.tv0)
    TextView tv0;
    @BindView(R.id.tvPhoneNumber)
    TextView tvPhoneNumber;
    @BindView(R.id.flBackSpace)
    FrameLayout flBackSpace;
    @BindView(R.id.flDial)
    FrameLayout flDial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialer);
        try {
            ButterKnife.bind(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        getSupportActionBar().hide();
        UiUtils.changeStatusBarColor(this, R.color.toolbar_blue_dark);
        if(getIntent().getExtras()!=null){
            if(!getIntent().getStringExtra("phoneNumber").isEmpty()){
                tvPhoneNumber.setText(getIntent().getStringExtra("phoneNumber"));
            }
        }
        setClickListeners();

    }

    private void setClickListeners() {
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvPhoneNumber.setText(tvPhoneNumber.getText().toString() + ("1"));
                if(tvPhoneNumber.getText().toString().length()<10){
                    tvPhoneNumber.setTextColor(getResources().getColor(R.color.red));
                } else {
                    tvPhoneNumber.setTextColor(getResources().getColor(R.color.colorAccent));
                }
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvPhoneNumber.setText(tvPhoneNumber.getText().toString() + ("2"));
                if(tvPhoneNumber.getText().toString().length()<10){
                    tvPhoneNumber.setTextColor(getResources().getColor(R.color.red));
                } else {
                    tvPhoneNumber.setTextColor(getResources().getColor(R.color.colorAccent));
                }
            }
        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvPhoneNumber.setText(tvPhoneNumber.getText().toString() + ("3"));
                if(tvPhoneNumber.getText().toString().length()<10){
                    tvPhoneNumber.setTextColor(getResources().getColor(R.color.red));
                } else {
                    tvPhoneNumber.setTextColor(getResources().getColor(R.color.colorAccent));
                }
            }
        });
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvPhoneNumber.setText(tvPhoneNumber.getText().toString() + ("4"));
                if(tvPhoneNumber.getText().toString().length()<10){
                    tvPhoneNumber.setTextColor(getResources().getColor(R.color.red));
                } else {
                    tvPhoneNumber.setTextColor(getResources().getColor(R.color.colorAccent));
                }
            }
        });


        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvPhoneNumber.setText(tvPhoneNumber.getText().toString() + ("5"));
                if(tvPhoneNumber.getText().toString().length()<10){
                    tvPhoneNumber.setTextColor(getResources().getColor(R.color.red));
                } else {
                    tvPhoneNumber.setTextColor(getResources().getColor(R.color.colorAccent));
                }
            }
        });


        tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvPhoneNumber.setText(tvPhoneNumber.getText().toString() + ("6"));
                if(tvPhoneNumber.getText().toString().length()<10){
                    tvPhoneNumber.setTextColor(getResources().getColor(R.color.red));
                } else {
                    tvPhoneNumber.setTextColor(getResources().getColor(R.color.colorAccent));
                }
            }
        });

        tv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvPhoneNumber.setText(tvPhoneNumber.getText().toString() + ("7"));
                if(tvPhoneNumber.getText().toString().length()<10){
                    tvPhoneNumber.setTextColor(getResources().getColor(R.color.red));
                } else {
                    tvPhoneNumber.setTextColor(getResources().getColor(R.color.colorAccent));
                }
            }
        });

        tv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvPhoneNumber.setText(tvPhoneNumber.getText().toString() + ("8"));
                if(tvPhoneNumber.getText().toString().length()<10){
                    tvPhoneNumber.setTextColor(getResources().getColor(R.color.red));
                } else {
                    tvPhoneNumber.setTextColor(getResources().getColor(R.color.colorAccent));
                }
            }
        });

        tv9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvPhoneNumber.setText(tvPhoneNumber.getText().toString() + ("9"));
                if(tvPhoneNumber.getText().toString().length()<10){
                    tvPhoneNumber.setTextColor(getResources().getColor(R.color.red));
                } else {
                    tvPhoneNumber.setTextColor(getResources().getColor(R.color.colorAccent));
                }
            }
        });


        tv0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvPhoneNumber.setText(tvPhoneNumber.getText().toString() + ("0"));
                if(tvPhoneNumber.getText().toString().length()<10){
                    tvPhoneNumber.setTextColor(getResources().getColor(R.color.red));
                } else {
                    tvPhoneNumber.setTextColor(getResources().getColor(R.color.colorAccent));
                }
            }
        });


        flBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvPhoneNumber.getText().toString().length() > 0) {
                    tvPhoneNumber.setText(tvPhoneNumber.getText().toString().substring(0, tvPhoneNumber.getText().length() - 1));
                    if(tvPhoneNumber.getText().toString().length()<10){
                        tvPhoneNumber.setTextColor(getResources().getColor(R.color.red));
                    } else {
                        tvPhoneNumber.setTextColor(getResources().getColor(R.color.colorAccent));
                    }
                }
            }
        });

        flDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvPhoneNumber.getText().toString().length() > 9) {
                    Uri call = Uri.parse("tel:" + tvPhoneNumber.getText().toString());
                    Intent intent = new Intent(Intent.ACTION_CALL, call);
                    startActivity(intent);
                } else {
                    ToastUtils.shortToast("Please enter correct phone number");
                }
            }
        });
    }
}