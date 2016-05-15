package com.aygx.dazahui.adapter;

import java.util.List;

import com.aygx.dazahui.fragment.news.BaseAdapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class NewsViewPager extends PagerAdapter {
	private List<BaseAdapter> activityList;
	private List<String> titleList;

	public NewsViewPager(List<BaseAdapter> activity, List<String> titleList) {
		this.activityList = activity;
		this.titleList = titleList;
	}
	
	@Override
	public int getCount() {
		return activityList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		BaseAdapter baseAdapter = activityList.get(position);
		container.addView(baseAdapter.view);
		baseAdapter.initData();
		return baseAdapter.view;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View)object);
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return titleList.get(position);
		
	}


}
