package com.wuyinlei.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.widgetutils.AutoGallery;
import com.example.widgetutils.FlowIndicator;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.okhttp.Request;
import com.wuyinlei.DefineView;
import com.wuyinlei.bean.FindAdBean;
import com.wuyinlei.bean.FindAdData;
import com.wuyinlei.http.OkHttpManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FindActivity extends BaseActivity implements DefineView {


    private LayoutInflater mInflater;
    /**
     * 轮播框
     */
    private AutoGallery headline_image_gallery;

    /**
     * 指示器
     */
    private FlowIndicator headline_circle_indicator;

    /**
     * Gallery索引
     */
    private int gallerySelectedPosition = 0;

    /**
     * 指示器索引
     */
    private int circleSelectedPosition = 0;
    private ImageView imgactivity;
    private ImageView imginvestor;
    private RelativeLayout findactivity;

    private Button btn_back;

    private RelativeLayout recent_activity_item,find_activity;

    private ImageLoader mImageLoader;
    private DisplayImageOptions mOptions;

    private FindAdData findAdData;
    private List<FindAdBean> mFindAdBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        //设置沉浸式状态栏
        setStatusBar();
        initView();
        initValidata();
        bindData();
        initListener();
    }

    @Override
    public void initView() {

        mInflater = LayoutInflater.from(this);
        imgactivity = (ImageView) findViewById(R.id.img_activity);
        imginvestor = (ImageView) findViewById(R.id.img_investor);
        findactivity = (RelativeLayout) findViewById(R.id.find_activity);
        headline_image_gallery = (AutoGallery) findViewById(R.id.headline_image_gallery);
        headline_circle_indicator = (FlowIndicator) findViewById(R.id.headline_circle_indicator);
        mFindAdBeans = new ArrayList<>();
        recent_activity_item = (RelativeLayout) findViewById(R.id.recent_activity_item);
        btn_back = (Button) findViewById(R.id.btn_back);
        find_activity = (RelativeLayout) findViewById(R.id.find_activity);
    }

    @Override
    public void initValidata() {

        mImageLoader = ImageLoader.getInstance();
        //配置
        mOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.defaultbg)
                .cacheOnDisk(true)   //设置保存在sdcard中
                .cacheInMemory(true) //设置保存在内存当中
                .build();
    }

    @Override
    public void initListener() {
        //后退按钮
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindActivity.this.finish();
            }
        });
        /**
         * 点击查看详情
         */
        recent_activity_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FindActivity.this, RecentActivity.class));
            }
        });

        find_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FindActivity.this,InvestorsActivity.class));
            }
        });
    }

    @Override
    public void bindData() {
        OkHttpManager.getAsync("https://z.36kr.com/api/p/sc/images?type=1", new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();
                findAdData = gson.fromJson(result, FindAdData.class);
                mFindAdBeans = findAdData.getData();
                initTopGallery();
            }
        });


    }

    /**
     * 初始化顶部的广告轮播条
     */
    private void initTopGallery() {
        //总共有多少项
        int topSize = mFindAdBeans.size();

        //设置指示器的多少项
        headline_circle_indicator.setCount(topSize);
        //当前选择项
        headline_circle_indicator.setSeletion(circleSelectedPosition);

        //设置画廊的总数
        headline_image_gallery.setLength(topSize);
        gallerySelectedPosition = topSize * 50 + circleSelectedPosition;
        //设置当前显示
        headline_image_gallery.setSelection(gallerySelectedPosition);
        //设置轮播间隔
        headline_image_gallery.setDelayMillis(3000);
        //开启轮播
        headline_image_gallery.start();

        headline_image_gallery.setAdapter(new GalleryAdapter());

        /**
         * 在这里监听Gallery图片被按下的是第几个，然后取得数值，赋值给circleSelectedPosition
         */
        headline_image_gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                circleSelectedPosition = position;
                gallerySelectedPosition = circleSelectedPosition % mFindAdBeans.size();
                headline_circle_indicator.setSeletion(gallerySelectedPosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    /**
     * Gallery适配器
     */
    class GalleryAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Object getItem(int position) {
            return mFindAdBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.item_gallery_layout, null);
                holder.header_image = (ImageView) convertView.findViewById(R.id.item_head_gallery_img);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            /**
             * 加载轮播广告图片
             */
            mImageLoader.displayImage(mFindAdBeans.get(position % mFindAdBeans.size()).getImg_url(), holder.header_image, mOptions);

            //Picasso.with(mView.getContext()).load(mHeadBeans.get(position%mHeadBeans.size()).getImgurl()).into(holder.header_image);
            return convertView;
        }
    }

    private static class ViewHolder {
        ImageView header_image;
    }

}
