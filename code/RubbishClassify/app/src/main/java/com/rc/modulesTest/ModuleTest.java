package com.rc.modulesTest;

import com.rc.modules.model.RcUser;
import com.rc.modules.service.UserService;
import com.rc.modules.service.UserServiceImpl;

import java.sql.SQLException;

public class ModuleTest {


    public static void main(String[] args) throws Exception {
        UserService userService = new UserServiceImpl();

        // 查找用戶信息
        RcUser rcUser = new RcUser();
        rcUser.setUserName("user");
        rcUser.setUserPwd("user");
        System.out.println(userService.loginCheck("user","user","2"));

    }
}
