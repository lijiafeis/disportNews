package com.aygx.dazahui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.aygx.dazahui.R;
import com.aygx.dazahui.activity.CollectActivity;
import com.aygx.dazahui.activity.LoginActivity;
import com.aygx.dazahui.utils.ShareUtils;

public class SildingMenuFragment extends Fragment {
	private View view;
	private TextView nickName;
	private TextView userName;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = View.inflate(getActivity(), R.layout.fragment_sildingmenu, null);
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		initView();
	}

	private void initView() {
		nickName = (TextView) view.findViewById(R.id.silding_nickName);
		userName = (TextView) view.findViewById(R.id.silding_userName);
		Button collect = (Button) view.findViewById(R.id.collect);
		collect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startCollect();
			}
		});
		if (ShareUtils.getlogin(getActivity())) {// 当是登录的状态的时候，才进行昵称和帐号的获取
			nickName.setText("昵称: " + ShareUtils.getUserNick(getActivity()));
			userName.setText("帐号: " + ShareUtils.getUserName(getActivity())[0]);
		}
	}

	protected void startCollect() {
		if(ShareUtils.getlogin(getActivity())){
			
			startActivity(new Intent(getActivity(), CollectActivity.class));
		}else{
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage("请登录");
			builder.setPositiveButton("登录", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
				
					startActivityForResult(new Intent(getActivity(),LoginActivity.class), 1);
					arg0.dismiss();
				}
			});
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					arg0.dismiss();
				}
			});
			builder.show();
		}
		
	}

}
