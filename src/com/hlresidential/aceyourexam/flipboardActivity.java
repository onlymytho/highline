package com.hlresidential.aceyourexam;



import android.app.Activity;
import android.os.Bundle;

public class flipboardActivity extends MyActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(new flipboardviewSrc(this));
	}
	
}
