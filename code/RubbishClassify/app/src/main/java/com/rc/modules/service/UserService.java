package com.rc.modules.service;

import com.rc.modules.model.RcUser;

public interface UserService {

    // 封装用户登录方法
    public RcUser loginCheck(String userName,String userPwd,String userRole) throws Exception;

    // 用户注册
    public boolean register(RcUser rcUser) throws Exception;


}
