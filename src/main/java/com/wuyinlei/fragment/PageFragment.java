package com.wuyinlei.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.wuyinlei.DefineView;
import com.wuyinlei.activity.R;
import com.wuyinlei.bean.CategoriesBean;
import com.wuyinlei.http.OkHttpManager;
import com.wuyinlei.url.Config;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by 若兰 on 2016/1/22.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class PageFragment extends BaseFragment implements DefineView {
    private View mView;

    private static final String KEY = "EXTRA";
    private CategoriesBean mCategoriesBean;

    public static PageFragment newInstance(CategoriesBean extra) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY,extra);
        PageFragment fragment = new PageFragment();
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

    private TextView mTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.page_fragment, container, false);
        }
        initView();
        initValidata();
        return mView;
    }


    @Override
    public void initView() {
        mTextView = (TextView) mView.findViewById(R.id.text_fragment);
        if (mCategoriesBean != null) {
            mTextView.setText(mCategoriesBean.getTitle());
        }
    }

    @Override
    public void initValidata() {
        OkHttpManager.getAsync(Config.CRAWLER_URL, new OkHttpManager.DataCallBack() {

            private CategoriesBean mCategoriesBean;

            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Document document = Jsoup.parse(result, Config.CRAWLER_URL);
               // mCategoriesBeanList = new CategoryDataManager().getCategoriesBeans_WYL(document);
              // Log.d("PageFragment", mCategoriesBeanList.toString());
               // CategoriesBean categoriesBean =  gson.fromJson(mCategoriesBeanList.toString(), CategoriesBean.class);
            }
        });
    }

    @Override
    public void initListener() {

    }

    @Override
    public void bindData() {

    }
}
