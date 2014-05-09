package com.hlresidential.aceyourexam;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;



import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class ReportFragment extends Fragment {
	

//	 series = new ArrayList<TimeSeries>();
	
	Activity activity;
	View view;
	private static final String TAG = ReportFragment.class.getSimpleName();

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
	private boolean isInitialized = false;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		loadPreferences();
		Log.i("StudyFragment", "LastScreen pref: " + prefLastScreen);
		view = inflater.inflate(R.layout.report_graph, container, false);
		
		return view;
	}
	
		
	@Override
	public void onStart() {
		super.onStart();
		
		
//		int[] x = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//		int[] y = { 30, 34, 45, 57, 77, 89, 100, 111, 123, 154};
//		String[] label = {"One","Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten"};
//		int[] y = { 100, 200, 300};
//		int[] y = { 100};		
//		String[] label = {"InCorrect","Median","Correct"};
//		String[] label = {"Single"};
		
//		Button btn_clear = (Button) getView().findViewById(R.id.btn_clear);
//		btn_clear.setOnClickListener(btnOnClickListener);
		

		ContentResolver cr = getActivity().getContentResolver();
		String[] projection2 = new String[] { HLdb.KEY_ROWID, HLdb.COL_NO_MILLISECONDS, HLdb.COL_NO_CORRECT, HLdb.COL_NO_QUESTIONS};
//		String[] selectionArg2 = new String[] { lastTestID };

		Cursor query = cr.query(HLProvider.CONTENT_URI_TESTS, projection2, null,
				null, null) ;
		TimeSeries series1 = new TimeSeries("Time");
		TimeSeries series2 = new TimeSeries("Score");
		TimeSeries series3 = new TimeSeries("Pass-Fail");
		int noOfRows = 0;
		if (query.getCount() > 0) {
			noOfRows = query.getCount();
			query.moveToFirst();
			
			for (int i = 0; i < noOfRows; i++) {
				int test_no = query.getInt(query.getColumnIndexOrThrow(HLdb.KEY_ROWID));
				int no_correct = query.getInt(query.getColumnIndexOrThrow(HLdb.COL_NO_CORRECT));
				int TotalNoOfRows = query.getInt(query.getColumnIndexOrThrow(HLdb.COL_NO_QUESTIONS));
				int seconds = (int) ((query.getInt(query.getColumnIndexOrThrow(HLdb.COL_NO_MILLISECONDS)) / 1000));
				int minutes = (int) (((query.getInt(query.getColumnIndexOrThrow(HLdb.COL_NO_MILLISECONDS)) / 1000) %3600 ) / 60);
				if(minutes == 0) minutes = 1;
				float iPercentComplete = 0;
				try {
					iPercentComplete = ((float) no_correct / TotalNoOfRows) * 100;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				    series1.add(test_no, minutes);
				    series2.add(test_no, no_correct);
				    series3.add(test_no, 7);
				    query.moveToNext();
				}
			
		} 
		
//		tv_time.setText("" + (seconds / 3600) + ":" + ((seconds % 3600) / 60) + ":" + ((seconds % 3600) % 60));
//		tv_no_correct.setText(query.getString(query.getColumnIndexOrThrow(HLdb.COL_NO_CORRECT)));	
//		TimeSeries series = new TimeSeries("line 1");
//		for (int i = 0; i < label.length; i++) {
//			    series.add(x[i], y[i]);
//			}
		
	
		
		
	
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		dataset.addSeries(series3);
		LinearLayout ln = (LinearLayout) getView().findViewById(R.id.chart);

		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.setChartTitle("Score / Time (seconds)");
		mRenderer.setXTitle("Test Number");
		mRenderer.setYTitle("Score");
		mRenderer.setAxesColor(Color.parseColor("#D7372F"));
		mRenderer.setLabelsColor(Color.parseColor("#000000"));
		mRenderer.setBackgroundColor(Color.parseColor("#FFFFFF"));
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(Color.WHITE);
		mRenderer.setMarginsColor(Color.WHITE);
		mRenderer.setShowGrid(true);
		
		
	
		XYSeriesRenderer renderer1 = new XYSeriesRenderer();
		renderer1.setColor((getResources().getColor(R.color.practice)));
		renderer1.setDisplayChartValues(true);
		renderer1.setChartValuesSpacing(10);
		renderer1.setPointStyle(PointStyle.TRIANGLE);
		renderer1.setFillPoints(true);
		mRenderer.addSeriesRenderer(renderer1);
		
		XYSeriesRenderer renderer2 = new XYSeriesRenderer();
		renderer2.setColor((getResources().getColor(R.color.study)));
		renderer2.setDisplayChartValues(true);
		renderer2.setChartValuesSpacing(20);
		renderer2.setPointStyle(PointStyle.CIRCLE);
		renderer2.setFillPoints(true);
		mRenderer.addSeriesRenderer(renderer2);
		
		XYSeriesRenderer renderer3 = new XYSeriesRenderer();
		renderer3.setColor((getResources().getColor(R.color.toreview)));
		renderer3.setDisplayChartValues(true);
		renderer3.setChartValuesSpacing(5);
		renderer3.setPointStyle(PointStyle.SQUARE);
		renderer3.setFillPoints(true);
		mRenderer.addSeriesRenderer(renderer3);

//		view = ChartFactory.getBarChartView(getActivity(), dataset, mRenderer, Type.DEFAULT);
		view = ChartFactory.getLineChartView(getActivity(), dataset, mRenderer);		
		view.setBackgroundColor(Color.parseColor("#FFFFFF"));
		
		ln.addView(view);
		
		query.close();
		
	}
	
	protected void loadPreferences() {
		{
			if (!isInitialized) {
				preferences = PreferenceManager
						.getDefaultSharedPreferences(this.getActivity());
				isInitialized = true;
			}
			
			prefLastScreen = preferences.getString(PREF_LAST_SCREEN, " ");

		}
	}
	  
	private OnClickListener btnOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
		Log.e(TAG, "oooooooooooon Click City");

		}
	};
	 
	
	
}
