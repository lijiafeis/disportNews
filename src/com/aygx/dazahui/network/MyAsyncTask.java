package com.aygx.dazahui.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Message;
import android.widget.ArrayAdapter;

import com.aygx.dazahui.adapter.ShiShiNews_listView_Adapter;
import com.aygx.dazahui.bean.ReDianNews;
import com.aygx.dazahui.bean.ShiShiShow;
import com.aygx.dazahui.bean.ReDianNews.Result;
import com.aygx.dazahui.bean.ShiShiNews;
import com.aygx.dazahui.db.MyShiShiNewDb;
import com.aygx.dazahui.fragment.news.OneActivity;
import com.aygx.dazahui.utils.DBUtils;
import com.aygx.dazahui.view.MyListView;
import com.google.gson.Gson;

public class MyAsyncTask extends AsyncTask<String, Void, String> {
	private MyListView listView;
	private List<String> list;
	private Activity mActivity;
	private boolean isNull = false;
	private List<String> agoList;
	private String reason;
	private int error_code;

	private SetReDianNews setReDianNews;

	// 当第一次从数据库中的到数据的时候，穿过来，用与比较从网络中的到得数据进行匹配，一样的数据不存到数据库中。

	// 无参构造函数

	private boolean flag = false;

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public MyAsyncTask(
			com.aygx.dazahui.network.MyAsyncTask.SetReDianNews setReDianNews2) {
		this.setReDianNews = setReDianNews2;
	}

	public static interface SetReDianNews {
		void getData(String relute);
	}

	public MyAsyncTask(MyListView listView, Activity mActivity,
			List<String> list) {
		this.listView = listView;
		this.mActivity = mActivity;
		this.agoList = list;
		System.out.println("agoList..." + agoList.size());
		this.list = new ArrayList<String>();
	}

	// 访问网络得到数据
	@Override
	protected String doInBackground(String... arg0) {
		if (!flag) {
			return getShiShiNews(arg0);
		} else {
			return getReDianNews(arg0);
		}
	}

	private String getReDianNews(String[] arg0) {
		StringBuffer sb = null;
		URL url;
		try {
			sb = new StringBuffer();
			url = new URL(arg0[0]);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStream inputStream = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					inputStream, "UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			// System.out.println(sb.toString());
			return sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.out.println("网址找不到");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	// 通过arg0得到实时新闻的连接，在通过网络进行设置。
	private String getShiShiNews(String... arg0) {
		StringBuffer sb = null;
		URL url;
		try {
			sb = new StringBuffer();
			url = new URL(arg0[0]);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStream inputStream = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					inputStream, "UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.out.println("网址找不到");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		if (result != null) {
			if (!flag) {
				setShiShinews(result);
			} else {
				setReDianNews(result);
			}

		}

	}

	private void setReDianNews(String result) {
		// Gson gson = new Gson();
		// ReDianNews reDianNews = gson.fromJson(result, ReDianNews.class);
		// List<Result> result2 = reDianNews.getResult();
		// Result result1 = result2.get(0);
		// String url = result1.getUrl();
		// System.out.println(url);
		if (setReDianNews != null) {
			setReDianNews.getData(result);
		}
	}

	// 从网络中的到实时新闻
	private void setShiShinews(String result) {
		Gson gson = new Gson();
		ShiShiNews shishiNews = gson.fromJson(result, ShiShiNews.class);
		String[] result2 = shishiNews.getResult();// 实时新闻的条目
		reason = shishiNews.getReason();
		error_code = shishiNews.getError_code();

		for (String string : result2) {
			if (agoList != null) {
				if (agoList.contains(string)) {
					// System.out.println(agoList.size());
					continue;
				}
			}
			System.out.println("......");
			agoList.add(0, string);
			list.add(string);
			// System.out.println(string);
		}
		setDataToDb(list);

		if (list.size() > 0) {
			List<ShiShiShow> showList = new ArrayList<ShiShiShow>();
			ShiShiShow show;
			for (String content : agoList) {
				show = new ShiShiShow();
				show.setContent(content);
				String[] str = DBUtils.quiryByShiShi_id(mActivity, content);
				show.setImgUrl(str[0]);
				show.setDate(str[1]);
				showList.add(show);
			}
			ShiShiNews_listView_Adapter adapter = new ShiShiNews_listView_Adapter(
					showList, mActivity);
			listView.setAdapter(adapter);
			listView.onRefreshComplete();
			// list.clear();

			OneActivity.list = agoList;
		} else {
			listView.onRefreshComplete();
		}
	}

	private void setDataToDb(List<String> list2) {
		ContentValues cv = new ContentValues();
		for (String string : list2) {
			cv.clear();
			cv.put(MyShiShiNewDb.REASON, reason);
			cv.put(MyShiShiNewDb.RESULT, string);
			cv.put(MyShiShiNewDb.ERROR_CODE, error_code);
			DBUtils.insertAllForShiShi(mActivity, cv);
		}
		// for(int i = list2.size()-1;i>=0;i--){
		// String string = list2.get(i);
		// cv.clear();
		// cv.put(MyShiShiNewDb.REASON, reason);
		// cv.put(MyShiShiNewDb.RESULT, string);
		// cv.put(MyShiShiNewDb.ERROR_CODE, error_code);
		// DBUtils.insertAll(mActivity, cv);
		// }
	}

}
