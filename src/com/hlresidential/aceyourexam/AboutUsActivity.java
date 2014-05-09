package com.hlresidential.aceyourexam;



import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;

public class AboutUsActivity extends MyActivity {

	Button btn_practice, btn_study, btn_test, btn_report, btn_aftyeryourexam;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_about);
		
		TextView tv_aboutus = (TextView) findViewById(R.id.tv_aboutus);
		tv_aboutus.setMovementMethod(new ScrollingMovementMethod());

	}
}
