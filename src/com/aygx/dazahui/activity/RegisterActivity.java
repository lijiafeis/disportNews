package com.aygx.dazahui.activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.SaveListener;

import com.aygx.dazahui.R;
import com.aygx.dazahui.bean.user.CollectBean;
import com.aygx.dazahui.bean.user.MyUser;
import com.aygx.dazahui.utils.ShareUtils;
import com.aygx.dazahui.utils.Utils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * 当用户点击注册按钮的时候，跳转到这个页面。
 * 
 * @author Administrator
 * 
 */
public class RegisterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
	}

	boolean flag = false;// 记录注册成不成功。
	private String nickName;
	private String userName;
	private String password;
	private MyUser myUser;

	public void register(View v) {
		EditText nickName1 = (EditText) findViewById(R.id.nickName);
		nickName = nickName1.getText().toString();
		EditText userName1 = (EditText) findViewById(R.id.username);
		userName = userName1.getText().toString();
		EditText password1 = (EditText) findViewById(R.id.password);
		password = password1.getText().toString();
		myUser = new MyUser();
		myUser.setNickName(nickName);
		myUser.setUsername(userName);
		myUser.setPassword(password);
		myUser.signUp(this, new SaveListener() {

			@Override
			public void onSuccess() {

				CollectBean myCollect = new CollectBean(userName);
				myCollect.setTitle("");
				myCollect.setDate("");
				myCollect.setUrl("");
				myCollect.setImg_url("");
				myCollect.save(RegisterActivity.this, new SaveListener() {
					@Override
					public void onSuccess() {
						Utils.showToast(RegisterActivity.this, "注册成功");
					}

					@Override
					public void onFailure(int arg0, String arg1) {

					}
				});
				

				
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Utils.showToast(RegisterActivity.this, arg1);
			}
		});
	}

	// 点击登录按钮的时候执行的方法。
	public void login(View v) {
		Intent intent1 = new Intent(this, LoginActivity.class);
		startActivityForResult(intent1, 1);
		setResult(2, getIntent());
		finish();
	}

}
