package com.wuyinlei.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.wuyinlei.bean.CategoriesBean;

import java.util.List;

/**
 * 当前类注释:Fragment自定义适配器
 */
public class FixedPagerAdapter extends FragmentStatePagerAdapter {

    private List<CategoriesBean> mCategoriesBeans;

    public void setCategoriesBeans(List<CategoriesBean> categoriesBeans) {
        mCategoriesBeans = categoriesBeans;
    }

    private List<Fragment> fragments;

    public FixedPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    /**
     * Return the number of views available.
     * 返回一个可以用的view的个数
     *
     * @return
     */
    @Override
    public int getCount() {
        return this.fragments.size();
    }

    /**
     * Create the page for the given position. The adapter is responsible for
     * adding the view to the container given here,
     * although it only must ensure this is done by the time it returns from finishUpdate(ViewGroup).
     * 这个同destroyItem（）相反，是对于给定的位置创建视图，适配器往container中添加
     *
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) super.instantiateItem(container, position);
        } catch (Exception e) {

        }
        return fragment;
    }

    /**
     * Remove a page for the given position. The adapter is responsible for
     * removing the view from its container,
     * although it only must ensure this is done by the time it returns from finishUpdate(View).
     * 移除给定位置的数据，适配器负责从container（容器）中取出，但是这个必须保证是在finishUpdate（view）
     * 返回的时间内完成
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    /**
     * 得到滑动页面的Title
     *
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mCategoriesBeans.get(position%mCategoriesBeans.size()).getTitle();
    }

    public List<Fragment> getFragments() {
        return fragments;
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }
}
