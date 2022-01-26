package com.rc.server.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rc.server.framework.utils.Res;
import com.rc.server.modules.dataManage.model.Rubbish;
import com.rc.server.modules.dataManage.service.RubbishService;
import com.rc.server.modules.systemManage.model.RcUser;
import com.rc.server.modules.systemManage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private RubbishService rubbishService;

    /**
     * 登录信息验证
     **/
//    @RequestMapping("/loginCheck")
//    public Res save(@RequestBody JSONObject jsonObject) {
////        RcUser rcUser = userService.getByLoginProp(jsonObject.getString("userName"),jsonObject.getString("userPwd"), ConstUtil.USER_ROLE_USER);
//        RcUser rcUser = userService.getByLoginProp(jsonObject.getString("userName"),jsonObject.getString("userPwd"),jsonObject.getString("userRole"));
//        return rcUser!=null?Res.ok().put("user",rcUser):Res.error("指定ID关联信息不存在");
//    }
    @RequestMapping("/loginCheck")
    public Res save(@RequestBody Map<String,Object> requestMap) {
//        Gson gson = new Gson();
//        JSONObject jsonObject = gson.fromJson(requestJson,JSONObject.class);
//        Gson gson = new Gson();
//        String str = gson.toJson(requestMap.get("nameValuePairs"));
//        JSONObject jsonObject = gson.fromJson(str,JSONObject.class);
//        JSON.parseObject(JSON.toJSONString(Map<String, ? extends Object>), Class<T> cls);

//        RcUser rcUser = userService.getByLoginProp(String.valueOf(requestMap.get("userName")),String.valueOf(requestMap.get("userPwd")),String.valueOf(requestMap.get("userRole")));
//        return rcUser!=null?Res.ok().put("user",rcUser):Res.error("指定ID关联信息不存在");


        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(requestMap.get("nameValuePairs")), JSONObject.class);
        RcUser rcUser = userService.getByLoginProp(jsonObject.getString("userName"),jsonObject.getString("userPwd"),jsonObject.getString("userRole"));
        return rcUser!=null?Res.ok().put("user",rcUser):Res.error("指定用户关联信息不存在");

    }

    @RequestMapping("/loginCheckTest")
    public Res loginCheckTest(@RequestBody JSONObject jsonObject) {
        RcUser rcUser = userService.getByLoginProp(jsonObject.getString("userName"),jsonObject.getString("userPwd"),jsonObject.getString("userRole"));
        return rcUser!=null?Res.ok().put("user",rcUser):Res.error("指定用户关联信息不存在");
    }

    /**
     * 注册新用户
     **/
//    @RequestMapping("/register")
//    public Res register(@RequestBody RcUser rcUser) {
//        userService.edit(rcUser);
//        return Res.ok();
//    }
    @RequestMapping("/register")
    public Res register(@RequestBody Map<String,Object> requestMap) {
        RcUser rcUser = JSON.parseObject(JSON.toJSONString(requestMap.get("nameValuePairs")), RcUser.class);
        userService.edit(rcUser);
        return Res.ok();
    }




    /**
     * 获取垃圾信息数据
     **/
//    @RequestMapping("/getRubbishByCond")
//    public Res getRubbishByCond(@RequestBody JSONObject jsonObject) {
//        List<Rubbish> rubbishList = rubbishService.getByCond(jsonObject);
//        return Res.ok().put("rubbishList",rubbishList);
//    }
    @RequestMapping("/getRubbishByCond")
    public Res getRubbishByCond(@RequestBody Map<String,Object> requestMap) {
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(requestMap.get("nameValuePairs")), JSONObject.class);
        List<Rubbish> rubbishList = rubbishService.getByCond(jsonObject);
        return Res.ok().put("rubbishList",rubbishList);
    }




}
