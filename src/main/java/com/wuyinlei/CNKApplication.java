package com.wuyinlei;

import android.app.Application;


public class CNKApplication  extends Application{
    private static CNKApplication instance=null;
    @Override
    public void onCreate() {
        super.onCreate();
        this.instance=this;
    }
    public static CNKApplication getInstance(){
        return instance;
    }
}
