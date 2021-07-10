package com.quickblox.sample.videochat.java.Call;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class OutgoingCallReceiver extends BroadcastReceiver {

    public static final String ABORT_PHONE_NUMBER = "1231231234";

    private static final String OUTGOING_CALL_ACTION = "android.intent.action.NEW_OUTGOING_CALL";
    private static final String INTENT_PHONE_NUMBER = "android.intent.extra.PHONE_NUMBER";

    @Override
    public void onReceive(final Context context, final Intent intent) {

        if (intent.getAction().equals(OutgoingCallReceiver.OUTGOING_CALL_ACTION)) {


            // get phone number from bundle
            String phoneNumber = intent.getExtras().getString(OutgoingCallReceiver.INTENT_PHONE_NUMBER);
            if ((phoneNumber != null) && phoneNumber.equals(OutgoingCallReceiver.ABORT_PHONE_NUMBER)) {

                this.abortBroadcast();
            }
        }
    }
}
