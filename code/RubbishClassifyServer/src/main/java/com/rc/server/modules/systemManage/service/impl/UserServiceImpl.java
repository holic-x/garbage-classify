package com.rc.server.modules.systemManage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rc.server.framework.exception.RcException;
import com.rc.server.framework.utils.CommonUtil;
import com.rc.server.framework.utils.PageUtil;
import com.rc.server.modules.systemManage.mapper.UserMapper;
import com.rc.server.modules.systemManage.model.RcUser;
import com.rc.server.modules.systemManage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 校验用户名有效性
     */
    private boolean validUserName(String userId,String userName){
        // 根据用户ID限定过滤条件（界定新增、修改操作）
        RcUser rcUser = userMapper.getByName(userName);
        if(rcUser==null){
            // 指定用户名没有被占用验证通过
            return true;
        }else{
            // 进一步校验userId(如果ID相同则说明是修改操作，排除自身)
            return rcUser.getUserId().equals(userId)?true:false;
        }
    }

    @Override
    public boolean edit(RcUser rcUser) {
        String userId = rcUser.getUserId();
        Timestamp currentTime = CommonUtil.getCurrentTimestamp();
        // 校验用户名有效性
        if(!validUserName(rcUser.getUserId(),rcUser.getUserName())){
            throw new RcException("用户名称已存在，请重新确认");
        }

        if (StringUtils.isEmpty(userId)) {
            // 执行新增操作
            rcUser.setUserId(CommonUtil.getRandomId());
            rcUser.setRemark(StringUtils.isEmpty(rcUser.getRemark())?"这个人很懒，什么都没有留下！":rcUser.getRemark());
            rcUser.setCreateTime(currentTime);
            rcUser.setModifyTime(currentTime);
            userMapper.insert(rcUser);
        } else {
            // 执行修改操作
            rcUser.setModifyTime(currentTime);
            userMapper.updateById(rcUser);
        }
        return true;
    }

    @Override
    public RcUser getByLoginProp(String userName, String userPwd, String userRole) {
        RcUser rcUser = userMapper.getByLoginProp(userName,userPwd,userRole);
        return rcUser;
    }


    @Override
    public boolean delete(List<String> idList) {
        userMapper.deleteBatchIds(idList);
        return true;
    }

    @Override
    public RcUser getById(String id) {
        return userMapper.getById(id);
    }

    @Override
    public Page<RcUser> getByPage(JSONObject queryCond) {
        Page<RcUser> page = new PageUtil<RcUser>().getPage(queryCond);
        page.setRecords(userMapper.getByPage(page, queryCond));
        return page;
    }

    @Override
    public boolean changeUserStatus(String userId,String userStatus){
        RcUser rcUser = new RcUser();
        rcUser.setUserId(userId);
        rcUser.setUserStatus(userStatus);
        return true;
    }

    @Override
    public boolean updateLoginPwd(String id, String oldPwd, String newPwd) {
        return false;
    }


}
