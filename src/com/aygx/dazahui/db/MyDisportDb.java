package com.aygx.dazahui.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * 娱乐新闻的数据库。
 * @author Administrator
 *
 */
public class MyDisportDb extends SQLiteOpenHelper {
	public final static String DB_NAME = "disport.db";
	public static final String TABLE_NAME = "disport";
	public static final String _ID = "_id";
	public static final String URL = "url";
	public static final String TITLE = "title";

	public MyDisportDb(Context context) {
		super(context, DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL("CREATE TABLE " + TABLE_NAME + "(" + _ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + TITLE + " text,"
				+ URL + " text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
