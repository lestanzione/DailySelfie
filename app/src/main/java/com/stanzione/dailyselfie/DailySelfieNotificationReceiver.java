package com.stanzione.dailyselfie;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DailySelfieNotificationReceiver extends BroadcastReceiver{
	
	private static final int SELFIE_NOTIFICATION_ID = 1;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		Intent notificationIntent = new Intent(context, PictureListActivity.class);
		
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				notificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
		
		Notification.Builder notificationBuilder = new Notification.Builder(
				context).setTicker("Daily Selfie")
				.setSmallIcon(android.R.drawable.ic_menu_camera)
				.setAutoCancel(true).setContentTitle("Daily Selfie")
				.setContentText("Time for another selfie")
				.setContentIntent(pendingIntent);
		
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		
		mNotificationManager.notify(SELFIE_NOTIFICATION_ID,
				notificationBuilder.build());
		
	}

}
