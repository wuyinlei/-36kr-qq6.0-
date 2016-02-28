package com.wuyinlei.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wuyinlei.DefineView;
import com.wuyinlei.bean.invesator.InvesatorData;
import com.wuyinlei.widget.RoundAngleImageView;

public class InvestorDetailActivity extends BaseActivity implements DefineView{


    private RoundAngleImageView imgheader;  //头像
    private TextView iteminverstorname;   //投资人姓名
    private TextView investmentfielditem;  //投资领域
    private TextView focusfaeasitem;   //关注方向
    private TextView privatelittertv;  //私信
    private TextView attentiontv;   //关注
    private WebView detailscontent;   //内容
    private InvesatorData mInvesatorData;

    //加载图片UML
    private ImageLoader mImageLoader;
    //加载配置方式
    private DisplayImageOptions mOptions;

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
        mInvesatorData = (InvesatorData) getIntent().getSerializableExtra("investor_url");
        mImageLoader = ImageLoader.getInstance();
        mOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.defaultbg)
                .cacheOnDisk(true)   //设置保存在sdcard中
                .cacheInMemory(true) //设置保存在内存当中
                .build();
    }

    @Override
    public void initValidata() {
        mImageLoader.displayImage(mInvesatorData.getUser().getAvatar(),imgheader,mOptions);
        iteminverstorname.setText(mInvesatorData.getUser().getName());
        investmentfielditem.setText(mInvesatorData.getUser().getIntro());
    }

    @Override
    public void initListener() {

    }

    @Override
    public void bindData() {

    }
}
