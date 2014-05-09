package com.hlresidential.aceyourexam;



import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;



public class AfterYourExamActivity extends MyActivity {

	Button btn_practice, btn_study, btn_test, btn_report, btn_aftyeryourexam;
	RelativeLayout layout;
	
	  private AdView adView;

	  /* Your ad unit id. Replace with your actual ad unit id. */
	  private static final String AD_UNIT_ID = "ca-app-pub-4347043595760955/4815070822";
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_afteryourexam);
		
		layout = (RelativeLayout) findViewById(R.id.relativecontainer);
		  // Create an ad.
	    adView = new AdView(this);
	    adView.setAdSize(AdSize.BANNER);
	    adView.setAdUnitId(AD_UNIT_ID);
	    
	    RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(
	            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
	    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
          
	    adView.setLayoutParams(params);
	    // Add the AdView to the view hierarchy. The view will have no size
	    // until the ad is loaded.
	    LinearLayout llayout = (LinearLayout) findViewById(R.id.linearLayout); // id.linearLayout
	    layout.addView(adView);

	    // Create an ad request. Check logcat output for the hashed device ID to
	    // get test ads on a physical device.
	    AdRequest adRequest = new AdRequest.Builder()
	        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//	        .addTestDevice("INSERT_YOUR_HASHED_DEVICE_ID_HERE")
	        .build();

	    // Start loading the ad in the background.
	    adView.loadAd(adRequest);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	    if (adView != null) {
	        adView.resume();
	      }
	}
	
	
	
	@Override
	public void onPause() {
	    if (adView != null) {
	        adView.pause();
	      }
		super.onPause();

	}
	  @Override
	  public void onDestroy() {
	    // Destroy the AdView.
	    if (adView != null) {
	      adView.destroy();
	    }
	    super.onDestroy();
	  }
}
