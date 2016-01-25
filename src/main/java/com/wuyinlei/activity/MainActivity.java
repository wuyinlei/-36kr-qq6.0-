package com.wuyinlei.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.nineoldandroids.view.ViewHelper;
import com.wuyinlei.DefineView;
import com.wuyinlei.adapter.LeftItemAdapter;
import com.wuyinlei.widget.DragLayout;

public class MainActivity extends BaseActivity implements DefineView {
	public DragLayout mDragLayout;

	public DragLayout getDragLayout() {
		return mDragLayout;
	}

	private ListView mListView;
	private ImageView iv_icon, iv_bottom;
	private LinearLayout ll_setting;
	private LeftItemAdapter mItemAdapter;

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
		mDragLayout = (DragLayout) findViewById(R.id.dl);
		mListView = (ListView) findViewById(R.id.lv);
		iv_icon = (ImageView) findViewById(R.id.iv_icon);
		iv_bottom = (ImageView) findViewById(R.id.iv_bottom);
		ll_setting = (LinearLayout) findViewById(R.id.ll_setting);
	}

	@Override
	public void initValidata() {
		mItemAdapter = new LeftItemAdapter();
		mListView.setAdapter(mItemAdapter);
	}

	@Override
	public void initListener() {
		mDragLayout.setDragListener(new CustomDragListener());
		iv_icon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mDragLayout.open();
			}
		});
		ll_setting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "点击设置干嘛", Toast.LENGTH_SHORT).show();
			}
		});
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
