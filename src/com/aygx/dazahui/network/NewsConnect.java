package com.aygx.dazahui.network;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;

import com.aygx.dazahui.view.MyListView;
/**
 * 从网络中获取新闻信息。
 * 获取实时新闻和通过字符串进行字符检索。
 */

public class NewsConnect {
	
	private Activity mActivity;
	private MyListView listView;
	private List<String> list;
//	public String relute;//定义一个字符串，用于回传数据
	private SetReDianNews setReDianNews;
	public NewsConnect(Activity mActivity, MyListView listView, List<String> list){
		this.mActivity = mActivity;
		this.listView = listView;
		this.list = list;
	}
	
	public NewsConnect(SetReDianNews setReDianNews){
		this.setReDianNews = setReDianNews;
	}
	
	public static interface SetReDianNews{
		void getData(String relute);
	}
	
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
	// 配置您申请的KEY
	public static final String APPKEY = "bed7b55772de6074e89fda20bbcb1190";
	
	
	//获取热点新闻
	public void getNews(String newsName){
		String result =null;
        String url ="http://op.juhe.cn/onebox/news/query";//请求接口地址
        Map<String,Object> params = new HashMap<String,Object>();//请求参数
            params.put("q",newsName);//需要检索的关键字,请UTF8 URLENCODE
            params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
            params.put("dtype","");//返回数据的格式,xml或json，默认json
            net(url, params, "GET",false);
            
	}
	
	
	
	//实时新闻的获取
	public  void getRequest2() {
		String result = null;
		String url = "http://op.juhe.cn/onebox/news/words";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("key", APPKEY);// 应用APPKEY(应用详细页查询)
		params.put("dtype", "");// 返回数据的格式,xml或json，默认json
		net(url, params, "GET",true);
	}

	private void net(String strUrl, Map<String,Object> params, String method, boolean flag) {
		if (method == null || method.equals("GET")) {
			strUrl = strUrl + "?" + urlencode(params);
		}
		if(flag){
			MyAsyncTask myAsyncTask = new MyAsyncTask(listView, mActivity, list);
			myAsyncTask.setFlag(false);
			myAsyncTask.execute(strUrl);
		}else{
			MyAsyncTask myAsyncTask = new MyAsyncTask(new MyAsyncTask.SetReDianNews() {
				
				@Override
				public void getData(String relute) {
//					System.out.println("............." + relute);
//					NewsConnect.this.relute = relute;
					if(relute != null && setReDianNews != null){
						setReDianNews.getData(relute);
					}
				}
			});
			myAsyncTask.setFlag(true);
			myAsyncTask.execute(strUrl);
		}
		
		
	}

	// 将map型转为请求参数型
	private String urlencode(Map<String, Object> data) {
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
