package com.aygx.dazahui.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aygx.dazahui.R;
import com.aygx.dazahui.bean.JokeShow;
import com.aygx.dazahui.bean.PicShow;
import com.squareup.picasso.Picasso;

public class JokeAdapter extends BaseAdapter {
	private Context context;
	private List<JokeShow> jokeList;
	private int item = 1;// 记录每个条目，
	private boolean flag;// 用来表示每一行要显示的是哪个数据。

	public JokeAdapter(List<JokeShow> showList, Context context) {
		this.context = context;
		this.jokeList = showList;
	}

	@Override
	public int getCount() {
		return jokeList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {

		ViewHodler view = null;

		if (arg1 == null) {
			arg1 = View.inflate(context, R.layout.joke_playing, null);
			view = new ViewHodler();
			view.textView = (TextView) arg1.findViewById(R.id.joke_content);
			arg1.setTag(view);
		} else {
			view = (ViewHodler) arg1.getTag();
		}
		view.textView.setText(jokeList.get(arg0).getContent());
		return arg1;
	}

	class ViewHodler {
		TextView textView;
	}

}
