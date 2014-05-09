package com.hlresidential.aceyourexam;

import android.app.ListFragment;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;



public class MyListFragment extends ListFragment {

    private static final String TAG = MyListFragment.class.getSimpleName();
	// Preferences
	SharedPreferences preferences;
    protected String prefLastScreen = " ";
	public static final String PREF_LAST_SCREEN = "prefLastScreen";
	public static final String PREF_LAST_SCREEN_MAIN = "prefLastScreenMain";
	public static final String PREF_LAST_SCREEN_STUDY = "prefLastScreenStudy";
	public static final String PREF_LAST_SCREEN_PRACTICE = "prefLastScreenPractice";
	public static final String PREF_LAST_SCREEN_TEST = "prefLastScreenTest";
	public static final String PREF_LAST_SCREEN_REPORT = "prefLastScreenReport";
	public static final String PREF_LAST_SCREEN_MORE = "prefLastScreenMore";
	public static final String PREF_LAST_TEST_ID = "prefLastTestID";
	public static final String PREF_PAUSED = "prefPaused";
	protected boolean isInitialized = false;

    @Override
    public void onResume() {
 //       Log.v(TAG, "onResume()");
 //       BusProvider.getInstance().register(this);
        super.onResume();
    }

    @Override
    public void onPause() {
//        Log.v(TAG, "onPause()");
//        BusProvider.getInstance().unregister(this);
        super.onPause();
    }

 //   protected void post(Object event) {
 //       Log.v(TAG, "post()");
 //       BusProvider.getInstance().post(event);
 //   }
    
	protected void loadPreferences() {
		{
			if (!isInitialized) {
				preferences = PreferenceManager
						.getDefaultSharedPreferences(this.getActivity());
				isInitialized = true;
			}
			
			prefLastScreen = preferences.getString(PREF_LAST_SCREEN, " ");

		}
//	protected void loadPreferences() {
//		prefLastScreen = preferences.getString(PREF_LAST_SCREEN, " ");
//		int junk = 0;
//		junk = 1;


	}
}

