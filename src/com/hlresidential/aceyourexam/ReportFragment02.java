package com.hlresidential.aceyourexam;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;



import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class ReportFragment02 extends Fragment {
	

	List<CategorySeries> series = new ArrayList<CategorySeries>();
	
	Activity activity;
	View view;
	private static final String TAG = ReportFragment02.class.getSimpleName();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.report_graph02, container, false);
		
		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		int[] y = { 200, 250, 300};
		String[] label = {"InCorrect","Median","Correct"};
	
		for (int x = 0; x < label.length; x++) {
			CategorySeries seri = new CategorySeries(label[x]);
		    series.add(seri);
		 
		}
	
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
	
				series.get(0).add("Bar " + (1 + 1), 689);
				series.get(1).add("Bar " + (2 + 2), 227);
				series.get(2).add("Bar " + (3 + 3), 300);
				
				series.get(0).add("Bar " + (1 + 1), 800);
				series.get(1).add("Bar " + (2 + 2), 500);
				series.get(2).add("Bar " + (3 + 3), 400);
				
				series.get(0).add("Bar " + (1 + 1), 700);
				series.get(1).add("Bar " + (2 + 2), 800);
				series.get(2).add("Bar " + (3 + 3), 300);
				dataset.addSeries(series.get(0).toXYSeries());
				dataset.addSeries(series.get(1).toXYSeries());
				dataset.addSeries(series.get(2).toXYSeries());
		

	
		LinearLayout ln = (LinearLayout) getView().findViewById(R.id.chart02);

		
		

		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.setChartTitle("Two Test Results");
		mRenderer.setXTitle("Test Number");
		mRenderer.setYTitle("Score");
		mRenderer.setAxesColor(Color.parseColor("#D7372F"));
		mRenderer.setLabelsColor(Color.parseColor("#000000"));
		
		mRenderer.setBackgroundColor(Color.parseColor("#FFFFFF"));
	
		mRenderer.setBarSpacing(1);
	
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(Color.WHITE);
		mRenderer.setMarginsColor(Color.WHITE);
	
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setColor((getResources().getColor(R.color.toreview)));
		renderer.setDisplayChartValues(true);
		renderer.setChartValuesSpacing(200);
		mRenderer.addSeriesRenderer(renderer);
		
		XYSeriesRenderer renderer2 = new XYSeriesRenderer();
		renderer2.setColor(getResources().getColor(R.color.practice));
		renderer2.setDisplayChartValues(true);

		mRenderer.addSeriesRenderer(renderer2);
		
		XYSeriesRenderer renderer3 = new XYSeriesRenderer();
		renderer3.setColor(getResources().getColor(R.color.study));
		renderer3.setDisplayChartValues(true);
		
		mRenderer.addSeriesRenderer(renderer3);

		
/*		LinearLayout ln = (LinearLayout) getView().findViewById(R.id.chart02);
		String[] titles = new String[] { "Sales growth January 1995 to December 2000" };
	    List<Date[]> dates = new ArrayList<Date[]>();
	    List<double[]> values = new ArrayList<double[]>();
	    Date[] dateValues = new Date[] { new Date(95, 0, 1), new Date(95, 3, 1), new Date(95, 6, 1),
	        new Date(95, 9, 1), new Date(96, 0, 1), new Date(96, 3, 1), new Date(96, 6, 1),
	        new Date(96, 9, 1), new Date(97, 0, 1), new Date(97, 3, 1), new Date(97, 6, 1),
	        new Date(97, 9, 1), new Date(98, 0, 1), new Date(98, 3, 1), new Date(98, 6, 1),
	        new Date(98, 9, 1), new Date(99, 0, 1), new Date(99, 3, 1), new Date(99, 6, 1),
	        new Date(99, 9, 1), new Date(100, 0, 1), new Date(100, 3, 1), new Date(100, 6, 1),
	        new Date(100, 9, 1), new Date(100, 11, 1) };
	    dates.add(dateValues);

	    values.add(new double[] { 4.9, 5.3, 3.2, 4.5, 6.5, 4.7, 5.8, 4.3, 4, 2.3, -0.5, -2.9, 3.2, 5.5,
	        4.6, 9.4, 4.3, 1.2, 0, 0.4, 4.5, 3.4, 4.5, 4.3, 4 });
	    int[] colors = new int[] { Color.BLUE };
	    PointStyle[] styles = new PointStyle[] { PointStyle.POINT };
	    XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
	    setChartSettings(renderer, "Sales growth", "Date", "%", dateValues[0].getTime(),
	        dateValues[dateValues.length - 1].getTime(), -4, 11, Color.GRAY, Color.LTGRAY);
	    renderer.setYLabels(10);
	    renderer.setXRoundedLabels(false);
	    XYSeriesRenderer xyRenderer = (XYSeriesRenderer) renderer.getSeriesRendererAt(0);
	    FillOutsideLine fill = new FillOutsideLine(FillOutsideLine.Type.BOUNDS_ABOVE);
	    fill.setColor(Color.GREEN);
	    xyRenderer.addFillOutsideLine(fill);
	    fill = new FillOutsideLine(FillOutsideLine.Type.BOUNDS_BELOW);
	    fill.setColor(Color.MAGENTA);
	    xyRenderer.addFillOutsideLine(fill);
	    fill = new FillOutsideLine(FillOutsideLine.Type.BOUNDS_ABOVE);
	    fill.setColor(Color.argb(255, 0, 200, 100));
	    fill.setFillRange(new int[] {10, 19});
	    xyRenderer.addFillOutsideLine(fill);
	    
	   

//	    return ChartFactory.getTimeChartIntent(context, buildDateDataset(titles, dates, values),
//	        renderer, "MMM yyyy");
		
//	    return  ChartFactory.getTimeChartView(context, buildDateDataset(titles, dates, values),
//		        renderer, "MMM yyyy");*/
	    
		
		view = ChartFactory.getBarChartView(getActivity(), dataset, mRenderer, Type.DEFAULT);
				
		view.setBackgroundColor(Color.parseColor("#FFFFFF"));
		
		ln.addView(view);
	}
	



}
