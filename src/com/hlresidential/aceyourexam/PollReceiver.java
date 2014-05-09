package com.hlresidential.aceyourexam;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

public class PollReceiver extends BroadcastReceiver {

	private static final String TAG = "PollReceiver";
	SharedPreferences preferences;
	public static final String PREF_NOTIFICATIONS = "notification_preference";
	public static final String PREF_UPDATE_FREQ = "PREF_UPDATE_FREQ";
	public static final String PREF_DURATION = "myDuration";
	// private boolean prefNotification;
	private static long prefDuration;
	private static final long defaultPERIOD = 86400000;
	Context context;
	int iPeriod = 0;

	@Override
	public void onReceive(Context ctxt, Intent i) {
		context = ctxt;
		// SharedPreferences preferences;
		// preferences = PreferenceManager.getDefaultSharedPreferences(ctxt);
		// prefDuration = preferences.getLong(PREF_DURATION, 60000);
		// Log.e(TAG, " ... onReceive ... myDuration: " + prefDuration );
		scheduleAlarms(ctxt, (long) defaultPERIOD, true); // 1 day

		// Log.e(TAG, " ... onReceive");
	}

	static void scheduleAlarms(Context ctxt, Long duration, Boolean bactive) {

		// Log.e(TAG, " ... scheduleAlarms ");
		SharedPreferences preferences;

		preferences = PreferenceManager.getDefaultSharedPreferences(ctxt);
		prefDuration = preferences.getLong(PREF_DURATION, defaultPERIOD); // 1
																			// day
																			// Log.e(TAG,
																			// " ... onReceive ... duration: "
																			// +
																			// duration);

		AlarmManager mgr = (AlarmManager) ctxt
				.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(ctxt, ScheduledService.class);
		PendingIntent pi = PendingIntent.getService(ctxt, 0, i, 0);

		mgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
				SystemClock.elapsedRealtime() + duration, duration, pi);
		if (bactive == false) {
			mgr.cancel(pi);
		}

	}

	/*
	 * public void getPreference() {
	 * 
	 * SharedPreferences preferences =
	 * PreferenceManager.getDefaultSharedPreferences(context);
	 * 
	 * prefNotification = preferences.getBoolean(PREF_NOTIFICATIONS, false);
	 * prefDuration = Integer.parseInt(preferences.getString(PREF_UPDATE_FREQ,
	 * "24")); iPeriod = 3600000; // 1 hour
	 * 
	 * 
	 * if (prefDuration == 999) { iPeriod = 60000; // 1 Minute for testing }
	 * else if (prefDuration == 998) { iPeriod = 120000; // 2 Minute for testing
	 * } else { iPeriod = iPeriod * prefDuration; } Log.e(TAG, " ... duration: "
	 * + iPeriod);
	 * 
	 * if (prefNotification == true) { Log.e(TAG,
	 * " ... Notificatoins are set to TRUE "); } else { Log.e(TAG,
	 * " ... Notificatoins are set to FALSE "); }
	 * 
	 * }
	 */
}
