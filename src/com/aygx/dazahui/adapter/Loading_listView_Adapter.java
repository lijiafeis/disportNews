package com.aygx.dazahui.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aygx.dazahui.R;
import com.aygx.dazahui.bean.ReDianNews;
import com.aygx.dazahui.bean.ReDianNews.Result;
import com.lidroid.xutils.BitmapUtils;
import com.squareup.picasso.Picasso;

public class Loading_listView_Adapter extends BaseAdapter {
//	private BitmapUtils bitmapUtils;// 用于访问网络的图片
	private List<ReDianNews.Result> newsList;
	private Context context;
	
	private Picasso picasso;
	
	public Loading_listView_Adapter(List<ReDianNews.Result> newsList,
			Context context) {
		this.newsList = newsList;
		this.context = context;
//		bitmapUtils = new BitmapUtils(context);
//		bitmapUtils.configDefaultLoadingImage(BitmapFactory.decodeResource(
//				context.getResources(), R.drawable.icon));
//		bitmapUtils.configDefaultLoadFailedImage(BitmapFactory.decodeResource(
//				context.getResources(), R.drawable.icon));
	}

	@Override
	public int getCount() {
		return newsList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return newsList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {

		View view = View
				.inflate(context, R.layout.loadding_listview_item, null);
		ImageView icon = (ImageView) view.findViewById(R.id.icon);
		TextView title = (TextView) view.findViewById(R.id.title);
		TextView root = (TextView) view.findViewById(R.id.laiyuan);
		TextView date = (TextView) view.findViewById(R.id.date);
		Result result = newsList.get(arg0);
		String img = result.getImg();
//		bitmapUtils.display(icon, img);
		if(!TextUtils.isEmpty(img)){
			Picasso.with(context).load(img).into(icon);
		}
		
		title.setText(result.getTitle());
		root.setText(result.getSrc());
		date.setText(result.getPdate());
		return view;
	}

}
