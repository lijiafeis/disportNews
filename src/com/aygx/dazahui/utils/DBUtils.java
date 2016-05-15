package com.aygx.dazahui.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aygx.dazahui.db.MyReDianNewsDb;
import com.aygx.dazahui.db.MyShiShiNewDb;

public class DBUtils {
	private static MyShiShiNewDb sqlite;
	private static SQLiteDatabase db;

	private static MyReDianNewsDb reDian;
	private static SQLiteDatabase sb;

	public static void insertAllForShiShi(Context context, ContentValues values) {
		if (sqlite == null) {
			sqlite = new MyShiShiNewDb(context);
		}

		db = sqlite.getWritableDatabase();
		db.insert(MyShiShiNewDb.TABLE_NAME, null, values);
		db.close();
	}

	public static Cursor quiryAllForShiShi(Context context) {
		if (sqlite == null) {
			sqlite = new MyShiShiNewDb(context);
		}
		db = sqlite.getReadableDatabase();
		Cursor query = db.query(MyShiShiNewDb.TABLE_NAME, null, null, null,
				null, null, null);
		return query;

	}

	// 对热点新闻进行查找。
	public static void insertAllForReDian(Context context, ContentValues values) {
		if (reDian == null) {
			reDian = new MyReDianNewsDb(context);
		}

		sb = reDian.getWritableDatabase();
		sb.insert(MyReDianNewsDb.TABLE_NAME, null, values);
		sb.close();
	}
	/*
	 * 查找全部
	 */
	public static Cursor quiryAllForReDian(Context context) {
		if (reDian == null) {
			reDian = new MyReDianNewsDb(context);
		}
		sb = reDian.getReadableDatabase();
		Cursor query = sb.query(MyReDianNewsDb.TABLE_NAME, null, null, null,
				null, null, null);
		return query;

	}
	/*
	 * 通过网络连接进行查找、0表示数据库中没有这个数据 1表示数据库有这一条数据
	 */
	public static int quiryByUrl(Context context,String url){
		if (reDian == null) {
			reDian = new MyReDianNewsDb(context);
		}
		sb = reDian.getReadableDatabase();
		Cursor query = sb.query(MyReDianNewsDb.TABLE_NAME, null, MyReDianNewsDb.NEWS_URL + " = ?", new String[]{url}, null, null, null);
		if(query.moveToFirst())
			return 1;
		return 0;
		
	}
	public static String[] quiryByShiShi_id(Context context,String id){
		if (reDian == null) {
			reDian = new MyReDianNewsDb(context);
		}
		sb = reDian.getReadableDatabase();
		Cursor query = sb.query(MyReDianNewsDb.TABLE_NAME, new String[]{MyReDianNewsDb.IMG_URL,MyReDianNewsDb.PDATE_SRC}, MyReDianNewsDb.SHISHI_ID + " = ?", new String[]{id}, null, null, null);
		String[] str = new String[2];
			while(query.moveToNext()){
				str[0] = query.getString(query.getColumnIndex(MyReDianNewsDb.IMG_URL));
				str[1] = query.getString(query.getColumnIndex(MyReDianNewsDb.PDATE_SRC));
			}
		return str;
	}
	
}
