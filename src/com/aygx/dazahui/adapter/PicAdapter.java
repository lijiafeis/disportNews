package com.aygx.dazahui.adapter;

import java.util.List;

import android.content.Context;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.aygx.dazahui.R;
import com.aygx.dazahui.bean.PicShow;
import com.aygx.dazahui.view.MGifView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class PicAdapter extends BaseAdapter {
	private Context context;
	private List<PicShow> picList;

	public PicAdapter(List<PicShow> picList, Context context) {
		this.context = context;
		this.picList = picList;
	}
	@Override
	public int getCount() {
		return picList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return picList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHodler view = null;

		if (arg1 == null) {
			arg1 = View.inflate(context, R.layout.pic_show_item, null);
			view = new ViewHodler();
			view.pic = (MGifView) arg1.findViewById(R.id.mgv);
			arg1.setTag(view);
		} else {
			view = (ViewHodler) arg1.getTag();
		}
		view.pic.setResource(picList.get(arg0).getUrl());
		System.out.println(picList.get(arg0).getUrl());
		return arg1;
		
	}

	class ViewHodler {
		MGifView pic;
	}
}
