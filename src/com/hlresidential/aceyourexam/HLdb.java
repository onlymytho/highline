package com.hlresidential.aceyourexam;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class HLdb {

	public static final String LOG_TAG = "hl";

	// tables
	public static final String QUESTION_TABLE = "questions";
	public static final String TESTS_TABLE = "tests";
	public static final String TEST_RESULTS_TABLE = "test_results";


	// questions Table Columns
	public static final String KEY_ROWID = "_id";
	public static final String COL_ID = "id";
	public static final String COL_QUESTION = "question";

	public static final String COL_DESCIPTION = "description";
	public static final String COL_ANSWER1 = "answer1";
	public static final String COL_ANSWER2 = "answer2";
	public static final String COL_ANSWER3 = "answer3";
	public static final String COL_ANSWER4 = "answer4";
	public static final String COL_GENTIMESTAMP = "gentimestamp";
	public static final String COL_CORRECT = "correct";
	public static final String COL_INCORRECT = "incorrect";
	public static final String COL_TESTTIMESTAMP = "testtimestamp";
	public static final String COL_ANSWER_KEY = "answer_key";
	public static final String COL_CATEGORY = "category";
	public static final String COL_SUBCATEGORY = "subcategory";
	public static final String COL_SUBCATEGORYORDER = "subcategoryorder";
	public static final String COL_ACTIVE = "active";
	public static final String COL_LAST_ANSWER = "last_answer";
	public static final String COL_DIFFICULTY = "difficulty";
	public static final String COL_INFO_LINK = "info_link";
	public static final String COL_FUTURE = "future";

	// categories Table Columns - not previously defined
	
	public static final String COL_CAT_DESCRIPTION = "cat_description";
	public static final String COL_CAT_LEVEL = "cat_level";

	// tests Table Columns - test taken header - not previously defined
	
	public static final String COL_TEST_DATE  = "test_date";
	public static final String COL_EXAM_DATE = "exam_date";
	public static final String COL_NO_QUESTIONS = "no_questions";
	public static final String COL_NO_MILLISECONDS 	= "no_milliseconds";
	public static final String COL_PERCENT_COMPLETE 	= "percent_complete";
	public static final String COL_QUESTION_ID = "question_id";
	public static final String COL_NO_CORRECT = "no_correct";
	public static final String COL_NO_INCORRECT = "no_incorrect";
	
	// test_results Table Columns - test taken details / facts - all columns previously defined



//	public static final String[] PROJECTION_CHAPTER = { KEY_ROWID, COL_CHAPTER,
//			COL_CHAPTERTITLE, };

	

	/* ************** MAKE SURE THERE'S and _id *********************** */
	public static final String RAWQUERY_1 = "select rules._id, rule, chapter, ruletitle, rules.keydescriptor, keydesc FROM rules LEFT OUTER JOIN keydescriptors ON rules.keydescriptor = keydescriptors.keydescriptor WHERE chapter = ?  and rules.keydescriptor = '2' group by 1, 2, 3, 4, 5 order by 1, 4";

	public static void onCreate(SQLiteDatabase db) {

		// Log.i(LOG_TAG, "onCreate");
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion,
			int newVersion) {
		Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");

		onCreate(db);
	}
}
