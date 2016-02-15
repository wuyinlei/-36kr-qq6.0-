package com.wuyinlei.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuyinlei.DefineView;
import com.wuyinlei.activity.MainActivity;
import com.wuyinlei.activity.R;
import com.wuyinlei.adapter.FixedPagerAdapter;
import com.wuyinlei.bean.CategoriesBean;
import com.wuyinlei.utils.CategoriesUtils;

import java.util.ArrayList;
import java.util.List;


public class MainInfoFragment extends Fragment implements DefineView ,OnPageChangeListener{
    private View mView;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<Fragment> mFragments;

    private FixedPagerAdapter mAdapter;
    private static List<CategoriesBean> sCategoriesBeans = CategoriesUtils.getCategoriedBeans();

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
        mAdapter = new FixedPagerAdapter(getChildFragmentManager());
        mAdapter.setCategoriesBeans(sCategoriesBeans);
        mFragments = new ArrayList<>();
        for (int i = 0; i < sCategoriesBeans.size();i++){
            BaseFragment fragment = null;
            //如果是第一个fragment，就设置给HomeFragment
            if (i == 0){
            fragment = HomeFragment.newInstance(sCategoriesBeans.get(i));
            }else if (i==1){
               //加载氪TV
                fragment = TvFragment.newInstance(sCategoriesBeans.get(i));
            } else { //如果不是第一个，就设置成PageFragment
                fragment = PageFragment.newInstance(sCategoriesBeans.get(i));
            }
            mFragments.add(fragment);
        }
        mAdapter.setFragments(mFragments);
        //设置模式可以滑动
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mViewPager.setAdapter(mAdapter);
        //把viewpager添加到tablayout中
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void initListener() {
        mViewPager.setOnPageChangeListener(this);
    }

    @Override
    public void bindData() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (position ==0) {
            //当滑动到了第一个的时候，在右滑的时候，draglayout可以滑出来
            ((MainActivity) getActivity()).getDragLayout().setIsDrag(true);
        } else {
            ((MainActivity) getActivity()).getDragLayout().setIsDrag(false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
