package com.aygx.dazahui.activity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.aygx.dazahui.R;
import com.aygx.dazahui.bean.user.MyUser;
import com.aygx.dazahui.fragment.news.LoaddingNewsActivity;
import com.aygx.dazahui.utils.ShareUtils;
import com.aygx.dazahui.utils.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

/**
 * 登录页面，登录成功后，记录当前的帐号和密码。 并设置登录成功为True;
 * 
 * @author Administrator
 * 
 */
public class LoginActivity extends Activity {

	private MyUser myUser;
	private String nickName;// 记录从网上的到昵称的名字

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

	}

	public void login(View v) {
		EditText userName = (EditText) findViewById(R.id.login_userName);
		EditText passWord = (EditText) findViewById(R.id.login_password);
		final String user = userName.getText().toString().trim();
		final String pass = passWord.getText().toString().trim();
		myUser = new MyUser();
		myUser.setUsername(user);
		myUser.setPassword(pass);

		myUser.login(this, new SaveListener() {

			@Override
			public void onSuccess() {
				Utils.showToast(LoginActivity.this, "登录成功");
				ShareUtils.setlogin(LoginActivity.this, true);
				ShareUtils.setUserName(LoginActivity.this, user, pass);
				BmobQuery<MyUser> query = new BmobQuery<MyUser>();
				String[] userName2 = ShareUtils.getUserName(LoginActivity.this);
				query.addWhereEqualTo("username", userName2[0]);
				//访问网络是在子线程中执行的，所以这里需要发送一个消息，当成功访问的时候，进行昵称的设置
				final Handler handler = new Handler() {
					public void handleMessage(android.os.Message msg) {
						if (msg.what == 0) {
							System.out.println(nickName);
							ShareUtils.setUserNick(LoginActivity.this, nickName);
							setResult(2, getIntent());
							finish();
						}
					};
				};
				query.findObjects(LoginActivity.this,
						new FindListener<MyUser>() {
							@Override
							public void onSuccess(List<MyUser> arg0) {
								for (MyUser myUser : arg0) {
									nickName = myUser.getNickName();
									handler.sendEmptyMessage(0);
								}
							}

							@Override
							public void onError(int arg0, String arg1) {
								System.out.println(arg1);
							}
						});
				ShareUtils.setUserNick(LoginActivity.this, nickName);
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Utils.showToast(LoginActivity.this, arg1);
			}
		});
	}

}
