package com.wuyinlei.bean;

/**
 * Created by 若兰 on 2016/2/27.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class UpdataInfoModel {

    /**
     * appname: "爱新闻",
     * lastForce: "1",
     * serverflag: "1",
     * serverVersion: "2",
     * updateUrl: "http://192.168.1.100:8080/JianShu.apk",
     * updateDeinfo: "V1.1版本更新"
     */

    private String appname;
    private String lastForce;
    private String serverflag;
    private String serverVersion;
    private String updateUrl;
    private String updateDeinfo;

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getLastForce() {
        return lastForce;
    }

    public void setLastForce(String lastForce) {
        this.lastForce = lastForce;
    }

    public String getServerflag() {
        return serverflag;
    }

    public void setServerflag(String serverflag) {
        this.serverflag = serverflag;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public String getUpdateDeinfo() {
        return updateDeinfo;
    }

    public void setUpdateDeinfo(String updateDeinfo) {
        this.updateDeinfo = updateDeinfo;
    }

    @Override
    public String toString() {
        return "UpdataInfoModel{" +
                "appname='" + appname + '\'' +
                ", lastForce='" + lastForce + '\'' +
                ", serverflag='" + serverflag + '\'' +
                ", serverVersion='" + serverVersion + '\'' +
                ", updateUrl='" + updateUrl + '\'' +
                ", updateDeinfo='" + updateDeinfo + '\'' +
                '}';
    }
}
