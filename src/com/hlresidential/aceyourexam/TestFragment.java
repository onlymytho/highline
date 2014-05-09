package com.hlresidential.aceyourexam;

import java.util.ArrayList;
import java.util.List;

import com.hlresidential.aceyourexam.VerticalViewPager.OnPageChangeListener;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class TestFragment extends MyFragment {
	OnSubmitListener mCallback;
	public interface OnSubmitListener {
		public void onSubmitSelected();
	}

	private ViewPager basePager;
	private VerticalAdapter vAdapter1;

	private BasePagerAdapter baseAdapter;

	private VerticalViewPager verticalPager1;
	private VerticalViewPager verticalPager2 = null;

	private List<View> pageViewsAL;
	private List<View> verticalViewsAL;
	private Activity activity;
	private Context context;
	private ContentResolver cr;
	private Cursor query;
	
	private Button btn_float_submit;
	private View view1;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;

		try {
			mCallback = (OnSubmitListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnSubmitListener");
		}
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();


		btn_float_submit = (Button) getView().findViewById(R.id.btn_float_submit);
		btn_float_submit.setOnClickListener(btnOnClickListener);
		
		pageViewsAL = new ArrayList<View>(75);
		verticalViewsAL = new ArrayList<View>(75);
		ContentResolver cr = getActivity().getContentResolver();
		String[] projection = new String[] { HLdb.COL_QUESTION,
				HLdb.COL_DESCIPTION, HLdb.COL_ANSWER1, HLdb.COL_ANSWER2,
				HLdb.COL_ANSWER3, HLdb.COL_ANSWER4, HLdb.KEY_ROWID,
				HLdb.COL_DIFFICULTY, HLdb.COL_ID, HLdb.COL_ANSWER_KEY,
				HLdb.COL_LAST_ANSWER };
		String randomOrder = "RANDOM() LIMIT 10";

		query = cr.query(HLProvider.CONTENT_URI, projection, null, null, randomOrder);

		int noOfRows = query.getCount();
		int iNote, iLastAnswer, iRandomizedLastAnswer  = 0;

		LayoutInflater inflater = this.activity.getLayoutInflater();
		TextView tv_question, tv_description, htv_rowid, htv_answerkey, htv_last_answer, htv_answerstring, tv_answer1, tv_answer2,  tv_answer3, tv_answer4;
		RadioGroup rg_answers;
		RadioButton rb_answer1;
		RadioButton rb_answer2;
		RadioButton rb_answer3;
		RadioButton rb_answer4;
		query.moveToFirst();

		String answerString[] = { "0", " ", " ", " ", " ", "0", "0", "0", "0","0" };
		RandomizeAnswers randomAnswers = new RandomizeAnswers();
		while (query.isAfterLast() == false) {
			verticalViewsAL
					.add(inflater.inflate(R.layout.test_row, null));
			tv_question = (TextView) verticalViewsAL.get(query.getPosition())
					.findViewById(R.id.tv_question);
			tv_description = (TextView) verticalViewsAL
					.get(query.getPosition()).findViewById(R.id.tv_description);
			tv_description = (TextView) verticalViewsAL
					.get(query.getPosition()).findViewById(R.id.tv_description);
			htv_rowid = (TextView) verticalViewsAL.get(query.getPosition())
					.findViewById(R.id.htv_rowid);

			String answerStrings[] = randomAnswers.getRandomAnswers(query);

			rg_answers = (RadioGroup) verticalViewsAL.get(query.getPosition())
					.findViewById(R.id.rg_answers);
			rb_answer1 = (RadioButton) verticalViewsAL.get(query.getPosition())
					.findViewById(R.id.rb_answer1);
			rb_answer2 = (RadioButton) verticalViewsAL.get(query.getPosition())
					.findViewById(R.id.rb_answer2);
			rb_answer3 = (RadioButton) verticalViewsAL.get(query.getPosition())
					.findViewById(R.id.rb_answer3);
			rb_answer4 = (RadioButton) verticalViewsAL.get(query.getPosition())
					.findViewById(R.id.rb_answer4);
/*			tv_answer1 = (TextView) verticalViewsAL.get(query.getPosition())
					.findViewById(R.id.tv_answer1);
			tv_answer2 = (TextView) verticalViewsAL.get(query.getPosition())
					.findViewById(R.id.tv_answer2);
			tv_answer3 = (TextView) verticalViewsAL.get(query.getPosition())
					.findViewById(R.id.tv_answer3);
			tv_answer4 = (TextView) verticalViewsAL.get(query.getPosition())
					.findViewById(R.id.tv_answer4);*/

			final RadioButton frb_answer1 = (RadioButton) verticalViewsAL.get(
					query.getPosition()).findViewById(R.id.rb_answer1);
			final RadioButton frb_answer2 = (RadioButton) verticalViewsAL.get(
					query.getPosition()).findViewById(R.id.rb_answer2);
			final RadioButton frb_answer3 = (RadioButton) verticalViewsAL.get(
					query.getPosition()).findViewById(R.id.rb_answer3);
			final RadioButton frb_answer4 = (RadioButton) verticalViewsAL.get(
					query.getPosition()).findViewById(R.id.rb_answer4);
			final TextView fhtv_rowid = (TextView) verticalViewsAL.get(
					query.getPosition()).findViewById(R.id.htv_rowid);
			final TextView fhtv_answerkey = (TextView) verticalViewsAL.get(
					query.getPosition()).findViewById(R.id.htv_answerkey);
			final TextView fhtv_last_answer = (TextView) verticalViewsAL.get(
					query.getPosition()).findViewById(R.id.htv_last_answer);
			final TextView fhtv_answerstring = (TextView) verticalViewsAL.get(
					query.getPosition()).findViewById(R.id.htv_answerstring);
			rg_answers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(RadioGroup group,
								int checkedId) {
							if (checkedId == -1)
								return;
							String row_Id = (String) fhtv_rowid.getText();
							if (checkedId == frb_answer1.getId()) {
								setupUpdateResult(row_Id, "1");
							} else if (checkedId == frb_answer2.getId()) {
								setupUpdateResult(row_Id, "2");
							} else if (checkedId == frb_answer3.getId()) {
								setupUpdateResult(row_Id, "3");
							} else if (checkedId == frb_answer4.getId()) {
								setupUpdateResult(row_Id, "4");
							}
						}

						private void setupUpdateResult(String row_Id, String clickedItem) {
							fhtv_last_answer.setText(clickedItem);
							// Need to un-randomize the last answer and it's correctness for the DB.
							boolean isCorrect = false;
							if (!fhtv_answerstring.getText().toString().equals("answerstring")) {
								StringBuffer sbTemp = new StringBuffer(fhtv_answerstring.getText().toString());
								String translatedLastAnswer =  Character.toString((char) sbTemp.charAt(Integer.parseInt(clickedItem)));
								if (translatedLastAnswer != null
										&& translatedLastAnswer.equals(fhtv_answerstring.getText().toString().substring(0, 1))) {
									isCorrect = true;
								}
// Now translate their answer choice back to it's original value in the DB, prior to being randomized,
// since the database has no knowledge of randomization of answers. Don't want to muddy the waters.
								if (translatedLastAnswer != null) {
//									int adjustRowID = Integer.parseInt(row_Id);
//									adjustRowID = adjustRowID + 1;
//									String adjustedRowID = (Integer.toString(adjustRowID));
									myonUpdateChosenAnswer(row_Id, translatedLastAnswer, isCorrect);
								}//   ques/a_key     ques/a_key     ques/a_key
							}    //   1      2       4      2       7      2
						}        //   2      1       5      3       8      2
					});          //   3      2       6      2       9      1
			                     //                                 10     4

			iNote = query.getPosition() + 1;
			fhtv_rowid.setText(query.getString(query
					.getColumnIndexOrThrow(HLdb.KEY_ROWID)));
			
			tv_question.setText("Q"
					+ iNote
					+ ".  "
					+ query.getString(query
							.getColumnIndexOrThrow(HLdb.COL_QUESTION)));

			rb_answer1.setText("A. " + answerStrings[1]);
			rb_answer2.setText("B. " + answerStrings[2]);
			rb_answer3.setText("C. " + answerStrings[3]);
			rb_answer4.setText("D. " + answerStrings[4]);

			tv_description.setText("Description.  "
					+ query.getString(query
							.getColumnIndexOrThrow(HLdb.COL_DESCIPTION)));

			iLastAnswer = 0;
			iRandomizedLastAnswer = 0;
			iLastAnswer = Integer.parseInt(query.getString(
					query.getColumnIndexOrThrow(HLdb.COL_LAST_ANSWER))
					.toString());
			iRandomizedLastAnswer = Integer.parseInt(answerStrings[9]); // needs to be translated.
			if (iRandomizedLastAnswer > 0 && iLastAnswer >  0) {
				fhtv_last_answer.setText(String.valueOf(iRandomizedLastAnswer));
				switch (iRandomizedLastAnswer) {
				case 1:
					rb_answer1.setChecked(true);
					break;
				case 2:
					rb_answer2.setChecked(true);
					break;
				case 3:
					rb_answer3.setChecked(true);
					break;
				case 4:
					rb_answer4.setChecked(true);
					break;
				}
			}
			int aRowID = query.getPosition();
//			htv_rowid.setText(String.valueOf(aRowID));
			fhtv_answerstring.setText(answerStrings[0] + answerStrings[5]
					+ answerStrings[6] + answerStrings[7] + answerStrings[8] + answerStrings[9]);

			query.moveToNext();
		}

		view1 = inflater.inflate(R.layout.vertical_pager1, null);
		pageViewsAL.add(view1);
		verticalPager1 = (VerticalViewPager) view1.findViewById(R.id.pager1);
		verticalPager2 = (VerticalViewPager) view1.findViewById(R.id.pager1);
		vAdapter1 = new VerticalAdapter(verticalViewsAL);
		verticalPager1.setAdapter(vAdapter1);
		basePager = (ViewPager) getView().findViewById(R.id.basePager);
		baseAdapter = new BasePagerAdapter(pageViewsAL);
		basePager.setAdapter(baseAdapter);
		// context = getActivity().getApplicationContext();
		// context = getActivity().getApplicationContext();
		// cursorAdapter = new CursorPagerAdapter(context, pageViews, query);
		// basePager.setAdapter(cursorAdapter);
		
		verticalPager1.setOnPageChangeListener(new OnPageChangeListener() {

            public void onPageSelected(int arg0) { }

            public void onPageScrolled(int arg0, float arg1, int arg2) { }

            public void onPageScrollStateChanged(int arg0) {
            	//((TestActivity) context).onShowFloatButton();
 //           	btn_floating_rb = (ImageButton) view1.findViewById(R.id.btn_floating_rb);
 //       		btn_floating_rb.bringToFront();
 //           	Log.e("TestFragment","onPageScrollStateChanged");
            	
            }
        });
		
	}
	Button.OnClickListener btnOnClickListener = new Button.OnClickListener() {
		

		@Override
		public void onClick(View v) {
//			Log.e("TestFragment", "Click Floating City in the clouds");
			mCallback.onSubmitSelected();
//			  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                  b = ActivityOptions.makeScaleUpAnimation(v, 0, 0, v.getWidth()/2, v.getHeight()/2).toBundle();
//                   b = ActivityOptions.makeScaleUpAnimation(v, (int)(v.getX() + v.getWidth()  / 2), (int)(v.getY() + v.getHeight() / 2), v.getWidth()/2, v.getHeight()/2).toBundle();
 //             }

			
		}
	};

	public void myonUpdateChosenAnswer(String id, String last_answers,
			boolean isCorrectAnswer) {

		ContentResolver cr = getActivity().getContentResolver();
		ContentValues cv = new ContentValues();
	//   ques/a_key     ques/a_key     ques/a_key
    //   1      2       4      2       7      2
    //   2      1       5      3       8      2
    //   3      2       6      2       9      1
    //                                 10     4
		if(isCorrectAnswer) {
			cv.put(HLdb.COL_CORRECT, 1);
			cv.put(HLdb.COL_INCORRECT, 0);
		} else {
			cv.put(HLdb.COL_CORRECT, 0);
			cv.put(HLdb.COL_INCORRECT, 1);
		}
		cv.put(HLdb.COL_LAST_ANSWER, Integer.parseInt(last_answers));
		String[] selectionArg1 = new String[] { id };

		int updatedRowCount = 0;

		updatedRowCount = cr.update(
		/* URI */HLProvider.CONTENT_URI,
		/* content values */cv,
		/* where */HLdb.KEY_ROWID + " = ? ",
		/* arguments */selectionArg1);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// loadPreferences();
		Log.i("ExperiementFragment", "LastScreen pref: " + prefLastScreen);

		String[] projection = new String[] { HLdb.COL_QUESTION,
				HLdb.COL_DESCIPTION, HLdb.COL_ANSWER1, HLdb.COL_ANSWER2,
				HLdb.COL_ANSWER3, HLdb.COL_ANSWER4, HLdb.KEY_ROWID,
				HLdb.COL_DIFFICULTY, HLdb.COL_ID, HLdb.COL_ANSWER_KEY };
		int[] viewfields = new int[] { R.id.tv_question, R.id.tv_descripton };

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.test_fragment, container,
				false);

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		query.close();
	}

}
