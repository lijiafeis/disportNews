package com.aygx.dazahui.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class ShareUtils {
	private static final String FILE_NAME = "dazahui";
	private static final String GUIDE_FLAG = "guide";
	
	private static final String USER_NAME = "user_name";
	private static final String PASS_WORD = "password";
	
	
	public static void setGuideShare(Context context,boolean flag){
		context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putBoolean(GUIDE_FLAG,flag).commit();
	}
	public static boolean getGuideShare(Context context){
		boolean boolean1 = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getBoolean(GUIDE_FLAG,false);
		return boolean1;
	}
	
	//判断是否登录成功
	public static void setlogin(Context context,boolean flag){
		context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putBoolean("login",flag).commit();
	}
	public static boolean getlogin(Context context){
		boolean boolean1 = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getBoolean("login",false);
		return boolean1;
	}
	
	
	
	//当用户登录成功的时候，自动保存帐号和密码
	public static void setUserName(Context context,String userName,String password){
		Editor edit = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
		edit.putString(USER_NAME,userName).commit();
		edit.putString(PASS_WORD,password).commit();
	}
	public static String[] getUserName(Context context){
		String userName = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString(USER_NAME,"");
		String passWord = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString(PASS_WORD,"");
		return new String[]{userName,passWord};
	}
	
}
