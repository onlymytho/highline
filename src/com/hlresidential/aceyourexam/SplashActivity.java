package com.hlresidential.aceyourexam;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

public class SplashActivity extends Activity  implements
OnSharedPreferenceChangeListener {

	
	public static final String PREF_NOTIFICATIONS = "notification_preference";
	public static final String PREF_UPDATE_FREQ = "PREF_UPDATE_FREQ";
	public static final String PREF_DURATION = "myDuration";
	public static final String FREQ = "FREQ";
	private boolean prefNotification;
	private long prefDuration;
	OnSharedPreferenceChangeListener listener;
	private WifiManager wifiManager;
	boolean alarmUp;
	SharedPreferences preferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		preferences = PreferenceManager.getDefaultSharedPreferences(this);

		getPreference();

		preferences.registerOnSharedPreferenceChangeListener(listener);
        
		setContentView(R.layout.activity_splash);
		//TODO make black text the height of it's image to the left

        
		Thread timeout = new Thread() {
			public void run() {
				try {
					sleep(2000);  // 2 second delay per specs
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent i = new Intent(SplashActivity.this,
							MainActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					finish();
				}
			}
		};
		timeout.start();
		

			initiateAlarm(true);
			Toast.makeText(SplashActivity.this,
					R.string.str_wifi_airwaves_exit, Toast.LENGTH_LONG)
					.show();
		
	}
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// TODO Auto-generated method stub
		prefNotification = preferences.getBoolean(PREF_NOTIFICATIONS, false);
		prefDuration = Long.parseLong(preferences.getString(PREF_UPDATE_FREQ,
				"1"));
		prefDuration = 60000 * prefDuration;
	}
	
	public void getPreference() {

		prefNotification = preferences.getBoolean(PREF_NOTIFICATIONS, false);
		prefDuration = Long.parseLong(preferences.getString(PREF_UPDATE_FREQ,
				"1"));
		prefDuration = 60000 * prefDuration;


	}
	
	public void initiateAlarm(Boolean bactive) {
		alarmUp = false;
		alarmUp = (PendingIntent.getBroadcast(getBaseContext(), 0, new Intent(
				this, ScheduledService.class), PendingIntent.FLAG_NO_CREATE) != null);
		if (alarmUp) {
			// Log.d("myTag", "Alarm is already active");
			Toast.makeText(this, " before scheduling Alarm is already active",
					Toast.LENGTH_LONG).show();
		} else {

			PollReceiver.scheduleAlarms(this, prefDuration, bactive);
			// Toast.makeText(this, "alarms were just scheduled for 1 minute",
			// Toast.LENGTH_LONG).show();
		}

		alarmUp = false;
		alarmUp = (PendingIntent.getBroadcast(getBaseContext(), 0, new Intent(
				this, ScheduledService.class), PendingIntent.FLAG_NO_CREATE) != null);
		// if (alarmUp) {
		// Log.d("myTag", "Alarm is already active");
		// Toast.makeText(this, " after scheduling Alarm is already active",
		// Toast.LENGTH_LONG).show();
		// }
	}
}


