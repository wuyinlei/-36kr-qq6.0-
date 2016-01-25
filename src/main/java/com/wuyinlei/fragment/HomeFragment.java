package com.wuyinlei.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.utils.adapter.helper.BaseAdapterHelper;
import com.utils.adapter.helper.QuickAdapter;
import com.utils.adapter.refresh.PullToRefreshListView;
import com.wuyinlei.DefineView;
import com.wuyinlei.activity.R;
import com.wuyinlei.bean.CategoriesBean;
import com.wuyinlei.bean.HomeNewsBean;
import com.wuyinlei.biz.HomeNewsDataManager;
import com.wuyinlei.http.OkHttpManager;
import com.wuyinlei.url.Config;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.List;

/**
 * Created by 若兰 on 2016/1/23.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class HomeFragment extends BaseFragment implements DefineView {

    private View mView;

    private static final String KEY = "EXTRA";

    /**
     * 分类数据
     */
    private CategoriesBean mCategoriesBean;

    /**
     * 主页新闻
     */
    private List<HomeNewsBean> homeBeans;

    /**
     * 项目集合
     */
    private String[] masks;

    /**
     * 下拉刷新组件
     */
    private PullToRefreshListView home_list_view;

    private QuickAdapter<HomeNewsBean> mQuickAdapter;

    //
    private FrameLayout home_framlayuot;
    //
    private LinearLayout empty,error,loading;

    public static HomeFragment newInstance(CategoriesBean extra) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY, extra);
        HomeFragment fragment = new HomeFragment();
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

    private int [] mask_colors;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.home_fragment, container, false);

        }
        initView();
        initValidata();
        initListener();
        return mView;
    }

    @Override
    public void initView() {
        home_list_view = (PullToRefreshListView) mView.findViewById(R.id.home_list_view);
        home_framlayuot = (FrameLayout) mView.findViewById(R.id.home_framlayout);
        empty = (LinearLayout) mView.findViewById(R.id.empty);
        error = (LinearLayout) mView.findViewById(R.id.error);
        loading = (LinearLayout) mView.findViewById(R.id.loading);
    }

    @Override
    public void initValidata() {
        masks = new String[]{"最新文章", "早期项目", "B轮后", "资本", "深度", "行研"};

        mask_colors = new int[]{R.color.mask_tags_1,R.color.mask_tags_3,R.color.mask_tags_5
                ,R.color.mask_tags_7,
                R.color.mask_tags_9,R.color.mask_tags_10};
        home_list_view.setVisibility(View.INVISIBLE);
        home_framlayuot.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        error.setVisibility(View.GONE);
        empty.setVisibility(View.GONE);

        OkHttpManager.getAsync(mCategoriesBean.getHref(), new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
               // Log.d("HomeFragment", "加载失败");
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                homeBeans = new HomeNewsDataManager().getHomeBeans(Jsoup.parse(result, Config.CRAWLER_URL));
               // Log.d("HomeFragment", homeBeans.toString());
                bindData();
            }
        });
    }

    @Override
    public void initListener() {

    }

    @Override
    public void bindData() {

        if (homeBeans != null) {

            home_list_view.setVisibility(View.VISIBLE);
            home_framlayuot.setVisibility(View.GONE);
            loading.setVisibility(View.GONE);
            error.setVisibility(View.GONE);
            empty.setVisibility(View.GONE);

            //item_news_img_icon  item_news_tv_name   item_news_tv_time  item_news_tv_type
            //item_news_tv_title  item_news_image_content
            mQuickAdapter = new QuickAdapter<HomeNewsBean>(getContext(), R.layout.item_home_new_layout, homeBeans) {
                @Override
                protected void convert(BaseAdapterHelper helper, HomeNewsBean item) {
                    String mask = item.getMask();
                    helper.setText(R.id.item_news_tv_name, item.getAuthorBean().getName())
                            .setText(R.id.item_news_tv_time, item.getDatetext())
                            .setText(R.id.item_news_tv_type, mask)
                            .setText(R.id.item_news_tv_title, item.getTitle());
                    int index = 0;
                    for (int i = 0; i < masks.length; i++) {
                        if (masks[i].equals(mask)) {
                            index = i;
                            break;
                        }
                    }
                    TextView tv_type = helper.getView(R.id.item_news_tv_type);
                    tv_type.setTextColor(getActivity()
                            .getResources().getColor(mask_colors[index]));
                    helper.getView(R.id.item_news_tv_arrow).setBackgroundColor(
                            getActivity().getResources().getColor(mask_colors[index])
                    );

                }
            };
            home_list_view.setAdapter(mQuickAdapter);
        } else {
            home_list_view.setVisibility(View.INVISIBLE);
            home_framlayuot.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
            error.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
    }
}
