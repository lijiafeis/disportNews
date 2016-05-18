package com.aygx.dazahui.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.aygx.dazahui.R;
import com.aygx.dazahui.adapter.Guide_ViewPager_Adapter;
import com.aygx.dazahui.utils.ShareUtils;

public class GuideActivity extends Activity {
	private ViewPager viewPager;
	private List<View> list;// 放引导页面。
	private Button button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guide);
		
		//判断share中有没有帐号。
		
		
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		button = (Button) findViewById(R.id.guide_button);
		init();
	}

	private void init() {
		list = new ArrayList<View>();
		View guide_1 = View.inflate(this, R.layout.guide_1, null);
		list.add(guide_1);
		View guide_2 = View.inflate(this, R.layout.guide_2, null);
		list.add(guide_2);
		View guide_3 = View.inflate(this, R.layout.guide_3, null);
		list.add(guide_3);
		View guide_4 = View.inflate(this, R.layout.guide_4, null);
		list.add(guide_4);
		if(list != null){
			setViewPagerAdapter();
		}
	}

	private Guide_ViewPager_Adapter adapter;
	
	private void setViewPagerAdapter() {
		adapter = new Guide_ViewPager_Adapter(list);
		viewPager.setAdapter(adapter);
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				button.setVisibility(arg0 == 3?View.VISIBLE:View.GONE);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		
	}
	
	public void click(View v){
		ShareUtils.setGuideShare(this,true);
		startActivity(new Intent(GuideActivity.this,MainActivity.class));
		finish();
	}

}
