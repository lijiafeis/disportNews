package com.aygx.dazahui.fragment.news;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.aygx.dazahui.R;
import com.aygx.dazahui.adapter.DisportNewsAdapter;
import com.aygx.dazahui.bean.DisportNewsShow;

public class TwoActivity extends BaseAdapter implements OnItemClickListener {

	private ListView listView;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {

				if (list.size() > 0) {
					DisportNewsAdapter adapter = new DisportNewsAdapter(
							mActivity, list);
					listView.setAdapter(adapter);
				}
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
		listView = (ListView) view_two.findViewById(R.id.news_two_listView);
		getDataForJsoup();
		listView.setOnItemClickListener(this);
		// 因为又开了一个线程，所以要收到消息才能执行。

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

	private void parseData(Elements select) {
		DisportNewsShow disportNewsShow = null;
		list = new ArrayList<DisportNewsShow>();
		for (int i = 0; i < select.size(); i++) {
			disportNewsShow = new DisportNewsShow();
			Element element = select.get(i);
			String text = element.text();
			String absUrl = element.absUrl("href");

//			System.out.println(text);
//			System.out.println(absUrl);
			disportNewsShow.setTitle(text);
			disportNewsShow.setUrl(absUrl);
			list.add(disportNewsShow);
		}

		handler.sendEmptyMessage(0);
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
		String url = list.get(arg2).getUrl();
		intent.putExtra(LoaddingNewsActivity.URL, url);
		mActivity.startActivity(intent);
		mActivity.overridePendingTransition(R.anim.go, R.anim.loading_news);

	}

}
