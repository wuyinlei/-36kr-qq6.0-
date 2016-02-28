package com.wuyinlei.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.squareup.okhttp.Request;
import com.wuyinlei.DefineView;
import com.wuyinlei.adapter.RecentActivityAdapter;
import com.wuyinlei.bean.recent.RecentNewsBean;
import com.wuyinlei.biz.RecentDataManager;
import com.wuyinlei.http.OkHttpManager;

import java.io.IOException;
import java.util.List;

public class RecentActivity extends BaseActivity implements DefineView{

    private RecyclerView recycleview;
    private LinearLayoutManager linearLayoutManager;
    private Button btn_back;

    //进行分页效果--主要用于近期活动列表
    private int page=1;      //页码 默认为第一页
    private int pageSize=95;   //每页的item数量
    private List<RecentNewsBean> recentNewsBeans;  //近期活动列表数据
    private RecentActivityAdapter mAdapter;

    private boolean isMore=true; //解决上拉重复加载的bug

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);
        setStatusBar();
        initView();
        bindData();
        initValidata();
        initListener();
    }

    @Override
    public void initView() {
        btn_back = (Button) findViewById(R.id.btn_back);
        recycleview = (RecyclerView) findViewById(R.id.recycleview);
        linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        recycleview.setLayoutManager(linearLayoutManager);

    }

    @Override
    public void initValidata() {
       recycleview.setOnScrollListener(new RecyclerView.OnScrollListener() {
           @Override
           public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
               super.onScrolled(recyclerView, dx, dy);
           }

           @Override
           public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
               super.onScrollStateChanged(recyclerView, newState);
           }
       });

    }

    @Override
    public void initListener() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void bindData() {

        String requestUrl="http://chuang.36kr.com/api/actapply?page="+page+"&pageSize="+pageSize;
        OkHttpManager.getAsync(requestUrl, new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                recentNewsBeans = RecentDataManager.getRecentDatas(result);
                mAdapter = new RecentActivityAdapter(recentNewsBeans);
                recycleview.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(new RecentActivityAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(View v, Object o) {
                        Log.d("RecentActivity", "nihao ");
                        RecentNewsBean recentNews = (RecentNewsBean) o;
                        Intent intent = new Intent(RecentActivity.this,RecentDetailActivity.class);
                        intent.putExtra("recent_link", recentNews.getLink());
                        startActivity(intent);

                    }
                });
            }
        });




    }

}
