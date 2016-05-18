package com.aygx.dazahui.activity;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UploadFileListener;

import com.aygx.dazahui.R;
import com.aygx.dazahui.utils.ShareUtils;
import com.aygx.dazahui.utils.Utils;
import com.squareup.okhttp.internal.Util;

public class IdeaActivity extends Activity {

	private EditText ideaEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_idea);

		ideaEditText = (EditText) findViewById(R.id.idea_editText);
	}

	// 设置点击提交的点击事件。
	public void commit(View v) {
		boolean isLogin = ShareUtils.getlogin(this);
		if (isLogin) {

			commitIdea();
		}else{
			Utils.showToast(this, "请登录");
		}

	}

	private void commitIdea() {
		String text = ideaEditText.getText().toString();
		System.out.println("李佳飞");
		try {
			byte[] b = text.getBytes();
			BufferedOutputStream stream = null;
			File file = null;
			try {
				file = new File(Environment.getExternalStorageDirectory()
						+ "/" + ShareUtils.getUserNick(this) + ".txt");
				if (file.exists()) {
					file.delete();
				}
				file.createNewFile();
				FileOutputStream fstream = new FileOutputStream(file);
				stream = new BufferedOutputStream(fstream);
				stream.write(b);
				stream.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (stream != null) {
					try {
						stream.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			BmobFile bmobFile = new BmobFile(file);
			bmobFile.upload(IdeaActivity.this, new UploadFileListener() {
				@Override
				public void onSuccess() {
					Utils.showToast(IdeaActivity.this, "提交成功");
				}

				@Override
				public void onFailure(int arg0, String arg1) {
					Utils.showToast(IdeaActivity.this, arg1);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
