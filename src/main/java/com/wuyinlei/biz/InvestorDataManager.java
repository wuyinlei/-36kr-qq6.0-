package com.wuyinlei.biz;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wuyinlei.bean.invesator.InvesatorData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 若兰 on 2016/2/20.
 */
public class InvestorDataManager {
    public static List<InvesatorData> getInvestorDatas(String data) {
        List<InvesatorData> invesatorDatas = null;
        try {
            invesatorDatas = new ArrayList<>();
            JSONObject result_object=new JSONObject(data);
            JSONArray data_object=result_object.getJSONObject("data").getJSONArray("data");
            Gson gson=new Gson();
            invesatorDatas = gson.fromJson(data_object.toString(), new TypeToken<List<InvesatorData>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invesatorDatas;
    }
}
