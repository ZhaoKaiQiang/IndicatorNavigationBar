/*
 * Copyright (c) 2014, 青岛司通科技有限公司 All rights reserved.
 * File Name：IndicatorNavigationBar.java
 * Version：V1.0
 * Author：zhaokaiqiang
 * Date：2014-10-17
 */
package com.example.indicatornavigationbar;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @ClassName: com.mengle.activity.IndicatorNavigationBar
 * @Description: 带有指示器的底部导航栏
 * @author zhaokaiqiang
 * @date 2014-10-17 上午11:07:40
 * 
 */
public class IndicatorNavigationBar extends LinearLayout implements
		OnClickListener, OnPageChangeListener {

	// 导航栏默认高度，不包括指示器高度，单位是dp
	private static final int HEIGHT_NAVIGATION_BAR = 40;
	// 指示器默认高度，单位是dp
	private static final int HEIGHT_INDICATOR = 4;

	private Context context;
	private ViewPager viewPager;
	// 指示器
	private ImageView ivBottomLine;
	// 当前显示的index
	private int currIndex = 0;
	// 存储移动位置
	private int positions[];
	// 标题数量
	private int titleCount;

	public IndicatorNavigationBar(Context context) {
		super(context);
		this.context = context;
	}

	/**
	 * 
	 * @Description: 依附到父布局上
	 * @param viewGroup
	 *            要依附在的父布局
	 * @param titles
	 *            底部显示的导航文字
	 * @param viewPager
	 *            绑定的ViewPager对象
	 * @return void
	 */
	public void attachToParent(ViewGroup viewGroup, String[] titles,
			ViewPager viewPager) {

		this.viewPager = viewPager;
		titleCount = titles.length;

		// 初始化主布局
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				dip2px(HEIGHT_NAVIGATION_BAR + HEIGHT_INDICATOR)));
		setBackgroundColor(getResources().getColor(android.R.color.transparent));
		setOrientation(VERTICAL);

		// 导航栏布局
		LinearLayout ll_navigation = new LinearLayout(context);
		ll_navigation.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, dip2px(HEIGHT_NAVIGATION_BAR)));
		ll_navigation.setBackgroundColor(getResources().getColor(
				android.R.color.holo_blue_light));
		ll_navigation.setOrientation(HORIZONTAL);

		// 生成导航按钮(TextView)
		for (int i = 0; i < titles.length; i++) {

			TextView textView = new TextView(context);
			textView.setLayoutParams(new LayoutParams(0,
					dip2px(HEIGHT_NAVIGATION_BAR), 1));
			textView.setText(titles[i]);
			textView.setGravity(Gravity.CENTER);
			textView.setTextSize(16);
			textView.setTextColor(getResources()
					.getColor(android.R.color.white));
			textView.setId(i);
			textView.setOnClickListener(this);
			ll_navigation.addView(textView);
		}
		// 添加导航
		this.addView(ll_navigation);

		// 指示器布局
		LinearLayout ll_indicator = new LinearLayout(context);
		ll_indicator.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, dip2px(HEIGHT_INDICATOR)));
		ll_indicator.setBackgroundColor(getResources().getColor(
				android.R.color.holo_blue_light));
		ll_indicator.setOrientation(HORIZONTAL);

		// 指示器
		ivBottomLine = new ImageView(context);
		ivBottomLine.setImageResource(android.R.color.holo_orange_light);
		ivBottomLine.setScaleType(ScaleType.MATRIX);
		ivBottomLine
				.setLayoutParams(new LinearLayout.LayoutParams(
						getScreenWidth(context) / titleCount,
						dip2px(HEIGHT_INDICATOR)));
		ll_indicator.addView(ivBottomLine);
		// 添加指示器
		this.addView(ll_indicator);

		viewGroup.addView(this);
		viewPager.setOnPageChangeListener(this);

		// 初始化移动位置
		positions = new int[titleCount];
		positions[0] = 0;
		int distance = (int) (getScreenWidth(context) / titleCount);
		for (int i = 1; i < titleCount; i++) {
			positions[i] = distance * i;
		}

	}

	@Override
	public void onClick(View v) {
		viewPager.setCurrentItem(v.getId());
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {

		Animation animation = new TranslateAnimation(currIndex * positions[1],
				positions[position], 0, 0);
		currIndex = position;
		animation.setFillAfter(true);
		animation.setDuration(300);
		ivBottomLine.startAnimation(animation);
	}

	private int dip2px(float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	// 获取屏幕宽度
	private int getScreenWidth(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		return dm.widthPixels;
	}
}
