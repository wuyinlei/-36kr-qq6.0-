package com.wuyinlei.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.wuyinlei.DefineView;

public class RecentDetailActivity extends BaseActivity implements DefineView{

    private WebView recent_item_details;
    private Button btn_back;
    private WebSettings mWebSettings;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_detail);
        setStatusBar();
        url = getIntent().getStringExtra("recent_link");
        Log.d("RecentDetailActivity", url);
        initView();
        initListener();
        bindData();
    }


    @Override
    public void initView() {
        recent_item_details = (WebView) findViewById(R.id.recent_item_details);
        btn_back = (Button) findViewById(R.id.btn_back);

    }

    @Override
    public void initValidata() {

    }

    @Override
    public void initListener() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecentDetailActivity.this.finish();
            }
        });
    }

    @Override
    public void bindData() {
        recent_item_details.setWebChromeClient(new WebChromeClient() {

        });

        recent_item_details.setWebViewClient(new WebViewClient() {

        });

        mWebSettings = recent_item_details.getSettings();
        mWebSettings.setJavaScriptEnabled(true);

        mWebSettings.setDomStorageEnabled(true);  //开启DOM

        mWebSettings.setDefaultTextEncodingName("utf-8"); //设置编码
        // // web页面处理
        mWebSettings.setAllowFileAccess(true);// 支持文件流

        //提高网页加载速度，暂时阻塞图片加载，然后网页加载好了，在进行加载图片
        mWebSettings.setBlockNetworkImage(true);
        //开启缓存机制
        mWebSettings.setAppCacheEnabled(true);

        recent_item_details.loadUrl(url);

    }
}
