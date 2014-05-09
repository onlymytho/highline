package com.hlresidential.aceyourexam;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class StudyFragmentAdapter extends SimpleCursorAdapter {

	private Context context;
	private final LayoutInflater inflater;
//	private String move_score = "";
//	private boolean isWhite = true;
//	private String score_number;
	private int pos = 0;
	HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
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
		public static final String PREF_PAUSED = "prefPaused";
		protected boolean isInitialized = false;
//		Typeface tf, tfBold;

	
    protected static class ViewHolder {
        protected TextView h_question;
        protected TextView h_answer;
        protected TextView h_description;
        protected TextView h_answer1;
        protected TextView h_answer2;
        protected TextView h_answer3;
        protected TextView h_answer4;
        protected TextView h_correct;
        protected int position;
      }
	
	public StudyFragmentAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to) {
		super(context, layout, c, from, to);
		this.context = context;
		inflater = LayoutInflater.from(context);
//		tf = Typeface.createFromAsset(context.getAssets(), "fonts/MYRIADPRO-REGULAR.OTF");
//		tfBold = Typeface.createFromAsset(context.getAssets(), "fonts/MYRIADPRO-BOLD.OTF");
	}
	
	   @Override
	    public boolean hasStableIds() {
	        return true;
	    }

	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
		View view = inflater.inflate(R.layout.study_row, parent, false);
		
		holder.h_question = (TextView) view.findViewById(R.id.tv_question);
		holder.h_answer = (TextView) view.findViewById(R.id.tv_answer);
		holder.h_answer1 = (TextView) view.findViewById(R.id.tv_answer1);
		holder.h_answer2 = (TextView) view.findViewById(R.id.tv_answer2);
		holder.h_answer3 = (TextView) view.findViewById(R.id.tv_answer3);
		holder.h_answer4 = (TextView) view.findViewById(R.id.tv_answer4);
		holder.h_correct = (TextView) view.findViewById(R.id.tv_correct);
		holder.h_description = (TextView) view.findViewById(R.id.tv_descripton);
//		holder.h_answer.setOnClickListener(mMoves_OnClickListener);
//		holder.h_description.setOnClickListener(mMoves_OnClickListener);
		holder.position = cursor.getPosition();
		bindView(view, context, cursor);
        view.setTag(holder);
		return view;
	}

	public void bindView(View view, final Context context, Cursor cursor) {

		TextView tv_question = (TextView) view.findViewById(R.id.tv_question);
		TextView tv_answer = (TextView) view.findViewById(R.id.tv_answer);
		TextView tv_answer1 = (TextView) view.findViewById(R.id.tv_answer1);
		TextView tv_answer2 = (TextView) view.findViewById(R.id.tv_answer2);
		TextView tv_answer3 = (TextView) view.findViewById(R.id.tv_answer3);
		TextView tv_answer4 = (TextView) view.findViewById(R.id.tv_answer4);
		TextView tv_correct = (TextView) view.findViewById(R.id.tv_correct);
		TextView tv_description = (TextView) view.findViewById(R.id.tv_descripton);
/*		tv_description.setTypeface(tf);
		tv_question.setTypeface(tfBold);
		tv_answer.setTypeface(tfBold);
		tv_answer1.setTypeface(tf);
		tv_answer2.setTypeface(tf);
		tv_answer3.setTypeface(tf);
		tv_answer4.setTypeface(tf);
		tv_correct.setTypeface(tf);*/
		
		int iNote = cursor.getPosition() + 1;

		if (cursor != null) {
			tv_question.setText("Q" + iNote + ".  " + cursor.getString(cursor.getColumnIndexOrThrow(HLdb.COL_QUESTION)));
			tv_description.setText("Description.  " + cursor.getString(cursor.getColumnIndexOrThrow(HLdb.COL_DESCIPTION)));
			tv_answer1.setTextColor(context.getResources().getColor(R.color.black));
			tv_answer2.setTextColor(context.getResources().getColor(R.color.black));
			tv_answer3.setTextColor(context.getResources().getColor(R.color.black));
			tv_answer4.setTextColor(context.getResources().getColor(R.color.black));
			tv_answer1.setText("A. " + cursor.getString(cursor.getColumnIndexOrThrow(HLdb.COL_ANSWER1)));
			tv_answer2.setText("B. " + cursor.getString(cursor.getColumnIndexOrThrow(HLdb.COL_ANSWER2)));
			tv_answer3.setText("C. " + cursor.getString(cursor.getColumnIndexOrThrow(HLdb.COL_ANSWER3)));
			tv_answer4.setText("D. " + cursor.getString(cursor.getColumnIndexOrThrow(HLdb.COL_ANSWER4)));
			int iAnswerKey = 
					cursor.getInt(cursor.getColumnIndexOrThrow(HLdb.COL_ANSWER_KEY));
			switch (iAnswerKey) {
			case 1:
				tv_answer.setText(cursor.getString(cursor.getColumnIndexOrThrow(HLdb.COL_ANSWER1)));
				tv_correct.setText("A");
				tv_answer1.setTextColor(context.getResources().getColor(R.color.toreview));
				tv_answer1.setTypeface(Typeface.DEFAULT_BOLD);
				break;
			case 2:
				tv_answer.setText(cursor.getString(cursor.getColumnIndexOrThrow(HLdb.COL_ANSWER2)));
				tv_correct.setText("B");
				tv_answer2.setTextColor(context.getResources().getColor(R.color.toreview));
				tv_answer2.setTypeface(Typeface.DEFAULT_BOLD);
				break;
			case 3:
				tv_answer.setText(cursor.getString(cursor.getColumnIndexOrThrow(HLdb.COL_ANSWER3)));
				tv_correct.setText("C");
				tv_answer3.setTextColor(context.getResources().getColor(R.color.toreview));
				tv_answer3.setTypeface(Typeface.DEFAULT_BOLD);
				break;
			case 4:
				tv_answer.setText( cursor.getString(cursor.getColumnIndexOrThrow(HLdb.COL_ANSWER4)));
				tv_correct.setText("D");
				tv_answer4.setTextColor(context.getResources().getColor(R.color.toreview));
				tv_answer4.setTypeface(Typeface.DEFAULT_BOLD);
				break;				
			default:
				tv_answer.setText("?? No Answer found ??");
				break;
			}
			loadPreferences();
			Log.i("StudyFragmentAdapter", " from within ....... LastScreen pref: " + prefLastScreen);
		}
		RelativeLayout view_answers = (RelativeLayout) view.findViewById(R.id.view_answers); 

		if(prefLastScreen.equals(PREF_LAST_SCREEN_STUDY)) {
			view_answers.setVisibility(View.GONE);
			tv_answer.setVisibility(View.VISIBLE);
		} else {
			view_answers.setVisibility(View.VISIBLE);
			tv_answer.setVisibility(View.GONE);
		} 
	}

	@Override
	public long getItemId(int position) {
	    Cursor cursor = getCursor();
	    if(cursor != null) {
	    cursor.moveToPosition(position);
	    
	    return cursor.getInt(cursor.getColumnIndex("_id"));
	    }
	    return -1;
	}

	protected void loadPreferences() {
		{
			if (!isInitialized) {
				preferences = PreferenceManager.getDefaultSharedPreferences(context);
				isInitialized = true;
			}
			
			prefLastScreen = preferences.getString(PREF_LAST_SCREEN, " ");

		}

	}
 

}
