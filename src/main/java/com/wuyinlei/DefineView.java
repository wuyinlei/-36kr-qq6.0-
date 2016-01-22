package com.wuyinlei;

public interface DefineView {

    /**
     * 初始化界面元素
     */
   void initView();

    /**
     * 初始化变量
     */
   void initValidata();

    /**
     * 初始化监听器
     */
    void initListener();

    /**
     * 绑定数据
     */
   void bindData();
   

}
