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
        itemBeans.add(new ItemBean(R.mipmap.icon_shoucang, "我的收藏"));
        itemBeans.add(new ItemBean(R.mipmap.icon_yijianfankui, "意见反馈"));
        return itemBeans;
    }

}
