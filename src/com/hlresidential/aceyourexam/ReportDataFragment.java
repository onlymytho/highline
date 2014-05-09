package com.hlresidential.aceyourexam;



import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

public class ReportDataFragment extends MyListFragment implements
		LoaderManager.LoaderCallbacks<Cursor>{

//	private static String str_where = HLdb.COL_ACTIVE + " = 1";
	//Shared Preferences
	SharedPreferences preferences;
	private SharedPreferences.Editor editor = null;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		activity = this.activity;
	}

	SimpleCursorAdapter adapter;
	Activity activity;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		
		loadPreferences();
		Log.i("PreTestFragment", "LastScreen pref: " + prefLastScreen);
		String[] columns = new String[] { HLdb.KEY_ROWID, HLdb.COL_NO_MILLISECONDS, HLdb.COL_NO_CORRECT, HLdb.COL_NO_QUESTIONS };

		int[] viewfields = new int[] { R.id.tv_test_id, R.id.tv_time };

//		SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),
//				R.layout.study_row, null, columns, viewfields);
		
		adapter = new ReportDataFragmentAdapter(getActivity(),
				R.layout.pretest_row, null, columns, viewfields);

		setListAdapter(adapter);

		getListView().setDivider(new ColorDrawable(R.color.gray));
		getListView().setDividerHeight(5);
		getListView().setOverScrollMode(getListView().OVER_SCROLL_NEVER);
		

		
//		getListView().setOnItemClickListener(myOnItemClickListener);
		getLoaderManager().initLoader(0, null, this);
		OnItemClickListener listener = new OnItemClickListener() 
		{
			@Override
			public void onItemClick ( AdapterView<?> arg0, View v, int position, long id )
			{
//				TextView tv_test_id_only = (TextView) v.findViewById(R.id.tv_test_id_only);
				
				
//				setPreference(PREF_LAST_TEST_ID, tv_test_id_only.getText().toString());
				
//				Toast.makeText(getActivity().getBaseContext(), " test id: " + Integer.parseInt(tv_test_id_only.getText().toString()), Toast.LENGTH_SHORT ).show();
//				 Intent i = new Intent(getActivity(), PostTestActivity.class);
//				 getActivity().startActivity(i);
			}
		};
		
		getListView().setOnItemClickListener(listener);
	}





	@Override
	public void onDestroy(){
	    super.onDestroy();
	   ;
	   ((SimpleCursorAdapter) this.getListAdapter()).swapCursor(null);
//	    threadutenti.cancel(true);
	}
	
		Handler handler = new Handler();
		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			String[] projection = new String[] { HLdb.KEY_ROWID, HLdb.COL_NO_MILLISECONDS, HLdb.COL_NO_CORRECT, HLdb.COL_NO_QUESTIONS};

		CursorLoader loader = new CursorLoader(getActivity(),
				HLProvider.CONTENT_URI_TESTS, projection, null, null, " 1 DESC");
			String[] argz = new String[] { "'text'" };
			return loader;
		}

		@Override
		public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
			((SimpleCursorAdapter) this.getListAdapter()).swapCursor(cursor);
			
		}

		@Override
		public void onLoaderReset(Loader<Cursor> arg0) {
			((SimpleCursorAdapter) this.getListAdapter()).swapCursor(null);
			
		}
		
		private void setPreference(String prefname, String prefvalue) {
			preferences = PreferenceManager
					.getDefaultSharedPreferences(this.getActivity());
			editor = preferences.edit();
			editor.putString(prefname, prefvalue);
			editor.commit();
		}
}

