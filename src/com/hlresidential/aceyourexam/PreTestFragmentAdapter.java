package com.hlresidential.aceyourexam;

import java.util.HashMap;



import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class PreTestFragmentAdapter extends SimpleCursorAdapter {

	private Context context;
	private final LayoutInflater inflater;
//	private String move_score = "";
//	private boolean isWhite = true;
//	private String score_number;
	private int pos = 0;
	

	HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
	private PostTestActivity mPostTestActivity;
	

//	public interface OnMoveScoreSelectedListener {
//		public void onMoveScoreClickSelected(boolean isWhite, String score_number);
//	}
	
    protected static class ViewHolder {
        protected TextView h_test_id;
        protected TextView h_test_id_only;
        protected TextView h_time;
        protected TextView h_no_correct_no_rows;
        protected int position;
      }
	
	public PreTestFragmentAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to) {
		super(context, layout, c, from, to);
		this.context = context;
		inflater = LayoutInflater.from(context);
		
	}
	
	   @Override
	    public boolean hasStableIds() {
	        return true;
	    }

	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
		View view = inflater.inflate(R.layout.pretest_row, parent, false);

		holder.h_test_id = (TextView) view.findViewById(R.id.tv_test_id);
		holder.h_test_id_only = (TextView) view.findViewById(R.id.tv_test_id_only);
//		holder.h_test_id.setOnClickListener(mOnClickListener);
		holder.h_time = (TextView) view.findViewById(R.id.tv_time);
//		holder.h_time.setOnClickListener(mOnClickListener);
		holder.h_no_correct_no_rows = (TextView) view.findViewById(R.id.tv_no_correct_no_rows);
//		holder.h_no_correct_no_rows.setOnClickListener(mOnClickListener);
		holder.position = cursor.getPosition();
//		pos = holder.position;
		bindView(view, context, cursor);
        view.setTag(holder);
		return view;
	}


	public void bindView(View view, final Context context, Cursor cursor) {


		TextView tv_test_id = (TextView) view.findViewById(R.id.tv_test_id);
		TextView tv_test_id_only = (TextView) view.findViewById(R.id.tv_test_id_only);
		TextView tv_time = (TextView) view.findViewById(R.id.tv_time);
		TextView tv_no_correct_no_rows = (TextView) view.findViewById(R.id.tv_no_correct_no_rows);
//		tv_test_id.setOnClickListener(mOnClickListener);
//		tv_time.setOnClickListener(mOnClickListener);
//		tv_no_correct_no_rows.setOnClickListener(mOnClickListener);
		int iNote = cursor.getPosition() + 1;
		pos  = cursor.getPosition();
		int seconds = (int) (cursor.getInt(cursor.getColumnIndexOrThrow(HLdb.COL_NO_MILLISECONDS)) / 1000); 

		if (cursor != null) {
			tv_test_id.setText("TEST " + cursor.getString(cursor.getColumnIndexOrThrow(HLdb.KEY_ROWID)) + ". ");
			tv_test_id_only.setText(cursor.getString(cursor.getColumnIndexOrThrow(HLdb.KEY_ROWID)));
			tv_time.setText("" + (seconds / 3600) + "h" + ((seconds % 3600) / 60) + "m" + ((seconds % 3600) % 60)+"s");
			tv_no_correct_no_rows.setText(cursor.getString(cursor.getColumnIndexOrThrow(HLdb.COL_NO_CORRECT)) + " / " + cursor.getString(cursor.getColumnIndexOrThrow(HLdb.COL_NO_QUESTIONS)));

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

}
