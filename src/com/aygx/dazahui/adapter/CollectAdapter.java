package com.aygx.dazahui.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aygx.dazahui.R;
import com.aygx.dazahui.bean.CollectShow;
import com.squareup.picasso.Picasso;

public class CollectAdapter extends BaseAdapter {

	private List<CollectShow> list;
	private Context context;

	public CollectAdapter(List<CollectShow> list, Context context) {
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHodler view;
		if (arg1 == null) {
			arg1 = View.inflate(context, R.layout.collect_listview_item, null);
			view = new ViewHodler();
			view.title = (TextView) arg1.findViewById(R.id.collect_title);
			view.icon = (ImageView) arg1.findViewById(R.id.collect_icon);
			view.date = (TextView) arg1.findViewById(R.id.collect_date);
			arg1.setTag(view);
		} else {
			view = (ViewHodler) arg1.getTag();
		}
		CollectShow collectShow = list.get(arg0);
		view.title.setText(collectShow.getTitle());
		String img_url = collectShow.getImg_url();
		String date = collectShow.getDate();
		if (!TextUtils.isEmpty(img_url)) {
			view.icon.setVisibility(View.VISIBLE);
			Picasso.with(context).load(collectShow.getImg_url())
					.into(view.icon);
		} else {
			view.icon.setVisibility(View.GONE);
		}
		view.date.setText(collectShow.getDate());
		return arg1;
	}

	class ViewHodler {
		TextView title;
		ImageView icon;
		TextView date;
	}

}
