package com.hlresidential.aceyourexam;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;

public class ScheduledService extends IntentService {
	private WifiManager wifiManager;
	// private static final String TAG = "ScheduledService";
	// private Uri sound;
	AudioManager am;
	int notificationVolume = 8;
	SharedPreferences preferences;
	public static final String PREF_NOTIFICATIONS = "notification_preference";
	private boolean prefNotification;

	public ScheduledService() {
		super("ScheduledService");
		// Log.e(TAG, " ... ScheduledService");

	}

	@Override
	protected void onHandleIntent(Intent intent) {

		// sound =
		// Uri.parse("android.resource://com.androidfactorem.airwave/raw/silent");

		// Log.e(TAG, " ... onHandleIntent");
		// Log.d(getClass().getSimpleName(), "WifiAirWave: WiFi is now RESET");

		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		prefNotification = preferences.getBoolean(PREF_NOTIFICATIONS, false);
		// Log.e(TAG, " ... Notification is : " + prefNotification);

		// am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		// int notificationVolume =
		// am.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
		// Log.e(TAG, " ... Notification Volume is : " + notificationVolume);
		// am.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 0,
		// AudioManager.FLAG_PLAY_SOUND);


					notifyDone();
			
		

		// Thread timeout = new Thread() {
		// public void run () {
		// try {
		// sleep(4000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// }

		// timeout.start();

		// am.setStreamVolume(AudioManager.STREAM_NOTIFICATION,
		// notificationVolume, AudioManager.FLAG_PLAY_SOUND);

	}



	void notifyDone() {

		String svc = Context.NOTIFICATION_SERVICE;
		int NOTIFICATION_REF = 1;

		PendingIntent pendIntent = 
	             PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
		
//		Intent intent = new Intent();
//		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
		Notification noti = new Notification.Builder(this)
		.setTicker(getString(R.string.str_notify_wifi_3))
		.setContentTitle(getString(R.string.str_notify_wifi_title3))
		.setContentText(getString(R.string.str_wifi_context3))
		.setProgress(100, 100, false).setOnlyAlertOnce(true)
		.setContentInfo(getString(R.string.str_wifi_info1))
		.setSmallIcon(R.drawable.ic_launcher)
		.setWhen(System.currentTimeMillis())
		.setContentIntent(pendIntent).getNotification();
		noti.flags=Notification.FLAG_AUTO_CANCEL;
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationManager.notify(NOTIFICATION_REF, noti); 
		
		
/*		Notification.Builder builder = new Notification.Builder(
				ScheduledService.this);

		builder.setSmallIcon(R.drawable.hl)
				.setTicker(getString(R.string.str_notify_wifi_3))
				.setContentTitle(getString(R.string.str_notify_wifi_title3))
				.setContentText(getString(R.string.str_wifi_context3))
				.setProgress(100, 100, false).setOnlyAlertOnce(true)
				.setContentInfo(getString(R.string.str_wifi_info1))
				.setContentIntent(pIntent).getNotification();
				// .setSound(sound)
				builder.flags=Notification.FLAG_AUTO_CANCEL;
				.setWhen(System.currentTimeMillis());
//		 PendingIntent contentIntent = 
//	             PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);  
		Notification notification = builder.getNotification();
		NotificationManager notificationManager = (NotificationManager) getSystemService(svc);

		notificationManager.notify(NOTIFICATION_REF, notification);*/

	};

	
	
	
	
	
	
/*	private boolean isNetworkConnected() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		boolean isNetworkConnected = false;

		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null) {
			if (networkInfo.isConnected()) {
				if ((networkInfo.getType() == ConnectivityManager.TYPE_WIFI)) {
					isNetworkConnected = true;
				}
			}
		}

		return isNetworkConnected;
	} */

}
