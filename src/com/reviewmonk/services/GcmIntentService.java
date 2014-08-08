package com.reviewmonk.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.reviewmonk.R;
import com.reviewmonk.activity.Push;

public class GcmIntentService extends IntentService {
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	private final static String TAG = "GcmIntentService";

	public GcmIntentService() {
		super("GcmIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		Log.d(TAG, "Notification Data Json :" + extras.getString("my_message"));

		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		String messageType = gcm.getMessageType(intent);
		if (!extras.isEmpty()) {
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
					.equals(messageType)) {
				sendNotification(extras);
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
					.equals(messageType)) {
				sendNotification(extras); // If it's a regular GCM message,
												// do some work.
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
					.equals(messageType)) {
				// This loop represents the service doing some work.
				for (int i = 0; i < 5; i++) {
					Log.d(TAG,
							" Working... " + (i + 1) + "/5 @ "
									+ SystemClock.elapsedRealtime());
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
					}
				}
				Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
				Log.i(TAG, extras.toString());
				sendNotification(extras);
			}
		} // Release the wake lock provided by the WakefulBroadcastReceiver.
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	} // Put the message into a notification and post it.

	// This is just one simple example of what you might choose to do with
	// a GCM message.
	private void sendNotification(Bundle extras) {
		String msg=extras.getString("message_text");
		
		Intent intent1=new Intent(this, Push.class);
		
		
		intent1.putExtra("msg", msg);
		intent1.putExtra("sender",extras.getString("sender"));
		String prod=extras.getString("product");
		if(prod!="")
		intent1.putExtra("product", prod);
		
		
		
		mNotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);
//		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//				intent1, PendingIntent.FLAG_ONE_SHOT);

		PendingIntent contentIntent=	PendingIntent.getActivity(this, 0, intent1, 
                PendingIntent.FLAG_UPDATE_CURRENT);
		
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("Review Monk Notification")
				.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
				.setContentText(msg)
				.setDefaults(
						Notification.DEFAULT_SOUND
								| Notification.DEFAULT_VIBRATE);

		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
	}
}