package com.wuyinlei.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.nostra13.universalimageloader.core.DisplayImageOptions;
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

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

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
    private DisplayImageOptions mOptions;

    private HomeNewsBean mHomeNewsBean;

    private String[] items = new String[]{"超大号字体", "大号字体", "正常字体", "小号字体", "超小号字体"};

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            bindData();
        }
    };
    private WebSettings mWebSettings;

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
        btn_font = (Button) findViewById(R.id.btn_font);
        btn_night = (Button) findViewById(R.id.btn_night);
        btn_share = (Button) findViewById(R.id.btn_share);
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

        mWebSettings = detailscontent.getSettings();
        mWebSettings.setJavaScriptEnabled(true);

        mWebSettings.setDomStorageEnabled(true);  //开启DOM

        mWebSettings.setDefaultTextEncodingName("utf-8"); //设置编码
        // // web页面处理
        mWebSettings.setAllowFileAccess(true);// 支持文件流

        //提高网页加载速度，暂时阻塞图片加载，然后网页加载好了，在进行加载图片
        mWebSettings.setBlockNetworkImage(true);
        //开启缓存机制
        mWebSettings.setAppCacheEnabled(true);

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
        mOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.defaultbg)
                .cacheOnDisk(true)   //设置保存在sdcard中
                .cacheInMemory(true) //设置保存在内存当中
                .build();

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
            mImageLoader.displayImage(mArticleBean.getHeadImage(),detailsad,mOptions);
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
                    showChooseDialog();
                    Toast.makeText(NewsDetailActivity.this, "改变字体", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_night:
                    changeTheme();
                    Toast.makeText(NewsDetailActivity.this, "调整黑白模式", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_share:
                    shareComplete();
                    break;
            }
        }


    }

    private void shareComplete() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }

    boolean flsg = true;
    /**
     * 主题切换
     */
    private void changeTheme() {
        if (flsg){
            flsg = false;
            detailscontent.setBackgroundResource(R.color.color_tab_title);
        } else {
            detailscontent.setBackgroundResource(R.color.color_white);
        }

    }

    private int mCurrentChooseItem;//记录当前选中的item   点击确定前

    private int mCurrentItem = 2;//选中的item   点击确定后

    /**
     * 调整字体大小
     */
    private void showChooseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("字体设置");
        builder.setSingleChoiceItems(items, mCurrentItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCurrentChooseItem = which;
            }
        });

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (mCurrentChooseItem) {
                    case 0:
                        mWebSettings.setTextSize(WebSettings.TextSize.LARGEST);
                        break;
                    case 1:
                        mWebSettings.setTextSize(WebSettings.TextSize.LARGER);
                        break;
                    case 2:
                        mWebSettings.setTextSize(WebSettings.TextSize.NORMAL);
                        break;
                    case 3:
                        mWebSettings.setTextSize(WebSettings.TextSize.SMALLER);
                        break;
                    case 4:
                        mWebSettings.setTextSize(WebSettings.TextSize.SMALLEST);
                        break;
                }
                mCurrentItem = mCurrentChooseItem;
            }
        });
        builder.setNegativeButton("取消",null);
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK();
    }
}
