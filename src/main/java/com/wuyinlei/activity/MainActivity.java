package com.wuyinlei.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.joanzapata.android.QuickAdapter;
import com.nineoldandroids.view.ViewHelper;
import com.wuyinlei.DefineView;
import com.wuyinlei.bean.AdHeadBean;
import com.wuyinlei.biz.HeadDataManager;
import com.wuyinlei.entity.ItemBean;
import com.wuyinlei.url.Config;
import com.wuyinlei.widget.DragLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public class MainActivity extends BaseActivity implements DefineView {
	private DragLayout dl;
	private ListView lv;
	private ImageView iv_icon, iv_bottom;
	private QuickAdapter<ItemBean> quickAdapter;
	private Button btn;
	private TextView iv_text;

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
		btn = (Button) findViewById(R.id.btn);
		iv_text = (TextView) findViewById(R.id.iv_text);
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
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(){
					@Override
					public void run() {
						super.run();
						Document document = null;
						try {
							document = Jsoup.connect(Config.CRAWLER_URL).timeout(100000).get();
						} catch (IOException e) {
							e.printStackTrace();
						}
						Gson gson = new Gson();
						/**
						 *  头条轮播数据
						 */
						List<AdHeadBean> headBeanList = new HeadDataManager().getHeadBeans_WYL(document);
						//System.out.print(gson.toJson(headBeanList));
						Message message = Message.obtain();
						message.obj = headBeanList;
						mHandler.sendMessageAtTime(message,2000);
					}
				}.start();
			}
		});
	}

	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			List<AdHeadBean> headBeanList = (List<AdHeadBean>) msg.obj;
			Gson gson = new Gson();
			String result = gson.toJson(headBeanList);
			iv_text.setText(result);
		}
	};

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
