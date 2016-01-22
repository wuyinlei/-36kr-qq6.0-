package com.wuyinlei.ui;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.joanzapata.android.QuickAdapter;
import com.nineoldandroids.view.ViewHelper;
import com.wuyinlei.DefineView;
import com.wuyinlei.entity.ItemBean;
import com.wuyinlei.widget.DragLayout;

public class MainActivity extends BaseActivity implements DefineView {
	private DragLayout dl;
	private ListView lv;
	private ImageView iv_icon, iv_bottom;
	private QuickAdapter<ItemBean> quickAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setStatusBar();
		initView();
		initValidata();
		initListener();
		bindData();

	}

	@Override
	public void initView() {
		dl = (DragLayout) findViewById(R.id.dl);
		iv_icon = (ImageView) findViewById(R.id.iv_icon);
		iv_bottom = (ImageView) findViewById(R.id.iv_bottom);
	}

	@Override
	public void initValidata() {

	}

	@Override
	public void initListener() {
		dl.setDragListener(new CustomDragListener());
		iv_icon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dl.open();
			}
		});
	}

	@Override
	public void bindData() {

	}

	class CustomDragListener implements DragLayout.DragListener{

		@Override
		public void onOpen() {

		}

		@Override
		public void onClose() {

		}

		@Override
		public void onDrag(float percent) {
			ViewHelper.setAlpha(iv_icon, 1 - percent);
		}
	}

}
