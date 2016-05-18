package com.aygx.dazahui.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aygx.dazahui.R;
import com.aygx.dazahui.adapter.PicAdapter;
import com.aygx.dazahui.bean.JokePicBean;
import com.aygx.dazahui.bean.PicShow;
import com.aygx.dazahui.db.MyPicDb;
import com.aygx.dazahui.network.MyJokeNetConnect;
import com.aygx.dazahui.network.MyJokeNetConnect.OnGetJoke;
import com.aygx.dazahui.utils.DBUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class PicFragment extends Fragment {
	private View view;
	private PullToRefreshListView listView;
	private int pager = 3;
	private List<PicShow> picList;// 用来存放所有的PIC信息。

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_pic, null);

		return view;
	}

	@Override
	public void onStart() {
		super.onStart();

		// 设置刷新的相关属性
		setPullToRefresh();
		boolean flag = getDataForDb();// 如果数据库中有数据，返回true,没有数据返回false;
		System.out.println(flag);
		if (!flag) {
			// 从网路中的到数据，在这之前，先从数据库中的到数据，如果数据库中有数据
			// 就不从网上得到数据。
			System.out.println("执行了吗？");
			getJokeForUrl(2, 10, false);
		}
	}

	private boolean getDataForDb() {

		Cursor pic_cursor = DBUtils.quiryAllForPic(getActivity());
		if (picList == null) {
			picList = new ArrayList<PicShow>();
		}
		PicShow picShow;
		// error
		if (pic_cursor.moveToFirst()) {
			while (pic_cursor.moveToNext()) {
				picShow = new PicShow();
				String title = pic_cursor.getString(pic_cursor
						.getColumnIndex(MyPicDb.TITLE));
				String date = pic_cursor.getString(pic_cursor
						.getColumnIndex(MyPicDb.TIME));
				String url = pic_cursor.getString(pic_cursor
						.getColumnIndex(MyPicDb.URL));
				picShow.setTitle(title);
				picShow.setDate(date);
				picShow.setUrl(url);

				picList.add(picShow);
			}
			System.out.println(picList.size());
			PicAdapter adapter = new PicAdapter(picList, getActivity());
			listView.setAdapter(adapter);
			return true;
		}
		return false;

	}

	/*
	 * 从网上的到数据，第一个参数是页数，第二个参数是每页的条数，第三个参数是表示上拉刷新的请求
	 */
	private void getJokeForUrl(int pager, int item, final boolean flag) {
		new MyJokeNetConnect(pager, item, new OnGetJoke() {
			/*
			 * 返回来的数据中pic是图片，总共有两个。
			 * 
			 * @see
			 * com.aygx.dazahui.network.MyJokeNetConnect.OnGetJoke#getResult
			 * (java.lang.String, java.lang.String)
			 */
			@Override
			public void getResult(String result) {
				// 解析数据
				Gson gson = new Gson();
				JokePicBean pic1 = gson.fromJson(result, JokePicBean.class);
				// 得到集合，其中有内容和时间
				com.aygx.dazahui.bean.JokePicBean.Shuju result3 = pic1
						.getResult();
				List<com.aygx.dazahui.bean.JokePicBean.Shuju.Data> picData = result3
						.getData();

				setDataforBean(picData, flag);

			}
		}).getPic();
	}

	private void setDataforBean(
			List<com.aygx.dazahui.bean.JokePicBean.Shuju.Data> picData,
			boolean flag) {
		if (picList == null) {
			picList = new ArrayList<PicShow>();
		}
		// 定义一个集合，用来存放每次访问网络得到的新的数据，只是临时存储。而ShowList是总的集合。
		// 只是用来往数据库中存储数据。
		ArrayList<PicShow> currorPicList = null;
		if (currorPicList == null) {
			currorPicList = new ArrayList<PicShow>();
		} else {
			currorPicList.clear();
		}
		PicShow picShow1;
		for (com.aygx.dazahui.bean.JokePicBean.Shuju.Data picShow : picData) {
			picShow1 = new PicShow();
			String title = picShow.getContent().trim();
			String date = picShow.getUpdatetime();
			String url = picShow.getUrl();
			picShow1.setTitle(title);
			picShow1.setDate(date);
			picShow1.setUrl(url);
			currorPicList.add(picShow1);
		}

		// 把临时数据保存在总的数据中区。
		picList.addAll(currorPicList);
		PicAdapter adapter = new PicAdapter(picList, getActivity());
		if (flag) {
			adapter.notifyDataSetChanged();
			pager++;
			listView.onRefreshComplete();

		} else {
			listView.setAdapter(adapter);
		}
		setDataForDb(currorPicList);

	}

	private void setDataForDb(ArrayList<PicShow> currorPicList) {

		ContentValues cv = new ContentValues();
		for (PicShow picShow : currorPicList) {
			cv.clear();
			cv.put(MyPicDb.TITLE, picShow.getTitle());
			cv.put(MyPicDb.TIME, picShow.getDate());
			cv.put(MyPicDb.URL, picShow.getUrl());
			DBUtils.insertAllForPic(getActivity(), cv);
		}

	}

	private void setPullToRefresh() {
		listView = (PullToRefreshListView) view
				.findViewById(R.id.pullToRefreshListView_pic);

		listView.setMode(Mode.PULL_FROM_END);
		init();// 对上拉刷新进行设置。
		// 上拉刷新
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> arg0) {
				// 设置时间
				String label = getDate();
				arg0.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// 从网络中得到数据。
				getJokeForUrl(pager, 10, true);
			}

			private String getDate() {

				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy年MM月dd日HH:mm:ss");
				String format = formatter.format(new java.util.Date());
				return format;
			}
		});

	}

	private void init() {
		ILoadingLayout endLabels = listView.getLoadingLayoutProxy(false, true);
		endLabels.setPullLabel("上拉刷新...");// 刚下拉时，显示的提示
		endLabels.setRefreshingLabel("正在载入...");// 刷新时
		endLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示
	}

}
