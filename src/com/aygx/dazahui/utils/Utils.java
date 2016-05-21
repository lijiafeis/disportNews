package com.aygx.dazahui.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;

import com.aygx.dazahui.bean.CollectShow;
import com.aygx.dazahui.bean.user.CollectBean;
import com.aygx.dazahui.db.MyCollectDb;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.widget.Toast;

public class Utils {
	public static void showToast(Context context, String str) {
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}

	public static void showAlertDialog(Context context, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message);
		builder.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss();
			}
		});
		builder.show();
	}

	/*
	 * 每次登录的时候，从网上的到数据，保存到本地的数据库中去
	 */
	static MyCollectDb collectDb;
	static SQLiteDatabase db;

	public static void getDataForUrlToDb(final Context context, String table) {
		if (collectDb == null) {
			collectDb = new MyCollectDb(context);
		}
		db = collectDb.getWritableDatabase();
		BmobQuery query = new BmobQuery(table);

		query.findObjects(context, new FindCallback() {

			@Override
			public void onFailure(int arg0, String arg1) {
			}

			@Override
			public void onSuccess(JSONArray arg0) {
				CollectShow bean = null;
				ContentValues cv = null;
				for (int i = 0; i < arg0.length(); i++) {
					JSONObject jsonObject;
					try {
						jsonObject = arg0.getJSONObject(i);
						String title = jsonObject.getString("title");
						String date = jsonObject.getString("date");
						String url = jsonObject.getString("url");
						String img_url = jsonObject.getString("img_url");
						if (!TextUtils.isEmpty(title)) {
							if (cv == null) {
								cv = new ContentValues();
							} else {
								cv.clear();
							}
							cv.put(MyCollectDb.TITLE, title);
							cv.put(MyCollectDb.DATE, date);
							cv.put(MyCollectDb.URL, url);
							cv.put(MyCollectDb.IMG_URL, img_url);
							DBUtils.insertAllForCollect(context, cv);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
}
