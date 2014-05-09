package com.hlresidential.aceyourexam;

import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class BasePagerAdapter extends PagerAdapter {
	List<View> mListViews;
	private Context context;

	public BasePagerAdapter(List<View> mListViews) {
		// TODO Auto-generated constructor stub
		this.mListViews = mListViews;
		this.context = context;
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView(mListViews.get(arg1));
	}

	@Override
	public int getCount() {
		return mListViews.size();
	}

	@Override
	public Object instantiateItem(View view, int pos) {
//		LayoutInflater inflater = (LayoutInflater) view.getContext()
//	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		((ViewPager) view).addView(mListViews.get(pos), 0);
		return mListViews.get(pos);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == (arg1);
	}
}