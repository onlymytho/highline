package com.hlresidential.aceyourexam;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class HLProvider extends ContentProvider {

	private HLdbHelper dbHelper;
	private static final String TAG = "HLProvider";

	private static final int ALL_QUESTIONS = 1;
	private static final int SINGLE_QUESTION = 2;
	private static final int SINGLE_QUESTION_UPDATE = 3;
	private static final int ALL_TESTS = 4;
	private static final int SINGLE_TESTS = 5;
	private static final int ALL_TEST_RESULTS = 6;
	private static final int SINGLE_TEST_RESULTS = 7;

	private static final String AUTHORITY = "com.hlresidential.testapp.contentproviderhl";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/questions");
	public static final Uri CONTENT_URI_UPDATE = Uri.parse("content://" + AUTHORITY
			+ "/update");
	public static final Uri CONTENT_URI_TESTS = Uri.parse("content://" + AUTHORITY
			+ "/tests");
	public static final Uri CONTENT_URI_TEST_RESULTS = Uri.parse("content://" + AUTHORITY
			+ "/test_results");

	public static final Uri CONTENT_URI_RAWQUERY = Uri.parse("content://"
			+ AUTHORITY + "/rawquery");

	private static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, "questions", ALL_QUESTIONS);
		uriMatcher.addURI(AUTHORITY, "questions/#", SINGLE_QUESTION);
		uriMatcher.addURI(AUTHORITY, "questions/#", SINGLE_QUESTION_UPDATE);
		uriMatcher.addURI(AUTHORITY, "tests/", ALL_TESTS);
		uriMatcher.addURI(AUTHORITY, "tests/#", SINGLE_TESTS);
		uriMatcher.addURI(AUTHORITY, "test_results/", ALL_TEST_RESULTS);
		uriMatcher.addURI(AUTHORITY, "test_results/#", SINGLE_TEST_RESULTS);
	}

	@Override
	public boolean onCreate() {
		// Log.i(TAG, "onCreate ");
		dbHelper = new HLdbHelper(getContext());
		return false;
	}

	@Override
	public String getType(Uri uri) {
//		 Log.i(TAG, "getType ");
		switch (uriMatcher.match(uri)) {
		case ALL_QUESTIONS:
//			Log.i(TAG, "getType:  ALL_QUESTIONS");
			return "vnd.android.cursor.dir/vnd.com.hlresidential.testapp.contentproviderhl.questions";
			
		case SINGLE_QUESTION:
//			Log.i(TAG, "getType:  SINGLE_QUESTION");
			return "vnd.android.cursor.item/vnd.com.hlresidential.testapp.contentproviderhl.questions/#";
		case SINGLE_QUESTION_UPDATE:
//			Log.i(TAG, "getType:  SINGLE_QUESTION_UPDATE");
			return "vnd.android.cursor.item/vnd.com.hlresidential.testapp.contentproviderhl.questions";
		case ALL_TESTS:
//			Log.i(TAG, "getType:  ALL_TESTS");
			return "vnd.android.cursor.dir/vnd.com.hlresidential.testapp.contentproviderhl.tests";	
		case SINGLE_TESTS:
//			Log.i(TAG, "getType:  SINGLE_TESTS");
			return "vnd.android.cursor.item/vnd.com.hlresidential.testapp.contentproviderhl.tests/#";
		case ALL_TEST_RESULTS:
//			Log.i(TAG, "getType:  ALL_TEST_RESULTS");
			return "vnd.android.cursor.dir/vnd.com.hlresidential.testapp.contentproviderhl.test_results";	
		case SINGLE_TEST_RESULTS:
//			Log.i(TAG, "getType:  SINGLE_TEST_RESULTS");
			return "vnd.android.cursor.item/vnd.com.hlresidential.testapp.contentproviderhl.test+results/#";
		default:
			throw new IllegalArgumentException("Unsupported URI Type: " + uri);
		}
	}

	private static final HashMap<String, String> SEARCH_PROJECTION_MAP;
	static {
		SEARCH_PROJECTION_MAP = new HashMap<String, String>();

		SEARCH_PROJECTION_MAP.put("_id", HLdb.KEY_ROWID + " AS " + "_id");
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {

		long id = 0;
		long rowID = 0;
		// Log.i(TAG, "insert ");
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		switch (uriMatcher.match(uri)) {
		case ALL_QUESTIONS:
			// do nothing
			break;
		case SINGLE_QUESTION:
			// do nothing
			break;
		case SINGLE_TESTS:
			rowID = db.insert(HLdb.TESTS_TABLE, null,
					values);

			if (rowID > 0) {
				uri = ContentUris.withAppendedId(CONTENT_URI_TESTS, rowID);
				getContext().getContentResolver().notifyChange(uri, null);
				return uri;
			}
			break;
		case ALL_TESTS:
			rowID = db.insert(HLdb.TESTS_TABLE, null,
					values);

			if (rowID > 0) {
				uri = ContentUris.withAppendedId(CONTENT_URI_TESTS, rowID);
				getContext().getContentResolver().notifyChange(uri, null);
				return uri;
			}
			break;	
		case ALL_TEST_RESULTS:
			rowID = db.insert(HLdb.TEST_RESULTS_TABLE, null,
					values);

			if (rowID > 0) {
				uri = ContentUris.withAppendedId(CONTENT_URI_TEST_RESULTS, rowID);
				getContext().getContentResolver().notifyChange(uri, null);
				return uri;
			}
			break;
		case SINGLE_TEST_RESULTS:
			rowID = db.insert(HLdb.TEST_RESULTS_TABLE, null,
					values);

			if (rowID > 0) {
				uri = ContentUris.withAppendedId(CONTENT_URI_TEST_RESULTS, rowID);
				getContext().getContentResolver().notifyChange(uri, null);
				return uri;
			}
			break;			
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		return Uri.parse(CONTENT_URI_TESTS + "/" + id);
	}

	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		String id;
		// Log.i(TAG, "query ");

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

		switch (uriMatcher.match(uri)) {
		case ALL_QUESTIONS:
			// do nothing
			queryBuilder.setTables(HLdb.QUESTION_TABLE);
			break;
		case SINGLE_QUESTION:
			queryBuilder.setTables(HLdb.QUESTION_TABLE);
			id = uri.getPathSegments().get(1);
			queryBuilder.appendWhere(HLdb.KEY_ROWID + "=" + id);
			break;
		case ALL_TESTS:
			// do nothing
			queryBuilder.setTables(HLdb.TESTS_TABLE);
			break;
		case SINGLE_TESTS:
			queryBuilder.setTables(HLdb.TESTS_TABLE);
			id = uri.getPathSegments().get(1);
			queryBuilder.appendWhere(HLdb.KEY_ROWID + "=" + id);
			break;
		case ALL_TEST_RESULTS:
			// do nothing
			queryBuilder.setTables(HLdb.TEST_RESULTS_TABLE);
			break;	
		case SINGLE_TEST_RESULTS:
			queryBuilder.setTables(HLdb.TEST_RESULTS_TABLE);
			id = uri.getPathSegments().get(1);
			queryBuilder.appendWhere(HLdb.KEY_ROWID + "=" + id);
			break;	
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}

		Cursor cursor = queryBuilder.query(db, projection, selection,
				selectionArgs, null, null, sortOrder);
		// Log.i(TAG, "query return cursor ");

		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;

	}


	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		int updateCount;
		String id;
//		 Log.i(TAG, "update ");
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		switch (uriMatcher.match(uri)) {
		case ALL_QUESTIONS:
			
//			Log.i(TAG, "update ALL_QUESTIONS" + ALL_QUESTIONS);
		//	selection = HLdb.KEY_ROWID
		//			+ "="
		//			+ id
		//			+ (!TextUtils.isEmpty(selection) ? " AND (" + selection
		//					+ ')' : "");
//			Log.i(TAG, "selection: " + selection + " values: " + values + " selectionArgs: " + selectionArgs);
			updateCount = db.update(HLdb.QUESTION_TABLE, values, selection,
					selectionArgs);
			getContext().getContentResolver().notifyChange(uri, null);
			// Log.i(TAG, "update return updateCount ");
			break;
		case SINGLE_QUESTION_UPDATE:
//			Log.i(TAG, "update SINGLE_QUESTION_UPDATE" + SINGLE_QUESTION_UPDATE);
			id = uri.getPathSegments().get(1);
			selection = HLdb.KEY_ROWID
					+ "="
					+ id
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection
							+ ')' : "");
//			Log.i(TAG, "selection: " + selection + " values: " + values + " selectionArgs: " + selectionArgs);
			updateCount = db.update(HLdb.QUESTION_TABLE, values, selection,
					selectionArgs);
			getContext().getContentResolver().notifyChange(uri, null);
			// Log.i(TAG, "update return updateCount ");
		case SINGLE_QUESTION:
//			Log.i(TAG, "update SINGLE_QUESTION" + SINGLE_QUESTION);
			id = uri.getPathSegments().get(1);
			selection = HLdb.KEY_ROWID
					+ "="
					+ id
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection
							+ ')' : "");
//			Log.i(TAG, "selection: " + selection + " values: " + values + " selectionArgs: " + selectionArgs);
			
			updateCount = db.update(HLdb.QUESTION_TABLE, values, selection,
					selectionArgs);
			getContext().getContentResolver().notifyChange(uri, null);
//			 Log.i(TAG, "update return updateCount ");
			break;
		case ALL_TESTS:
			
//			Log.i(TAG, "update ALL_TESTS" + ALL_TESTS);
		//	selection = HLdb.KEY_ROWID
		//			+ "="
		//			+ id
		//			+ (!TextUtils.isEmpty(selection) ? " AND (" + selection
		//					+ ')' : "");
//			Log.i(TAG, "selection: " + selection + " values: " + values + " selectionArgs: " + selectionArgs);
			updateCount = db.update(HLdb.TESTS_TABLE, values, selection,
					selectionArgs);
			getContext().getContentResolver().notifyChange(uri, null);
			// Log.i(TAG, "update return updateCount ");
			break;	
		case SINGLE_TESTS:
//			Log.i(TAG, "update SINGLE_TESTS" + SINGLE_TESTS);
			id = uri.getPathSegments().get(1);
			selection = HLdb.KEY_ROWID
					+ "="
					+ id
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection
							+ ')' : "");
//			Log.i(TAG, "selection: " + selection + " values: " + values + " selectionArgs: " + selectionArgs);
			
			updateCount = db.update(HLdb.TESTS_TABLE, values, selection,
					selectionArgs);
			getContext().getContentResolver().notifyChange(uri, null);
//			 Log.i(TAG, "update return updateCount ");
			break;
		default:
			
			throw new IllegalArgumentException("Unsupported URI update: " + uri);
		}
		return updateCount;
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

}
