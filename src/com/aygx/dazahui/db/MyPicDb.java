package com.aygx.dazahui.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * 图片的数据库
 * @author Administrator
 *
 */
public class MyPicDb extends SQLiteOpenHelper {
	public final static String DB_NAME = "pic.db";
	public static final String TABLE_NAME = "pic";
	public static final String _ID = "_id";
	public static final String TITLE = "title";
	public static final String URL = "url";
	public static final String TIME = "date";
	public MyPicDb(Context context) {
		super(context, DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL("CREATE TABLE " + TABLE_NAME + "(" + _ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + TITLE + " text,"
				+ TIME + " text,"+ URL + " text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}

}
