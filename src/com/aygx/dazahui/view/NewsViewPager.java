package com.aygx.dazahui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
/**
 * 事件分发机制
 * @author Administrator
 *
 */
public class NewsViewPager  extends ViewPager{

	public NewsViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public NewsViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	/*
	 * 当第一页的时候就可以侧滑，
	 * @see android.view.ViewGroup#dispatchTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if(getCurrentItem() == 0){
			getParent().requestDisallowInterceptTouchEvent(false);
		}else{
			getParent().requestDisallowInterceptTouchEvent(true);
		}
		return super.dispatchTouchEvent(ev);
	}

}
