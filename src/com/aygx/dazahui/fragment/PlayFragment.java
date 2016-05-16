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
import android.widget.TextView;

import com.aygx.dazahui.R;
import com.aygx.dazahui.adapter.JokeAdapter;
import com.aygx.dazahui.bean.JokeBean;
import com.aygx.dazahui.bean.JokeBean.Shuju;
import com.aygx.dazahui.bean.JokeBean.Shuju.Data;
import com.aygx.dazahui.bean.JokeShow;
import com.aygx.dazahui.db.MyJokeDb;
import com.aygx.dazahui.network.MyJokeNetConnect;
import com.aygx.dazahui.network.MyJokeNetConnect.OnGetJoke;
import com.aygx.dazahui.utils.DBUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * 当第一次打开页面的时候，先看数据库中有没有数据。没有的话访问网络进行数据的读取。
 * 
 * @author Administrator
 * 
 */
public class PlayFragment extends Fragment {

	private View view;
	private PullToRefreshListView listView;
	private List<JokeShow> showList; // 用来存放所有的joke信息

	private int pager = 3;// 每次上拉刷新的时候，要获取的页数,每获取一次数据，+1
	private TextView pic_textView;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		pic_textView = (TextView) getActivity().findViewById(R.id.pic);
		pic_textView.setVisibility(View.VISIBLE);

		view = inflater.inflate(R.layout.fragment_home_play, null);
		return view;
	}

	@Override
	public void onPause() {
		super.onPause();
		pic_textView.setVisibility(View.GONE);
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

		Cursor cursor = DBUtils.quiryAllForJoke(getActivity());
		if (showList == null) {
			showList = new ArrayList<JokeShow>();
		}
		JokeShow show;
		// error
		if (cursor.moveToFirst()) {
			while (cursor.moveToNext()) {
				String string2 = cursor.getString(cursor
						.getColumnIndex(MyJokeDb.CONTENT));
				String replaceAll = string2.replaceAll("\\n", "").trim();
				String time = cursor.getString(cursor
						.getColumnIndex(MyJokeDb.TIME));
				show = new JokeShow();
				show.setContent(replaceAll);
				show.setDate(time);

				showList.add(show);
			}
			JokeAdapter adapter = new JokeAdapter(showList, getActivity());
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
				// 存储joke数据
				JokeBean fromJson = gson.fromJson(result, JokeBean.class);
				// 得到集合，其中有内容和时间
				Shuju result2 = fromJson.getResult();
				List<com.aygx.dazahui.bean.JokeBean.Shuju.Data> jokeData = result2
						.getData();

				setDataforBean(jokeData,flag);

			}

		}).getNewsJoke();
	}

	private void setDataforBean(
			List<com.aygx.dazahui.bean.JokeBean.Shuju.Data> data5,
			boolean flag) {
		if (showList == null ) {
			showList = new ArrayList<JokeShow>();
		}
		// 定义一个集合，用来存放每次访问网络得到的新的数据，只是临时存储。而ShowList是总的集合。
		// 只是用来往数据库中存储数据。
		ArrayList<JokeShow> currorList = null;
		if (currorList == null) {
			currorList = new ArrayList<JokeShow>();
		} else {
			currorList.clear();
		}

		JokeShow show;
		for (Data data : data5) {
			show = new JokeShow();
			String trim = data.getContent().trim();
			String replaceAll = trim.replaceAll("\\n", "").trim();
			show.setContent(replaceAll);
			show.setDate(data.getUpdatetime());
			currorList.add(show);

		}

		// 把临时数据保存在总的数据中区。
		showList.addAll(currorList);
		JokeAdapter adapter = new JokeAdapter(showList, getActivity());
		if (flag) {
			adapter.notifyDataSetChanged();
			pager++;
			listView.onRefreshComplete();

		} else {

			listView.setAdapter(adapter);
		}
		// Collections.reverse(showList);// 把集合反转
		System.out.println(showList.size());
		setDataForDb(currorList);

	}

	private void setDataForDb(List<JokeShow> showList2) {

		ContentValues cv = new ContentValues();
		for (JokeShow jokeShow : showList2) {
			cv.clear();
			cv.put(MyJokeDb.CONTENT, jokeShow.getContent());
			cv.put(MyJokeDb.TIME, jokeShow.getDate());
			DBUtils.insertAllForJoke(getActivity(), cv);
		}
	}

	private void setPullToRefresh() {
		listView = (PullToRefreshListView) view
				.findViewById(R.id.pullToRefreshListView);

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
