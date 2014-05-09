package com.hlresidential.aceyourexam;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HLdbHelper extends SQLiteOpenHelper {

	public static String DATABASE_PATH;
	public static final String DATABASE_NAME = "hl.db";
	private static final int DATABASE_VERSION = 1;
	private Context context;
	private SQLiteDatabase db;

	public HLdbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
		String packageName = context.getPackageName();
		DATABASE_PATH = String.format(context.getString(R.string.str_databasepath),
				packageName);
		openDataBase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		HLdb.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		HLdb.onUpgrade(db, oldVersion, newVersion);
	}

	// Performing a database existence check
	private boolean checkDataBase() {
		SQLiteDatabase checkDb = null;
		try {
			String path = DATABASE_PATH + DATABASE_NAME;
			checkDb = SQLiteDatabase.openDatabase(path, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (SQLException e) {
			Log.e(this.getClass().toString(), context.getString(R.string.str_error_while_checking_db));
		}

		if (checkDb != null) {
			checkDb.close();
		}
		return checkDb != null;
	}

	// Method for copying the database
	private void copyDataBase() throws IOException {
		//Log.i(this.getClass().toString(), "... in copyDataBase ");
		InputStream externalDbStream = context.getAssets().open(DATABASE_NAME);

		String outFileName = DATABASE_PATH + DATABASE_NAME;

		OutputStream localDbStream = new FileOutputStream(outFileName);

		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = externalDbStream.read(buffer)) > 0) {
			localDbStream.write(buffer, 0, bytesRead);
		}

		localDbStream.close();
		externalDbStream.close();
	}

	public void createDataBase() {
		//Log.i(this.getClass().toString(), "... in createDataBase ");
		boolean dbExist = checkDataBase();
		if (!dbExist) {
			this.getReadableDatabase();
			try {
				copyDataBase();
			} catch (IOException e) {
				Log.e(this.getClass().toString(), context.getString(R.string.str_copying_error));
				throw new Error(context.getString(R.string.str_error_copying_database_exclamation));
			}
		} else {
			//Log.i(this.getClass().toString(), "Database already exists");
		}
	}

	public SQLiteDatabase openDataBase() throws SQLException {
		String path = DATABASE_PATH + DATABASE_NAME;
		// Log.i(this.getClass().toString(), "Starting openDatabase " + path);
		if (db == null) {
			createDataBase();
			db = SQLiteDatabase.openDatabase(path, null,
					SQLiteDatabase.OPEN_READWRITE);
		}

		return db;
	}

}
