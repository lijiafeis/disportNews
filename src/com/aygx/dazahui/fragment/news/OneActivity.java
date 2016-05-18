package com.aygx.dazahui.fragment.news;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

import com.aygx.dazahui.R;
import com.aygx.dazahui.adapter.ShiShiNews_listView_Adapter;
import com.aygx.dazahui.bean.ShiShiShow;
import com.aygx.dazahui.db.MyShiShiNewDb;
import com.aygx.dazahui.network.NewsConnect;
import com.aygx.dazahui.utils.DBUtils;
import com.aygx.dazahui.utils.Utils;
import com.aygx.dazahui.view.MyListView;
import com.aygx.dazahui.view.MyListView.OnRefreshListener;

public class OneActivity extends BaseAdapter implements OnItemClickListener {
	public MyListView listView;
	public static List<String> list;// 记录显示条目
	public boolean isLoaddingPager = false; //点击返回来是否显示在原来的位置
	public static final String NEWS_TITILE = "news_titile";

	// public static Handler handler = new Handler(){
	// public void handleMessage(android.os.Message msg) {
	// list = (List<String>) msg.obj;
	// System.out.println(OneActivity.this.list.size());
	// };
	// };

	public OneActivity(Activity mActivity) {
		super(mActivity);
	}
	
	@Override
	public void initView() {
		super.initView();
	}

	public void initData() {
		View view_one = View.inflate(mActivity, R.layout.news_viewpager_one,
				null);
		fl.addView(view_one);
		listView = (MyListView) view_one.findViewById(R.id.myListView);

		listView.setonRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				newsConnect = new NewsConnect(mActivity, listView, list);
				newsConnect.getRequest2();
			}
		});
		getListViewData();

		// 处理点击事件
		listView.setOnItemClickListener(this);
	}

	private NewsConnect newsConnect;
	public int item;

	private void getListViewData() {
		getDataFromDb();
	}

	private void getDataFromDb() {

		list = new ArrayList<String>();
		Cursor quiryAll = DBUtils.quiryAllForShiShi(mActivity);
		while (quiryAll.moveToNext()) {
			String content = quiryAll.getString(quiryAll
					.getColumnIndex(MyShiShiNewDb.RESULT));
			list.add(content);
		}
		if (list != null && list.size() > 0) {
			List<ShiShiShow> showList = new ArrayList<ShiShiShow>();
			Collections.reverse(list);
			ShiShiShow show;
//			System.out.println("测试");
			for (String content : list) {
				show = new ShiShiShow();
				show.setContent(content);
				String[] str = DBUtils.quiryByShiShi_id(mActivity, content);
				show.setImgUrl(str[0]);
				show.setDate(str[1]);
				showList.add(show);
				
			}
			ShiShiNews_listView_Adapter adapter = new ShiShiNews_listView_Adapter(showList, mActivity);
			listView.setAdapter(adapter);
			if(isLoaddingPager){
				listView.setSelection(item);
				isLoaddingPager = false;
			}
			// System.out.println(list);
		} else {
			newsConnect = new NewsConnect(mActivity, listView, list);
			newsConnect.getRequest2();
		}

	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// 点击进入详情页的Activity
//		System.out.println(arg2);
		Intent intent = new Intent(mActivity, LoaddingNewsActivity.class);
		intent.putExtra(NEWS_TITILE, list.get(arg2 -1));
		mActivity.startActivity(intent);
		isLoaddingPager = true;
		item = arg2;
		mActivity.overridePendingTransition(R.anim.left, R.anim.right);
	}


}
