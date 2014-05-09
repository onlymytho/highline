package com.hlresidential.aceyourexam;


import android.app.Fragment;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MyFragment extends Fragment {

    private static final String TAG = MyFragment.class.getSimpleName();
    
	// Preferences

	public static final String PREF_LAST_SCREEN = "prefLastScreen";
	public static final String PREF_LAST_SCREEN_MAIN = "prefLastScreenMain";
	public static final String PREF_LAST_SCREEN_STUDY = "prefLastScreenStudy";
	public static final String PREF_LAST_SCREEN_PRACTICE = "prefLastScreenPractice";
	public static final String PREF_LAST_SCREEN_TEST = "prefLastScreenTest";
	public static final String PREF_LAST_SCREEN_REPORT = "prefLastScreenReport";
	public static final String PREF_LAST_SCREEN_MORE = "prefLastScreenMore";
	protected SharedPreferences preferences;
	protected SharedPreferences.Editor editor = null;
	protected boolean isInitialized = false;
	protected String prefLastScreen = " ";
	
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

    protected void post(Object event) {
//        Log.v(TAG, "post()");
//        BusProvider.getInstance().post(event);
    }

/*    @Subscribe
    public void changeLastScreen(OttoActivityEventBus lastScreenChangedBusEvent) {
    	Button btn_odd_title1;
        Log.v(TAG, "Horrraaayyyy changeLastScreen(): " + lastScreenChangedBusEvent.getLastScreen());
        lstScreen = lastScreenChangedBusEvent.getLastScreen();
        if(lstScreen.equals(PREF_LAST_SCREEN_PRACTICE)) {
        	btn_odd_title1 = (Button) getView().findViewById(R.id.btn_odd_title);
			btn_odd_title1.setVisibility(View.VISIBLE);
			Log.e(TAG, "  Set Visiblity lstScreen := " + lstScreen);
			getView().invalidate();
		}
    }*/
    
	protected void loadPreferences() {
		prefLastScreen = preferences.getString(PREF_LAST_SCREEN, " ");


	}
	public void initializePreference() {
		if (!isInitialized) {
			preferences = PreferenceManager
					.getDefaultSharedPreferences(this.getActivity());
			editor = preferences.edit();
			editor.commit();
			loadPreferences();
			isInitialized = true;
		}
	}
}
