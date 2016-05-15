package com.aygx.dazahui.utils;

import android.content.Context;

public class ShareUtils {
	private static final String FILE_NAME = "dazahui";
	private static final String GUIDE_FLAG = "guide";
	public static void setGuideShare(Context context,boolean flag){
		context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putBoolean(GUIDE_FLAG,flag).commit();
	}
	public static boolean getGuideShare(Context context){
		boolean boolean1 = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getBoolean(GUIDE_FLAG,false);
		return boolean1;
	}
	
}
