package com.wuyinlei.biz;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wuyinlei.bean.recent.RecentNewsBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 若兰 on 2016/2/20.
 */
public class RecentDataManager {

    public static List<RecentNewsBean> getRecentDatas(String data){
        List<RecentNewsBean> recentNewsBeens = null;

        try {
            recentNewsBeens = new ArrayList<>();
            JSONObject result_object = new JSONObject(data);
            JSONObject data_object = result_object.getJSONObject("data");
            JSONArray datas_array = data_object.getJSONArray("data");
            Gson gson = new Gson();
            recentNewsBeens = gson.fromJson(datas_array.toString(),new TypeToken<List<RecentNewsBean>>(){}.getType());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return recentNewsBeens;
    }
}
