package com.aygx.dazahui.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.Window;

import com.aygx.dazahui.R;
import com.aygx.dazahui.db.MyReDianNewsDb;
import com.aygx.dazahui.db.MyShiShiNewDb;
import com.aygx.dazahui.utils.ShareUtils;

public class WelcomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
		
		//创建实时新闻的数据库
		MyShiShiNewDb db = new MyShiShiNewDb(this);
		SQLiteDatabase writableDatabase = db.getWritableDatabase();
		MyReDianNewsDb redianDb = new MyReDianNewsDb(this);
		SQLiteDatabase sb = redianDb.getWritableDatabase();
		
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
