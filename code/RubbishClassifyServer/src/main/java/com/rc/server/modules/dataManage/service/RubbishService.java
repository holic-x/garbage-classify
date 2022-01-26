package com.rc.server.modules.dataManage.service;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rc.server.modules.dataManage.model.Rubbish;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2020-08-11
 */
public interface RubbishService {

    /**
     * 添加/修改垃圾信息(根据指定的ID进行判断)
     **/
    public boolean edit(Rubbish rubbish);

    /**
     * 删除垃圾信息
     **/
    public boolean delete(List<String> idList);

    /**
     * 根据垃圾id获取垃圾信息详情(Map<String,Object>)
     **/
     public Rubbish getById(String id);

    /**
     * 根据筛选条件查找垃圾列表
     **/
    public List<Rubbish> getByCond(JSONObject queryCond);

    /**
     * 根据条件分页查找垃圾列表
     **/
    public Page<Rubbish> getByPage(JSONObject queryCond);

}
