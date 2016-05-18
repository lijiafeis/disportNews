package com.aygx.dazahui.fragment;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.FindListener;

import com.aygx.dazahui.R;
import com.aygx.dazahui.activity.LoginActivity;
import com.aygx.dazahui.activity.RegisterActivity;
import com.aygx.dazahui.bean.user.MyUser;
import com.aygx.dazahui.utils.ShareUtils;

public class SettingFragment extends Fragment implements OnClickListener {

	private View view;
	private Button landing;
	private Button register;
	public static final String USER = "user";
	private boolean isRegister;
	private LinearLayout linearLayout;
	private TextView nickName;
	private TextView userName;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		isRegister = ShareUtils.getlogin(getActivity());
		System.out.println("OnCreatView");
		view = inflater.inflate(R.layout.fragment_home_setting, null);
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();

		initView();

		initData();

	}

	private void initData() {

	}

	private void initView() {
		landing = (Button) view.findViewById(R.id.landing);
		register = (Button) view.findViewById(R.id.register);
		linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
		nickName = (TextView) view.findViewById(R.id.nickName);
		userName = (TextView) view.findViewById(R.id.userName);

		// 对下面的几个条目进行设置。
		TextView safe = (TextView) view.findViewById(R.id.safe);
		TextView system_Setting = (TextView) view.findViewById(R.id.system_setting);
		TextView idea = (TextView) view.findViewById(R.id.idea);
		TextView app = (TextView) view.findViewById(R.id.app);
		
		//设计点击事件
		safe.setOnClickListener(this);
		system_Setting.setOnClickListener(this);
		idea.setOnClickListener(this);
		app.setOnClickListener(this);
		
		
		if (isRegister) {
			linearLayout.setVisibility(View.VISIBLE);
			landing.setVisibility(View.GONE);
			register.setVisibility(View.GONE);
			BmobQuery<MyUser> query = new BmobQuery<MyUser>();
			String[] userName2 = ShareUtils.getUserName(getActivity());
			query.addWhereEqualTo("username", userName2[0]);
			query.findObjects(getActivity(), new FindListener<MyUser>() {

				@Override
				public void onSuccess(List<MyUser> arg0) {
					for (MyUser myUser : arg0) {
						nickName.setText("昵称:" + myUser.getNickName());

					}
				}

				@Override
				public void onError(int arg0, String arg1) {
					System.out.println(arg1);
				}
			});

			userName.setText("帐号:" + userName2[0]);
		} else {
			linearLayout.setVisibility(View.GONE);
			landing.setVisibility(View.VISIBLE);
			register.setVisibility(View.VISIBLE);
			landing.setOnClickListener(this);
			register.setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.landing:
			Intent intent1 = new Intent(getActivity(), LoginActivity.class);
			startActivityForResult(intent1, 1);
			break;
		case R.id.register:
			Intent intent = new Intent(getActivity(), RegisterActivity.class);
			startActivityForResult(intent, 1);
			break;
		//点击下面的几个条目
		case R.id.safe:
			break;
		case R.id.system_setting:
			break;
		case R.id.idea:
			break;
		case R.id.app:
			break;
			
			
		default:
			break;
		}
	}

}
