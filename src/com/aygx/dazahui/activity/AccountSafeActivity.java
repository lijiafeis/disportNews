package com.aygx.dazahui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

import com.aygx.dazahui.R;
import com.aygx.dazahui.utils.ShareUtils;
import com.aygx.dazahui.utils.Utils;

public class AccountSafeActivity extends Activity {

	private EditText oldPass;
	private EditText newPass;
	private EditText newPass1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accountsafe);
		initView();
	}

	private void initView() {
		oldPass = (EditText) findViewById(R.id.oldPass);
		newPass = (EditText) findViewById(R.id.newPass);
		newPass1 = (EditText) findViewById(R.id.newPass1);
	}

	// 响应点击事件的修改密码
	public void revise(View v) {
		String old = oldPass.getText().toString();
		String new1 = newPass.getText().toString();
		String new2 = newPass1.getText().toString();
		if (new1.equals(new2)) {
			BmobUser.updateCurrentUserPassword(this, old, new1,
					new UpdateListener() {

						@Override
						public void onSuccess() {
							Utils.showToast(AccountSafeActivity.this, "修改成功");
							BmobUser.logOut(AccountSafeActivity.this);// 退出当前的登录
							ShareUtils
									.setlogin(AccountSafeActivity.this, false);// 设置当前没有登录为false
							ShareUtils.setUserName(AccountSafeActivity.this,
									"", "");// 设置密码为空。
							ShareUtils
									.setUserNick(AccountSafeActivity.this, "");// 设置当前账户的昵称为空。

							setResult(2, getIntent());
						}

						@Override
						public void onFailure(int arg0, String arg1) {
							Utils.showToast(AccountSafeActivity.this, arg1);
						}
					});
		} else {
			Utils.showToast(this, "两次输入的密码不一样");
		}

	}
	// 注销当前帐号
	public void logout(View v) {
		BmobUser.logOut(AccountSafeActivity.this);// 退出当前的登录
		ShareUtils.setlogin(AccountSafeActivity.this, false);// 设置当前没有登录为false
		ShareUtils.setUserName(AccountSafeActivity.this, "", "");// 设置密码为空。
		ShareUtils.setUserNick(this,"");
		setResult(2, getIntent());
		finish();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		setResult(2, getIntent());
	}

}
