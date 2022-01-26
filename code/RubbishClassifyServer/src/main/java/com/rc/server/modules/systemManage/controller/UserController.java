package com.rc.server.modules.systemManage.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rc.server.framework.utils.PageHelper;
import com.rc.server.framework.utils.Res;
import com.rc.server.modules.systemManage.model.RcUser;
import com.rc.server.modules.systemManage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/sys/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 保存信息
     **/
//    @RequestMapping("/edit")
    @RequestMapping(value = {"/save", "/update"})
    public Res save(@RequestBody RcUser rcUser) {
        if (userService.edit(rcUser)) {
            return Res.ok();
        } else {
            return Res.error("信息编辑失败");
        }
//        return AjaxResultUtil.success(); // return AjaxResult
    }

    /**
     * 删除信息(单条或批量删除)
     **/
    @RequestMapping("/delete")
    public Res delete(@RequestBody JSONObject requestParam) {
        userService.delete(requestParam.getObject("userIdList", ArrayList.class));
        return Res.ok();
    }

    /**
     * list信息
     **/
    @RequestMapping("/list")
    // @RequestBody HashMap<String, String> map 转化JSONObject.parseObject(JSON.toJSONString(map))
    public Res list(@RequestBody JSONObject queryCond) {
        Page<RcUser> pageData = userService.getByPage(queryCond);
        // 借助分页插件转化分页数据
        return Res.ok().put("page", new PageHelper<RcUser>(pageData));
    }

    /**
     * 获取信息
     **/
    @RequestMapping("/info/{userId}")
    public Res info(@PathVariable String userId) {
        RcUser rcUser = userService.getById(userId);
        return rcUser != null ? Res.ok().put("user", rcUser) : Res.error("指定ID关联信息不存在");
    }

}
