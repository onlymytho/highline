package com.hlresidential.aceyourexam;



import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class ActivityPreferences extends Activity {

	// private String strAction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Bundle extras = getIntent().getExtras();
		// if (extras != null) {
		// strAction = getIntent().getStringExtra("Init");
		// }

		// if (strAction == "Init") {
		// finish();
		// }

		FragmentManager mFragmentManager = getFragmentManager();
		FragmentTransaction mFragmentTransaction = mFragmentManager
				.beginTransaction();
		PrefsFragment mPrefsFragment = new PrefsFragment();
		mFragmentTransaction.replace(android.R.id.content, mPrefsFragment);
		mFragmentTransaction.commit();

		// getFragmentManager().beginTransaction()
		// .replace(android.R.id.content, new PrefsFragment()).commit();

	}

	public static class PrefsFragment extends PreferenceFragment {

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			addPreferencesFromResource(R.layout.preferences);

		}
	}
}
