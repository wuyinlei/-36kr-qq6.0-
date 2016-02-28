package com.wuyinlei.utils;


import com.wuyinlei.activity.R;
import com.wuyinlei.entity.ItemBean;

import java.util.ArrayList;
import java.util.List;

public class ItemDataUtils {
    public static List<ItemBean> getItemBeans() {
        List<ItemBean> itemBeans = new ArrayList<>();
        itemBeans.add(new ItemBean(R.mipmap.icon_zhanghaoxinxi, "账号信息"));
        itemBeans.add(new ItemBean(R.mipmap.icon_wodeguanzhu, "我的关注"));
        itemBeans.add(new ItemBean(R.mipmap.tabbar_icon_discovery_highlight, "我的发现"));
        itemBeans.add(new ItemBean(R.mipmap.icon_shoucang, "我的收藏"));
        itemBeans.add(new ItemBean(R.mipmap.icon_yijianfankui, "意见反馈"));
        itemBeans.add(new ItemBean(R.mipmap.tabbar_icon_news_highlight, "关于我们"));
        return itemBeans;
    }

}
