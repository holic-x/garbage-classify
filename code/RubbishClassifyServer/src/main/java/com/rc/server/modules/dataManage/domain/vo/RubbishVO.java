package com.rc.server.modules.dataManage.domain.vo;

import com.rc.server.modules.dataManage.model.Rubbish;
import lombok.Data;

@Data
public class RubbishVO extends Rubbish {

    private String classifyCode;

    private String classifyName;
}
