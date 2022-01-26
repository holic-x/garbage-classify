package com.rc.modules.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.rc.framework.http.ResUtil;
import com.rc.framework.utils.CustomOkHttpUtil;
import com.rc.modules.model.Rubbish;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RubbishServiceImpl implements RubbishService {
    @Override
    public List<Rubbish> getRubbishByCond(JSONObject queryCond) throws Exception {
        String res = CustomOkHttpUtil.okHttpPostJson("getRubbishByCond", queryCond);
//        Gson gson = new Gson();
        // 对日期格式进行处理
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        // 对结果进行解析
        Map<String, Object> responseObj = ResUtil.parseRes(res);
        List<Rubbish> rubbishList = new ArrayList<>();
        if (responseObj != null) {
//            rubbishList = gson.fromJson(gson.toJson(responseObj.get("rubbishList")), ArrayList.class);
//            rubbishList = gson.fromJson(gson.toJson(responseObj.get("rubbishList")), new TypeToken<List<Rubbish>>() {
//            }.getType());

            Type type = new TypeToken<List<Rubbish>>(){}.getType();
            rubbishList = gson.fromJson(gson.toJson(responseObj.get("rubbishList")),type);
        }
        return rubbishList;
    }
}
