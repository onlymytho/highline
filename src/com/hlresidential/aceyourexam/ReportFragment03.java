package com.hlresidential.aceyourexam;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
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

public class ReportFragment03 extends Fragment {
	

	List<CategorySeries> series = new ArrayList<CategorySeries>();
	
	Activity activity;
	View view;
	private static final String TAG = ReportFragment03.class.getSimpleName();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.report_graph03, container, false);
		
		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		int[] y = { 100, 200, 300};
		String[] label = {"InCorrect","Median","Correct"};
	
		for (int x = 0; x < label.length; x++) {
			CategorySeries seri = new CategorySeries(label[x]);
		    series.add(seri);
		 
		}
	
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
	
				series.get(0).add("Bar " + (1 + 1), 100);
				series.get(1).add("Bar " + (2 + 2), 700);
				series.get(2).add("Bar " + (3 + 3), 300);
				
				series.get(0).add("Bar " + (1 + 1), 400);
				series.get(1).add("Bar " + (2 + 2), 700);
				series.get(2).add("Bar " + (3 + 3), 600);
				
				series.get(0).add("Bar " + (1 + 1), 500);
				series.get(1).add("Bar " + (2 + 2), 800);
				series.get(2).add("Bar " + (3 + 3), 200);
				dataset.addSeries(series.get(0).toXYSeries());
				dataset.addSeries(series.get(1).toXYSeries());
				dataset.addSeries(series.get(2).toXYSeries());
		

	
		LinearLayout ln = (LinearLayout) getView().findViewById(R.id.chart03);

		
		

		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.setChartTitle("Free Test Results");
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
		renderer.setColor((getResources().getColor(R.color.practice)));
		renderer.setDisplayChartValues(true);
		renderer.setChartValuesSpacing(200);
		mRenderer.addSeriesRenderer(renderer);
		
		XYSeriesRenderer renderer2 = new XYSeriesRenderer();
		renderer2.setColor(getResources().getColor(R.color.test));
		renderer2.setDisplayChartValues(true);

		mRenderer.addSeriesRenderer(renderer2);
		
		XYSeriesRenderer renderer3 = new XYSeriesRenderer();
		renderer3.setColor(getResources().getColor(R.color.study));
		renderer3.setDisplayChartValues(true);
		
		mRenderer.addSeriesRenderer(renderer3);

		view = ChartFactory.getBarChartView(getActivity(), dataset, mRenderer, Type.DEFAULT);
				
		view.setBackgroundColor(Color.parseColor("#FFFFFF"));
		
		ln.addView(view);
	}
	



}
