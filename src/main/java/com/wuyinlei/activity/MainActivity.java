package com.wuyinlei.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.nineoldandroids.view.ViewHelper;
import com.squareup.okhttp.Request;
import com.wuyinlei.CNKApplication;
import com.wuyinlei.DefineView;
import com.wuyinlei.UpdateReceiver;
import com.wuyinlei.adapter.LeftItemAdapter;
import com.wuyinlei.bean.UpdataInfoModel;
import com.wuyinlei.http.OkHttpManager;
import com.wuyinlei.url.Config;
import com.wuyinlei.widget.DragLayout;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class MainActivity extends BaseActivity implements DefineView {
	public DragLayout mDragLayout;

	public DragLayout getDragLayout() {
		return mDragLayout;
	}

	private ListView mListView;
	private ImageView iv_icon, iv_bottom;
	private LinearLayout ll_setting;
	private LeftItemAdapter mItemAdapter;

	private UpdateReceiver mUpdateReceiver;
	private IntentFilter mIntentFilter;

	private HashMap<String,Object> tmpMap = CNKApplication.getInstance().getTempMap();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//registerBroadcast();
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

		OkHttpManager.getAsync(Config.update_url, new OkHttpManager.DataCallBack() {
			@Override
			public void requestFailure(Request request, IOException e) {

			}

			@Override
			public void requestSuccess(String result) throws Exception {
				try {
					JSONObject object = new JSONObject(result);
					UpdataInfoModel model = new UpdataInfoModel();
					model.setAppname(object.getString("appname"));
					model.setLastForce(object.getString("lastForce"));
					model.setServerflag(object.getString("serverflag"));
					model.setServerVersion(object.getString("serverVersion"));
					model.setUpdateDeinfo(object.getString("updateDeinfo"));
					model.setUpdateUrl(object.getString("updateUrl"));

					tmpMap.put(Config.KEY_APP_UPDATE,model);
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		});
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
				switch (position){
					case 0: //账号信息
						break;
					case 1:  //我的关注
						break;
					case 2:  //我的发现
						openActivity(FindActivity.class);
						break;
					case 3:  //我的收藏
						break;
					case 4:   //意见反馈
						startActivity(new Intent(MainActivity.this,SuggestActivity.class));
						break;
					case 5:  //关于我们
						startActivity(new Intent(MainActivity.this,AboutActivity.class));
						break;
				}
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


	/**
	 * 自动更新
	 */
	protected void getAppUpdateOk(){
		this.sendBroadcast(new Intent(UpdateReceiver.UPDATE_ACTION));
	}


	/**
	 * 广播注册
	 */
	private void registerBroadcast(){
		mUpdateReceiver = new UpdateReceiver(false);
		mIntentFilter = new IntentFilter(UpdateReceiver.UPDATE_ACTION);
		this.registerReceiver(mUpdateReceiver, mIntentFilter);
	}

	private void unRegisterBroadcast(){
		try{
				this.unregisterReceiver(mUpdateReceiver);
		}catch (Exception e){

		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		//unRegisterBroadcast();
	}
}
