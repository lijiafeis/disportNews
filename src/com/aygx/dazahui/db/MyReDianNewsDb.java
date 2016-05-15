package com.aygx.dazahui.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyReDianNewsDb extends SQLiteOpenHelper {
	//
	// 名称 类型 说明
	// error_code int 返回码
	// reason string 返回说明
	// result string 返回结果集
	// title string 新闻标题
	// content string 新闻摘要内容
	// img_width string 图片宽度
	// full_title string 完整标题
	// pdate string 发布时间
	// src string 新闻来源
	// img_length string 图片高度
	// img string 图片链接
	// url string 新闻链接
	// pdate_src string 发布完整时间
	public final static String DB_NAME = "rediannews.db";
	public static final String TABLE_NAME = "redian";
	public static final String _ID = "_id";
	public static final String SHISHI_ID = "shishi_id";
	public static final String REASON = "reason";
	public static final String TITLE = "title";// 标题
	public static final String CONTENT = "content";// 摘要内容
	public static final String IMG_WIDTH = "img_width";
	public static final String FULL_TITLE = "full_title";// 完整标题
	public static final String PDATE = "pdate";// 发布时间
	public static final String IMG_LENGTH = "img_length";
	public static final String IMG_URL = "img_url";
	public static final String NEWS_URL = "news_url";
	public static final String PDATE_SRC = "pdate_src";
	public static final String ERROR_CODE = "error_code";

	public MyReDianNewsDb(Context context) {
		super(context, DB_NAME, null, 1);
	}
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL("CREATE TABLE " + TABLE_NAME + "(" + _ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + SHISHI_ID
				+ " text," + REASON + " text," + TITLE + " text," + CONTENT
				+ " text," + IMG_WIDTH + " text," + FULL_TITLE + " text,"
				+ PDATE + " text," + IMG_LENGTH + " text," + IMG_URL + " text,"
				+ NEWS_URL + " text," + PDATE_SRC + " text," + ERROR_CODE
				+ " INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
