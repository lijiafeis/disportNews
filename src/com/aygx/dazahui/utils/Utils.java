package com.aygx.dazahui.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.Toast;

public class Utils {
	public static void showToast(Context context,String str){
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}
	
	public static void showAlertDialog(Context context,String message){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message);
		builder.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss();
			}
		});
		builder.show();
	}
	
}
