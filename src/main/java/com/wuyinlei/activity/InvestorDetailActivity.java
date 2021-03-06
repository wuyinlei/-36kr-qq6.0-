package com.wuyinlei.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wuyinlei.DefineView;
import com.wuyinlei.bean.invesator.InvesatorData;
import com.wuyinlei.widget.RoundAngleImageView;

import java.util.Arrays;

public class InvestorDetailActivity extends BaseActivity implements DefineView{


    private RoundAngleImageView imgheader;  //头像
    private TextView iteminverstorname;   //投资人姓名
    private TextView investmentfielditem;  //投资领域
    private TextView focusfaeasitem;   //关注方向
    private TextView privatelittertv;  //私信
    private TextView attentiontv;   //关注
    private WebView detailscontent;   //内容
    private InvesatorData mInvesatorData;
    private TextView investor_count;

    //加载图片UML
    private ImageLoader mImageLoader;
    //加载配置方式
    private DisplayImageOptions mOptions;

    private WebSettings mWebSettings;

    String url = "https://rong.36kr.com/userinfo/";
    String id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor_detail);
        //设置沉浸式状态栏
        setStatusBar();
        initView();
        initValidata();
        initListener();
        bindData();
    }



    @Override
    public void initView() {
        imgheader = (RoundAngleImageView) findViewById(R.id.img_header);
        iteminverstorname = (TextView) findViewById(R.id.item_inverstor_name);
        investmentfielditem = (TextView) findViewById(R.id.investment_field_item);
        focusfaeasitem = (TextView) findViewById(R.id.focus_faeas_item);
        privatelittertv = (TextView) findViewById(R.id.private_litter_tv);
        attentiontv = (TextView) findViewById(R.id.attention_tv);
        detailscontent = (WebView) findViewById(R.id.details_content);
        investor_count = (TextView) findViewById(R.id.investor_count);
        mInvesatorData = (InvesatorData) getIntent().getSerializableExtra("investor_url");
        mImageLoader = ImageLoader.getInstance();
        mOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.defaultbg)
                .cacheOnDisk(true)   //设置保存在sdcard中
                .cacheInMemory(true) //设置保存在内存当中
                .build();

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

        //detailscontent.loadUrl(url+mInvesatorData.getUser().getId());

    }

    @Override
    public void initValidata() {
        mImageLoader.displayImage(mInvesatorData.getUser().getAvatar(),imgheader,mOptions);
        iteminverstorname.setText(mInvesatorData.getUser().getName());
        investmentfielditem.setText(mInvesatorData.getUser().getIntro());
        focusfaeasitem.setText(Arrays.toString(mInvesatorData.getInvestPhases()));
        investor_count.setText(mInvesatorData.getInvestComCount());

    }

    @Override
    public void initListener() {

    }

    @Override
    public void bindData() {

    }
}
