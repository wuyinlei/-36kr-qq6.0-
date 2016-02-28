package com.wuyinlei.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.okhttp.Request;
import com.utils.adapter.refresh.PullToRefreshListView;
import com.wuyinlei.DefineView;
import com.wuyinlei.bean.invesator.InvesatorData;
import com.wuyinlei.biz.InvestorDataManager;
import com.wuyinlei.http.OkHttpManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InvestorsActivity extends BaseActivity implements DefineView {

    private FrameLayout home_framelayout;
    private LinearLayout loading, empty, error;
    private TextView top_bar_text;
    private PullToRefreshListView investor_listview;
    private Button btn_back;

    private List<InvesatorData> mInvesatorDatas;

    private ImageLoader mImageLoader;
    private DisplayImageOptions mOptions;

    private LayoutInflater mInflater;

    private View load_more;
    private MyAdapter mAdapter;

    private String investor_url = "https://rong.36kr.com/api/organization/investor";
    private int page_num = 1;

    private boolean isMore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investors);
        setStatusBar();
        initView();
        initValidata();
        initListener();
        bindData();
    }

    @Override
    public void initView() {
        top_bar_text = (TextView) findViewById(R.id.top_bar_text);
        top_bar_text.setText("寻找投资人");
        btn_back = (Button) findViewById(R.id.btn_back);
        investor_listview = (PullToRefreshListView) findViewById(R.id.investors_listview);

        home_framelayout = (FrameLayout) findViewById(R.id.home_framlayout);
        loading = (LinearLayout) findViewById(R.id.loading);
        empty = (LinearLayout) findViewById(R.id.empty);
        error = (LinearLayout) findViewById(R.id.error);
        mInvesatorDatas = new ArrayList<>();

        mInflater = LayoutInflater.from(this);
        mAdapter = new MyAdapter();
    }

    @Override
    public void initValidata() {
        mImageLoader = ImageLoader.getInstance();
        mOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.defaultbg)
                .cacheOnDisk(true)   //设置保存在sdcard中
                .cacheInMemory(true) //设置保存在内存当中
                .build();
        load_more = mInflater.inflate(R.layout.recycler_load_more_layout, null);
        investor_listview.addFooterView(load_more);
    }

    @Override
    public void initListener() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
         * 对listview实现滑动监听事件
         */
        investor_listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (investor_listview.getLastVisiblePosition() == investor_listview.getCount() - 1) {
                        isMore = true;
                        if (isMore) {
                            page_num++;
                            OkHttpManager.getAsync(investor_url + "?page=" + page_num, new OkHttpManager.DataCallBack() {
                                @Override
                                public void requestFailure(Request request, IOException e) {

                                }

                                @Override
                                public void requestSuccess(String result) throws Exception {
                                    List<InvesatorData> invesatorDataTemps = new InvestorDataManager().getInvestorDatas(result);
                                    mInvesatorDatas.addAll(invesatorDataTemps);
                                    mAdapter.notifyDataSetChanged();
                                    isMore = true;
                                }
                            });
                        }
                    }
                } else if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    if (investor_listview.getFirstVisiblePosition() == 1){
                        OkHttpManager.getAsync(investor_url + "?page=" + page_num, new OkHttpManager.DataCallBack() {
                            @Override
                            public void requestFailure(Request request, IOException e) {

                            }

                            @Override
                            public void requestSuccess(String result) throws Exception {
                                List<InvesatorData> invesatorDataTemps = new InvestorDataManager().getInvestorDatas(result);
                                mInvesatorDatas.clear();
                                mInvesatorDatas.addAll(invesatorDataTemps);
                                investor_listview.onRefresh();
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
                if (scrollState == SCROLL_STATE_FLING) {

                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        investor_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(InvestorsActivity.this,InvestorDetailActivity.class);
                InvesatorData invesatorData = mInvesatorDatas.get(position);
                intent.putExtra("investor_url",invesatorData);
                startActivity(intent);
            }
        });
    }

    @Override
    public void bindData() {
        home_framelayout.setVisibility(View.VISIBLE);
        error.setVisibility(View.GONE);
        empty.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        investor_listview.setVisibility(View.GONE);

        OkHttpManager.getAsync(investor_url + "?page=" + page_num, new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                mInvesatorDatas = new InvestorDataManager().getInvestorDatas(result);

                Log.d("InvestorsActivity", mInvesatorDatas.size() + "-------" + mInvesatorDatas.toString());
                if (mInvesatorDatas != null && mInvesatorDatas.size() > 0) {
                    investor_listview.setVisibility(View.VISIBLE);
                    home_framelayout.setVisibility(View.GONE);
                    loading.setVisibility(View.GONE);
                    empty.setVisibility(View.GONE);
                    error.setVisibility(View.GONE);
                    investor_listview.setAdapter(mAdapter);
                }

            }
        });
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mInvesatorDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mInvesatorDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = null;
            if (convertView == null) {
                holder = new Holder();
                convertView = mInflater.inflate(R.layout.item_investor_list_layout, null);
                holder.item_img = (ImageView) convertView.findViewById(R.id.item_img);
                holder.itme_title_name = (TextView) convertView.findViewById(R.id.item_title_name);
                holder.item_title_content = (TextView) convertView.findViewById(R.id.item_title_content);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            holder.itme_title_name.setText(mInvesatorDatas.get(position).getUser().getName());
            holder.item_title_content.setText(mInvesatorDatas.get(position).getUser().getIntro());
            mImageLoader.displayImage(mInvesatorDatas.get(position).getUser().getAvatar(), holder.item_img, mOptions);
            return convertView;
        }

    }

    public static class Holder {
        ImageView item_img;
        TextView itme_title_name, item_title_content;
    }
}
