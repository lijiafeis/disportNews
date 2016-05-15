package com.aygx.dazahui.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyShiShiNewDb extends SQLiteOpenHelper {
	public static final String DB_NAME = "news.db";
	public static final String TABLE_NAME = "shishinews";
	public static final String _ID = "_id";
	public static final String REASON = "reason";
	public static final String RESULT = "result";
	public static final String ERROR_CODE = "error_code";
	public MyShiShiNewDb(Context context) {
		super(context, DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL("CREATE TABLE "+ TABLE_NAME + "("+_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ REASON + " text," + RESULT + " text,"+ ERROR_CODE + " INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
