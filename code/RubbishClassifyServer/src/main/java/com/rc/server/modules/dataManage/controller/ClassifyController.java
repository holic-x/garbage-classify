package com.rc.server.modules.dataManage.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rc.server.framework.domain.LabelValueBean;
import com.rc.server.framework.utils.PageHelper;
import com.rc.server.framework.utils.Res;
import com.rc.server.modules.dataManage.model.Classify;
import com.rc.server.modules.dataManage.service.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dataManage/classify")
public class ClassifyController {

    @Autowired
    private ClassifyService classifyService;


    /**
     * 保存信息
     **/
    @RequestMapping(value = {"/save","/update"})
    public Res edit(@RequestBody Classify classify) {
        classifyService.edit(classify);
        return Res.ok();
    }

    /**
     * 删除信息(单条或批量删除)
     **/
    @RequestMapping("/delete")
    public Res delete(@RequestBody JSONObject requestParam) {
        classifyService.delete(requestParam.getObject("classifyIdList", ArrayList.class));
        return Res.ok();
    }

    /**
     * list信息
     **/
    @RequestMapping("/list")
    // @RequestBody HashMap<String, String> map 转化JSONObject.parseObject(JSON.toJSONString(map))
    public Res list(@RequestBody JSONObject queryCond) {
        Page<Classify> pageData = classifyService.getByPage(queryCond);
        // 借助分页插件转化分页数据
        return Res.ok().put("page", new PageHelper<Classify>(pageData));
    }

    /**
     * 获取信息
     **/
    @RequestMapping("/info/{classifyId}")
    public Res info(@PathVariable String classifyId) {
        Classify classify = classifyService.getById(classifyId);
        return classify != null ? Res.ok().put("classify", classify) : Res.error("指定ID关联信息不存在");
    }


    /**
     * 封装标签信息
     **/
    @RequestMapping("/getLabelLsit")
    // @RequestBody HashMap<String, String> map 转化JSONObject.parseObject(JSON.toJSONString(map))
    public Res getLabelLsit(@RequestBody JSONObject queryCond) {
        List<LabelValueBean> classifyList = classifyService.getLabelLsit(queryCond);
        // 借助分页插件转化分页数据
        return Res.ok().put("classifyList",classifyList);
    }


}
