package com.aygx.dazahui.fragment.news;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;

import com.aygx.dazahui.R;

public class BaseAdapter {
	public View view;
	public FrameLayout fl;
	public Activity mActivity;
	public BaseAdapter(Activity mActivity){
		this.mActivity = mActivity;
		initView();
	}
	

	public void initView() {
		view = View.inflate(mActivity, R.layout.news_baseadapter, null);
		fl = (FrameLayout) view.findViewById(R.id.news_frameLayout);
	}


	public void initData(){
	}
}
