package com.hlresidential.aceyourexam;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class WasToReviewFragment extends Fragment implements
		OnCheckedChangeListener, OnClickListener {
	
	public interface TouchAnimationEndListener {
	public void onAnimationTouchToggler(boolean enabled);
	}

	public static final String PREF_LAST_SCREEN = "prefLastScreen";
	public static final String PREF_LAST_SCREEN_MAIN = "prefLastScreenMain";
	public static final String PREF_LAST_SCREEN_STUDY = "prefLastScreenStudy";
	public static final String PREF_LAST_SCREEN_PRACTICE = "prefLastScreenPractice";
	public static final String PREF_LAST_SCREEN_TEST = "prefLastScreenTest";
	public static final String PREF_LAST_SCREEN_REPORT = "prefLastScreenReport";
	public static final String PREF_LAST_SCREEN_TOREVIEW = "prefLastScreenToReview";
	public static final String PREF_LAST_SCREEN_MORE = "prefLastScreenMore";
	public static final String CURSOR_FRAGMENT_POS = "cursorFragmentPos";
	private int cursor_fragment_pos = -1;
	Bundle outState;
	private boolean ignoreClick = true;

	// Preferences
	SharedPreferences preferences;
	private SharedPreferences.Editor editor = null;
	private boolean isInitialized = false;
	String prefLastScreen = " ";
	View linear_layout_odd, linear_layout_even;
	private ViewSwitcher viewSwitcher;

	Button btn_prev_even, btn_next_even, btn_submit_even, btn_prev_odd, btn_next_odd, btn_submit_odd;
	ContentResolver cr;
	ContentValues values;
	Cursor cursor;
	ScrollView scrollview_even, scrollview_odd;
	ViewGroup viewgroup;
	TextView tv_odd_question;
	RadioGroup rg_odd;
	RadioButton rb_odd_answer1;
	RadioButton rb_odd_answer2;
	RadioButton rb_odd_answer3;
	RadioButton rb_odd_answer4;
	TextView tv_odd_description;
	View view_odd;
	Integer iChosen = 0;
	Integer ioddChosen = 0;
	Integer ievenChosen = 0;
	Integer iCorrect = 0;
	Integer ioddCorrect = 0;
	Integer ievenCorrect = 0;
	TextView tv_even_question;
	RadioGroup rg_even;
	RadioButton rb_even_answer1;
	RadioButton rb_even_answer2;
	RadioButton rb_even_answer3;
	RadioButton rb_even_answer4;
	TextView tv_even_description;

	View view_even;

	Activity activity;
	View view;
	private static final String TAG = WasToReviewFragment.class.getSimpleName();
	String lstScreen = " ";
	boolean willAnimateFlip = true;
	boolean oddSetFlipOff = false;
	boolean odd = false;

	// Cursor paging variables
	int j = 0;
	int iNote = 0;
	int no2 = 4;
	boolean initialized = false;
	boolean isItMoveNext = true;
	int iRepeat = 9;
	int iRepeatHalfLife = 5;
	float fDistance = 15f;
	float fDistanceHalfLife = 10f;
	ObjectAnimator animX = null;
	ObjectAnimator animY = null;
	String random_answer1 = " ";
	String random_answer2 = " ";
	String random_answer3 = " ";
	String random_answer4 = " ";

	TextView tv_even_temp, tv_odd_temp;


	private GestureDetector gestureDetector;
	View.OnClickListener clickListener;

	boolean backPressed = false;
	private ArrayList<ViewSwitcherPOJO> screenStatusArray;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState != null) {
			// Since this fragment doesn't rely on fragment arguments, we must
			// handle state restores and saves ourselves.
			outState.putInt(CURSOR_FRAGMENT_POS, cursor_fragment_pos);
			// mViewType = savedInstanceState.getInt(STATE_VIEW_TYPE);
			// mTrackId = savedInstanceState.getString(STATE_SELECTED_TRACK_ID);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.toreview_flip, container, false);

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

	}

	@Override
	public void onStart() {
		super.onStart();

		RandomizeAnswers randomAnswers = new RandomizeAnswers();
		setRetainInstance(true);
//L		Log.e(TAG, "setup FragmentTitle ... just started");

		viewgroup = (ViewGroup) getView().findViewById(R.id.container);
		linear_layout_even = (View) getView().findViewById(
				R.id.linear_layout_even);
		linear_layout_odd = (View) getView().findViewById(
				R.id.linear_layout_odd);

		scrollview_odd = (ScrollView) getView().findViewById(
				R.id.scrollview_odd);
		TextView tv_odd_question = (TextView) getView().findViewById(
				R.id.tv_odd_question);

		btn_prev_even = (Button) getView().findViewById(R.id.btn_prev_even);
		btn_prev_even.setOnClickListener((OnClickListener) this);

		btn_next_even = (Button) getView().findViewById(R.id.btn_next_even);
		btn_next_even.setOnClickListener((OnClickListener) this);
		
		btn_submit_even = (Button) getView().findViewById(R.id.btn_submit_even);
		btn_submit_even.setOnClickListener((OnClickListener) this);	
		
		btn_prev_odd = (Button) getView().findViewById(R.id.btn_prev_odd);
		btn_prev_odd.setOnClickListener((OnClickListener) this);

		btn_next_odd = (Button) getView().findViewById(R.id.btn_next_odd);
		btn_next_odd.setOnClickListener((OnClickListener) this);
		
		btn_submit_odd = (Button) getView().findViewById(R.id.btn_submit_odd);
		btn_submit_odd.setOnClickListener((OnClickListener) this);	
		
		rg_odd = (RadioGroup) getView().findViewById(R.id.rg_odd);
		rb_odd_answer1 = (RadioButton) getView().findViewById(
				R.id.rb_odd_answer1);
		rb_odd_answer2 = (RadioButton) getView().findViewById(
				R.id.rb_odd_answer2);
		rb_odd_answer3 = (RadioButton) getView().findViewById(
				R.id.rb_odd_answer3);
		rb_odd_answer4 = (RadioButton) getView().findViewById(
				R.id.rb_odd_answer4);
		tv_odd_description = (TextView) getView().findViewById(
				R.id.tv_odd_descripton);
		view_odd = (View) getView().findViewById(R.id.view_odd);
		rg_odd.setOnCheckedChangeListener(this);

		scrollview_even = (ScrollView) getView().findViewById(
				R.id.scrollview_even);
		tv_even_question = (TextView) getView().findViewById(
				R.id.tv_even_question);
		rg_even = (RadioGroup) getView().findViewById(R.id.rg_even);
		rb_even_answer1 = (RadioButton) getView().findViewById(
				R.id.rb_even_answer1);
		rb_even_answer2 = (RadioButton) getView().findViewById(
				R.id.rb_even_answer2);
		rb_even_answer3 = (RadioButton) getView().findViewById(
				R.id.rb_even_answer3);
		rb_even_answer4 = (RadioButton) getView().findViewById(
				R.id.rb_even_answer4);
		tv_even_description = (TextView) getView().findViewById(
				R.id.tv_even_descripton);
		view_even = (View) getView().findViewById(R.id.view_even);
		rg_even.setOnCheckedChangeListener(this);

		ViewGroup container = viewgroup;

		viewSwitcher = new ViewSwitcher(container);

		tv_even_temp = (TextView) getView().findViewById(R.id.tv_even_temp);
		tv_odd_temp = (TextView) getView().findViewById(R.id.tv_odd_temp);
	}

	public void onClick(View v) {
		String direction = "";
		if (v == btn_prev_even  || v == btn_prev_odd) {
			onForeignButtonSelected(2);
		} else if (v == btn_next_even  || v == btn_next_odd) {
			onForeignButtonSelected(3);
		} else if (v == btn_submit_even  || v == btn_submit_odd) {
			onForeignButtonSelected(1);
		}  
	}
	
	@Override
	public void onResume() {
		super.onResume();
		this.activity = activity;

		cr = getActivity().getContentResolver();
		values = new ContentValues();

		String randomOrder = "RANDOM() LIMIT 10";
		String[] projection = new String[] { HLdb.COL_QUESTION,
				HLdb.COL_DESCIPTION, HLdb.COL_ANSWER1, HLdb.COL_ANSWER2,
				HLdb.COL_ANSWER3, HLdb.COL_ANSWER4, HLdb.COL_ANSWER_KEY,
				HLdb.KEY_ROWID, HLdb.COL_DIFFICULTY, HLdb.COL_ID,
				HLdb.COL_LAST_ANSWER };

		String selection = HLdb.COL_ACTIVE + "= ? ";
		String[] selectionArgs = new String[] { "1" };
		cursor = cr.query(HLProvider.CONTENT_URI, projection, null, null,
				randomOrder);
				//TODO
//				null);

		screenStatusArray = new ArrayList<ViewSwitcherPOJO>(cursor.getCount());
		for (int i = 0; i < (cursor.getCount()); i++) {
			ViewSwitcherPOJO screenStatusPOJOItem = new ViewSwitcherPOJO();
			screenStatusPOJOItem.setAll(false, false, -1, isOdd(cursor.getPosition()),
					"0", " ", " ", " ", " ","0","0","0","0","0");

			screenStatusArray.add(screenStatusPOJOItem);
		}

		LinearLayout myEvenLayout = (LinearLayout) getView().findViewById(
				R.id.linear_layout_even);
		int indexEven = ((ViewGroup) myEvenLayout.getParent())
				.indexOfChild(myEvenLayout);
		LinearLayout myOddLayout = (LinearLayout) getView().findViewById(
				R.id.linear_layout_odd);
		int indexOdd = ((ViewGroup) myOddLayout.getParent())
				.indexOfChild(myOddLayout);

		if (cursor_fragment_pos != -1) {
			if (cursor != null) {
				cursor.moveToPosition(cursor_fragment_pos);
				if (isOdd(cursor.getPosition())) {
					odd = true;
				} else {
					odd = false;
				}
			}

		} else if (cursor != null) {
			nextRow();
		}

	}

	@Override
	public void onAttach(Activity activity) {
			super.onAttach(activity);

	}

	public void onNoMoreRows() {
		Animator anim = AnimatorInflater.loadAnimator(this.getActivity(),
				R.anim.right_at_end);
		anim.setTarget(viewgroup);
		if (!backPressed) {
			anim.start();
		}
	}

	void rowDirection(String direction) {

		if (direction.equals("Previous") && !cursor.isFirst()) {
			prevRow();
			viewSwitcher.swap();
 		} else if (direction.equals("Previous") && cursor.getPosition() < 1) {
			onNoMoreRows();
		}
		if (direction.equals("Next") && !cursor.isLast()) {
			nextRow();
			viewSwitcher.swap();
		} else if (direction.equals("Next")
				&& cursor.getPosition() == (cursor.getCount()) - 1) {
			onNoMoreRows();
		}
	}

	private void nextRow() {
//L		Log.e(TAG, "nextRow()");
		cursor.moveToNext();
		rowOddOrEven();
	}

	private void prevRow() {
		//L		Log.e(TAG, "prevRow()");
		cursor.moveToPrevious();
		rowOddOrEven();
	}

	public void rowOddOrEven() {
		//L	Log.e(TAG, "rowOddOrEven()");
		if (isOdd(cursor.getPosition())) { 
			oddRow();
		} else {
			evenRow();
		}
	}

	private void oddRow() {
		//L		Log.e(TAG, "oddRow()");
		String answerString[] = { "0", " ", " ", " ", " ", "0", "0", "0", "0",
				"0" };
		RandomizeAnswers randomAnswers = new RandomizeAnswers();
		cursor_fragment_pos = cursor.getPosition(); // #### ??
		odd = true;
		tv_odd_question = (TextView) getView().findViewById(
				R.id.tv_odd_question);
		//L		Log.d(TAG, "is ODD ______");
		tv_odd_question.setVisibility(View.VISIBLE);
		tv_odd_description.setVisibility(View.GONE);
		ViewSwitcherPOJO screenStatusPOJOItem = new ViewSwitcherPOJO();
		String answerStrings[] = randomAnswers.getRandomAnswers(cursor);

		if (screenStatusArray.get(cursor_fragment_pos).getScreenRowID() == -1
				&& cursor_fragment_pos > -1
				&& cursor_fragment_pos < (cursor.getCount() )) { //pjb removed -1
				screenStatusPOJOItem.setAll(true, false, cursor.getColumnIndexOrThrow(HLdb.KEY_ROWID),
						isOdd(cursor_fragment_pos),
						answerStrings[0], answerStrings[1],  answerStrings[2], answerStrings[3],
						answerStrings[4], answerStrings[5], answerStrings[6], answerStrings[7],
						answerStrings[8], answerStrings[9]);
				screenStatusArray.set(cursor_fragment_pos, screenStatusPOJOItem);
			
		} 
		iCorrect = screenStatusArray.get(cursor_fragment_pos).getCorrectAnswer();
		rb_odd_answer1.setVisibility(View.VISIBLE);
		rb_odd_answer2.setVisibility(View.VISIBLE);
		rb_odd_answer3.setVisibility(View.VISIBLE);
		rb_odd_answer4.setVisibility(View.VISIBLE);
		rb_odd_answer4.setVisibility(View.VISIBLE);
		btn_prev_odd.setVisibility(View.VISIBLE);
		btn_next_odd.setVisibility(View.VISIBLE);
		btn_submit_odd.setVisibility(View.VISIBLE);
		int iLastAnswer = 0;
		int iRandomizedLastAnswer = 0;
		iLastAnswer = Integer.parseInt(screenStatusArray.get(cursor_fragment_pos).getTranslatedLastAnswer());

		switch (Integer.parseInt(screenStatusArray.get(cursor_fragment_pos).getTranslatedLastAnswer())) {
		case 1:
			rb_odd_answer1.setChecked(true);
			break;
		case 2:
			rb_odd_answer2.setChecked(true);
			break;
		case 3:
			rb_odd_answer3.setChecked(true);
			break;
		case 4:
			rb_odd_answer4.setChecked(true);
			break;
		default:
			rg_odd.clearCheck();
		}
		iNote = cursor.getPosition() + 1; // remote + 1

		tv_odd_question.setText("Q"
				+ iNote
				+ ". "
//				+ cursor.getString(cursor.getColumnIndexOrThrow(HLdb.COL_ID))
//				+ " <id> "
				+ cursor.getString(cursor
						.getColumnIndexOrThrow(HLdb.COL_QUESTION)));

		rb_odd_answer1.setText(screenStatusArray.get(cursor_fragment_pos).getAnswerOneText());
		rb_odd_answer2.setText(screenStatusArray.get(cursor_fragment_pos).getAnswerTwoText());
		rb_odd_answer3.setText(screenStatusArray.get(cursor_fragment_pos).getAnswerThreeText());
		rb_odd_answer4.setText(screenStatusArray.get(cursor_fragment_pos).getAnswerFourText());
		ioddCorrect = iCorrect;

		tv_odd_description.setText("Description.  "
				+ cursor.getString(cursor
						.getColumnIndexOrThrow(HLdb.COL_DESCIPTION)));
		if (screenStatusArray.get(cursor_fragment_pos).isScreenIsAnswered()) {
			tv_odd_temp.setText("view ANSWERED! (ODD). c:" + ioddCorrect + " clkd:" + screenStatusArray.get(cursor_fragment_pos).getTranslatedLastAnswer());
			onOddSubmitButton();
		} else {
			tv_odd_temp.setText("NOT Answered NOT (ODD).");
		}
	}

	private void evenRow() {
		//L		Log.e(TAG, "evenRow()");
		String answerString[] = { "0", " ", " ", " ", " ", "0", "0", "0", "0",
				"0" };
		RandomizeAnswers randomAnswers = new RandomizeAnswers();
		cursor_fragment_pos = cursor.getPosition();
		odd = false;
		tv_even_question = (TextView) getView().findViewById(
				R.id.tv_even_question);
		//L		Log.d(TAG, "is EVEN ______");
		tv_even_question.setVisibility(View.VISIBLE);
		tv_even_description.setVisibility(View.GONE);
		ViewSwitcherPOJO screenStatusPOJOItem = new ViewSwitcherPOJO();
		String answerStrings[] = randomAnswers.getRandomAnswers(cursor);

		if (screenStatusArray.get(cursor_fragment_pos).getScreenRowID() == -1
				&& cursor_fragment_pos > -1
				&& cursor_fragment_pos < (cursor.getCount() - 1)) {
				screenStatusPOJOItem.setAll(true, false, cursor.getColumnIndexOrThrow(HLdb.KEY_ROWID),
						isOdd(cursor_fragment_pos),
						answerStrings[0], answerStrings[1],  answerStrings[2], answerStrings[3],
						answerStrings[4], answerStrings[5], answerStrings[6], answerStrings[7],
						answerStrings[8], answerStrings[9]);
				screenStatusArray.set(cursor_fragment_pos, screenStatusPOJOItem);
			
		} 
		iCorrect = screenStatusArray.get(cursor_fragment_pos).getCorrectAnswer(); // is wrong after EOF
		
		rb_even_answer1.setChecked(false);
		rb_even_answer2.setChecked(false);
		rb_even_answer3.setChecked(false);
		rb_even_answer4.setChecked(false);
		rb_even_answer1.setVisibility(View.VISIBLE);
		rb_even_answer2.setVisibility(View.VISIBLE);
		rb_even_answer3.setVisibility(View.VISIBLE);
		rb_even_answer4.setVisibility(View.VISIBLE);
		rb_even_answer4.setVisibility(View.VISIBLE);
		btn_prev_even.setVisibility(View.VISIBLE);
		btn_next_even.setVisibility(View.VISIBLE);
		btn_submit_even.setVisibility(View.VISIBLE);
		int iLastAnswer = 0;
		int iRandomizedLastAnswer = 0;
		iLastAnswer = Integer.parseInt(screenStatusArray.get(cursor_fragment_pos).getTranslatedLastAnswer());

		switch (Integer.parseInt(screenStatusArray.get(cursor_fragment_pos).getTranslatedLastAnswer())) {
		case 1:
			rb_even_answer1.setChecked(true);
			break;
		case 2:
			rb_even_answer2.setChecked(true);
			break;
		case 3:
			rb_even_answer3.setChecked(true);
			break;
		case 4:
			rb_even_answer4.setChecked(true);
			break;
		default:
			rg_even.clearCheck();
		}

		iNote = cursor.getPosition() + 1; 

		tv_even_question.setText("Q"
				+ iNote
				+ ". "
//				+ cursor.getString(cursor.getColumnIndexOrThrow(HLdb.COL_ID))
//				+ " <id> "
				+ cursor.getString(cursor
						.getColumnIndexOrThrow(HLdb.COL_QUESTION)));

		rb_even_answer1.setText(screenStatusArray.get(cursor_fragment_pos).getAnswerOneText());
		rb_even_answer2.setText(screenStatusArray.get(cursor_fragment_pos).getAnswerTwoText());
		rb_even_answer3.setText(screenStatusArray.get(cursor_fragment_pos).getAnswerThreeText());
		rb_even_answer4.setText(screenStatusArray.get(cursor_fragment_pos).getAnswerFourText());
		ievenCorrect = iCorrect;

		tv_even_description.setText("Description.  "
				+ cursor.getString(cursor
						.getColumnIndexOrThrow(HLdb.COL_DESCIPTION)));
		if (screenStatusArray.get(cursor_fragment_pos).isScreenIsAnswered()) {
			tv_even_temp.setText("view ANSWERED! (EVEN). c:" + ievenCorrect + " clkd:" + screenStatusArray.get(cursor_fragment_pos).getTranslatedLastAnswer());
			onEvenSubmitButton();
		} else {
			tv_even_temp.setText("NOT Answered NOT (EVEN).");
		}
	}

	public void onSubmitButtons(boolean odd) {
		//L		Log.e(TAG, "onSubmitButtons(");
		if (odd) {
			onOddSubmitButton();
		} else {
			onEvenSubmitButton();
		}
	}

	private void onOddSubmitButton() {
		//L		Log.e(TAG, "onOddSubmitButton()");
		screenStatusArray.get(cursor_fragment_pos).setScreenIsAnswered(true);
		iCorrect = screenStatusArray.get(cursor_fragment_pos).getCorrectAnswer();
		ioddCorrect = iCorrect;
		ioddChosen = Integer.parseInt(screenStatusArray.get(cursor_fragment_pos).getTranslatedLastAnswer());
		tv_odd_temp.setText("view ANSWERED! (ODD). c:" + ioddCorrect + " clkd:" + screenStatusArray.get(cursor_fragment_pos).getTranslatedLastAnswer());
		tv_odd_temp.setText("view ANSWERED! (ODD). c:" + ioddCorrect + " clkd:" + ioddChosen);

		rb_odd_answer1.setChecked(false); ignoreClick = true;
		rb_odd_answer2.setChecked(false); ignoreClick = true;
		rb_odd_answer3.setChecked(false); ignoreClick = true;
		rb_odd_answer4.setChecked(false); ignoreClick = true;
		rb_odd_answer1.setVisibility(View.GONE);
		rb_odd_answer2.setVisibility(View.GONE);
		rb_odd_answer3.setVisibility(View.GONE);
		rb_odd_answer4.setVisibility(View.GONE);
		tv_odd_description.setVisibility(View.VISIBLE);

		switch (ioddCorrect) {
		case 1:
			rb_odd_answer1.setChecked(true); ignoreClick = true;
			rb_odd_answer1.setVisibility(View.VISIBLE);
			break;
		case 2:
			rb_odd_answer2.setChecked(true); ignoreClick = true;
			rb_odd_answer2.setVisibility(View.VISIBLE);
			break;
		case 3:
			rb_odd_answer3.setChecked(true); ignoreClick = true;
			rb_odd_answer3.setVisibility(View.VISIBLE);
			break;
		case 4:
			rb_odd_answer4.setChecked(true); ignoreClick = true;
			rb_odd_answer4.setVisibility(View.VISIBLE);
			break;
		}
		//L		Log.e(TAG, "... ioddCorrect: " + ioddCorrect + " ioodChosen: " + ioddChosen + " pos: " + cursor_fragment_pos);
		if (ioddCorrect == ioddChosen) { 
		} else {

			animY = ObjectAnimator.ofFloat(linear_layout_odd, "translationY",
					fDistanceHalfLife);
			animY = ObjectAnimator.ofFloat(linear_layout_odd, "translationX",
					fDistanceHalfLife);
			switch (ioddChosen) {
			case 1:
				rb_odd_answer1.setVisibility(View.VISIBLE);
				animX = ObjectAnimator.ofFloat(rb_odd_answer1, "translationX",
						fDistance);
				break;
			case 2:
				rb_odd_answer2.setVisibility(View.VISIBLE);
				animX = ObjectAnimator.ofFloat(rb_odd_answer2, "translationX",
						fDistance);
				break;
			case 3:
				rb_odd_answer3.setVisibility(View.VISIBLE);
				animX = ObjectAnimator.ofFloat(rb_odd_answer3, "translationX",
						fDistance);
				break;
			case 4:
				rb_odd_answer4.setVisibility(View.VISIBLE);
				animX = ObjectAnimator.ofFloat(rb_odd_answer4, "translationX",
						fDistance);
				break;
			}

			animX.setRepeatCount(iRepeat);
			animX.setRepeatMode(ObjectAnimator.REVERSE);
			animX.setDuration(100);

			AnimatorSet animSetXY = new AnimatorSet();
			animY.setRepeatCount(iRepeatHalfLife);
			animY.setRepeatMode(ObjectAnimator.REVERSE);
			animY.setDuration(50);

			animSetXY.playSequentially(animY, animX);
			animSetXY.start();
		}
	}

	private void onEvenSubmitButton() {
		//L		Log.e(TAG, "onEvenSubmitButton()");
		screenStatusArray.get(cursor_fragment_pos).setScreenIsAnswered(true);
		iCorrect = screenStatusArray.get(cursor_fragment_pos).getCorrectAnswer();
		ievenCorrect = iCorrect;
		ievenChosen = Integer.parseInt(screenStatusArray.get(cursor_fragment_pos).getTranslatedLastAnswer());;// Let see
		
		tv_even_temp.setText("view ANSWERED! (EVEN). c:" + ievenCorrect + " clkd:" + screenStatusArray.get(cursor_fragment_pos).getTranslatedLastAnswer());
		//L		Log.e(TAG, "view ANSWERED! (EVEN). c:" + ievenCorrect + " clkd:" + screenStatusArray.get(cursor_fragment_pos).getTranslatedLastAnswer());
		//L		Log.e(TAG, "view ANSWERED! (EVEN). c:" + ievenCorrect + " clkd:" + ievenCorrect);

	
		rb_even_answer1.setChecked(false); ignoreClick = true;
		rb_even_answer2.setChecked(false); ignoreClick = true;
		rb_even_answer3.setChecked(false); ignoreClick = true;
		rb_even_answer4.setChecked(false); ignoreClick = true;
		rb_even_answer1.setVisibility(View.GONE);
		rb_even_answer2.setVisibility(View.GONE);
		rb_even_answer3.setVisibility(View.GONE);
		rb_even_answer4.setVisibility(View.GONE);
		tv_even_description.setVisibility(View.VISIBLE);

		
		switch (ievenCorrect) {
		case 1:
			rb_even_answer1.setChecked(true); ignoreClick = true;
			rb_even_answer1.setVisibility(View.VISIBLE);
			break;
		case 2:
			rb_even_answer2.setChecked(true); ignoreClick = true;
			rb_even_answer2.setVisibility(View.VISIBLE);
			break;
		case 3:
			rb_even_answer3.setChecked(true); ignoreClick = true;
			rb_even_answer3.setVisibility(View.VISIBLE);
			break;
		case 4:
			rb_even_answer4.setChecked(true); ignoreClick = true;
			rb_even_answer4.setVisibility(View.VISIBLE);
			break;
		}
		//L		Log.e(TAG, "... ievenCorrect: " + ievenCorrect + " ievenChosen: " + ievenChosen + " pos: " + cursor_fragment_pos);
		if (ievenCorrect == ievenChosen) {
		} else {

			animY = ObjectAnimator.ofFloat(linear_layout_even, "translationY",
					fDistanceHalfLife);
			animY = ObjectAnimator.ofFloat(linear_layout_even, "translationX",
					fDistanceHalfLife);
			switch (ievenChosen) {  
			case 1:
				rb_even_answer1.setVisibility(View.VISIBLE);
				animX = ObjectAnimator.ofFloat(rb_even_answer1, "translationX",
						fDistance);
				break;
			case 2:
				rb_even_answer2.setVisibility(View.VISIBLE);
				animX = ObjectAnimator.ofFloat(rb_even_answer2, "translationX",
						fDistance);
				break;
			case 3:
				rb_even_answer3.setVisibility(View.VISIBLE);
				animX = ObjectAnimator.ofFloat(rb_even_answer3, "translationX",
						fDistance);
				break;
			case 4:
				rb_even_answer4.setVisibility(View.VISIBLE);
				animX = ObjectAnimator.ofFloat(rb_even_answer4, "translationX",
						fDistance);
				break;
			}

			animX.setRepeatCount(iRepeat);
			animX.setRepeatMode(ObjectAnimator.REVERSE);
			animX.setDuration(100);

			AnimatorSet animSetXY = new AnimatorSet();
			animY.setRepeatCount(iRepeatHalfLife);
			animY.setRepeatMode(ObjectAnimator.REVERSE);
			animY.setDuration(50);

			animSetXY.playSequentially(animY, animX);
			animSetXY.start();
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		//L		Log.e(TAG, "onCheckedChanged(");
		if(ignoreClick) {
			ignoreClick = false;
			return;
		}
		ignoreClick = false;	
		iChosen = 0;
		if(isOdd(cursor_fragment_pos)) {
		ioddChosen = iChosen = Integer.parseInt(screenStatusArray.get(cursor_fragment_pos).getTranslatedLastAnswer());
		} else {
		ievenChosen = iChosen = Integer.parseInt(screenStatusArray.get(cursor_fragment_pos).getTranslatedLastAnswer());
		}
		switch (checkedId) {
		case R.id.rb_odd_answer1:
			iChosen = 1;
			ioddChosen = 1;
			break;
		case R.id.rb_odd_answer2:
			iChosen = 2;
			ioddChosen = 2;
			break;
		case R.id.rb_odd_answer3:
			iChosen = 3;
			ioddChosen = 3;
			break;
		case R.id.rb_odd_answer4:
			iChosen = 4;
			ioddChosen = 4;
			break;
		case R.id.rb_even_answer1:
			iChosen = 1;
			ievenChosen = 1;
			break;
		case R.id.rb_even_answer2:
			iChosen = 2;
			ievenChosen = 2;
			break;
		case R.id.rb_even_answer3:
			iChosen = 3;
			ievenChosen = 3;
			break;
		case R.id.rb_even_answer4:
			iChosen = 4;
			ievenChosen = 4;
			break;

		}
		if(iChosen > 0) {
		screenStatusArray.get(cursor_fragment_pos).setTranslatedLastAnswer(iChosen.toString());
		//L		Log.e(TAG, "iChosen has be set to : " + iChosen);
		}
	}

	private void setupScrollBarColor() {
		//L		Log.e(TAG, "setupScrollBarColor()");
		// even
		try {
			Field mScrollCacheField = View.class
					.getDeclaredField("mScrollCache");
			mScrollCacheField.setAccessible(true);
			Object mScrollCache = mScrollCacheField.get(scrollview_even);

			Field scrollBarField = mScrollCache.getClass().getDeclaredField(
					"scrollBar");
			scrollBarField.setAccessible(true);
			Object scrollBar = scrollBarField.get(mScrollCache);

			Method method = scrollBar.getClass().getDeclaredMethod(
					"setVerticalThumbDrawable", Drawable.class);
			method.setAccessible(true);

			if (lstScreen.equals(PREF_LAST_SCREEN_REPORT)) {
				method.invoke(scrollBar,
						getResources().getDrawable(R.color.report));
			} else {
				method.invoke(scrollBar,
						getResources().getDrawable(R.color.toreview));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// =================================================================
		// odd
		try {
			Field mScrollCacheField_odd = View.class
					.getDeclaredField("mScrollCache");
			mScrollCacheField_odd.setAccessible(true);
			Object mScrollCache_odd = mScrollCacheField_odd.get(scrollview_odd);

			Field scrollBarField_odd = mScrollCache_odd.getClass()
					.getDeclaredField("scrollBar");
			scrollBarField_odd.setAccessible(true);
			Object scrollBar_odd = scrollBarField_odd.get(mScrollCache_odd);

			Method method = scrollBar_odd.getClass().getDeclaredMethod(
					"setVerticalThumbDrawable", Drawable.class);
			method.setAccessible(true);

			if (lstScreen.equals(PREF_LAST_SCREEN_REPORT)) {
				method.invoke(scrollBar_odd,
						getResources().getDrawable(R.color.report));
			} else {
				method.invoke(scrollBar_odd,
						getResources().getDrawable(R.color.toreview));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	boolean isOdd(int val) {
		return (val & 0x01) != 0;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		cursor.close();
	}

	public void onForeignButtonSelected(int which) {
				Log.e(TAG, "onForeignButtonSelected Positon: " + cursor.getPosition());
		cursor_fragment_pos = cursor.getPosition();
		iChosen = Integer.parseInt(screenStatusArray.get(cursor_fragment_pos).getTranslatedLastAnswer());
		
		String direction = "";
		switch (which) {
		case 1:
			//L			Log.e(TAG, "onSubmitSelected from Main Activity");
			if(isOdd(cursor.getPosition())) {
				odd = true;
				ioddChosen = iChosen;
			} else {
				odd = false;
				ievenChosen = iChosen;
			}
			onSubmitButtons(odd);
			break;
		case 2:
			//L			Log.e(TAG, "onPrevSelected from Main Activity");
			direction = "Previous";
			rowDirection(direction);
			break;
		case 3:
			//L			Log.e(TAG, "onNextSelected from Main Activity");
			direction = "Next";
			rowDirection(direction);
			break;
		}
	}

}


