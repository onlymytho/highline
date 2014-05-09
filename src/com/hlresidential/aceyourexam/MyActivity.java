package com.hlresidential.aceyourexam;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MyActivity extends Activity {

	String selectParam = "";
	private Menu menu=null;
	public boolean menuVisibilityHomeButton = true;
	private static final String TAG = MyFragment.class.getSimpleName();

	SharedPreferences preferences;
	public static final String PREF_LAST_SCREEN = "prefLastScreen";
	public static final String PREF_LAST_SCREEN_MAIN = "prefLastScreenMain";
	public static final String PREF_LAST_SCREEN_STUDY = "prefLastScreenStudy";
	public static final String PREF_LAST_SCREEN_PRACTICE = "prefLastScreenPractice";
	public static final String PREF_LAST_SCREEN_TEST = "prefLastScreenTest";
	public static final String PREF_LAST_SCREEN_REPORT = "prefLastScreenReport";
	public static final String PREF_LAST_SCREEN_MORE = "prefLastScreenMore";
	public static final String PREF_LAST_TEST_ID = "prefLastTestID";
	public static final String PREF_PAUSED = "prefPaused";
	private SharedPreferences.Editor editor = null;
	String prefLastScreen = " ";
	private boolean isInitialized = false;

	
//    if (getResources().getConfiguration().orientation
//            == Configuration.ORIENTATION_LANDSCAPE) {
        // If the screen is now in landscape mode, we can show the
        // dialog in-line with the list so we don't need this activity.
//    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.menu=menu;
		getMenuInflater().inflate(R.menu.main, menu);
		
		MenuItem itemMain = menu.findItem(R.id.item_more_questions);
//		itemMain.setVisible(menuVisibilityHomeButton);
		
	
		// TODO add icon to title bar
		// TODO setting colors of menu items
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		Intent i;
//		 Toast.makeText(getApplicationContext(), "item id " + item.getItemId(), Toast.LENGTH_LONG).show();
		switch (item.getItemId()) {
		// case android.R.id.am_main:
		// Intent intent = new Intent(this, ActivityMain.class);
		// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// startActivity(intent);
		// return true;
		case R.id.item_more_questions:

			i = new Intent(this, MoreQuestionsActivity.class);
			startActivity(i);
			return true;
		case R.id.item_aboutus:

			i = new Intent(this, AboutUsActivity.class);
			startActivity(i);
			return true;
		case R.id.item_settings:

			i = new Intent(this, ActivityPreferences.class);
			startActivity(i);
			return true;
		case R.id.test_settings:

//			i = new Intent(this, BannerSample.class);
//			startActivity(i);
//			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	public void initializePreference() {
		if (!isInitialized) {
			preferences = PreferenceManager
					.getDefaultSharedPreferences(MyActivity.this
							.getBaseContext());
			editor = preferences.edit();
			editor.commit();
			loadPreferences();
			isInitialized = true;
		}
	}



	private void savePreferences() {
		editor.putString(PREF_LAST_SCREEN, prefLastScreen);
		editor.commit();

	}

	private void loadPreferences() {
		prefLastScreen = preferences.getString(PREF_LAST_SCREEN, " ");


	}
	
	// Fragment access to prefs
	
    public String getMyPref(String prefRequest) {
    	String myPrefString = " ";
    	if (prefRequest.equals(PREF_LAST_SCREEN) ) {
    		myPrefString = prefLastScreen;
       	} 
        return myPrefString;
    }

	public String getLastScreen() {
		String myPrefString = " ";
		myPrefString = prefLastScreen;
		return myPrefString;
	}
	
    @Override
    public void onResume() {
        Log.v(TAG, "onResume()");
 //       BusProvider.getInstance().register(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.v(TAG, "onPause()");
 //       BusProvider.getInstance().unregister(this);
        super.onPause();
    }

 //   protected void post(Object event) {
 //       Log.v(TAG, "post()");
  //      BusProvider.getInstance().post(event);
//    }
}

