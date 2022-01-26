package com.rc.server.modules.systemManage.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rc.server.modules.systemManage.model.RcUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<RcUser> {

    /**
     * 根据用户id获取用户信息详情
     **/
    public RcUser getById(@Param(value = "userId") String userId);

    /**
     * 根据用户姓名获取用户信息详情
     **/
    public RcUser getByName(@Param(value = "userName") String userName);


    /**
     * 根据用户登录属性获取用户信息详情
     **/
    public RcUser getByLoginProp(@Param(value = "userName") String userName,@Param(value = "userPwd") String userPwd,@Param(value = "userRole") String userRole);


    /**
     * 根据筛选条件获取用户信息(未删除),如果不指定参数则默认筛选全部
     **/
    public List<RcUser> getByCond(@Param(value = "queryCond") JSONObject jsonObject);

    /**
     * 根据条件分页查找用户列表
     **/
    public List<RcUser> getByPage(Page page, @Param(value = "queryCond") JSONObject jsonObject);

}
