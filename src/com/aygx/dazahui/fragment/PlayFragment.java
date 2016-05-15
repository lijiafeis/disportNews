package com.aygx.dazahui.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.aygx.dazahui.R;
import com.aygx.dazahui.adapter.JokeAdapter;
import com.aygx.dazahui.bean.JokeBean;
import com.aygx.dazahui.bean.JokeBean.Shuju;
import com.aygx.dazahui.bean.JokeBean.Shuju.Data;
import com.aygx.dazahui.bean.JokeShow;
import com.aygx.dazahui.fragment.news.BaseAdapter;
import com.aygx.dazahui.network.MyJokeNetConnect;
import com.aygx.dazahui.network.MyJokeNetConnect.OnGetJoke;
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
	private JokeAdapter adapter;
	private List<JokeShow> showList;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_home_play, null);
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();

		// 设置刷新的相关属性
		setPullToRefresh();

		getJokeForUrl();
		// 从网路中的到数据

	}

	

	private void getJokeForUrl() {
		new MyJokeNetConnect(1, 20, new OnGetJoke() {

			@Override
			public void getResult(String result) {
				// 解析数据
				Gson gson = new Gson();
				JokeBean fromJson = gson.fromJson(result, JokeBean.class);
				// 得到集合，其中有内容和时间
				Shuju result2 = fromJson.getResult();
				List<com.aygx.dazahui.bean.JokeBean.Shuju.Data> data5 = result2
						.getData();
				showList = new ArrayList<JokeShow>();
				JokeShow show;
				System.out.println("网络" + data5.size());
				for (Data data : data5) {
					show = new JokeShow();
					show.setContent(data.getContent().trim());
					show.setDate(data.getUpdatetime());
					showList.add(show);
				}
				System.out.println("本地" + showList.size());
				// adapter = new JokeAdapter(getActivity(),showList);
//				adapter = new JokeAdapter(getActivity(), showList);
				for (JokeShow jokeShow : showList) {
					System.out.println(jokeShow.getContent());
				}
				listView.setAdapter(new JokeAdapter(getActivity(), showList));

			}
		}).getNewsJoke();
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

				new MyJokeNetConnect(1, 10, new OnGetJoke() {
					@Override
					public void getResult(String result) {
						System.out.println(result);
					}
				});
			}

			private String getDate() {

				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy年MM月dd日HH:mm:ss");
				String format = formatter.format(new java.util.Date());
				return format;
			}
		});
		ArrayList<String> string = new ArrayList<String>();
		string.add("asfdsaf");
		string.add("dfeead");
		string.add("dfeead");
		string.add("dfeead");
		string.add("dfeead");
		string.add("dfeead");
		string.add("dfeead");
		// ArrayAdapter<String> adapter = new
		// ArrayAdapter<String>(getActivity(),
		// android.R.layout.simple_list_item_1,string);
		
	}

	private void init() {
		ILoadingLayout endLabels = listView.getLoadingLayoutProxy(false, true);
		endLabels.setPullLabel("上拉刷新...");// 刚下拉时，显示的提示
		endLabels.setRefreshingLabel("正在载入...");// 刷新时
		endLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示
	}

}
