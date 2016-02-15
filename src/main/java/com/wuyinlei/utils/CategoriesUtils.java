package com.wuyinlei.utils;

import com.wuyinlei.bean.CategoriesBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 若兰 on 2016/1/23.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class CategoriesUtils {

    public static List<CategoriesBean> getCategoriedBeans(){
        List<CategoriesBean> categoriesBeans = new ArrayList<>();
        categoriesBeans.add(new CategoriesBean("最新文章","http://www.36kr.com/","全部"));
        categoriesBeans.add(new CategoriesBean("氪TV","http://tv.36kr.com/","氪TV"));
        categoriesBeans.add(new CategoriesBean("早期项目","http://www.36kr.com/columns/starding","早期项目"));
        categoriesBeans.add(new CategoriesBean("B轮后","http://www.36kr.com/columns/bplus","B轮后"));
        categoriesBeans.add(new CategoriesBean("资本","http://www.36kr.com/columns/capital","资本"));
        categoriesBeans.add(new CategoriesBean("深度","http://www.36kr.com/columns/deep","深度"));
        categoriesBeans.add(new CategoriesBean("行研","http://www.36kr.com/columns/research","行研"));
        return categoriesBeans;
    }
}
