package com.aygx.dazahui.utils;

import android.content.Context;
import android.widget.Toast;

public class Utils {
	public static void showToast(Context context,String str){
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}
}
