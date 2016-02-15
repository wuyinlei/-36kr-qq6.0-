package com.wuyinlei.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.okhttp.Request;
import com.wuyinlei.DefineView;
import com.wuyinlei.activity.R;
import com.wuyinlei.adapter.HomeRecycleAdapter;
import com.wuyinlei.adapter.TvAdapter;
import com.wuyinlei.bean.ArticleTv;
import com.wuyinlei.bean.CategoriesBean;
import com.wuyinlei.biz.ArticleTvManager;
import com.wuyinlei.http.OkHttpManager;
import com.wuyinlei.url.Config;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;


/**
 * Created by 若兰 on 2016/1/28.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class TvFragment extends BaseFragment implements DefineView {


    private View mView;

    private static final String KEY = "EXTRA";

    private List<ArticleTv> mArticleTvs;

    /**
     * 分类数据
     */
    private CategoriesBean mCategoriesBean;

    private LayoutInflater mInflater;

    /**
     * RecycleView的adapter
     */
    private HomeRecycleAdapter mAdapter;


    /**
     * 下拉刷新组件
     */
    private RecyclerView recycleview;


    //
    private FrameLayout home_framlayuot;
    //三个加载提示布局
    private LinearLayout empty, error, loading;

    /**
     * ImageLoader
     */
    private ImageLoader mImageLoader;

    /**
     * 图片加载设置
     */
    private DisplayImageOptions mOptions;

    //判断是否是最后一个item
    private int lastItem;

    /**
     * 解决上拉加载更多重复加载的
     */
    private boolean isLordMore = true;


    private LinearLayoutManager linearLayoutManager;

    /**
     * 下拉刷新控件
     */
    private SwipeRefreshLayout mRefreshLayout;
    private TvAdapter adapter;


    public static TvFragment newInstance(CategoriesBean extra) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY, extra);
        TvFragment fragment = new TvFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCategoriesBean = (CategoriesBean) bundle.getSerializable(KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.page_fragment, container, false);
        }
        mInflater = LayoutInflater.from(getActivity());
        initView();
        initValidata();
        initListener();
        return mView;
    }


    @Override
    public void initView() {
        recycleview = (RecyclerView) mView.findViewById(R.id.recycleview);
        home_framlayuot = (FrameLayout) mView.findViewById(R.id.home_framlayout);
        empty = (LinearLayout) mView.findViewById(R.id.empty);
        error = (LinearLayout) mView.findViewById(R.id.error);
        loading = (LinearLayout) mView.findViewById(R.id.loading);
        mRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swiperefreshlayout);
    }

    @Override
    public void initValidata() {
        //设置下拉的时候的圈圈的颜色
        mRefreshLayout.setColorSchemeResources(
                android.R.color.background_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark);
        //进度条颜色
        // mRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.color_white);

        //拖动多长的时候开始刷新
        mRefreshLayout.setDistanceToTriggerSync(100);
        //mRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.color_white));

        //设置大小
        mRefreshLayout.setSize(SwipeRefreshLayout.LARGE);

        //设置进度条的偏移量
        mRefreshLayout.setProgressViewOffset(false, 0, 50);
        recycleview.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false);
        recycleview.setLayoutManager(linearLayoutManager);


        /**
         * 刚开始加载数据的时候，设置等待页面
         */
      /*  recycleview.setVisibility(View.INVISIBLE);
        home_framlayuot.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        error.setVisibility(View.GONE);
        empty.setVisibility(View.GONE);*/

        OkHttpManager.getAsync(Config.BASE_TV_URL, new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Log.d("TvFragment", "解析失败了");
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                /**
                 * jsoup解析数据
                 */
                Document document = Jsoup.parse(result, Config.CRAWLER_URL);
                mArticleTvs = new ArticleTvManager().getArticleTvBeans(document);
                Log.d("TvFragment", "mArticleTvs.size():" + mArticleTvs.size());
                if (mArticleTvs != null && mArticleTvs.size() > 0) {
                   /* recycleview.setVisibility(View.VISIBLE);
                    home_framlayuot.setVisibility(View.GONE);
                    loading.setVisibility(View.GONE);
                    error.setVisibility(View.GONE);
                    empty.setVisibility(View.GONE);*/
                    bindData();
                }
            }
        });
    }

    @Override
    public void initListener() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mRefreshLayout.isRefreshing()) {
                            {
                                mRefreshLayout.setRefreshing(false);
                            }
                            Toast.makeText(getContext(), "刷新完成", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 5000);
            }
        });

        /**
         *
         */
        recycleview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItem + 1 == linearLayoutManager.getItemCount()) {
                    if (isLordMore) {
                        isLordMore = false;
                        //构造下一页数据的地址

                        String lordMoreUrl = "http://tv.36kr.com/video/get_more";

                        OkHttpManager.getAsync(lordMoreUrl, new OkHttpManager.DataCallBack() {
                            @Override
                            public void requestFailure(Request request, IOException e) {

                            }

                            @Override
                            public void requestSuccess(String result) throws Exception {
                                Document document = Jsoup.parse(result, Config.CRAWLER_URL);
                                List<ArticleTv> tempArticles = new ArticleTvManager().getArticleTvBeans(document);
                                bindLordMore(tempArticles);
                                isLordMore = true;
                            }
                        });
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void bindLordMore(List<ArticleTv> articleTvs) {
        mArticleTvs.addAll(articleTvs);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void bindData() {
        adapter = new TvAdapter(mArticleTvs);

        /**
         * 不要忘记添加数据  要不然啥都没有
         */
        //mAdapter.setArticleTvs(mArticleTvs);
        recycleview.setAdapter(adapter);

        adapter.setOnItemClickListener(new TvAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, Object o) {
                ArticleTv articleTv = (ArticleTv) o;
                Toast.makeText(getContext(), articleTv.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
