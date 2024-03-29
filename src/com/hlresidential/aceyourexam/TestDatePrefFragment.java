package com.hlresidential.aceyourexam;


import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;

public class TestDatePrefFragment extends DialogFragment {
	
	public static String TAG = "DateDialogFragment";
	static Context mContext; //I guess hold the context that called it. Needed when making a DatePickerDialog. I guess its needed when conncting the fragment with the context
	static int mYear;
	static int mMonth;
	static int mDay;
	static com.hlresidential.aceyourexam.MainActivity.TestDatePrefFragmentListener mListener;
	
	public static TestDatePrefFragment newInstance(Context context, com.hlresidential.aceyourexam.MainActivity.TestDatePrefFragmentListener listener, Calendar now) {
		TestDatePrefFragment dialog = new TestDatePrefFragment();
		mContext = context;
		mListener = listener;
		
		mYear = now.get(Calendar.YEAR);
		mMonth = now.get(Calendar.MONTH);
		mDay = now.get(Calendar.DAY_OF_MONTH);
		/*I dont really see the purpose of the below*/
		Bundle args = new Bundle();
		args.putString("title", "Set Date");
		dialog.setArguments(args);//setArguments can only be called before fragment is attached to an activity, so right after the fragment is created
		
		
		return dialog;
	}
	
	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new DatePickerDialog(mContext, mDateSetListener, mYear, mMonth, mDay);
	}
	
	
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			
			mListener.updateChangedDate(year, monthOfYear, dayOfMonth);
		}
	};
	
	
	
	

}
