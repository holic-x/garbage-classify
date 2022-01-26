package com.rc.server.modules.dataManage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rc.server.framework.domain.LabelValueBean;
import com.rc.server.framework.exception.RcException;
import com.rc.server.framework.utils.CommonUtil;
import com.rc.server.framework.utils.PageUtil;
import com.rc.server.modules.dataManage.mapper.ClassifyMapper;
import com.rc.server.modules.dataManage.model.Classify;
import com.rc.server.modules.dataManage.service.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClassifyServiceImpl implements ClassifyService {

    @Autowired
    private ClassifyMapper classifyMapper;

    /**
     * 校验分类编号或者分类名称有效性
     */
    private boolean validKeyword(String validType,String classifyId,String keyword){
        Classify classify = null;
        // 根据校验类型、数据ID限定过滤条件（界定新增、修改操作）
        if("code".equals(validType)){
            classify = classifyMapper.getByCode(keyword);
        }else if("name".equals(validType)){
            classify = classifyMapper.getByName(keyword);
        }
        if(classify==null){
            // 指定内容没有被占用验证通过
            return true;
        }else{
            // 进一步校验主键(如果ID相同则说明是修改操作，排除自身)
            return classify.getClassifyId().equals(classifyId)?true:false;
        }
    }

    @Override
    public boolean edit(Classify classify) {
        String classifyId = classify.getClassifyId();

        // 校验分类编号和名称
        if(!validKeyword("code",classifyId,classify.getClassifyCode())){
            throw new RcException("指定分类编号不能重复");
        }
        if(!validKeyword("name",classifyId,classify.getClassifyName())){
            throw new RcException("指定分类名称不能重复");
        }
        Timestamp currentTime = CommonUtil.getCurrentTimestamp();
        classify.setModifyTime(currentTime);
        if (StringUtils.isEmpty(classifyId)) {
            // 设置ID
            classify.setClassifyId(CommonUtil.getRandomId());
            // 默认操作权限放行
            classify.setOperPermission("1");
            classify.setCreateTime(currentTime);
            // 执行新增操作
            classifyMapper.insert(classify);
        } else {
            // 执行修改操作
            classifyMapper.updateById(classify);
        }
        return true;
    }

    @Override
    public boolean delete(List<String> idList) {
        classifyMapper.deleteBatchIds(idList);
        return true;
    }

    @Override
    public Classify getById(String id) {
        return classifyMapper.getById(id);
    }

    @Override
    public List<LabelValueBean> getLabelLsit(JSONObject queryCond) {
        List<LabelValueBean> beanList = new ArrayList<>();
        List<Classify> classifyList = classifyMapper.getByCond(queryCond);
        for (Classify classify : classifyList) {
            beanList.add(new LabelValueBean(classify.getClassifyName(),classify.getClassifyId()));
        }
        return beanList;
    }

    @Override
    public Page<Classify> getByPage(JSONObject queryCond) {
        Page<Classify> page = new PageUtil<Classify>().getPage(queryCond);
        page.setRecords(classifyMapper.getByPage(page, queryCond));
        return page;
    }
}
