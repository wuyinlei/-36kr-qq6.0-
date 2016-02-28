package com.wuyinlei.bean;

import java.util.List;

/**
 * Created by 若兰 on 2016/2/20.
 */
public class FindAdData {

    private String code;
    private List<FindAdBean> data;

    public FindAdData() {
    }

    public FindAdData(String code, List<FindAdBean> data) {
        this.code = code;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<FindAdBean> getData() {
        return data;
    }

    public void setData(List<FindAdBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FindAdData{" +
                "code='" + code + '\'' +
                ", data=" + data +
                '}';
    }
}
