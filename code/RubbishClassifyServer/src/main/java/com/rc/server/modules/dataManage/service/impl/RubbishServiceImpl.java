package com.rc.server.modules.dataManage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rc.server.framework.utils.CommonUtil;
import com.rc.server.framework.utils.PageUtil;
import com.rc.server.modules.dataManage.mapper.RubbishMapper;
import com.rc.server.modules.dataManage.model.Rubbish;
import com.rc.server.modules.dataManage.service.RubbishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.List;

@Service
public class RubbishServiceImpl implements RubbishService {

    @Autowired
    private RubbishMapper rubbishMapper;

    @Override
    public boolean edit(Rubbish rubbish) {
        String rubbishId = rubbish.getRubbishId();
        Timestamp currentTime = CommonUtil.getCurrentTimestamp();
        rubbish.setModifyTime(currentTime);

        if (StringUtils.isEmpty(rubbishId)) {
            // 执行新增操作
            rubbish.setRubbishId(CommonUtil.getRandomId());
            rubbish.setCreateTime(currentTime);
            rubbishMapper.insert(rubbish);
        } else {
            // 执行修改操作
            rubbishMapper.updateById(rubbish);
        }
        return true;
    }

    @Override
    public boolean delete(List<String> idList) {
        rubbishMapper.deleteBatchIds(idList);
        return true;
    }

    @Override
    public Rubbish getById(String id) {
        return rubbishMapper.getById(id);
    }

    @Override
    public List<Rubbish> getByCond(JSONObject queryCond) {
        List<Rubbish> rubbishList = rubbishMapper.getByCond(queryCond);
        return rubbishList;
    }

    @Override
    public Page<Rubbish> getByPage(JSONObject queryCond) {
        Page<Rubbish> page = new PageUtil<Rubbish>().getPage(queryCond);
        page.setRecords(rubbishMapper.getByPage(page, queryCond));
        return page;
    }
}
