package com.wuyinlei.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.okhttp.Request;
import com.wuyinlei.DefineView;
import com.wuyinlei.bean.ArticleBean;
import com.wuyinlei.bean.HomeNewsBean;
import com.wuyinlei.biz.ArticleDataManager;
import com.wuyinlei.http.OkHttpManager;
import com.wuyinlei.url.Config;
import com.wuyinlei.widget.RoundAngleImageView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class NewsDetailActivity extends BaseActivity implements DefineView {

    private Button btn_back,btn_font,btn_night,btn_share;
    private RelativeLayout layoutbottombar;
    private TextView detailstitle;
    private RoundAngleImageView detailsavatar;
    private TextView detailsname;
    private TextView detailstime;
    private ImageView detailsad;
    private WebView detailscontent;
    private RelativeLayout relativecontent;
    private FrameLayout promptframelayout;
    private ArticleBean mArticleBean;

    private FrameLayout home_framelayout;
    private LinearLayout loading,empty,error;

    private String titleUrl,titleId;

    private ImageLoader mImageLoader;
    private HomeNewsBean mHomeNewsBean;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            bindData();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);
        setStatusBar();

        Intent mIntent=getIntent();
        titleUrl=mIntent.getStringExtra("titleUrl");
        titleId=mIntent.getStringExtra("titleId");
        mHomeNewsBean = (HomeNewsBean) mIntent.getSerializableExtra("news_item");
        //homeNewsBean = (HomeNewsBean) getIntent().getSerializableExtra("news_item");
        initView();
        initValidata();
        initListener();
    }

    @Override
    public void initView() {
        layoutbottombar = (RelativeLayout) findViewById(R.id.layout_bottom_bar);
        detailstitle = (TextView) findViewById(R.id.details_title);
        detailsavatar = (RoundAngleImageView) findViewById(R.id.details_avatar);
        detailsname = (TextView) findViewById(R.id.details_name);
        detailstime = (TextView) findViewById(R.id.details_time);
        detailsad = (ImageView) findViewById(R.id.details_ad);
        detailscontent = (WebView) findViewById(R.id.details_content);
        relativecontent = (RelativeLayout) findViewById(R.id.relative_content);
        promptframelayout = (FrameLayout) findViewById(R.id.prompt_framelayout);

        home_framelayout=(FrameLayout)this.findViewById(R.id.prompt_framelayout);
        loading=(LinearLayout)this.findViewById(R.id.loading);
        empty=(LinearLayout)this.findViewById(R.id.empty);
        error=(LinearLayout)this.findViewById(R.id.error);

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new MyOnClickListener());
        btn_font = (Button) findViewById(R.id.btn_font);
        btn_font.setOnClickListener(new MyOnClickListener());
        btn_night = (Button) findViewById(R.id.btn_night);
        btn_night.setOnClickListener(new MyOnClickListener());
        btn_share = (Button) findViewById(R.id.btn_share);
        btn_share.setOnClickListener(new MyOnClickListener());
    }

    @Override
    public void initValidata() {
        relativecontent.setVisibility(View.GONE);
        home_framelayout.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        empty.setVisibility(View.GONE);
        error.setVisibility(View.GONE);

        detailscontent.setWebChromeClient(new WebChromeClient() {

        });

        detailscontent.setWebViewClient(new WebViewClient() {

        });

        WebSettings webSettings = detailscontent.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webSettings.setDomStorageEnabled(true);  //开启DOM

        webSettings.setDefaultTextEncodingName("utf-8"); //设置编码
        // // web页面处理
        webSettings.setAllowFileAccess(true);// 支持文件流

        //提高网页加载速度，暂时阻塞图片加载，然后网页加载好了，在进行加载图片
        webSettings.setBlockNetworkImage(true);
        //开启缓存机制
        webSettings.setAppCacheEnabled(true);

        OkHttpManager.getAsync(titleUrl, new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                relativecontent.setVisibility(View.GONE);
                home_framelayout.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
                empty.setVisibility(View.GONE);
                error.setVisibility(View.VISIBLE);
            }

            @Override
            public void requestSuccess(String result) throws Exception {

                final Document document= Jsoup.parse(result, Config.CRAWLER_URL);

               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       mArticleBean = new ArticleDataManager(titleId).getArticleBean(document);
                       Log.d("NewsDetailActivity", mArticleBean.toString());
                       Message message = Message.obtain();
                       message.obj = mArticleBean;
                       mHandler.sendMessage(message);
                   }
               }).start();

            }
        });

    }


    @Override
    public void initListener() {
        btn_back.setOnClickListener(new MyOnClickListener());
        btn_font.setOnClickListener(new MyOnClickListener());
        btn_night.setOnClickListener(new MyOnClickListener());
        btn_share.setOnClickListener(new MyOnClickListener());
    }

    @Override
    public void bindData() {

        mImageLoader = ImageLoader.getInstance();

        if (mArticleBean != null){
            relativecontent.setVisibility(View.VISIBLE);
            home_framelayout.setVisibility(View.GONE);
            loading.setVisibility(View.GONE);
            empty.setVisibility(View.GONE);
            error.setVisibility(View.GONE);
            mImageLoader.displayImage(mArticleBean.getHeadImage(), detailsavatar);
            detailstitle.setText(mArticleBean.getTitle());
            detailsname.setText(mHomeNewsBean.getAuthorBean().getName());
            detailstime.setText("发表于：" + mArticleBean.getDatetext());
            detailscontent.loadDataWithBaseURL(Config.CRAWLER_URL,mArticleBean.getContext(),"text/html","UTF-8","");
            mImageLoader.displayImage(mArticleBean.getHeadImage(),detailsad);
        }
        else{
            relativecontent.setVisibility(View.GONE);
            home_framelayout.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
            error.setVisibility(View.GONE);
        }

    }

    class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_back:
                    NewsDetailActivity.this.finish();
                    break;
                case R.id.btn_font:
                    Toast.makeText(NewsDetailActivity.this, "改变字体", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_night:
                    Toast.makeText(NewsDetailActivity.this, "调整黑白模式", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_share:
                    Toast.makeText(NewsDetailActivity.this, "分享", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }


}
