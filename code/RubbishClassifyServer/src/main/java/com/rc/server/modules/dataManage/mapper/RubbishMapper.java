package com.rc.server.modules.dataManage.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rc.server.modules.dataManage.model.Rubbish;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RubbishMapper extends BaseMapper<Rubbish> {


    /**
     * 根据ID获取详情
     **/
    public Rubbish getById(@Param(value = "rubbishId") String rubbishId);

    /**
     * 根据classifyId获取记录列表
     **/
    public  List<Rubbish> getByClassifyId(@Param(value = "classifyId") String classifyId);

    /**
     * 根据筛选条件获取信息
     **/
    public List<Rubbish> getByCond(@Param(value = "queryCond") JSONObject jsonObject);

    /**
     * 根据条件分页查找列表
     **/
    public List<Rubbish> getByPage(Page page, @Param(value = "queryCond") JSONObject jsonObject);

}
