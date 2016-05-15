package com.aygx.dazahui.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aygx.dazahui.R;
import com.aygx.dazahui.bean.DisportNewsShow;

public class DisportNewsAdapter extends BaseAdapter {
	
	
	private Context context;
	private List<DisportNewsShow> list;
	
	public DisportNewsAdapter(Context context,List<DisportNewsShow> list){
		this.context = context;
		this.list = list;
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
			arg1 = View.inflate(context, R.layout.disportnews_show,null);
			view = new ViewHodler();
			view.title = (TextView) arg1.findViewById(R.id.disportnews);
			arg1.setTag(view);
		}else{
			view = (ViewHodler) arg1.getTag();
		}
		
		DisportNewsShow disportNewsShow = list.get(arg0);
		String title = disportNewsShow.getTitle();
		view.title.setText(title);
		return arg1;
	}
	class ViewHodler{
		TextView title;
	}
}
