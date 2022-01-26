package com.rc.server.modules.dataManage.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rc.server.modules.dataManage.model.Classify;
import com.rc.server.modules.dataManage.model.Rubbish;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassifyMapper extends BaseMapper<Classify> {


    /**
     * 根据ID获取详情
     **/
    public Classify getById(@Param(value = "classifyId") String classifyId);

    /**
     * 根据Code获取详情
     **/
    public Classify getByCode(@Param(value = "classifyCode") String classifyCode);

    /**
     * 根据Name获取详情
     **/
    public Classify getByName(@Param(value = "classifyName") String classifyName);


    /**
     * 根据筛选条件获取信息
     **/
    public List<Classify> getByCond(@Param(value = "queryCond")JSONObject jsonObject);


    /**
     * 根据条件分页查找列表
     **/
    public List<Classify> getByPage(Page page, @Param(value = "queryCond") JSONObject jsonObject);


}
