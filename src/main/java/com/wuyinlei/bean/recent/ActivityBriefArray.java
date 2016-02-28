package com.wuyinlei.bean.recent;

/**
 * Created by 若兰 on 2016/2/20.
 */
public class ActivityBriefArray {

    private String title;
    private String value;

    public ActivityBriefArray() {
    }

    public ActivityBriefArray(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
