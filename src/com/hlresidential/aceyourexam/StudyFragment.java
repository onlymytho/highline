package com.hlresidential.aceyourexam;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

public class StudyFragment extends MyListFragment implements
		LoaderManager.LoaderCallbacks<Cursor>{

	private static String str_where = HLdb.COL_ACTIVE + " = 1";

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
 
	}

	SimpleCursorAdapter adapter;
	Activity activity;

	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		loadPreferences();
		Log.i("StudyFragment", "LastScreen pref: " + prefLastScreen);
//		loadPreferences();
//		RelativeLayout view_answers = (RelativeLayout) getView().findViewById(R.id.view_answers); 
//		TextView tv_answer = (TextView) getView().findViewById(R.id.tv_answer);
//		if(prefLastScreen.equals(PREF_LAST_SCREEN_STUDY)) {
//			view_answers.setVisibility(View.GONE);
//			tv_answer.setVisibility(View.VISIBLE);
//		} else {
//			view_answers.setVisibility(View.VISIBLE);
//			tv_answer.setVisibility(View.GONE);
//		} 
		
		String[] columns = new String[] { HLdb.COL_QUESTION,
				HLdb.COL_DESCIPTION, HLdb.COL_ANSWER1, HLdb.COL_ANSWER2,
				HLdb.COL_ANSWER3, HLdb.COL_ANSWER4, HLdb.KEY_ROWID,
				HLdb.COL_DIFFICULTY, HLdb.COL_ID, HLdb.COL_ANSWER_KEY };
		int[] viewfields = new int[] { R.id.tv_question, R.id.tv_descripton };

//		SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),
//				R.layout.study_row, null, columns, viewfields);
		
		adapter = new StudyFragmentAdapter(getActivity(),
				R.layout.study_row, null, columns, viewfields);

		setListAdapter(adapter);

		getListView().setDivider(new ColorDrawable(R.color.gray));
		getListView().setDividerHeight(5);
		getListView().setOverScrollMode(getListView().OVER_SCROLL_NEVER);
		

		
//		getListView().setOnItemClickListener(myOnItemClickListener);
		getLoaderManager().initLoader(0, null, this);

	}





	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		return super.onCreateView(inflater, container, savedInstanceState);
	}





	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		

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
			String[] projection = new String[] { HLdb.COL_QUESTION, HLdb.COL_DESCIPTION, HLdb.COL_ANSWER1 , HLdb.COL_ANSWER2, HLdb.COL_ANSWER3, HLdb.COL_ANSWER4, HLdb.KEY_ROWID, HLdb.COL_DIFFICULTY, HLdb.COL_ID, HLdb.COL_ANSWER_KEY};

		CursorLoader loader = new CursorLoader(getActivity(),
				HLProvider.CONTENT_URI, projection, str_where, null, null);
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
}
