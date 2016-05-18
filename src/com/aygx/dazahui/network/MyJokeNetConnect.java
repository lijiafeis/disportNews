package com.aygx.dazahui.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.aygx.dazahui.bean.JokePicBean;
import com.aygx.dazahui.network.MyJokeAsyncTask.OnResult;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class MyJokeNetConnect {

	/**
	 * 笑话大全调用示例代码 － 聚合数据 在线接口文档：http://www.juhe.cn/docs/95
	 **/
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

	// 配置您申请的KEY
	public static final String APPKEY = "ef58a2578afebd39fe55214a058033e9";
	static String result1 = null;
	// 2.最新笑话
	private static int jokePager;// 笑话的页数；
	private int jokeItem;// 每页的个数

	public static OnGetJoke onGetJoke;

	// 当访问完joke的数据后在进行pic的访问

	 public MyJokeNetConnect() {
		 
	}

	public MyJokeNetConnect(int jokePager, int jokeItem, OnGetJoke onGetJoke2) {
		this.jokeItem = jokeItem;
		this.jokePager = jokePager;
		this.onGetJoke = onGetJoke2;
	}

	public interface OnGetJoke {
		void getResult(String result);

	}

	public void getNewsJoke() {
		String result = null;
		String url = "http://japi.juhe.cn/joke/content/text.from";// 请求接口地址
		Map params = new HashMap();// 请求参数
		params.put("page", jokePager + "");// 当前页数,默认1
		params.put("pagesize", jokeItem + "");// 每次返回条数,默认1,最大20
		params.put("key", APPKEY);// 您申请的key
		try {
			net(url, params, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private static int pagerforPic = 1;//用来记录的图片的页数。
	
	// 4.最新趣图
	public void getPic() {
		String result = null;
		String url = "http://japi.juhe.cn/joke/img/text.from";// 请求接口地址
		Map params = new HashMap();// 请求参数
		params.put("page", jokePager + "");// 当前页数,默认1
		params.put("pagesize", jokeItem + "");// 每次返回条数,默认1,最大20
		params.put("key", APPKEY);// 您申请的key
		try {
			net(url, params, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param strUrl
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param method
	 *            请求方法
	 * @return 网络请求字符串
	 * @throws Exception
	 */
	private void net(String strUrl, Map params, String method) throws Exception {
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		if (method == null || method.equals("GET")) {
			strUrl = strUrl + "?" + urlencode(params);
		}
		visitUrl(strUrl);
	}

	private void visitUrl(String strUrl) {
		MyJokeAsyncTask myAsyncTask = new MyJokeAsyncTask();
		myAsyncTask.execute(strUrl);
		myAsyncTask.setOnResult(new OnResult() {

			@Override
			public void getResult(String result) {
				if(onGetJoke != null){
					onGetJoke.getResult(result);
				}
			}
		});
	}

	// 将map型转为请求参数型
	public String urlencode(Map<String, Object> data) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry i : data.entrySet()) {
			try {
				sb.append(i.getKey()).append("=")
						.append(URLEncoder.encode(i.getValue() + "", "UTF-8"))
						.append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
