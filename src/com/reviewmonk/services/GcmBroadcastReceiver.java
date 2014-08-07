package com.reviewmonk.services;

import com.reviewmonk.activity.Push;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.widget.Toast;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) 
    {        ComponentName comp = new ComponentName(context.getPackageName(),
                GcmIntentService.class.getName());        
    startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
        Toast.makeText(context, "wow!! received new push notification"+Push.PROPERTY_REG_ID, Toast.LENGTH_LONG).show();
    }
}