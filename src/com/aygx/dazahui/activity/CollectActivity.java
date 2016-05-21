package com.aygx.dazahui.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindCallback;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.aygx.dazahui.R;
import com.aygx.dazahui.adapter.CollectAdapter;
import com.aygx.dazahui.bean.CollectShow;
import com.aygx.dazahui.bean.user.CollectBean;
import com.aygx.dazahui.db.MyCollectDb;
import com.aygx.dazahui.fragment.news.LoaddingNewsActivity;
import com.aygx.dazahui.fragment.news.NewsContentActivity;
import com.aygx.dazahui.utils.DBUtils;
import com.aygx.dazahui.utils.ShareUtils;
import com.aygx.dazahui.utils.Utils;

public class CollectActivity extends Activity implements OnClickListener,
		OnItemClickListener, OnItemLongClickListener {

	private ImageView collect_back;
	private ListView listView;
	private String table;// 定义网上的表明。

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_collect);
		initView();
		getCollectDataForDb();
		setListViewAdapter();

	}

	private void initView() {
		collect_back = (ImageView) findViewById(R.id.collect_back);

		listView = (ListView) findViewById(R.id.collect_listView);

		collect_back.setOnClickListener(this);

		table = ShareUtils.getUserName(CollectActivity.this)[0];

	}

	private List<CollectShow> list;
	private CollectAdapter adapter;

	private void getCollectDataForDb() {
		Cursor cursor = DBUtils.quiryAllForCollect(this);
		list = new ArrayList<CollectShow>();
		CollectShow collectShow = null;
		while (cursor.moveToNext()) {
			collectShow = new CollectShow();
			String title = cursor.getString(cursor
					.getColumnIndex(MyCollectDb.TITLE));
			String url = cursor.getString(cursor
					.getColumnIndex(MyCollectDb.URL));
			String date = cursor.getString(cursor
					.getColumnIndex(MyCollectDb.DATE));
			String img_url = cursor.getString(cursor
					.getColumnIndex(MyCollectDb.IMG_URL));
			collectShow.setDate(date);
			collectShow.setTitle(title);
			collectShow.setImg_url(img_url);
			collectShow.setUrl(url);
			System.out.println(title + url + date + img_url);
			list.add(collectShow);
		}
	}

	private void setListViewAdapter() {
		if (listView != null) {
			adapter = new CollectAdapter(list, this);
			listView.setAdapter(adapter);
			// 设置ListView的点击事件
			listView.setOnItemClickListener(this);
			// 设置ListView的长点击事件。
			listView.setOnItemLongClickListener(this);
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.collect_back:
			finish();
			overridePendingTransition(R.anim.left, R.anim.back);
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(this, NewsContentActivity.class);
		System.out.println("你点的是第" + arg2 + "个新闻");
		CollectShow collectShow = list.get(arg2);
		Bundle bundle = new Bundle();
		String url = collectShow.getUrl(); // 新闻连接
		String title = collectShow.getTitle(); // 新闻标题
		String img_url = collectShow.getImg_url(); // 新闻图片标题
		String pdate_src = collectShow.getDate(); // 时间
		bundle.putString(LoaddingNewsActivity.URL, url);
		bundle.putString(LoaddingNewsActivity.TITLE, title);
		bundle.putString(LoaddingNewsActivity.DATE, pdate_src);
		bundle.putString(LoaddingNewsActivity.IMG_URL, img_url);
		intent.putExtra(LoaddingNewsActivity.BUNDLE, bundle);
		startActivity(intent);
		overridePendingTransition(R.anim.go, R.anim.loading_news);
	}

	// 可能在查看的时候取消收藏，这是就要在OnStart函数中进行列表的刷新。
	@Override
	protected void onResume() {
		super.onResume();
		getCollectDataForDb();
		setListViewAdapter();
	}

	private String[] item = new String[] { "分享", "删除" };

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {

		final CollectShow collectShow = list.get(arg2);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setItems(item, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				if ("分享".equals(item[arg1])) {
					showShare(collectShow);
				} else {

					delectDataForUrl(collectShow.getUrl());
					// 删除数据完后重新加载数据。
				}
			}

		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss();
			}
		});
		builder.show();
		return true;
	}

	private CollectBean myCollect = null;
	// 点击删除的时候从网上删除数据
	private void delectDataForUrl(final String url1) {

		BmobQuery query = new BmobQuery(table);
		query.addWhereEqualTo("url", url1);
		query.findObjects(CollectActivity.this, new FindCallback() {

			@Override
			public void onFailure(int arg0, String arg1) {
			}

			@Override
			public void onSuccess(JSONArray arg0) {
				for (int i = 0; i < arg0.length(); i++) {
					try {
						String id = (String) arg0.getJSONObject(i).get(
								"objectId");
						// 删除
						if (myCollect == null) {
							myCollect = new CollectBean(table);
							myCollect.delete(CollectActivity.this, id,
									new DeleteListener() {

										@Override
										public void onSuccess() {
											DBUtils.delectCollectForUrl(
													CollectActivity.this, url1);
											getCollectDataForDb();
											setListViewAdapter();
										}

										@Override
										public void onFailure(int arg0,
												String arg1) {
											Utils.showToast(
													CollectActivity.this, arg1);
										}
									});

						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	private void showShare(CollectShow collectShow) {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(collectShow.getTitle());
		// titleUrl是网和QQ标题的网络链接，仅在人人空间使用
		oks.setTitleUrl(collectShow.getUrl());
		// text是分享文本，所有平台都需要这个字段
		oks.setText(collectShow.getTitle());
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		// oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl(collectShow.getUrl());
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment("我是测试评论文本");
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl(collectShow.getUrl());

		// 启动分享GUI
		oks.show(this);
	}

}
