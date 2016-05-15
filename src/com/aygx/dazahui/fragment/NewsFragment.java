package com.aygx.dazahui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aygx.dazahui.R;
import com.aygx.dazahui.adapter.NewsViewPager;
import com.aygx.dazahui.fragment.news.BaseAdapter;
import com.aygx.dazahui.fragment.news.OneActivity;
import com.aygx.dazahui.fragment.news.TwoActivity;

public class NewsFragment extends Fragment {

	private View view;
	private ViewPager viewPager_news;
	private PagerTabStrip tabStrip;
	private List<BaseAdapter> activityList;// 存放Activity的集合
	private List<String> titleList;// 存放上面标题的集合

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_home_news, null);
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		viewPager_news = (ViewPager) view.findViewById(R.id.news_viewPager);
		setTitleStrip();
		setViewPager();
		NewsViewPager newsViewPager = new NewsViewPager(activityList, titleList);

		viewPager_news.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// 得到显示的页面
				// 只用滑动到这个页面的时候，才开始加载页面的数据。
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		viewPager_news.setAdapter(newsViewPager);

	}

	// 对ViewPager进行设计,显示的页面进行存储
	private void setViewPager() {
		activityList = new ArrayList<BaseAdapter>();
		OneActivity one = new OneActivity(getActivity());
		activityList.add(one);
		TwoActivity two = new TwoActivity(getActivity());
		activityList.add(two);
	}

	// 对上面的标题进行设计
	private void setTitleStrip() {
		titleList = new ArrayList<String>();
		titleList.add("新闻");
		titleList.add("娱乐");
		tabStrip = (PagerTabStrip) view.findViewById(R.id.news_tabstrip);
		tabStrip.setDrawFullUnderline(false);// 取消tab下面的长横线
		tabStrip.setBackgroundColor(getActivity().getResources().getColor(
				R.color.news_bg));
		// 设置当前tab页签的下划线颜色
		tabStrip.setTabIndicatorColor(getActivity().getResources().getColor(
				R.color.news_viewtitle));
		tabStrip.setTextSpacing(100);

	}
}
