package com.aygx.dazahui.fragment.news;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.aygx.dazahui.R;
import com.aygx.dazahui.adapter.DisportNewsAdapter;
import com.aygx.dazahui.bean.DisportNewsShow;
import com.aygx.dazahui.db.MyDisportDb;
import com.aygx.dazahui.utils.DBUtils;
import com.aygx.dazahui.utils.Utils;
import com.aygx.dazahui.view.MyListView;
import com.aygx.dazahui.view.MyListView.OnRefreshListener;

public class TwoActivity extends BaseAdapter implements OnItemClickListener {

	private MyListView listView;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				if (dbList.size() > 0) {
					DisportNewsAdapter adapter = new DisportNewsAdapter(
							mActivity, dbList);
					listView.setAdapter(adapter);
					// 先是完数据，隐藏下拉刷新的控件。
				}
				listView.onRefreshComplete();
			}

		};
	};

	public TwoActivity(Activity mActivity) {
		super(mActivity);

	}

	@Override
	public void initView() {
		super.initView();
	}

	public void initData() {
		View view_two = View.inflate(mActivity, R.layout.news_viewpager_two,
				null);
		fl.addView(view_two);
		listView = (MyListView) view_two.findViewById(R.id.news_two_listView);
		// 设置下拉刷新的操作，当下拉刷新的时候才进行访问网络得到数据。

		listView.setonRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				getDataForJsoup();
			}
		});
		// 先从数据库中的数据，进行显示。
		getListViewData();

		listView.setOnItemClickListener(this);
		// 因为又开了一个线程，所以要收到消息才能执行。

	}

	// 访问数据库得到数据
	private void getListViewData() {
		getDataFromDb();
	}

	private List<DisportNewsShow> dbList;

	private void getDataFromDb() {
		DisportNewsShow disportNewsShow = null;
		dbList = new ArrayList<DisportNewsShow>();
		Cursor quiryAll = DBUtils.quiryAllForDisport(mActivity);
		while (quiryAll.moveToNext()) {
			String title = quiryAll.getString(quiryAll
					.getColumnIndex(MyDisportDb.TITLE));
			String url = quiryAll.getString(quiryAll
					.getColumnIndex(MyDisportDb.URL));
			disportNewsShow = new DisportNewsShow();
			disportNewsShow.setTitle(title);
			disportNewsShow.setUrl(url);
			dbList.add(disportNewsShow);
		}
		if (dbList != null && dbList.size() > 0) {
			Collections.reverse(dbList);
			adapterForDB = new DisportNewsAdapter(mActivity, dbList);
			listView.setAdapter(adapterForDB);

			// System.out.println(list);
		} else {
			Utils.showToast(mActivity, "请下拉刷新");
		}

	}

	/*
	 * 用于从网络中获取数据，比解析。
	 */
	private void getDataForJsoup() {
		new Thread() {
			public void run() {
				Document document;

				try {
					document = Jsoup.connect(
							"http://www.chinanews.com/entertainment.shtml")
							.get();
					Elements select = document.select("div.content_list a");
					parseData(select);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}.start();

	}

	/*
	 * 从网上获取数据，解析，封装。
	 */

	private ArrayList<DisportNewsShow> list;

	private DisportNewsAdapter adapterForDB;

	private void parseData(Elements select) {
		DisportNewsShow disportNewsShow = null;
		list = new ArrayList<DisportNewsShow>();
		for (int i = 0; i < select.size(); i++) {
			disportNewsShow = new DisportNewsShow();
			Element element = select.get(i);
			String text = element.text();
			String absUrl = element.absUrl("href");

			// System.out.println(text);
			// System.out.println(absUrl);
			disportNewsShow.setTitle(text);
			disportNewsShow.setUrl(absUrl);
			list.add(disportNewsShow);
		}

		List<DisportNewsShow> butongList = new ArrayList<DisportNewsShow>();// 用来保存两个不同的数据，保存到数据库中去

		// 从网上得到数据，并比较从数据库中的到数据，如果两个集合中有不同的，添加到数据库中去。
		List<String> stringList_URL = new ArrayList<String>();//用来存储网络上的标题
		List<String> stringList_bendi = new ArrayList<String>();//用来存储本地的标题
		for (DisportNewsShow disportNewsShow1 : list) {
			stringList_URL.add(disportNewsShow1.getTitle());
		}
		for (DisportNewsShow disportNewsShow1 : dbList) {
			stringList_bendi.add(disportNewsShow1.getTitle());
		}
		for (int i =0; i < stringList_URL.size(); i++) {// 网上的数据
			if (stringList_bendi != null) { // 当从数据库中得到数据不为空时，才进行数据的添加
				if (stringList_bendi.contains(stringList_URL.get(i))) {
					// System.out.println(agoList.size());
					continue;
				}
			}
			System.out.println("......");
			dbList.add(0, list.get(i));//总共的集合。
			butongList.add(list.get(i));//只是一次返回的数据不同的集合
			// System.out.println(string);
		}
		// 先保存不同的数据到数据库中去
		setDataToDb(butongList);
		// 进行数据的显示
		handler.sendEmptyMessage(0);
	}

	/*
	 * 先保存不同的数据到数据库中去
	 */
	private void setDataToDb(List<DisportNewsShow> list2) {
		ContentValues cv = new ContentValues();
		for (DisportNewsShow disport : list2) {
			cv.clear();
			cv.put(MyDisportDb.TITLE, disport.getTitle());
			cv.put(MyDisportDb.URL, disport.getUrl());
			DBUtils.insertAllForDisport(mActivity, cv);
		}
	}

	/*
	 * 点击条目的时候
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long)
	 */

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(mActivity, NewsContentActivity.class);
		System.out.println("你点的是第" + arg2 + "个新闻");
		String url = dbList.get(arg2).getUrl();
		intent.putExtra(LoaddingNewsActivity.URL, url);
		mActivity.startActivity(intent);
		mActivity.overridePendingTransition(R.anim.go, R.anim.loading_news);
		
	}

}
