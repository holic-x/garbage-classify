package com.rc.server.framework.utils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.StringUtils;

/**
 * @ClassName PageUtil
 * @Description 分页工具类
 * @Author
 * @Date 2020/5/9 8:42
 * @Version
 **/
public class PageUtil<T> {

    /**
     * @MethodName getPage
     * @Description 自定义获取分页组件
     * @Param [jsonObject]
     * @return com.baomidou.mybatisplus.plugins.Page<T>
     **/
    public Page<T> getPage(JSONObject jsonObject) {
        String pageNumberStr = jsonObject.getString("page");
        String pageSizeStr = jsonObject.getString("limit");
        // 当前页数pageNumber默认1、pageSize默认10
        Integer pageNumber = (StringUtils.isEmpty(pageNumberStr))?1:Integer.valueOf(pageNumberStr);
        Integer pageSize = (StringUtils.isEmpty(pageSizeStr))?10:Integer.valueOf(pageSizeStr);
        Page<T> page = new Page<>(pageNumber,pageSize);
        return page;
    }

}
