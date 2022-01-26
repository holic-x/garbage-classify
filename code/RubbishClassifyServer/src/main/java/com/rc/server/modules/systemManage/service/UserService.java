package com.rc.server.modules.systemManage.service;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rc.server.modules.systemManage.model.RcUser;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2020-08-11
 */
public interface UserService {

    /**
     * 添加/修改用户信息(根据指定的ID进行判断)
     **/
    public boolean edit(RcUser rcUser);

    /**
     * 根据用户登录属性获取用户信息
     **/
    public RcUser getByLoginProp(String userName,String userPwd,String userRole);

    /**
     * 删除用户信息
     **/
    public boolean delete(List<String> idList);

    /**
     * 根据用户id获取用户信息详情
     **/
     public RcUser getById(String id);

    /**
     * 根据条件分页查找用户列表
     **/
    public Page<RcUser> getByPage(JSONObject queryCond);

    /**
     * 启用/禁用用户账号
     * 通过id、userStatus设置用户账号状态（交互传入json对象）
     **/
    public boolean changeUserStatus(String userId,String userStatus);

    /**
     * 修改指定用户登录密码
     **/
    public boolean updateLoginPwd(String id, String oldPwd, String newPwd);

}
