package com.aygx.dazahui.fragment.news;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.aygx.dazahui.R;
import com.aygx.dazahui.adapter.Loading_listView_Adapter;
import com.aygx.dazahui.bean.ReDianNews;
import com.aygx.dazahui.bean.ReDianNews.Result;
import com.aygx.dazahui.db.MyReDianNewsDb;
import com.aygx.dazahui.network.NewsConnect;
import com.aygx.dazahui.utils.DBUtils;
import com.aygx.dazahui.utils.Utils;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.event.OnKey;

public class LoaddingNewsActivity extends Activity implements
		OnItemClickListener {
	private String title = null;
	private ListView loaddingListView;
	
	public static final String URL = "url";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.news_lodding_pager);

		initView();

		getIntentString();

		getDataFromNet();

	}

	private void initView() {
		loaddingListView = (ListView) findViewById(R.id.news_loading_listView);
		AlphaAnimation alpha = new AlphaAnimation(0.0f, 1.0f);
		alpha.setDuration(2000);
		loaddingListView.setAnimation(alpha);
	
	}

	private void getIntentString() {
		intent = getIntent();
		title = intent.getStringExtra(OneActivity.NEWS_TITILE);
		Utils.showToast(this, title);
	}

	// 从网上的到数据，在通过回调函数返回回来。
	private void getDataFromNet() {
		new NewsConnect(new NewsConnect.SetReDianNews() {

			@Override
			public void getData(String relute) {
				showDataToListView(relute);

			}

		}).getNews(title);

	}

	private List<ReDianNews.Result> newsList;

	private void showDataToListView(String result) {
		newsList = new ArrayList<ReDianNews.Result>();
		Gson gson = new Gson();
		ReDianNews reDianNews = gson.fromJson(result, ReDianNews.class);
		List<Result> result2 = reDianNews.getResult();
		for (Result result3 : result2) {
			newsList.add(result3);
		}

		System.out.println("总共有" + newsList.size() + "条新闻");

		Loading_listView_Adapter adapter = new Loading_listView_Adapter(
				newsList, this);
		loaddingListView.setAdapter(adapter);
		loaddingListView.setOnItemClickListener(this);
	}

	// 点击条目执行的方法。
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(this, NewsContentActivity.class);
		System.out.println("你点的是第" + arg2 + "个新闻");
		
		setDataToDb(arg2);
		String url = newsList.get(arg2).getUrl();
		intent.putExtra(URL, url);
		startActivity(intent);
		overridePendingTransition(R.anim.go, R.anim.loading_news);

	}

	private ContentValues cv;
	private Intent intent;

	private void setDataToDb(int arg2) {
		Result result = newsList.get(arg2);
		String url = result.getUrl();
		int quiryByUrl = DBUtils.quiryByUrl(this, url);
		if (0 == quiryByUrl) {
			// 数据库中没有这条数据
			if (cv == null) {
				cv = new ContentValues();
			}else{
				cv.clear();
			}
			cv.put(MyReDianNewsDb.SHISHI_ID, title);//添加实时新闻的标题
			cv.put(MyReDianNewsDb.TITLE, result.getTitle());
			cv.put(MyReDianNewsDb.CONTENT,result.getContent());
			cv.put(MyReDianNewsDb.IMG_WIDTH, result.getImg_width());
			cv.put(MyReDianNewsDb.FULL_TITLE, result.getFull_title());
			cv.put(MyReDianNewsDb.PDATE, result.getPdate());
			cv.put(MyReDianNewsDb.IMG_LENGTH,result.getImg_length());
			cv.put(MyReDianNewsDb.IMG_URL, result.getImg());
			cv.put(MyReDianNewsDb.NEWS_URL, result.getUrl());
			cv.put(MyReDianNewsDb.PDATE_SRC, result.getPdate_src());
			
			DBUtils.insertAllForReDian(this,cv);
		}
	}
	
	@Override
	public void onBackPressed() {
		setResult(1, intent);
		finish();
	}
	

}
