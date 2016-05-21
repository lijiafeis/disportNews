package com.aygx.dazahui.db;

import com.aygx.dazahui.utils.ShareUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyCollectDb extends SQLiteOpenHelper {
	private static Context context;
	
	public final static String DB_NAME = "collect.db";
	public static final String TABLE_NAME = "collect";
	public static final String _ID = "_id";
	public static final String URL = "url";
	public static final String TITLE = "title";
	public static final String DATE = "date";
	public static final String IMG_URL = "img_url";

	public MyCollectDb(Context context) {
		super(context, DB_NAME, null, 1);
		this.context = context;
	}
	@Override
	public void onCreate(SQLiteDatabase arg0){
		arg0.execSQL("CREATE TABLE " +TABLE_NAME + "(" + _ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + TITLE + " text,"
				+ URL + " text," + DATE + " text," + IMG_URL + " text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
