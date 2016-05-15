package com.aygx.dazahui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.aygx.dazahui.R;
import com.aygx.dazahui.fragment.NewsFragment;
import com.aygx.dazahui.fragment.PlayFragment;
import com.aygx.dazahui.fragment.SettingFragment;
import com.aygx.dazahui.fragment.UtilsFragment;
import com.aygx.dazahui.fragment.news.OneActivity;
import com.aygx.dazahui.utils.Utils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends FragmentActivity {
	private ImageView icon;// 左上角的图标，点击打开侧滑栏
	private SlidingMenu menu;
	private RadioGroup group;
	private FragmentManager supportFragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_main);

		setSlidingMenu(); // 设置侧滑菜单

		setImageViewIcon();// 设置点击坐上角的图标显示侧滑菜单

		setRadioButton();// 设置点击下面的四个按钮，显示四个Fragment.

	}

	private boolean flag = false;
	private long currentTimeMillis;
	@Override
	public void onBackPressed() {
		if (flag) {
			long currentTimeMillis2 =  System.currentTimeMillis();
			System.out.println(currentTimeMillis2);
			if(System.currentTimeMillis() - currentTimeMillis < 3000)
				
				finish();
			else{
				flag =!flag;
			}
		} else {
			Utils.showToast(this, "再按一次退出应用");
			flag = !flag;
			currentTimeMillis = System.currentTimeMillis();
			System.out.println(currentTimeMillis);
		}

	}

	private void setRadioButton() {
		group = (RadioGroup) findViewById(R.id.home_radioGroup);
		RadioButton btn_1 = (RadioButton) group.getChildAt(0);
		btn_1.setChecked(true);
		supportFragmentManager = getSupportFragmentManager();
		changerFragment(new NewsFragment(), false);

		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				switch (arg1) {
				case R.id.btn_1:
					changerFragment(new NewsFragment(), true);
					break;
				case R.id.btn_2:
					changerFragment(new PlayFragment(), true);
					break;
				case R.id.btn_3:
					changerFragment(new UtilsFragment(), true);
					break;
				case R.id.btn_4:
					changerFragment(new SettingFragment(), true);
					break;
				default:
					break;
				}
			}
		});

	}

	// 变换Fragment的方法
	private void changerFragment(Fragment fragment, boolean flag) {
		FragmentTransaction beginTransaction = supportFragmentManager
				.beginTransaction();
		beginTransaction.replace(R.id.home_frameLayout, fragment);
		if (!flag) {
			beginTransaction.addToBackStack(null);
		}
		beginTransaction.commit();
	}

	// 打开侧滑栏方法
	private void setImageViewIcon() {
		icon = (ImageView) findViewById(R.id.home_icon);
		icon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				menu.showMenu();
			}
		});
	}

	private void setSlidingMenu() {
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		// 设置触摸屏幕的模式
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow_left);

		// 设置滑动菜单视图的宽度
		// menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setBehindWidth(500);// 设置SlidingMenu菜单的宽度
		// 设置渐入渐出效果的值
		menu.setFadeDegree(0.35f);
		/**
		 * SLIDING_WINDOW will include the Title/ActionBar in the content
		 * section of the SlidingMenu, while SLIDING_CONTENT does not.
		 */
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		// 为侧滑菜单设置布局
		menu.setMenu(R.layout.activity_home_left_menu);
	}
	
	
}
