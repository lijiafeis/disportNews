package com.aygx.dazahui.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.view.Window;

import cn.bmob.v3.Bmob;

import com.aygx.dazahui.R;
import com.aygx.dazahui.db.MyCollectDb;
import com.aygx.dazahui.db.MyDisportDb;
import com.aygx.dazahui.db.MyJokeDb;
import com.aygx.dazahui.db.MyPicDb;
import com.aygx.dazahui.db.MyReDianNewsDb;
import com.aygx.dazahui.db.MyShiShiNewDb;
import com.aygx.dazahui.utils.ShareUtils;

public class WelcomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bmob.initialize(this, "f62ff6bd8e329e363296c6a69834ca94");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
		
		// 创建实时新闻的数据库
		MyShiShiNewDb db = new MyShiShiNewDb(this);
		// 创建热点新闻的数据库
		MyReDianNewsDb redianDb = new MyReDianNewsDb(this);
		// 创建娱乐新闻的数据库
		MyDisportDb disport = new MyDisportDb(this);
		// 创建Joke的数据库
		MyJokeDb jokedb = new MyJokeDb(this);
		//创建pic数据库
		MyPicDb picDb = new MyPicDb(this);
		
		handler.sendEmptyMessageDelayed(0, 200);
	}

	private Handler handler = new Handler(new Callback() {

		@Override
		public boolean handleMessage(Message arg0) {
			if (arg0.what == 0) {
				boolean flag = ShareUtils.getGuideShare(WelcomeActivity.this);
				if (flag) {
					startActivity(new Intent(WelcomeActivity.this,
							MainActivity.class));
					WelcomeActivity.this.finish();
				} else {
					startActivity(new Intent(WelcomeActivity.this,
							GuideActivity.class));
					WelcomeActivity.this.finish();
				}

			}

			return false;

		}
	});
}
