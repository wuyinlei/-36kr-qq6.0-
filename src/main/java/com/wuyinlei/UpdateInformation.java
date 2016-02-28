package com.wuyinlei;

import com.wuyinlei.activity.R;

/**
 * Created by 若兰 on 2016/2/27.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class UpdateInformation {

    public static String appname = CNKApplication.getInstance().getResources().getString(R.string.app_name);
    public static int localVersion = 1; //本地版本
    public static String versionName = ""; //本地版本名字
    public static int serverVersion = 1; //服务器版本
    public static int serverflag = 0; // 服务器标志
    public static int lastForce = 0; //之前强制升级版本
    public static String updateUrl = "";//升级包获取地址
    public static String upgradeinfo = "";//升级信息

    public static String downloadDir = "wuyinlei";//下载目录

}
