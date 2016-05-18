package com.aygx.dazahui.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyJokeDb extends SQLiteOpenHelper {
	public final static String DB_NAME = "joke.db";
	public static final String TABLE_NAME = "joke";
	public static final String _ID = "_id";
	public static final String CONTENT = "content";
	public static final String TIME = "date";

	public MyJokeDb(Context context) {
		super(context, DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL("CREATE TABLE " + TABLE_NAME + "(" + _ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + CONTENT + " text,"
				+ TIME + " text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
