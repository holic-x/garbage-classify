package com.rc.server.modules.dataManage.service;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rc.server.framework.domain.LabelValueBean;
import com.rc.server.modules.dataManage.model.Classify;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2020-08-11
 */
public interface ClassifyService {

    /**
     * 添加/修改垃圾分类信息(根据指定的ID进行判断)
     **/
    public boolean edit(Classify classify);

    /**
     * 删除垃圾分类信息
     **/
    public boolean delete(List<String> idList);

    /**
     * 根据垃圾分类id获取垃圾分类信息详情(Map<String,Object>)
     **/
     public Classify getById(String id);

    /**
     * 根据筛选条件查找垃圾分类列表封装为标签数据
     **/
    public List<LabelValueBean> getLabelLsit(JSONObject queryCond);

    /**
     * 根据条件分页查找垃圾分类列表
     **/
    public Page<Classify> getByPage(JSONObject queryCond);

}
