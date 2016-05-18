package com.aygx.dazahui.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;

class MyJokeAsyncTask extends AsyncTask<String, Void, String> {

	private OnResult onResult;

	public void setOnResult(OnResult onResult) {
		this.onResult = onResult;
	}

	interface OnResult {
		void getResult(String result);
	}

	boolean flag = false;// 用来表示走的是第几个循环，如果走完的时候，在返回数据。

	@Override
	protected String doInBackground(String... arg0) {
		StringBuffer sb = null;
		URL url;
		try {
			sb = new StringBuffer();
				String string = arg0[0];
				url = new URL(string);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				InputStream inputStream = conn.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						inputStream, "UTF-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				// System.out.println(sb.toString());
				return sb.toString();

		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.out.println("网址找不到");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (onResult != null) {
			onResult.getResult(result);
		}
	}
}
