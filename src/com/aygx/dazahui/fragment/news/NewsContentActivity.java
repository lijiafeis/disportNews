package com.aygx.dazahui.fragment.news;

import java.io.IOException;
import java.util.jar.JarEntry;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator.IsEmpty;
import org.w3c.dom.Text;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.aygx.dazahui.R;
import com.aygx.dazahui.db.MyCollectDb;
import com.aygx.dazahui.utils.DBUtils;
import com.aygx.dazahui.utils.ShareUtils;
import com.aygx.dazahui.utils.Utils;
import com.lidroid.xutils.DbUtils;

public class NewsContentActivity extends Activity implements OnClickListener {
	private ImageView content_back;
	private ImageView content_share;
	private ImageView content_collect;
	private ProgressBar progressBar;

	private WebView webView;

	private String url;
	private String title;
	private String date;
	private String img_url;
	boolean isCheck = true;// 记录收藏按钮的状态。

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.news_content_pager);

		// 先判断数据库中有没有这条数据。

		getUrlForIntent();
		initView();
		if (url != null) {

			int isExist = DBUtils.quiryCollectByUrl(this, url);
			if (1 == isExist) {
				content_collect.setImageResource(R.drawable.collect_pre_icon);
				isCheck = true;
			} else {

				content_collect.setImageResource(R.drawable.collect);
				isCheck = false;
			}
		}

	}

	// 得到传过来的数据
	private void getUrlForIntent() {
		Bundle bundle = getIntent().getBundleExtra(LoaddingNewsActivity.BUNDLE);
		url = bundle.getString(LoaddingNewsActivity.URL);
		title = bundle.getString(LoaddingNewsActivity.TITLE);
		date = bundle.getString(LoaddingNewsActivity.DATE);
		img_url = bundle.getString(LoaddingNewsActivity.IMG_URL);
	}

	private void initView() {
		content_back = (ImageView) findViewById(R.id.content_back);
		content_share = (ImageView) findViewById(R.id.content_share);
		content_collect = (ImageView) findViewById(R.id.content_collect);

		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		progressBar.setMax(100);
		webView = (WebView) findViewById(R.id.webView);

		setWebView();

		content_back.setOnClickListener(this);
		content_share.setOnClickListener(this);
		content_collect.setOnClickListener(this);
	}

	// private String title;

	// 处理与网页有关的操作
	private void setWebView() {
		// getTitleForJsoup();

		webView.loadUrl(url);
		webView.requestFocus();
		WebSettings settings = webView.getSettings();
		settings.setSupportZoom(true); // 支持缩放
		// settings.setBuiltInZoomControls(true); // 启用内置缩放装置
		// settings.setJavaScriptEnabled(true); // 启用JS脚本
		settings.setUseWideViewPort(true); // 将图片调整到适合webview的大小
		settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webView.setWebViewClient(new WebViewClient() {
			// 当点击链接时,希望覆盖而不是打开新窗口
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url); // 加载新的url
				return true; // 返回true,代表事件已处理,事件流到此终止
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				Utils.showToast(NewsContentActivity.this, "访问出错");
				super.onReceivedError(view, errorCode, description, failingUrl);
			}

		});

		// 点击后退按钮,让WebView后退一页(也可以覆写Activity的onKeyDown方法)
		webView.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
						webView.goBack(); // 后退
						return true; // 已处理
					}
				}
				return false;
			}
		});

		webView.setWebChromeClient(new WebChromeClient() {
			// 当WebView进度改变时更新窗口进度
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// Activity的进度范围在0到10000之间,所以这里要乘以100
				progressBar.setProgress(newProgress);
				progressBar.setVisibility(newProgress == 100 ? View.GONE
						: View.VISIBLE);

			}
		});
	}

	// private void getTitleForJsoup() {
	// System.out.println("访问网络的地址" + url);
	// new Thread() {
	//
	// @Override
	// public void run() {
	// super.run();
	// Document document;
	// try {
	// document = Jsoup.connect(url).get();
	// title = document.title();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }.start();
	// }

	// 点击上面返回和分享的时候
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.content_back:
			finish();
			// 执行动画
			overridePendingTransition(R.anim.left, R.anim.back);
			break;
		case R.id.content_share:
			shareApp();
			break;
		case R.id.content_collect:
			// 点击按钮，显示收藏。
			setCollect();
			break;
		default:
			break;
		}
	}

	/*
	 * 先从数据库中得到数据，看这条数据是不是在数据库中，如果存在，设置为点击完的图片，如果不是设置默认。
	 */
	private void setCollect() {
		if (!ShareUtils.getlogin(this)) {
			Utils.showAlertDialog(this, "请登录");
		} else {
			if (isCheck) { // 数据库中有这条数据。点击是想取消收藏。
				content_collect.setImageResource(R.drawable.collect);

				// 从数据库中删除这条数据
				if (!TextUtils.isEmpty(url)) {
					DBUtils.delectCollectForUrl(this, url);
				}
				isCheck = false;
			} else {
				content_collect.setImageResource(R.drawable.collect_pre_icon);

				// 添加数据到数据库
				if (!TextUtils.isEmpty(url) && !TextUtils.isEmpty(title)) {
					insertDataForDb();
				} else {
					Utils.showToast(this, "收藏失败");
				}
				isCheck = true;
			}
		}

	}

	private ContentValues cv;

	// 添加收藏信息到数据库中去。
	private void insertDataForDb() {
		if (cv == null) {
			cv = new ContentValues();
		} else {
			cv.clear();
		}
		cv.put(MyCollectDb.URL, url);
		cv.put(MyCollectDb.TITLE, title);
		cv.put(MyCollectDb.DATE, date);
		cv.put(MyCollectDb.IMG_URL, img_url);
		DBUtils.insertAllForCollect(this, cv);

		Utils.showToast(this, "收藏成功");

	}

	// 点击分享按钮执行的方法。
	private void shareApp() {
		showShare();
	}

	private void showShare() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(title);
		// titleUrl是网和QQ标题的网络链接，仅在人人空间使用
		oks.setTitleUrl(url);
		// text是分享文本，所有平台都需要这个字段
		oks.setText(title);
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		// oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl(url);
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment("我是测试评论文本");
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl(url);

		// 启动分享GUI
		oks.show(this);
	}

	// 进行手势的判断finish当前的页面
	// @Override
	// public boolean onTouchEvent(MotionEvent event) {
	// int startX = 0;
	// int startY = 0;
	// switch (event.getAction()){
	// case MotionEvent.ACTION_DOWN:
	// startX = (int) event.getX();
	// startY = (int) event.getY();
	// break;
	// case MotionEvent.ACTION_UP:
	// int endX = (int) event.getX();
	// int endY = (int) event.getY();
	// if(endX - startX > 300){
	// finish();
	// }
	// break;
	// default:
	// break;
	// }
	// return true;
	// }
	// @Override
	// public boolean dispatchTouchEvent(MotionEvent event) {
	// int startX = 0;
	// int startY = 0;
	// switch (event.getAction()) {
	// case MotionEvent.ACTION_DOWN:
	// startX = (int) event.getX();
	// startY = (int) event.getY();
	// break;
	// case MotionEvent.ACTION_UP:
	// int endX = (int) event.getX();
	// int endY = (int) event.getY();
	// if (endX - startX > 500 && Math.abs(endY - startY)<100) {
	// System.out.println(endX - startX);
	// finish();
	// }
	// if (Math.abs(endY - startY) > 100) {
	// return false;
	// }
	// break;
	// default:
	// break;
	// }
	// return false;
	// }

}
