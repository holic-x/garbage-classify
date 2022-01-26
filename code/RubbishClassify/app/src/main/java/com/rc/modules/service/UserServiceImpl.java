package com.rc.modules.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rc.framework.http.ResUtil;
import com.rc.framework.utils.CustomOkHttpUtil;
import com.rc.modules.model.RcUser;


import org.json.JSONObject;

import java.util.Map;

public class UserServiceImpl implements UserService {

    @Override
    public RcUser loginCheck(String userName, String userPwd, String userRole) throws Exception {
        JSONObject requestJson = new JSONObject();
        requestJson.put("userName", userName);
        requestJson.put("userPwd", userPwd);
        requestJson.put("userRole", userRole);
        String res = CustomOkHttpUtil.okHttpPostJson("loginCheck", requestJson);
        Gson gson = new Gson();
        // 对结果进行解析
//        Map<String,Object> responseObj = gson.fromJson(res, Map.class);
        Map<String, Object> responseObj = ResUtil.parseRes(res);
        RcUser rcUser = null;
        if(responseObj!=null){
            rcUser = gson.fromJson(gson.toJson(responseObj.get("user")), RcUser.class);
        }
        return rcUser;
    }

    @Override
    public boolean register(RcUser rcUser) throws Exception {
        Gson gson = new Gson();
//        JSONObject requestJson = gson.fromJson(gson.toJson(rcUser), JSONObject.class);  解析无效
//        JsonObject requestJson =  new JsonParser().parse(gson.toJson(rcUser)).getAsJsonObject();

        JSONObject requestJson = new JSONObject();
        requestJson.put("userName",rcUser.getUserName());
        requestJson.put("userPwd",rcUser.getUserPwd());
        requestJson.put("userRole","2");
        String res = CustomOkHttpUtil.okHttpPostJson("register", requestJson);
        // 对结果进行解析
        return ResUtil.validRes(res);
    }

}
