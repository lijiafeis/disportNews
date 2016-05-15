package com.aygx.dazahui.adapter;

import java.util.List;

import com.aygx.dazahui.R;
import com.aygx.dazahui.bean.ReDianNews;
import com.aygx.dazahui.bean.ShiShiShow;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ShiShiNews_listView_Adapter extends BaseAdapter {

	private List<ShiShiShow> list;
	private Context context;

	public ShiShiNews_listView_Adapter(List<ShiShiShow> showList, Context context) {
		this.list = showList;
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
		if(arg1 == null){
			arg1= View.inflate(context, R.layout.news_shishi_listview_item,null);
			view = new ViewHodler();
			view.content = (TextView) arg1.findViewById(R.id.shishi_content);
			view.imageView = (ImageView) arg1.findViewById(R.id.shishi_news_imageView);
			view.date = (TextView) arg1.findViewById(R.id.shishi_date);
			arg1.setTag(view);
		}else{
			view = (ViewHodler) arg1.getTag();
		}
		ShiShiShow show = list.get(arg0);
		String date = show.getDate();
		String imgUrl = show.getImgUrl();
		if(!TextUtils.isEmpty(imgUrl)){
			Picasso.with(context).load(imgUrl).into(view.imageView);
		}
		view.date.setText(date);
		view.content.setText(show.getContent());
		return arg1;
	}
	class ViewHodler{
		ImageView imageView;
		TextView content;
		TextView date;
	}

}
