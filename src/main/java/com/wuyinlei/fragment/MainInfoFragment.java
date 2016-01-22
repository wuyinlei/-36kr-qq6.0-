package com.wuyinlei.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuyinlei.DefineView;
import com.wuyinlei.activity.R;


public class MainInfoFragment extends Fragment implements DefineView {
    private View mView;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.one_frag_layout, container, false);
        }
        initView();
        initValidata();
        initListener();
        bindData();
        return mView;
    }

    @Override
    public void initView() {
        mTabLayout = (TabLayout) mView.findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) mView.findViewById(R.id.viewPager);
    }

    @Override
    public void initValidata() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void bindData() {

    }
}
