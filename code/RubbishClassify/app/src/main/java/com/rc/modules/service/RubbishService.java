package com.rc.modules.service;

import com.rc.modules.model.Rubbish;

import org.json.JSONObject;

import java.util.List;

/**
 * 垃圾信息相关Service处理
 */
public interface RubbishService {

    // 根据筛选条件获取垃圾信息列表
    public List<Rubbish> getRubbishByCond(JSONObject queryCond) throws Exception;

}
