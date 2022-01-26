package com.rc.server.modules.systemManage.controller;

import com.alibaba.fastjson.JSONObject;
import com.rc.server.framework.shiro.ShiroUtil;
import com.rc.server.framework.utils.Res;
import com.rc.server.modules.systemManage.service.LoginAuthService;
import com.rc.server.modules.systemManage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginAuthService loginAuthService;


    /**
     * 登录
     **/
    @RequestMapping("/toLogin")
    public Res login(@RequestBody JSONObject requestParam) {
        Map<String,Object> resMap = loginAuthService.toLogin(requestParam.getString("userName"),requestParam.getString("password"));
        if(resMap==null){
            return Res.error("用户身份验证失败");
        }else{
            return Res.ok().put("resMap",resMap);
        }
    }

    /**
     * 注销
     **/
    @RequestMapping("/toLogout")
    public Res logout() {
//        Subject subject = SecurityUtils.getSubject();
//        if(subject.isAuthenticated()){
//            // 销毁SESSION(清理权限缓存)
//            subject.logout();
//        }
        // 执行登录退出操作
        return Res.ok();
    }

    /**
     * 获取当前登录用户
     **/
    @RequestMapping("/getLoginUser")
    public Res getLoginUser() {
        return Res.ok().put("user", ShiroUtil.getCurrentUser());
    }

    /**
     * 修改登录密码
     **/
//    @RequestMapping("/updateLoginPwd")
//    public Res updateLoginPwd(@RequestBody JSONObject requestParam) {
//        if(userService.updateLoginPwd(requestParam.getString("userId"),requestParam.getString("oldPwd"),requestParam.getString("newPwd"))){
//            return Res.ok();
//        }else{
//            return Res.error();
//        }
//    }

    @GetMapping("/noLogin")
    public Res noLogin() {
        // 将未登录状态处理为token失效
        return Res.error(401,"用户尚未登录");
    }

    @GetMapping("/loginFail")
    public Res loginFail() {
        // 将未登录状态处理为token失效
        return Res.error(401,"用户登录失败或认证失败");
    }


}
